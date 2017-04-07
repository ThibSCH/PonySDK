
package com.ponysdk.core.ui.datagrid.test;

import java.util.Comparator;
import java.util.function.Function;

import com.ponysdk.core.server.service.query.SortingType;

public class ColumnDescriptor<DataType, RenderedType> {

    private HeaderRenderer headerRenderer;
    private CellRenderer<DataType, RenderedType> cellRenderer;
    private Function<DataType, RenderedType> extractor;
    private String identifier;

    private Comparator<DataType> comparator;
    private SortingType sortingType = SortingType.NONE;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    HeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    public void setHeaderRenderer(final HeaderRenderer headerCellRender) {
        this.headerRenderer = headerCellRender;
    }

    public CellRenderer<DataType, RenderedType> getCellRenderer() {
        return cellRenderer;
    }

    public void setCellRenderer(final CellRenderer<DataType, RenderedType> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public Comparator<DataType> getComparator() {
        if (comparator == null) return null;

        switch (sortingType) {
            case ASCENDING:
                return comparator;
            case DESCENDING:
                return comparator.reversed();
            case NONE:
            default:
                return null;
        }
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public boolean isSorted() {
        return sortingType != SortingType.NONE;
    }

    public void setComparator(final Comparator<RenderedType> propertyComparator) {
        final Comparator<DataType> comp = (arg0, arg1) -> {
            //Use the provided comparator on datas as they are rendered in this column
            final RenderedType value0 = getRenderedValue(arg0);
            final RenderedType value1 = getRenderedValue(arg1);
            return propertyComparator.compare(value0, value1);
        };
        this.comparator = comp;
    }

    public void setSortingType(final SortingType sortingType) {
        this.sortingType = sortingType;
    }

    public void setExtractor(final Function<DataType, RenderedType> extractor) {
        this.extractor = extractor;
    }

    public RenderedType getRenderedValue(final DataType data) {
        return extractor.apply(data);
    }

}
