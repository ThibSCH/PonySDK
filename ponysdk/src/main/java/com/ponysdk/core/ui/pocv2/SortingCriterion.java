
package com.ponysdk.core.ui.pocv2;

import com.ponysdk.core.server.service.query.SortingType;

public class SortingCriterion implements Criterion {

    private String propName;

    private SortingType sortType;

    public SortingCriterion(final String propName, final SortingType sortType) {
        this.propName = propName;
        this.sortType = sortType;
    }

    @Override
    public void setPropertyName(final String propName) {
        this.propName = propName;
    }

    @Override
    public String getPropertyName() {
        return propName;
    }

    public SortingType getSortType() {
        return sortType;
    }

    public void setSortType(final SortingType sortType) {
        this.sortType = sortType;
    }

}
