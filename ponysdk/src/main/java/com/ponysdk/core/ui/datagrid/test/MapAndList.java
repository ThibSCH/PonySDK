
package com.ponysdk.core.ui.datagrid.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MapAndList<DataType> implements Iterable<DataType> {

    private final Function<DataType, ?> keyProvider;
    private final Map<Object, DataType> map = new HashMap<>();
    private final List<DataType> list = new ArrayList<>();

    protected MapAndList(final Function<DataType, ?> keyProvider) {
        this.keyProvider = keyProvider;
    }

    public DataType get(final Object key) {
        return map.get(key);
    }

    public DataType get(final int index) {
        return list.get(index);
    }

    public int indexOf(final DataType data) {
        return list.indexOf(data);
    }

    public void set(final DataType data) {
        final Object key = keyProvider.apply(data);

        if (map.containsKey(key)) {
            map.put(key, data);
        } else {
            map.put(key, data);
            list.add(data);
        }
    }

    public int remove(final DataType data) {
        final Object key = keyProvider.apply(data);

        if (map.containsKey(key)) {
            final int index = list.indexOf(data);
            list.remove(index);
            map.remove(key);
            return index;
        } else return -1;
    }

    public boolean contains(final DataType data) {
        final Object key = keyProvider.apply(data);
        return map.containsKey(key);
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<DataType> iterator() {
        return list.iterator();
    }

    public void sort(final Comparator<DataType> comparator) {
        Collections.sort(list, comparator);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(list.toString());
        sb.append(" // ");
        sb.append(map.toString());
        return sb.toString();
    }

}
