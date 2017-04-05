
package com.ponysdk.core.ui.datagrid.test.header.decorator;

import com.ponysdk.core.server.service.query.SortingType;
import com.ponysdk.core.ui.basic.PPanel;
import com.ponysdk.core.ui.basic.event.PClickEvent;
import com.ponysdk.core.ui.basic.event.PClickHandler;
import com.ponysdk.core.ui.datagrid.test.ColumnDescriptor;
import com.ponysdk.core.ui.datagrid.test.HeaderRenderer;
import com.ponysdk.core.ui.list.renderer.header.HeaderSortingHelper;

public class SortableDecorator extends AbstractDecorator {

    public SortableDecorator(final HeaderRenderer decoratedRenderer, final ColumnDescriptor<?, ?> column) {
        super(decoratedRenderer, column);
    }

    @Override
    public void decorate(final PPanel panel) {
        final PClickHandler handler = event -> {
            final SortingType currentSortingType = column.getSortingType();
            final SortingType newSortingType = HeaderSortingHelper.getNextSortingType(currentSortingType);

            panel.removeStyleName(HeaderSortingHelper.getAssociatedStyleName(currentSortingType));
            panel.addStyleName(HeaderSortingHelper.getAssociatedStyleName(newSortingType));

            column.setSortingType(newSortingType);

        };
        panel.addDomHandler(handler, PClickEvent.TYPE);
    }

}
