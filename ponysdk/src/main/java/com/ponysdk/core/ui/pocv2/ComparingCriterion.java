
package com.ponysdk.core.ui.pocv2;

import java.util.Arrays;
import java.util.Collection;

import com.ponysdk.core.server.service.query.ComparatorType;
import com.ponysdk.core.ui.pocv2.CoumpoundCriterion.LogicalOperator;

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

    public void setComparType(final ComparatorType comparType) {
        this.comparType = comparType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public CoumpoundCriterion or(final ComparingCriterion otherCrit) {
        final Collection<ComparingCriterion> criteria = Arrays.asList(this, otherCrit);
        return new CoumpoundCriterion(LogicalOperator.OR, criteria);
    }

    public CoumpoundCriterion and(final ComparingCriterion otherCrit) {
        final Collection<ComparingCriterion> criteria = Arrays.asList(this, otherCrit);
        return new CoumpoundCriterion(LogicalOperator.AND, criteria);
    }
}
