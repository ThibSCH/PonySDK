
package com.ponysdk.core.ui.datagrid.test;

public interface ColumnSortListener<DataType> {

    void onColumnSorted(ColumnDescriptor<DataType, ?> column);

}
