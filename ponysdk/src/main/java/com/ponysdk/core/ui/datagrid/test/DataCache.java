
package com.ponysdk.core.ui.datagrid.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;

public class DataCache<DataType> {

    private Function<DataType, ?> keyProvider;
    private List<DataRow<DataType>> list;
    private TreeSet<DataRow<DataType>> tree;

    private boolean sorted;

    public DataCache() {
        this(Function.identity());
    }

    public DataCache(final Function<DataType, ?> keyProvider) {
        this.keyProvider = keyProvider;
        this.list = new ArrayList<>();
    }

    public void setData(final DataType data) {
        if (contains(data)) {
            update(data);
        } else add(data);
    }

    public boolean contains(final DataType data) {
        final Collection<DataRow<DataType>> col = isSorted() ? tree : list;
        final DataRow<DataType> row = new DataRow<>(keyProvider.apply(data), data);
        return col.contains(row);
    }

    private void update(final DataType data) {
        final DataRow<DataType> row = new DataRow<>(keyProvider.apply(data), data);

        if (isSorted()) {
            tree.remove(row);
            tree.add(row);
        } else {
            list.set(indexOf(data), row);
        }
    }

    public int indexOf(final DataType data) {
        final DataRow<DataType> row = new DataRow<>(keyProvider.apply(data), data);
        if (isSorted()) {
            if (contains(data)) return tree.headSet(row).size();
            else return -1;
        } else return list.indexOf(row);
    }

    private void add(final DataType data) {
        final Collection<DataRow<DataType>> col = isSorted() ? tree : list;
        final DataRow<DataType> row = new DataRow<>(keyProvider.apply(data), data);
        col.add(row);
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setComparator(final Comparator<DataType> newComparator) {
        if (isSorted()) {
            //Switching from tree to list
            if (newComparator == null) {
                list.clear();
                list.addAll(tree);
                sorted = false;
            } else { //Switching from a tree to another
                final TreeSet<DataRow<DataType>> newTree = new TreeSet<>(
                    (arg0, arg1) -> newComparator.compare(arg0.getData(), arg1.getData()));
                newTree.addAll(tree);
                tree = newTree;
            }
        } else {
            if (newComparator != null) {
                //Switching from a list to a tree
                final TreeSet<DataRow<DataType>> newTree = new TreeSet<>(
                    (arg0, arg1) -> newComparator.compare(arg0.getData(), arg1.getData()));
                newTree.addAll(list);
                tree = newTree;
                sorted = true;
            }
        }

    }

    public void debugPrint() {
        final Collection<DataRow<DataType>> col = isSorted() ? tree : list;
        col.forEach(row -> System.err.println(row.getData()));
        System.err.println("----");
    }

    public static void main(final String[] args) {
        final DataCache<String> cache = new DataCache<>(s -> s.length());

        cache.setData("fff");
        cache.setData("b");
        cache.setData("cc");
        cache.debugPrint();

        System.err.println("Tri par length");
        cache.setComparator((arg0, arg1) -> Integer.compare(arg0.length(), arg1.length()));
        cache.debugPrint();

        System.err.println("Tri par ordre alpha");
        cache.setComparator(String::compareTo);
        cache.debugPrint();

        System.err.println("Sans comparator");
        cache.setComparator(null);
        cache.debugPrint();

        cache.setData("aaaa");
        cache.debugPrint();

        System.err.println("Tri par ordre alpha");
        cache.setComparator(String::compareTo);
        cache.debugPrint();

    }
}
