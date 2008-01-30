/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.windows.description;

import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import interfacedemo.components.TitledDraggablePopupMenu;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionWindow extends TitledDraggablePopupMenu {

    private HTMLView description;
    
    public DescriptionWindow() {
        super(new BorderLayout(), "Description");
        
        description = new HTMLView();
        description.setContents("My contents. A problem description, maybe?");
        getContentContainer().add(description, BorderLayout.CENTER);
    }
    
}