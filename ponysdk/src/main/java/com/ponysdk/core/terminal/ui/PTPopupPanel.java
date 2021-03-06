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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.ponysdk.core.model.ClientToServerModel;
import com.ponysdk.core.model.HandlerModel;
import com.ponysdk.core.model.ServerToClientModel;
import com.ponysdk.core.terminal.UIBuilder;
import com.ponysdk.core.terminal.instruction.PTInstruction;
import com.ponysdk.core.terminal.model.BinaryModel;
import com.ponysdk.core.terminal.model.ReaderBuffer;

public class PTPopupPanel extends PTSimplePanel implements MouseDownHandler, MouseUpHandler, MouseMoveHandler {

    protected boolean autoHide;
    protected boolean draggable;

    private boolean dragging;
    private int dragStartX;
    private int dragStartY;

    @Override
    public PopupPanel cast() {
        return (PopupPanel) uiObject;
    }

    @Override
    public void create(final ReaderBuffer buffer, final int objectId, final UIBuilder uiBuilder) {
        final BinaryModel binaryModel = buffer.readBinaryModel();
        if (ServerToClientModel.POPUP_AUTO_HIDE.equals(binaryModel.getModel())) {
            autoHide = binaryModel.getBooleanValue();
        } else {
            autoHide = false;
            buffer.rewind(binaryModel);
        }

        super.create(buffer, objectId, uiBuilder);
    }

    @Override
    protected PopupPanel createUIObject() {
        final PopupPanel popupPanel = new PopupPanel(autoHide) {

            @Override
            protected void onPreviewNativeEvent(final NativePreviewEvent event) {
                PTPopupPanel.this.onPreviewNativeEvent(event);
                super.onPreviewNativeEvent(event);
            }
        };

        popupPanel.addCloseHandler(event -> {
            final PTInstruction instruction = new PTInstruction(getObjectID());
            instruction.put(ClientToServerModel.HANDLER_CLOSE);
            uiBuilder.sendDataToServer(cast(), instruction);
        });

        return popupPanel;
    }

    @Override
    public boolean update(final ReaderBuffer buffer, final BinaryModel binaryModel) {
        final PopupPanel popup = cast();
        final int modelOrdinal = binaryModel.getModel().ordinal();
        if (ServerToClientModel.ANIMATION.ordinal() == modelOrdinal) {
            popup.setAnimationEnabled(binaryModel.getBooleanValue());
            return true;
        } else if (ServerToClientModel.POPUP_CENTER.ordinal() == modelOrdinal) {
            popup.show();
            popup.center();
            return true;
        } else if (ServerToClientModel.POPUP_SHOW.ordinal() == modelOrdinal) {
            popup.show();
            return true;
        } else if (ServerToClientModel.POPUP_POSITION_AND_SHOW.ordinal() == modelOrdinal) {
            popup.setVisible(true);
            return true;
        } else if (ServerToClientModel.POPUP_HIDE.ordinal() == modelOrdinal) {
            popup.hide();
            return true;
        } else if (ServerToClientModel.POPUP_GLASS_ENABLED.ordinal() == modelOrdinal) {
            popup.setGlassEnabled(binaryModel.getBooleanValue());
            return true;
        } else if (ServerToClientModel.POPUP_MODAL.ordinal() == modelOrdinal) {
            popup.setModal(binaryModel.getBooleanValue());
            return true;
        } else if (ServerToClientModel.POPUP_POSITION_LEFT.ordinal() == modelOrdinal) {
            final int left = binaryModel.getIntValue();
            // ServerToClientModel.POPUP_POSITION_TOP
            final int top = buffer.readBinaryModel().getIntValue();
            popup.setPopupPosition(left, top);
            return true;
        } else if (ServerToClientModel.POPUP_DRAGGABLE.ordinal() == modelOrdinal) {
            draggable = binaryModel.getBooleanValue();
            if (draggable) {
                popup.addDomHandler(this, MouseDownEvent.getType());
                popup.addDomHandler(this, MouseUpEvent.getType());
                popup.addDomHandler(this, MouseMoveEvent.getType());
            }
            return true;
        } else {
            return super.update(buffer, binaryModel);
        }
    }

    @Override
    public void onMouseDown(final MouseDownEvent event) {
        if (draggable && DOM.getCaptureElement() == null) {
            dragging = true;
            DOM.setCapture(cast().getElement());
            dragStartX = event.getX();
            dragStartY = event.getY();
        }
    }

    @Override
    public void onMouseMove(final MouseMoveEvent event) {
        if (dragging) {
            final int absX = event.getX() + uiObject.getAbsoluteLeft();
            final int absY = event.getY() + uiObject.getAbsoluteTop();

            if (absX < Document.get().getBodyOffsetLeft() || absX >= Window.getClientWidth()
                    || absY < Document.get().getBodyOffsetTop()) {
                return;
            }

            cast().setPopupPosition(absX - dragStartX, absY - dragStartY);
        }
    }

    @Override
    public void onMouseUp(final MouseUpEvent event) {
        dragging = false;
        DOM.releaseCapture(uiObject.getElement());
    }

    private void onPreviewNativeEvent(final NativePreviewEvent event) {
        // NativeEvent nativeEvent = eventbus.getNativeEvent();
        //
        // if (!eventbus.isCanceled() && (eventbus.getTypeInt() == Event.ONMOUSEDOWN)
        // /* &&
        // isCaptionEvent(nativeEvent) */) {
        // nativeEvent.preventDefault();
        // }
    }

    @Override
    public void addHandler(final ReaderBuffer buffer, final HandlerModel handlerModel) {
        if (HandlerModel.HANDLER_POPUP_POSITION.equals(handlerModel)) {
            final PopupPanel popup = cast();
            popup.setVisible(true);
            popup.show();
            Scheduler.get().scheduleDeferred(() -> {
                final PTInstruction eventInstruction = new PTInstruction(getObjectID());

                final JSONArray widgetInfo = new JSONArray();
                int i = 0;
                widgetInfo.set(i++, new JSONNumber(popup.getOffsetWidth()));
                widgetInfo.set(i++, new JSONNumber(popup.getOffsetHeight()));
                widgetInfo.set(i++, new JSONNumber(Window.getClientWidth()));
                widgetInfo.set(i++, new JSONNumber(Window.getClientHeight()));

                eventInstruction.put(ClientToServerModel.POPUP_POSITION, widgetInfo);

                uiBuilder.sendDataToServer(cast(), eventInstruction);
            });
        } else {
            super.addHandler(buffer, handlerModel);
        }
    }
}
