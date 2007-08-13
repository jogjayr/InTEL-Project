/*
 * CheckWindow.java
 *
 * Created on July 18, 2007, 6:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.AppWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class CheckWindow extends AppWindow {
    
    private BButton checkButton;
    private FBDWorld fbd;
    private FBDInterface fbdInterface;
    
    /** Creates a new instance of CheckWindow */
    public CheckWindow(FBDInterface fbdInterface, FBDWorld fbd) {
        super(new BorderLayout());
        this.fbd = fbd;
        this.fbdInterface = fbdInterface;
        
        checkButton = new BButton("Check!", new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                performCheck();
            }
        }, "check");
        add(checkButton, BorderLayout.CENTER);
    }
    
    protected void performCheck() {
        System.out.println("performing FBD check...");
        
        if(fbd.checkDiagram()) {
            System.out.println("test passed!");
            fbd.createEquationWorld();
            fbdInterface.enableEquationButton();
            fbd.setLocked();
        
            StaticsApplication.getApp().setAdvice(
                    "FBD Check: Congratulations, your FBD is correct! " +
                    "You can now go to the Equilibrium Equation mode.");
        } else {
            System.out.println("test failed!");
        
            //StaticsApplication.getApp().setAdvice(
            //        "FBD Check: Your FBD is not yet correct. " +
            //        "Please examine it closely, make some changes and try again.");
        }
    }
}
