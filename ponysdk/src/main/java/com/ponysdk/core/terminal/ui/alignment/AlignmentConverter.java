
package com.ponysdk.core.terminal.ui.alignment;

import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.ponysdk.core.model.PAlignment;
import com.ponysdk.core.model.PHorizontalAlignment;
import com.ponysdk.core.model.PVerticalAlignment;

public class AlignmentConverter {

    public static HorizontalAlignmentConstant asHorizontalAlignmentConstant(final byte byteValue) {
        final PHorizontalAlignment alignment = PHorizontalAlignment.fromByte(byteValue);
        switch (alignment) {
            case ALIGN_LEFT:
                return HasHorizontalAlignment.ALIGN_LEFT;
            case ALIGN_CENTER:
                return HasHorizontalAlignment.ALIGN_CENTER;
            case ALIGN_RIGHT:
                return HasHorizontalAlignment.ALIGN_RIGHT;
            default:
                throw new IllegalArgumentException("Undefined alignement");
        }
    }

    public static VerticalAlignmentConstant asVerticalAlignmentConstant(final byte byteValue) {
        final PVerticalAlignment alignment = PVerticalAlignment.fromByte(byteValue);
        switch (alignment) {
            case ALIGN_TOP:
                return HasVerticalAlignment.ALIGN_TOP;
            case ALIGN_MIDDLE:
                return HasVerticalAlignment.ALIGN_MIDDLE;
            case ALIGN_BOTTOM:
                return HasVerticalAlignment.ALIGN_BOTTOM;
            default:
                throw new IllegalArgumentException("Undefined alignement");
        }
    }

    public static Alignment asAlignment(final byte byteValue) {
        final PAlignment alignment = PAlignment.fromByte(byteValue);
        switch (alignment) {
            case BEGIN:
                return Alignment.BEGIN;
            case END:
                return Alignment.END;
            case STRETCH:
                return Alignment.STRETCH;
            default:
                throw new IllegalArgumentException("Undefined alignement");
        }
    }
}
