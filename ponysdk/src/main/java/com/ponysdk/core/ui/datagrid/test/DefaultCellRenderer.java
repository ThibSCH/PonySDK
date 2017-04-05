
package com.ponysdk.core.ui.datagrid.test;

import java.util.function.Function;

import com.ponysdk.core.ui.basic.Element;
import com.ponysdk.core.ui.basic.PLabel;
import com.ponysdk.core.ui.basic.PWidget;

public class DefaultCellRenderer<DataType> implements CellRenderer<DataType, String> {

    private final Function<DataType, String> transform = String::valueOf;
    private String rendered;

    @Override
    public PWidget render(final DataType data) {
        rendered = transform.apply(data);
        return Element.newPLabel(rendered);
    }

    @Override
    public PWidget update(final DataType data, final PWidget widget) {
        rendered = transform.apply(data);
        ((PLabel) widget).setText(rendered);
        return widget;
    }

    @Override
    public void reset(final PWidget widget) {
        ((PLabel) widget).setText("");
    }

    @Override
    public String getRenderedValue() {
        return rendered;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

}
