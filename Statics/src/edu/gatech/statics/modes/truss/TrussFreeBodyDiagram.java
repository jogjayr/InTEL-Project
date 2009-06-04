/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussFreeBodyDiagram extends FreeBodyDiagram {

    public TrussFreeBodyDiagram(BodySubset bodies) {
        super(bodies);
    }

    /**
     * This creates an ordinary initial state, but has the slight difference of adding given loads,
     * so the student does not need to slog through these.
     * @return
     */
    @Override
    protected FBDState createInitialState() {
        // automatically add the given loads on these problems.
        // we're nice to the students here.

        Builder builder = super.createInitialState().getBuilder();
        for (SimulationObject obj : getSchematic().allObjects()) {
            if (obj instanceof Load) {
                Load load = (Load) obj;

                boolean isPresent = false;
                for (Body body : getBodySubset().getBodies()) {
                    if (body.getAttachedObjects().contains(load)) {
                        isPresent = true;
                    }
                }

                if (isPresent) {
                    builder.addLoad(load.getAnchoredVector());
                }
            }
        }
        return builder.build();
    }

    /**
     * This gets the normal adjacent objects, but without the ZFMs
     * @return
     */
    @Override
    public List<SimulationObject> getAdjacentObjects() {
        // NOTE: THIS CODE IS COPIED AND PASTED FROM THE SUPER IMPLEMENTATION
        // It seemed easier to simply copy because of the very small change, which would
        // be awkward to design around.

        List<SimulationObject> adjacentObjects = new ArrayList<SimulationObject>();
        List<SimulationObject> centralObjects = getCentralObjects();

        for (Body body : getSchematic().allBodies()) {
            // go through each body in the schematic (not our list!)

            // ********
            // This is the change from the super implementation
            if (body instanceof ZeroForceMember) {
                continue;
            }
            // ********

            for (SimulationObject obj : body.getAttachedObjects()) {
                if (obj instanceof Connector) {
                    // through each connector
                    Connector connector = (Connector) obj;
                    if ((getBodySubset().getBodies().contains(connector.getBody1()) ||
                            getBodySubset().getBodies().contains(connector.getBody2())) &&
                            !centralObjects.contains(body) && !adjacentObjects.contains(body)) {
                        // ok, the body is attached to a body in the diagram,
                        // but is not a body in the diagram

                        adjacentObjects.add(body);

                        // now we want to add adjacent points, but not points that are already in the diagram.
                        for (SimulationObject attached : body.getAttachedObjects()) {
                            if (attached instanceof Point && !centralObjects.contains(attached) && !adjacentObjects.contains(attached)) {
                                adjacentObjects.add(attached);
                            }
                        }
                    }
                }
            }
        }
        return adjacentObjects;
    }
}
