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
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.util.SelectionFilter;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class LoadSelector extends Tool {

    private EquationBar activeEquation;

    /** Creates a new instance of LoadSelector */
    public LoadSelector(EquationBar activeEquation) {
        this.activeEquation = activeEquation;
    }
    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    @Override
    protected void onActivate() {
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_loadSelect");
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null || !(obj instanceof Load)) {
            return;
        }

        Logger.getLogger("Statics").info("Selected... " + obj);

        if (obj != null) {

            // if the term has already been added, select it.
//                if (((ArbitraryEquationMathState) activeEquation.getMath().getState()).getTerms().containsKey(((Load)obj).getAnchoredVector())) {
//                    activeEquation.focusOnTerm(((Load)obj).getAnchoredVector());
//                } else {
                    // otherwise, add it.
                    activeEquation.performAdd(((Load)obj).getAnchoredVector());
//                }

//            // store the point, finish.
//            //world.setMomentPoint((Point) obj);
//            //getWorld().clearSelection();
//            SetMomentPoint setMomentPointAction = new SetMomentPoint((Point) obj);
//            diagram.performAction(setMomentPointAction);



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
