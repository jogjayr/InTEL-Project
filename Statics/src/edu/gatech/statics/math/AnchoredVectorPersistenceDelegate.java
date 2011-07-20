/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.math;

import edu.gatech.statics.exercise.Diagram;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 * Unused class. Unsure as to function
 * @author Trudetski
 */
public class AnchoredVectorPersistenceDelegate extends PersistenceDelegate {

    private Diagram diagram;
    /**
     * Constructor
     * @param diagram
     */
    public AnchoredVectorPersistenceDelegate(Diagram diagram) {
        this.diagram = diagram;
    }
    /**
     *
     * @param oldInstance
     * @param out
     * @return null
     */
    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        //out.w
        //new Statement()
        return null;
    }
    
}
