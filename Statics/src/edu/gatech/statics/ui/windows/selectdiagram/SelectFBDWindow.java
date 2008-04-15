/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.selectdiagram;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectFBDWindow extends TitledDraggablePopupWindow {

    public static final String windowName = "diagrams";

    @Override
    public String getName() {
        return windowName;
    }
    private BContainer mainContainer;

    public SelectFBDWindow() {
        super(new BorderLayout(), "Select Diagram");
        
        //BLabel title = new BLabel("Select FBD", "application_popup_title");
        //getContentContainer().add(title, BorderLayout.NORTH);

        mainContainer = new RowDiagramContainer();
        
        BContainer spacer = GroupLayout.makeVBox(GroupLayout.LEFT);
        spacer.setPreferredSize(-1, 4);
        getContentContainer().add(spacer, BorderLayout.NORTH);
        
        //new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        //BScrollPane scrollPane = new BScrollPane(mainContainer);
        //scrollPane.setPreferredSize(150, 150);
        //getContentContainer().add(scrollPane, BorderLayout.CENTER);
        getContentContainer().add(mainContainer, BorderLayout.CENTER);
    }
}
