/*
 * PurseExerciseGraded.java
 * 
 * Created on Oct 23, 2007, 4:00:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example01;

import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.ModalPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExerciseGraded extends PurseExercise {

    
    public void postLoadExercise() {
    
        ModalPopupWindow popup = new ModalPopupWindow(StaticsApplication.getApp().getCurrentInterface().getToolbar(), new BorderLayout());
        popup.setModal(true);
        popup.add(new BLabel("HELLO??"), BorderLayout.CENTER);
        popup.popup(200, 200, true);
    }
}
