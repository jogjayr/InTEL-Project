/*
 * EquationBar.java
 *
 * Created on July 19, 2007, 4:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.application.ui.AppInterface;
import edu.gatech.statics.application.ui.AppWindow;
import edu.gatech.statics.modes.fbd.FBDWorld;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationBar extends AppWindow {
    
    private EquationWorld equation;
    private BButton returnButton;
    private EquationInterface iface;

    private BButton sumFxButton;
    private BButton sumFyButton;
    private BButton sumMpButton;
    
    private BButton solveButton;
    
    /** Creates a new instance of EquationBar */
    public EquationBar(final EquationWorld equation, final EquationInterface iface) {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        setStyleClass("info_window");
        this.equation = equation;
        this.iface = iface;
        
        returnButton = new BButton("Return", new ButtonListener(), "return");
        add(returnButton);
        
        ActionListener buttonListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(event.getAction().equals("sumFx")) {
                    SumBar sumBar = new SumBar(iface, equation.sumFx, equation);
                    iface.setPalette(sumBar);
                }
                if(event.getAction().equals("sumFy")) {
                    SumBar sumBar = new SumBar(iface, equation.sumFy, equation);
                    iface.setPalette(sumBar);
                }
                if(event.getAction().equals("sumMp")) {
                    SumBar sumBar = new SumBar(iface, equation.sumMp, equation);
                    iface.setPalette(sumBar);
                    
                    // also load select point tool if we need it
                    if(!equation.sumMp.getObservationPointSet()) {
                        PointSelector tool = new PointSelector(equation);
                        tool.activate();
                    }
                }
                if(event.getAction().equals("solve")) {
                    SolveBar solveBar = new SolveBar(equation, iface);
                    iface.setPalette(solveBar);
                }
            }
        };
        
        try {
            BIcon icon;
            BButton button;

            if(equation.sumFx.isLocked())
                icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumFxCheck.png")));
            else icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumFx.png")));
            sumFxButton = new BButton(icon, buttonListener1, "sumFx");
            add(sumFxButton);

            if(equation.sumFy.isLocked())
                icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumFyCheck.png")));
            else icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumFy.png")));
            sumFyButton = new BButton(icon, buttonListener1, "sumFy");
            add(sumFyButton);

            if(equation.sumMp.isLocked())
                icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumMpCheck.png")));
            else icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/sumMp.png")));
            sumMpButton = new BButton(icon, buttonListener1, "sumMp");
            add(sumMpButton);
            
            solveButton = new BButton("Solve", buttonListener1, "solve");
            
            if(     equation.sumFx.isLocked() && 
                    equation.sumFy.isLocked() &&
                    equation.sumMp.isLocked())
                solveButton.setEnabled(true);
            else solveButton.setEnabled(false);
            add(solveButton);
            
        } catch(Exception e) {}
    }
    
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            getApp().loadFBD((FBDWorld) equation.getParentWorld());
        }
    }
}
