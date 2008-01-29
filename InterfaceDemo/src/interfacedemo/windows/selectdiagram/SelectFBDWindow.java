/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.windows.selectdiagram;

import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import interfacedemo.components.TitledDraggablePopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectFBDWindow extends TitledDraggablePopupMenu {

    private List<SelectFBDItem> items = new ArrayList<SelectFBDItem>();
    private BContainer mainContainer;
    
    public SelectFBDWindow() {
        super(new BorderLayout(),"Select Diagram");
        
        BLabel title = new BLabel("Select FBD", "application_popup_title");
        getContentContainer().add(title, BorderLayout.NORTH);
        
        mainContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        getContentContainer().add(new BScrollPane(mainContainer), BorderLayout.CENTER);
        
        // TEST TEST
        addItem(new CreateNewFBDItem());
        addItem(new SelectFBDItem(1));
        addItem(new SelectFBDItem(2));
        addItem(new SelectFBDItem(3));
    }
    
    public void addItem(SelectFBDItem item) {
        items.add(item);
        
        int componentCount = mainContainer.getComponentCount();
        BContainer rowContainer = null;
        if(componentCount > 0)
            rowContainer = (BContainer) mainContainer.getComponent(componentCount-1);
        
        // if we have yet to place a row,
        // or if the rows are empty, create a new row.
        if( rowContainer == null || rowContainer.getComponentCount() == 3) {
            rowContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
            mainContainer.add(rowContainer);
        }
        
        rowContainer.add(item);
    }
    
    private class CreateNewFBDItem extends SelectFBDItem {
        public CreateNewFBDItem() {
            super(-1);
        }

        @Override
        protected BLabel setupLabel() {
            return new BLabel("Create New FBD!");
        }
        
    }
}
