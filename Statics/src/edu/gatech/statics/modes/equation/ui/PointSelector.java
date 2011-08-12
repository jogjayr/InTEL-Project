/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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

/**
 *
 * @author Calvin Ashmore
 */
public class PointSelector extends Tool {

    private EquationDiagram diagram;
    private String mathName;

    /**
     * Creates a new instance of PointSelector
     * @param diagram EquationDiagram instance in which PointSelector will work
     * @param mathName Name of equation math
     */
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
        
        StaticsApplication.logger.info("Selected... " + obj);

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
