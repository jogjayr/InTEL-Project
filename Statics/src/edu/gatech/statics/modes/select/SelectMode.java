/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectMode extends Mode {

    public static final SelectMode instance = new SelectMode();

    /**
     * 
     * @param key
     * @return
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    /**
     * Creates a select diagram type with name "select" and priority 100
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("select", 100);
    }
}
