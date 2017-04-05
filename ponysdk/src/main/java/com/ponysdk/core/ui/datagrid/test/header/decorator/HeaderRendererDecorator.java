
package com.ponysdk.core.ui.datagrid.test.header.decorator;

import com.ponysdk.core.ui.basic.PPanel;
import com.ponysdk.core.ui.datagrid.test.HeaderRenderer;

public interface HeaderRendererDecorator extends HeaderRenderer {

    abstract void decorate(PPanel panel);
}