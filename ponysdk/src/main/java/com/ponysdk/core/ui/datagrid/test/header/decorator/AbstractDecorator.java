
package com.ponysdk.core.ui.datagrid.test.header.decorator;

import com.ponysdk.core.ui.basic.PPanel;
import com.ponysdk.core.ui.datagrid.test.ColumnDescriptor;
import com.ponysdk.core.ui.datagrid.test.HeaderRenderer;

public abstract class AbstractDecorator<DataType> implements HeaderRendererDecorator {

    private final HeaderRenderer decoratedRenderer;
    protected final ColumnDescriptor<DataType, ?> column;

    public AbstractDecorator(final HeaderRenderer decoratedRenderer, final ColumnDescriptor<DataType, ?> column) {
        this.decoratedRenderer = decoratedRenderer;
        this.column = column;
    }

    @Override
    public PPanel render() {
        final PPanel rendered = decoratedRenderer.render();
        decorate(rendered);
        return rendered;
    }

}
