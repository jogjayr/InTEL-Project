/*
 * PointSelector.java
 *
 * Created on July 27, 2007, 11:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.manipulators.SelectionTool;

/**
 *
 * @author Calvin Ashmore
 */
public class PointSelector extends SelectionTool {
    
    /** Creates a new instance of PointSelector */
    public PointSelector(EquationWorld world) {
        super(world, Point.class);
    }
    
    protected void onActivate() {
        super.onActivate();
        StaticsApplication.getApp().setAdvice(java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_feedback_momentPointSelect"));
    }
    
    public void onClick(SimulationObject obj) {
        
        if(obj == null || !(obj instanceof Point))
            return;
        
        System.out.println("Selected... "+obj);
        
        if(obj != null) {
            
            // store the point, finish.
            ((EquationWorld) getWorld()).setMomentPoint((Point) obj);
            getWorld().clearSelection();
            finish();
        }
    }
}
