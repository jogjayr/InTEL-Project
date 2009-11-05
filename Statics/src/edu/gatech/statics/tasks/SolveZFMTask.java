/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveZFMTask extends Task {

    /**
     * For persistence, do not use.
     * @param name
     */
    public SolveZFMTask(String name) {
        super(name);
    }

    public boolean isSatisfied() {

        ZFMDiagram diagram = (ZFMDiagram) Exercise.getExercise().getDiagram(null, ZFMMode.instance.getDiagramType());
        
        // terminate early if the diagram does not yet exist.
        if (diagram == null) {
            return false;
        }
        return diagram.isLocked();
    }

    public String getDescription() {
        return "Find all zero force members";
    }
}
