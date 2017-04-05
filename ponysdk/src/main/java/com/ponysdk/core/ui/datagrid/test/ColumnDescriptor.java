
package com.ponysdk.core.ui.datagrid.test;

import java.util.Comparator;

import com.ponysdk.core.server.service.query.SortingType;

public class ColumnDescriptor<DataType, RenderedType> {

    private HeaderRenderer headerRenderer;
    private CellRenderer<DataType, RenderedType> cellRenderer;
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

    CellRenderer<DataType, RenderedType> getCellRenderer() {
        return cellRenderer;
    }

    public void setCellRenderer(final CellRenderer<DataType, RenderedType> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public Comparator<DataType> getComparator() {
        return comparator;
    }

    public void setComparator(final Comparator<DataType> comparator) {
        this.comparator = comparator;
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public void setSortingType(final SortingType sortingType) {
        this.sortingType = sortingType;
    }

}
