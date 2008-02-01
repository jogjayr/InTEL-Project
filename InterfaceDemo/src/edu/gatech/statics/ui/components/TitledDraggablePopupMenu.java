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
public class TitledDraggablePopupMenu extends DraggablePopupWindow {

    private BContainer titleContainer;
    private BContainer contentContainer;
    private BLabel titleLabel;

    public BLabel getTitleLabel() {
        return titleLabel;
    }

    protected BContainer getContentContainer() {
        return contentContainer;
    }

    public TitledDraggablePopupMenu(BLayoutManager layout, String title) {
        this(layout, title, true);
    }

    public TitledDraggablePopupMenu(BLayoutManager layout, String title, boolean closable) {
        super(InterfaceRoot.getInstance().getMenuBar(), new BorderLayout());

        titleContainer = new BContainer(new BorderLayout());
        titleContainer.setStyleClass("draggable_title");
        //titleLabel = new BLabel(title, "draggable_title");
        titleLabel = new BLabel(title);
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        if (closable) {
            
            BIcon closeIcon = null;
            try {
                closeIcon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("close.png")));
            } catch(IOException ex) {
                // do nothing?
            }
            
            //BLabel closeLabel = new BLabel(closeIcon);
            /*closeLabel.addListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    TitledDraggablePopupMenu.this.setVisible(false);
                }
            });*/
            /*closeLabel.addListener(new MouseListener() {

                public void mousePressed(MouseEvent event) {
                    TitledDraggablePopupMenu.this.setVisible(false);
                }

                public void mouseReleased(MouseEvent event) {}
                public void mouseEntered(MouseEvent event) {}
                public void mouseExited(MouseEvent event) {}
            });*/
            //titleContainer.add(closeLabel, BorderLayout.EAST);
            
            BButton closeButton = new BButton(closeIcon, new ActionListener() {
            //BButton closeButton = new BButton("X", new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    TitledDraggablePopupMenu.this.setVisible(false);
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
}
