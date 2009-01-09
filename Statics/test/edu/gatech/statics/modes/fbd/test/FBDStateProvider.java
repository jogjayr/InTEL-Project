/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.objects.SimulationObject;
import java.util.Map;

/**
 * Individual test methods designed to test the FBD check should create anonymous classes
 * that implement this interface, and run the check method.
 * @author Calvin Ashmore
 */
public interface FBDStateProvider {

    /**
     * Creates a FBD state with the given objects. This is a general template for testing
     * the FBDCheck on any sort of FBD.
     * @param objects
     * @return
     */
    public FBDState createState(Map<String, SimulationObject> objects, FBDState.Builder builder);
}
