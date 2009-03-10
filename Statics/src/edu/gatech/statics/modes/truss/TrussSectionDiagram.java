/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.SimulationObject;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionDiagram extends Diagram<TrussSectionState> {

    public TrussSectionDiagram() {
        super(null);
    }

    @Override
    protected TrussSectionState createInitialState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void completed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mode getMode() {
        return TrussSectionMode.instance;
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
