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

package com.ponysdk.core.terminal.ui;

import com.google.gwt.user.client.ui.MenuBar;
import com.ponysdk.core.model.ServerToClientModel;
import com.ponysdk.core.terminal.UIBuilder;
import com.ponysdk.core.terminal.model.BinaryModel;
import com.ponysdk.core.terminal.model.ReaderBuffer;

public class PTMenuBar extends PTWidget<MenuBar> {

    private static final String MENUBAR_STYLE = "pony-MenuBar";

    private boolean isVertical;

    @Override
    public void create(final ReaderBuffer buffer, final int objectId, final UIBuilder uiService) {
        isVertical = buffer.readBinaryModel().getBooleanValue();

        super.create(buffer, objectId, uiService);
    }

    @Override
    protected MenuBar createUIObject() {
        final MenuBar menuBar = new MenuBar(isVertical);
        menuBar.addStyleName(MENUBAR_STYLE);
        return menuBar;
    }

    @Override
    public void add(final ReaderBuffer buffer, final PTObject ptObject) {
        final MenuBar menuBar = cast();

        if (ptObject instanceof PTMenuItem) {
            final PTMenuItem menuItem = (PTMenuItem) ptObject;
            final BinaryModel binaryModel = buffer.readBinaryModel();
            if (ServerToClientModel.BEFORE_INDEX.equals(binaryModel.getModel())) {
                menuBar.insertItem(menuItem.cast(), binaryModel.getIntValue());
            } else {
                buffer.rewind(binaryModel);
                menuBar.addItem(menuItem.cast());
            }
        } else {
            final PTMenuItemSeparator menuItem = (PTMenuItemSeparator) ptObject;
            final BinaryModel binaryModel = buffer.readBinaryModel();
            if (ServerToClientModel.BEFORE_INDEX.equals(binaryModel.getModel())) {
                menuBar.insertSeparator(menuItem.cast(), binaryModel.getIntValue());
            } else {
                buffer.rewind(binaryModel);
                menuBar.addSeparator(menuItem.cast());
            }
        }
    }

    @Override
    public void remove(final ReaderBuffer buffer, final PTObject ptObject) {
        if (ptObject instanceof PTMenuItem) {
            final PTMenuItem menuItem = (PTMenuItem) ptObject;
            uiObject.removeItem(menuItem.cast());
        } else {
            final PTMenuItemSeparator menuItem = (PTMenuItemSeparator) ptObject;
            uiObject.removeSeparator(menuItem.cast());
        }
    }

    @Override
    public boolean update(final ReaderBuffer buffer, final BinaryModel binaryModel) {
        final int modelOrdinal = binaryModel.getModel().ordinal();
        if (ServerToClientModel.CLEAR.ordinal() == modelOrdinal) {
            uiObject.clearItems();
            return true;
        } else if (ServerToClientModel.ANIMATION.ordinal() == modelOrdinal) {
            uiObject.setAnimationEnabled(binaryModel.getBooleanValue());
            return true;
        } else {
            return super.update(buffer, binaryModel);
        }
    }
}
