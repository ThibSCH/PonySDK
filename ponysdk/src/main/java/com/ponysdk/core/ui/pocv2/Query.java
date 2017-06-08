
package com.ponysdk.core.ui.pocv2;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private final List<SortingCriterion> sortCriteria = new ArrayList<>();
    private final List<ComparingCriterion> compareCriteria = new ArrayList<>();
    private int pageSize = Integer.MAX_VALUE;
    private int pageNum = 0;

    private final QueryMode queryMode = QueryMode.FULL_RESULT;

    public enum QueryMode {
        PAGINATION,
        FULL_RESULT
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ComparingCriterion> getCompareCriteria() {
        return compareCriteria;
    }

    public List<SortingCriterion> getSortCriteria() {
        return sortCriteria;
    }

    public void addSortingCriterion(final SortingCriterion crit) {
        sortCriteria.add(crit);
    }

    public void addComparingCriterion(final ComparingCriterion crit) {
        compareCriteria.add(crit);
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }
}
