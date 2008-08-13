/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.exercise.BodySubset;

/**
 * This is a special subclass of FreeBodyDiagram which does not advance to the equation stage.
 * @author Calvin Ashmore
 */
public class SimpleFreeBodyDiagram extends FreeBodyDiagram {

    public SimpleFreeBodyDiagram(BodySubset bodies) {
        super(bodies);
    }

    /**
     * We override postSolve to do nothing: we will not advance to equation mode
     */
    @Override
    public void completed() {
    }
}
