/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDState implements DiagramState<FreeBodyDiagram> {

    private boolean solved = false;
    
    // should we use Load or AnchoredVector in here?
    private List<Load> addedLoads = new ArrayList<Load>();
    
    
    private FBDState(Builder builder) {}
    
    public static class Builder implements edu.gatech.statics.util.Builder<FBDState> {

        public Builder(FBDState state) {
        }
        
        public Builder() {
            
        }

        public FBDState build() {
            return new FBDState(this);
        }
        
    }
    
    public boolean isLocked() {
        
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public DiagramState<FreeBodyDiagram> restore() {
        
    }

    public List<SimulationObject> getStateObjects() {
        
    }

}
