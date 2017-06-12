
package com.ponysdk.core.ui.pocv2;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private final List<SortingCriterion> sortCriteria = new ArrayList<>();
    private ComparingCriterion compareCriterion;
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

    public ComparingCriterion getCompareCriterion() {
        return compareCriterion;
    }

    public List<SortingCriterion> getSortCriteria() {
        return sortCriteria;
    }

    public void addSortingCriterion(final SortingCriterion crit) {
        sortCriteria.add(crit);
    }

    public void setComparingCriterion(final ComparingCriterion crit) {
        compareCriterion = crit;
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }
}
