
package com.ponysdk.core.ui.datagrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import com.ponysdk.core.ui.basic.IsPWidget;
import com.ponysdk.core.ui.basic.PWidget;

public class DataGrid<DataType> implements IsPWidget {

    private final View view;
    private final List<ColumnDescriptor<DataType>> columns = new ArrayList<>();
    private final Function<DataType, ?> keyProvider;

    private TreeSet<Decorator<DataType>> rows;

    public DataGrid(final View view, final Function<DataType, ?> keyProvider, final Comparator<DataType> comparator) {
        this.view = view;
        this.keyProvider = keyProvider;
        this.rows = new TreeSet<>((o1, o2) -> comparator.compare(o1.data, o2.data));
    }

    @Override
    public PWidget asWidget() {
        return view.asWidget();
    }

    public void addColumnDescriptor(final ColumnDescriptor<DataType> column) {
        if (columns.add(column)) {
            int r = 0;
            final int c = columns.size() - 1;

            drawHeader(c, column);

            for (final Decorator<DataType> w : rows) {
                drawCell(r++, c, column, w.data);
            }
        }
    }

    public void setData(final DataType data) {
        final Decorator<DataType> d = new Decorator<>(keyProvider.apply(data), data);

        if (rows.contains(d)) {
            final int indexBefore = rows.headSet(d).size();
            if (indexBefore != -1) {
                rows.remove(d);
                rows.add(d);
                final int indexAfter = rows.headSet(d).size();
                if (indexBefore == indexAfter) {
                    update(indexAfter, d);
                } else {
                    draw(Math.min(indexBefore, indexAfter), d);
                }
            }
        } else {
            rows.add(d);
            draw(rows.headSet(d).size(), d);
        }
    }

    public void removeColumn(final ColumnDescriptor<DataType> column) {
        if (columns.isEmpty()) return;

        final int c = columns.indexOf(column);

        if (c != -1) {
            int r = 0;

            final int size = columns.size() - 1;

            columns.remove(c);

            for (int i = c; i < columns.size(); i++) {
                final ColumnDescriptor<DataType> currentColumn = columns.get(i);
                drawHeader(i, currentColumn);
                for (final Decorator<DataType> d : rows) {
                    drawCell(r++, i, currentColumn, d.data);
                }
                r = 0;
            }

            resetColumn(size, column);
        }
    }

    public void removeData(final DataType data) {
        final Decorator<DataType> d = new Decorator<>(keyProvider.apply(data), data);
        final Decorator<DataType> higher = rows.higher(d);
        final int index = rows.headSet(d).size();
        if (rows.remove(d)) {
            draw(index, higher);
            resetRow(rows.size());
        }
    }

    public List<ColumnDescriptor<DataType>> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    private void drawHeader(final int c, final ColumnDescriptor<DataType> column) {
        view.setHeader(c, column.getHeaderRenderer().render());
    }

    private void update(final int r, final Decorator<DataType> d) {
        int c = 0;
        for (final ColumnDescriptor<DataType> column : columns) {
            drawCell(r, c++, column, d.data);
        }
    }

    private void draw(final int fromRow, final Decorator<DataType> from) {
        if (from == null) return;
        final SortedSet<Decorator<DataType>> tail = rows.tailSet(from);
        int r = fromRow;
        int c = 0;

        for (final Decorator<DataType> w : tail) {
            for (final ColumnDescriptor<DataType> column : columns) {
                drawCell(r, c++, column, w.data);
            }
            c = 0;
            r++;
        }
    }

    private void drawCell(final int r, final int c, final ColumnDescriptor<DataType> column, final DataType data) {
        PWidget w = view.getCell(r, c);

        if (w == null) {
            w = column.getCellRenderer().render(data);
            view.setCell(r, c, w);
        } else {
            w = column.getCellRenderer().update(data, w);
        }

    }

    private void resetColumn(final Integer c, final ColumnDescriptor<DataType> column) {
        final PWidget header = view.getHeader(c);
        if (header != null) header.removeFromParent();

        for (int r = 0; r < view.getRowCount(); r++) {
            column.getCellRenderer().reset(view.getCell(r, c));
        }
    }

    private void resetRow(final Integer r) {
        int c = 0;
        for (final ColumnDescriptor<DataType> column : columns) {
            column.getCellRenderer().reset(view.getCell(r, c++));
        }
    }

    public void setSorting(final Comparator<DataType> sortComparator) {
        final TreeSet<Decorator<DataType>> newTreeSet = new TreeSet<>((o1, o2) -> sortComparator.compare(o1.data, o2.data));
        newTreeSet.addAll(rows);
        rows = newTreeSet;

        draw(0, rows.first()); //refresh grid
    }

}
