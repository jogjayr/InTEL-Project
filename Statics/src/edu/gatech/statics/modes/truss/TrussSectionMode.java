/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionMode extends Mode {

    public static final TrussSectionMode instance = new TrussSectionMode();

    /**
     * 
     * @param key
     * @return
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key != null) {
            throw new IllegalArgumentException("DiagramKey " + key + " should be null!");
        }
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    /**
     * Creates a section diagram with name "sections" and priority 150
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("sections", 150);
    }
}
