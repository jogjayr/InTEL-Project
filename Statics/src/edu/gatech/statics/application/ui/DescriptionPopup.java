/*
 * DescriptionPopup.java
 * 
 * Created on Nov 8, 2007, 12:38:26 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.KeyEvent;
import com.jmex.bui.event.KeyListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionPopup extends DraggablePopupWindow implements TaskStatusListener {

    HTMLView view;
    
    public DescriptionPopup(BWindow parentWindow) {
        super(parentWindow, new BorderLayout());
        
        //setStyleClass("description_window");
        setStyleClass("info_window");
        
        BContainer titleBar = new BContainer(new BorderLayout());
        titleBar.setStyleClass("title_container");
        titleBar.add(new BLabel("About"),BorderLayout.CENTER);
        
        final ActionListener dismissListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dismiss();
            }
        };
        titleBar.add(new BButton("X", dismissListener, "dismiss"), BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);
        addDragHandle(titleBar);
        
        BContainer viewContainer = new BContainer(new BorderLayout());
        viewContainer.setStyleClass("padded_container");
        
        view = new HTMLView();
        view.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
        
        viewContainer.add(view, BorderLayout.CENTER);
        addDragHandle(view);
        add(viewContainer, BorderLayout.CENTER);
        
        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent event) {
                dismiss();
            }
            public void keyReleased(KeyEvent event) {}
        };
        addListener(keyListener);
        
        //BButton button = new BButton("OK",dismissListener,"ok");
        //add(button, BorderLayout.SOUTH);
        
        /*BBackground background = StaticsApplication.getApp().getBuiStyle().getBackground(this, "infoWindow");
        for (int state = 0; state < STATE_COUNT; state++)
            setBackground(state, background);*/
    }

    @Override
    public void popup(int x, int y, boolean above) {
        super.popup(x, y, above);
        StaticsApplication.getApp().getExercise().addTaskListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        StaticsApplication.getApp().getExercise().removeTaskListener(this);
    }

    public void taskSatisfied(Task task) {
        view.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
    }
    
}
