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
import com.jmex.bui.util.Dimension;

/**
 *
 * @author Calvin Ashmore
 */
public class SidebarWindow extends BContainer {

    private BContainer titleContainer;
    private BLabel titleLabel;
    private BContainer contentContainer;

    public SidebarWindow(BLayoutManager layout, String title) {
        super(new BorderLayout());

        titleContainer = new BContainer(new BorderLayout());
        titleContainer.setStyleClass("draggable_title");
        titleLabel = new BLabel(title);
        titleContainer.add(titleLabel, BorderLayout.CENTER);

        contentContainer = new BContainer(layout);
//
//            @Override
//            protected Dimension computePreferredSize(int whint, int hhint) {
//                return super.computePreferredSize(whint, hhint);
//            }
//
//        };
        contentContainer.setStyleClass("translucent_container");

//        BScrollPane contentScrollPane = new BScrollPane(contentContainer);
//        contentScrollPane.setShowScrollbarAlways(false);

        add(titleContainer, BorderLayout.NORTH);
        //add(contentScrollPane, BorderLayout.CENTER);
        add(contentContainer, BorderLayout.CENTER);
    }

    public BLabel getTitleLabel() {
        return titleLabel;
    }

    protected BContainer getContentContainer() {
        return contentContainer;
    }

//    @Override
//    protected Dimension computePreferredSize(int whint, int hhint) {
//        return super.computePreferredSize(whint, hhint);
//    }
}
