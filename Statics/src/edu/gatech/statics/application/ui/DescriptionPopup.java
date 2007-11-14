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
import com.jmex.bui.BWindow;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.KeyEvent;
import com.jmex.bui.event.KeyListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionPopup extends ModalPopupWindow {

    public DescriptionPopup(BWindow parentWindow) {
        super(parentWindow, new BorderLayout());
        
        setStyleClass("description_window");
        
        HTMLView view = new HTMLView();
        view.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
        
        add(view, BorderLayout.CENTER);
        
        final ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dismiss();
            }
        };
        
        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent event) {
                dismiss();
            }
            public void keyReleased(KeyEvent event) {}
        };
        addListener(keyListener);
        
        BButton button = new BButton("OK",actionListener,"ok");
        add(button, BorderLayout.SOUTH);
        
        BBackground background = StaticsApplication.getApp().getBuiStyle().getBackground(this, "infoWindow");
        for (int state = 0; state < STATE_COUNT; state++)
            setBackground(state, background);
    }
    
}
