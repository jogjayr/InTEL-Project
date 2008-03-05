/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.components;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.InterfaceRoot;
import java.io.IOException;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class TitledDraggablePopupWindow extends DraggablePopupWindow {

    private BContainer titleContainer;
    private BContainer contentContainer;
    private BLabel titleLabel;

    abstract public String getName();
    
    public BLabel getTitleLabel() {
        return titleLabel;
    }

    protected BContainer getContentContainer() {
        return contentContainer;
    }

    public TitledDraggablePopupWindow(BLayoutManager layout, String title) {
        this(layout, title, true);
    }

    public TitledDraggablePopupWindow(BLayoutManager layout, String title, boolean closable) {
        super(InterfaceRoot.getInstance().getMenuBar(), new BorderLayout());

        titleContainer = new BContainer(new BorderLayout());
        titleContainer.setStyleClass("draggable_title");
        //titleLabel = new BLabel(title, "draggable_title");
        titleLabel = new BLabel(title);
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        if (closable) {
            
            BIcon closeIcon = null;
            try {
                closeIcon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/interfaceTextures/close.png")));
            } catch(IOException ex) {
                // do nothing?
            }
            
            BButton closeButton = new BButton(closeIcon, new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    TitledDraggablePopupWindow.this.setVisible(false);
                }
            },"");
            titleContainer.add(closeButton, BorderLayout.EAST);
        }

        contentContainer = new BContainer(layout);

        addDragHandle(titleContainer);
        addDragHandle(titleLabel);
        addDragHandle(contentContainer);

        add(titleContainer, BorderLayout.NORTH);
        add(contentContainer, BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        pack();
    }
    
    
}
