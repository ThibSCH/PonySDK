
package com.ponysdk.core.ui.datagrid.test;

import java.util.Objects;

public class DataRow<DataType> {

    private final DataType data;
    private final Object key;

    public DataRow(final Object key, final DataType data) {
        this.key = key;
        this.data = data;
    }

    public DataType getData() {
        return data;
    }

    public Object getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;

        if (obj instanceof DataRow) {
            final DataRow<?> otherRow = (DataRow<?>) obj;
            return Objects.equals(key, otherRow.getKey());
        }
        return false;
    }
}
