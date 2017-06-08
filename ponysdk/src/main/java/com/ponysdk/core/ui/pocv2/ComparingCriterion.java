
package com.ponysdk.core.ui.pocv2;

import com.ponysdk.core.server.service.query.ComparatorType;

public class ComparingCriterion implements Criterion {

    private String propName;
    private Object value;
    private ComparatorType comparType;

    @Override
    public void setPropertyName(final String propName) {
        this.propName = propName;
    }

    @Override
    public String getPropertyName() {
        return propName;
    }

    public ComparatorType getComparType() {
        return comparType;
    }

    public void setComparType(ComparatorType comparType) {
        this.comparType = comparType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
