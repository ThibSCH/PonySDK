/*
 * Copyright (c) 2011 PonySDK
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

package com.ponysdk.sample.client;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import com.ponysdk.core.server.application.UIContext;
import com.ponysdk.core.server.concurrent.PScheduler;
import com.ponysdk.core.ui.basic.Element;
import com.ponysdk.core.ui.basic.PButton;
import com.ponysdk.core.ui.basic.PLabel;
import com.ponysdk.core.ui.basic.PPanel;
import com.ponysdk.core.ui.basic.PWindow;
import com.ponysdk.core.ui.datagrid.test.CellRenderer;
import com.ponysdk.core.ui.datagrid.test.ColumnDescriptor;
import com.ponysdk.core.ui.datagrid.test.DataGrid;
import com.ponysdk.core.ui.datagrid.test.DefaultCellRenderer;
import com.ponysdk.core.ui.datagrid.test.HeaderRenderer;
import com.ponysdk.core.ui.datagrid.test.header.decorator.SortableDecorator;
import com.ponysdk.core.ui.main.EntryPoint;
import com.ponysdk.sample.client.event.UserLoggedOutEvent;
import com.ponysdk.sample.client.event.UserLoggedOutHandler;

public class UISampleEntryPoint implements EntryPoint, UserLoggedOutHandler {

    private int rowToRemove = 2;

    @Override
    public void start(final UIContext uiContext) {
        testGrid();
    }

    private HeaderRenderer newHeaderRenderer(final ColumnDescriptor<?, ?> col, final boolean sortable) {
        final HeaderRenderer headerRenderer = () -> {
            final PLabel caption = Element.newPLabel(col.getIdentifier());
            caption.setStyleProperty("font-weight", "bold");

            final PPanel panel = Element.newPFlowPanel();
            panel.add(caption);
            return panel;
        };

        if (sortable) {
            return new SortableDecorator(headerRenderer, col);
        } else return headerRenderer;
    }

    private void addColumn(final DataGrid<Integer> grid, final int i) {
        final ColumnDescriptor<Integer, String> column = new ColumnDescriptor<>();
        final String colName = "Header n�" + i;
        final CellRenderer<Integer, String> cellRenderer = new DefaultCellRenderer<>();
        final HeaderRenderer headerRenderer = newHeaderRenderer(column, i % 2 == 0);

        column.setHeaderRenderer(headerRenderer);
        column.setCellRenderer(cellRenderer);
        column.setIdentifier(colName);

        grid.addColumn(column);
    }

    private void testGrid() {
        final DataGrid<Integer> grid = new DataGrid<>();
        final AtomicInteger i = new AtomicInteger();

        for (int cpt = 0; cpt < 10; cpt++) {
            addColumn(grid, i.incrementAndGet());
        }

        final Duration period = Duration.ofMillis(10);
        PScheduler.scheduleAtFixedRate(() -> {
            grid.setData((int) (Math.random() * 50));
            grid.removeData((int) (Math.random() * 50));
        }, period);

        //        for (int t = 1; t <= 40; t++) {
        //            grid.setData(t);
        //        }

        final PButton addColBtn = Element.newPButton("add column");
        addColBtn.addClickHandler(e -> addColumn(grid, i.incrementAndGet()));

        final PButton addRowBtn = Element.newPButton("add row");
        addRowBtn.addClickHandler(e -> {
            final int rand = (int) (Math.random() * 100);
            grid.setData(rand);
        });

        final PButton deleteRow = Element.newPButton("delete row");
        deleteRow.addClickHandler(e -> {
            grid.removeData(rowToRemove);
            rowToRemove++;
        });

        PWindow.getMain().add(addColBtn);
        PWindow.getMain().add(addRowBtn);
        PWindow.getMain().add(deleteRow);
        PWindow.getMain().add(grid);
    }

    @Override
    public void onUserLoggedOut(final UserLoggedOutEvent event) {
        // TODO Auto-generated method stub

    }
}
