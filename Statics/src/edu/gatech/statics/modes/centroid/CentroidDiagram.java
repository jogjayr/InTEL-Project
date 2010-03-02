/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.centroid.CentroidState.Builder;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.representations.MimicRepresentation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidDiagram extends Diagram<CentroidState> {

    private static final float TOLERANCE = .01f;
//    private CentroidPartObject cpObj;
    private CentroidBody body;

    public CentroidDiagram(CentroidBody body) {
        super(body);
        this.body = body;

        // pick out the actual centroid part object from the schematic
//        for (SimulationObject obj : Exercise.getExercise().getSchematic().allObjects()) {
//            if (obj instanceof CentroidPartObject) {
//                CentroidPartObject cpObjTest = (CentroidPartObject) obj;
//                if (cpObjTest.getCentroidPart().equals(body)) {
//                    cpObj = cpObjTest;
//                }
//            }
//        }
    }

    @Override
    public void activate() {
        super.activate();
        //TODO: ADD THE SPECIFIC CODE THAT RECONSTRUCTS THE DIAGRAM
        updateCentroid();

        if (isSolved()) {
            StaticsApplication.getApp().setDefaultUIFeedback("You have solved for the values for the centroid.");
        } else {
            StaticsApplication.getApp().setDefaultUIFeedback("Enter the total area, the X, and Y position for the section.");
        }
        StaticsApplication.getApp().resetUIFeedback();

        // activate the mimic representations
        for (SimulationObject obj : getBaseObjects()) {
            if (obj instanceof CentroidPartObject) {
                setCentroidPartActive((CentroidPartObject) obj, true);
            }
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        //TODO: ADD THE SPECIFIC CODE THAT DECONSTRUCTS THE DIAGRAM

        // deactivate the mimic representations
        for (SimulationObject obj : getBaseObjects()) {
            if (obj instanceof CentroidPartObject) {
                setCentroidPartActive((CentroidPartObject) obj, false);
            }
        }
    }

    public void setSolved() {
        //updateResultant();
        Builder builder = getCurrentState().getBuilder();
        builder.setSolved(true);
        pushState(builder.build());
        clearStateStack();
    }

    public boolean isSolved() {
        return getCurrentState().isLocked();
    }

    protected CentroidPart getCentroidPart() {
        return (CentroidPart) getKey();
    }

    @Override
    public String getDescriptionText() {
        return "Determine the centroid for the part: " + getKey();
    }

    @Override
    protected CentroidState createInitialState() {
        CentroidState.Builder builder = new CentroidState.Builder();
        return builder.build();
    }

    @Override
    public void completed() {
        //nothing apparently
    }

    @Override
    public Mode getMode() {
        return CentroidMode.instance;
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        //objects.add(cpObj);
        objects.addAll(body.getParts());


        //measurements?
        return objects;
    }

    public void updateCentroid() {
        //TODO FILL THIS OUT
        //Needs to update so that the centroid is displayed properly once the user solves,
        //but this might not be needed and we can do it like friction problems for mu
    }

    private void setCentroidPartActive(CentroidPartObject part, boolean active) {
        for (Representation representation : part.allRepresentations()) {
            if (representation instanceof MimicRepresentation) {
                MimicRepresentation mimic = (MimicRepresentation) representation;
                if (active) {
                    mimic.activate();
                } else {
                    mimic.deactivate();
                }
            }
        }
    }
}
