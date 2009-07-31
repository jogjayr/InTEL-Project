/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.sidebar;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class SidebarWindow extends BContainer {

    private BContainer titleContainer;
    private BLabel titleLabel;
    private BContainer contentContainer;

    public SidebarWindow(BLayoutManager layout, String title) {
        //super(InterfaceRoot.getInstance().getMenuBar(), new BorderLayout());
        super(new BorderLayout());

        titleContainer = new BContainer(new BorderLayout());
        titleContainer.setStyleClass("draggable_title");
        //titleLabel = new BLabel(title, "draggable_title");
        titleLabel = new BLabel(title);
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        //if (closable) {

//        BIcon closeIcon = null;
//        try {
//            closeIcon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/interfaceTextures/close.png")));
//        } catch (IOException ex) {
//            // do nothing?
//        }
//
//        BButton closeButton = new BButton(closeIcon, new ActionListener() {
//
//            public void actionPerformed(ActionEvent event) {
//                //TitledDraggablePopupWindow.this.setVisible(false);
//                toggleCollapsed();
//            }
//        }, "");
//        titleContainer.add(closeButton, BorderLayout.EAST);

        contentContainer = new BContainer(layout);
        contentContainer.setStyleClass("translucent_container");

        BScrollPane contentScrollPane = new BScrollPane(contentContainer);
        contentScrollPane.setShowScrollbarAlways(false);

//        addDragHandle(titleContainer);
//        addDragHandle(titleLabel);
//        addDragHandle(contentContainer);

        add(titleContainer, BorderLayout.NORTH);
        add(contentScrollPane, BorderLayout.CENTER);
    }

    public BLabel getTitleLabel() {
        return titleLabel;
    }

    protected BContainer getContentContainer() {
        return contentContainer;
    }

    private void toggleCollapsed() {
    }
}
