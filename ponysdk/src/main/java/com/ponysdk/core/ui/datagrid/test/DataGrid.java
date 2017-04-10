
package com.ponysdk.core.ui.datagrid.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.ponysdk.core.ui.basic.IsPWidget;
import com.ponysdk.core.ui.basic.PWidget;
import com.ponysdk.core.ui.datagrid.View;
import com.ponysdk.core.ui.datagrid.impl.DefaultView;

public class DataGrid<DataType> implements IsPWidget, ColumnSortListener<DataType> {

    private final Function<DataType, ?> keyProvider = Function.identity();

    private final MapAndList<DataType> rows = new MapAndList<>(keyProvider);
    private final MapAndList<ColumnDescriptor<DataType, ?>> columns = new MapAndList<>(col -> col.getIdentifier());

    private final List<ColumnDescriptor<DataType, ?>> sortedColumns = new ArrayList<>();

    private final View view = new DefaultView();

    public void addColumn(final ColumnDescriptor<DataType, ?> newColumn) {
        if (!columns.contains(newColumn)) {
            columns.set(newColumn);

            final int index = columns.size() - 1;
            drawHeader(index, newColumn);
            drawColumn(index, newColumn);
        }
    }

    public void removeColumn(final ColumnDescriptor<DataType, ?> column) {
        final int index = columns.remove(column);
        if (index != -1) {
            drawColFrom(index);
            resetCol(columns.size());
        }
    }

    public void setData(final DataType data) {
        if (!rows.contains(data)) {
            rows.set(data);
        }

        drawRowFrom(data);
    }

    public void removeData(final DataType data) {
        final int index = rows.remove(data);
        if (index != -1) {
            drawRowFrom(index);
            resetRow(rows.size());
        }
    }

    private void drawHeader(final int c, final ColumnDescriptor<DataType, ?> column) {
        view.setHeader(c, column.getHeaderRenderer().render());
    }

    private void drawColumn(final int c, final ColumnDescriptor<DataType, ?> column) {
        int r = 0;
        for (final DataType data : rows) {
            drawCell(r++, c, column, data);
        }
    }

    private void drawRow(final int r, final DataType data) {
        int c = 0;
        for (final ColumnDescriptor<DataType, ?> column : columns) {
            drawCell(r, c++, column, data);
        }
    }

    private void resetCol(final int c) {
        view.getHeader(c).removeFromParent();
        for (int r = 0; r < rows.size(); r++) {
            view.getCell(r, c).removeFromParent();
        }
    }

    private void resetRow(final int r) {
        int c = 0;
        for (final ColumnDescriptor<DataType, ?> col : columns) {
            col.getCellRenderer().reset(view.getCell(r, c++));
        }
    }

    private void drawRowFrom(final int index) {
        for (int i = index; i <= rows.size() - 1; i++) {
            drawRow(i, rows.get(i));
        }
    }

    private void drawColFrom(final int index) {
        for (int i = index; i <= columns.size() - 1; i++) {
            final ColumnDescriptor<DataType, ?> column = columns.get(i);
            drawHeader(i, column);
            drawColumn(i, column);
        }
    }

    private void drawRowFrom(final DataType data) {
        final int index = rows.indexOf(data);
        drawRowFrom(index);
    }

    private void drawCell(final int r, final int c, final ColumnDescriptor<DataType, ?> column, final DataType data) {
        PWidget w = view.getCell(r, c);

        if (w == null) {
            w = column.getCellRenderer().render(data);
            view.setCell(r, c, w);
        } else {
            w = column.getCellRenderer().update(data, w);
        }
    }

    @Override
    public PWidget asWidget() {
        return view.asWidget();
    }

    @Override
    public void onColumnSorted(final ColumnDescriptor<DataType, ?> column) {
        sortedColumns.remove(column);
        if (column.isSorted()) {
            sortedColumns.add(column);
        }
        sortByColumns();
    }

    private void sortByColumns() {
        if (sortedColumns.isEmpty()) return;

        Comparator<DataType> combinedComparator = sortedColumns.get(0).getComparator();
        for (int i = 1; i < sortedColumns.size(); i++) {
            final Comparator<DataType> additionalComparator = sortedColumns.get(i).getComparator();
            combinedComparator = combinedComparator.thenComparing(additionalComparator);
        }
        rows.sort(combinedComparator);
        drawRowFrom(0);
    }

    public void clearColumnsUsedToSort() {
        sortedColumns.clear();
    }

}
