
package com.ponysdk.core.ui.datagrid.test;

import java.util.function.Function;

import com.ponysdk.core.ui.basic.Element;
import com.ponysdk.core.ui.basic.PLabel;
import com.ponysdk.core.ui.basic.PWidget;

public class DefaultCellRenderer<DataType, RenderedType> implements CellRenderer<DataType, RenderedType> {

    /** The function used to extract the rendered value from the original data **/
    private final Function<DataType, RenderedType> transform;
    /** The function used to render graphically the extracted data **/
    private final Function<RenderedType, String> toTextTrasnform;

    public DefaultCellRenderer(final Function<DataType, RenderedType> extract, final Function<RenderedType, String> toText) {
        this.transform = extract;
        this.toTextTrasnform = toText;
    }

    @Override
    public PWidget render(final DataType data) {
        final String rendered = toTextTrasnform.apply(getRenderedValue(data));
        return Element.newPLabel(rendered);
    }

    @Override
    public PWidget update(final DataType data, final PWidget widget) {
        final String rendered = toTextTrasnform.apply(getRenderedValue(data));
        ((PLabel) widget).setText(rendered);
        return widget;
    }

    @Override
    public void reset(final PWidget widget) {
        ((PLabel) widget).setText("");
    }

    @Override
    public RenderedType getRenderedValue(final DataType data) {
        return transform.apply(data);
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

}
