/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.description;

import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionWindow extends TitledDraggablePopupWindow {

    public static final String windowName = "description";
    @Override
    public String getName() {
        return windowName;
    }
    
    private HTMLView description;
    
    public DescriptionWindow() {
        super(new BorderLayout(), "Description");
        
        description = new HTMLView();
        description.setContents("My contents. A problem description, maybe?");
        getContentContainer().add(description, BorderLayout.CENTER);
    }
    
}