/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.exercise.state;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.Builder;
import java.util.List;

/**
 * DiagramState represents the state of a Diagram. The classes that make up the
 * actual diagram states should be immutable and final. This interface exists
 * to provide the underlying type.
 * @author Calvin Ashmore
 * @param <T> The type of diagram this state describes
 */
public interface DiagramState<T extends Diagram> extends State {
    
    boolean isLocked();
    
    /**
     * Returns a builder that will construct this instance. This is to be passed
     * to the serializer to encode this instance. 
     * @return
     */
    Builder<? extends DiagramState<T>> getBuilder();
    
    /**
     * Creates a diagram state from a deserialized copy.
     * It is assumed that this object is the deserialized copy, which is updated, but
     * may not include the correct in-world references.
     * @return
     */
    DiagramState<T> restore();
    
    //List<SimulationObject> getStateObjects();
}
