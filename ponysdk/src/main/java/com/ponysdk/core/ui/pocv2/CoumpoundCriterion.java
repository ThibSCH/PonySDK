/*
 * Copyright (c) 2017 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.core.ui.pocv2;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author tschoebel
 *
 */
public class CoumpoundCriterion extends ComparingCriterion {

    public enum LogicalOperator {
        OR,
        AND;

        private LogicalOperator() {
        }
    }

    private final Collection<ComparingCriterion> criteria;
    private final LogicalOperator operator;

    public CoumpoundCriterion(final LogicalOperator operator,final Collection<ComparingCriterion> criteria) {
        this.criteria = criteria;
        this.operator = operator;
    }

    public CoumpoundCriterion(final LogicalOperator operator, final ComparingCriterion... criteria) {
        this.criteria = Arrays.asList(criteria);
        this.operator = operator;
    }

    public Collection<ComparingCriterion> getCriteria() {
        return criteria;
    }

    public LogicalOperator getOperator() {
        return operator;
    }
}
