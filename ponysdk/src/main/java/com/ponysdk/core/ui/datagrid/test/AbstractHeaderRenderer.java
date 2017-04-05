
package com.ponysdk.core.ui.datagrid.test;

import com.ponysdk.core.ui.basic.Element;
import com.ponysdk.core.ui.basic.PFlowPanel;
import com.ponysdk.core.ui.basic.PPanel;
import com.ponysdk.core.ui.basic.PWidget;

public abstract class AbstractHeaderRenderer implements HeaderRenderer {

    @Override
    public PPanel render() {
        final PFlowPanel panel = Element.newPFlowPanel();
        panel.add(render0());
        return panel;
    }

    protected abstract PWidget render0();

}
