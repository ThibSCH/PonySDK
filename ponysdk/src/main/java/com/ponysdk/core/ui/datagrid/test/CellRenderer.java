
package com.ponysdk.core.ui.datagrid.test;

import com.ponysdk.core.ui.basic.PWidget;

public interface CellRenderer<DataType, RenderedType> {

    /** Render this data graphically **/
    PWidget render(final DataType data);

    /** Update the previous rendered widget to render the new data **/
    PWidget update(final DataType data, PWidget current);

    /** Resets the graphic view */
    void reset(PWidget widget);

    /**
     * Returns the value currently rendered
     * Ex : if this renderer is used to display a quantity extracted from an instance of DataType it
     * should return this quantity
     */
    RenderedType getRenderedValue(final DataType data);

    String getDescription();
}
