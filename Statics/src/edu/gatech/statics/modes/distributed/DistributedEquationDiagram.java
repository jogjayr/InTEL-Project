/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.modes.equation.EquationDiagram;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedEquationDiagram extends EquationDiagram {

    /**
     * 
     * @param bodies Subset of bodies to create EquationDiagram for
     */
    public DistributedEquationDiagram(BodySubset bodies) {
        super(bodies);
    }

    /**
     * Activate this diagram
     */
    @Override
    public void activate() {
        super.activate();
        DistributedUtil.grayoutSolvedDistributedObjects();
    }
}
