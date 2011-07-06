/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class ZFMMode extends Mode {

    public static final ZFMMode instance = new ZFMMode();

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
     * Creates DiagramType with name "zfm" and priority 90
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("zfm", 90);
    }
}
