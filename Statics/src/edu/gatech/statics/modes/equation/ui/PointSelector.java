/*
 * PointSelector.java
 *
 * Created on July 27, 2007, 11:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.util.SelectionFilter;

/**
 *
 * @author Calvin Ashmore
 */
public class PointSelector extends Tool {

    private EquationDiagram world;

    /** Creates a new instance of PointSelector */
    public PointSelector(EquationDiagram world) {
        this.world = world;
    }

    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Point;
        }
    };
    
    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }
    

    @Override
    protected void onActivate() {
        StaticsApplication.getApp().setAdvice(java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_feedback_momentPointSelect"));
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null || !(obj instanceof Point)) {
            return;
        }

        System.out.println("Selected... " + obj);

        if (obj != null) {

            // store the point, finish.
            world.setMomentPoint((Point) obj);
            //getWorld().clearSelection();
            finish();
        }
    }

    @Override
    protected void onCancel() {
    }

    @Override
    protected void onFinish() {
    }
}
