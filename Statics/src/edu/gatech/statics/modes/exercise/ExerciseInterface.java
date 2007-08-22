/*
 * ExercizeInterface.java
 *
 * Created on June 13, 2007, 11:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.exercise;

import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.ui.*;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseInterface extends AppInterface {
    
    //private BWindow titleWindow;
    private BWindow descriptionWindow;
    private ExercizeFBDBar fbdBar;
    
    /** Creates a new instance of ExercizeInterface */
    public ExerciseInterface() {
        
        //titleWindow = new BWindow(getApp().getBuiStyle(), new BorderLayout(5,5));
        //titleWindow.setStyleClass("info_window");
        //titleWindow.add(new BLabel("Exercize"), BorderLayout.CENTER);
        //getBuiNode().addWindow(titleWindow);
        //titleWindow.pack();
        //titleWindow.center();
        //titleWindow.setLocation(titleWindow.getX(), getScreenHeight() - titleWindow.getHeight());
        
        descriptionWindow = new BWindow(getApp().getBuiStyle(), new BorderLayout(5,5));
        descriptionWindow.setStyleClass("info_window");
        HTMLView view = new HTMLView();
        view.setStyleSheet(getHtmlStyle());
        view.setContents(getApp().getExercise().getDescription());
        descriptionWindow.add(view, BorderLayout.CENTER);
        getBuiNode().addWindow(descriptionWindow);
        //descriptionWindow.setBounds(getRightAnchor(descriptionWindow),getTopAnchor(descriptionWindow),200,200);
        descriptionWindow.setBounds(0,0,200,200);
        descriptionWindow.setLocation(
                getScreenWidth()-200 - RootInterface.borderSize - RootInterface.windowSpacing,
                getScreenHeight()-200 - RootInterface.borderSize - RootInterface.windowSpacing);
        
        fbdBar = new ExercizeFBDBar();
        getBuiNode().addWindow(fbdBar);
        fbdBar.layoutToolbar();
        
        getBuiNode().updateGeometricState(0, true);
    }
    
}
