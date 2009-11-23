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
import edu.gatech.statics.modes.equation.actions.SetMomentPoint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.util.SelectionFilter;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class PointSelector extends Tool {

    private EquationDiagram diagram;
    private String mathName;

    /** Creates a new instance of PointSelector */
    public PointSelector(EquationDiagram diagram, String mathName) {
        this.diagram = diagram;
        this.mathName = mathName;
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
        StaticsApplication.getApp().setUIFeedbackKey("equation_feedback_momentPointSelect");
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null || !(obj instanceof Point)) {
            return;
        }

        Logger.getLogger("Statics").info("Selected... " + obj);

        if (obj != null) {

            // store the point, finish.
            //world.setMomentPoint((Point) obj);
            //getWorld().clearSelection();
            SetMomentPoint setMomentPointAction = new SetMomentPoint((Point) obj, mathName);
            diagram.performAction(setMomentPointAction);

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
