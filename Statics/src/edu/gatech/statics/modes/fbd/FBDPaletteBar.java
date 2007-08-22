/*
 * FBDPaletteBar.java
 *
 * Created on June 25, 2007, 8:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.application.ui.*;
import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.GroupLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDPaletteBar extends Toolbar {
    
    private BButton returnButton;
    private BButton nextButton;
    private FBDWorld fbd;
    
    /** Creates a new instance of FBDPaletteBar */
    public FBDPaletteBar(FBDWorld fbd) {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        setStyleClass("info_window");
        this.fbd = fbd;
        
        returnButton = new BButton("Return", new ButtonListener(), "return");
        add(returnButton);
        
        nextButton = new BButton("Equation", new ButtonListener2(), "equation");
        if(!fbd.isLocked())
            nextButton.setEnabled(false);
        add(nextButton);
        
        FBDIcon icon = new FBDIcon(getApp(), fbd);
        add(icon);
    }
    
    public void enableEquationButton() {nextButton.setEnabled(true);}
    
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            getApp().loadExercizeWorld();
        }
    }
    
    private class ButtonListener2 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            getApp().loadEquation(fbd);
        }
    }
}
