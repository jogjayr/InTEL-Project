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
 *
 * @author Trudetski
 */
public class AnchoredVectorPersistenceDelegate extends PersistenceDelegate {

    private Diagram diagram;

    public AnchoredVectorPersistenceDelegate(Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        //out.w
        //new Statement()
        return null;
    }
    
}
