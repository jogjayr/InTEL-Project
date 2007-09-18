/*
 * DescriptionIcon.java
 * 
 * Created on Sep 3, 2007, 1:59:22 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionIcon extends AppWindow {

    public DescriptionIcon() {
        super(new BorderLayout());
        
        BButton button = new BButton("Description",new ActionListener() {
                public void actionPerformed(ActionEvent event) {doPopup();}
            },"description");
        
        add(button, BorderLayout.CENTER);
    }

    void doPopup() {
        final BPopupWindow window = new BPopupWindow(this, new BorderLayout());
        window.setModal(true);
        window.setStyleClass("description_window");

        HTMLView view = new HTMLView();
        //view.setStyleSheet("infoWindow");
        view.setContents(StaticsApplication.getApp().getExercise().getDescription());
        
        window.add(view, BorderLayout.CENTER);
        
        BButton button = new BButton("OK",new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                window.dismiss();
            }
        },"ok");
        window.add(button, BorderLayout.SOUTH);
        
        /*window.addListener(new MouseListener() {
            public void mousePressed(MouseEvent event) {}
            public void mouseReleased(MouseEvent event) {
                window.dismiss();
            }
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        });*/
        
        window.popup(0,0,true);
        //window.popup(0, 0, true);
        
        window.setBounds(0, 0, 300, 200);
        window.center();
        
        BBackground background = StaticsApplication.getApp().getBuiStyle().getBackground(this, "infoWindow");
        for (int state = 0; state < window.STATE_COUNT; state++)
            window.setBackground(state, background);

        //window.setBackground(window.getState(), new TintedBackground(new ColorRGBA(.7f,.6f,.6f,.8f)));
    }
}
