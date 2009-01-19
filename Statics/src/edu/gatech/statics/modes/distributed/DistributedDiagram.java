/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SolveListener;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.distributed.actions.SetMagnitudeValue;
import edu.gatech.statics.modes.distributed.actions.SetPositionValue;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedDiagram extends Diagram<DistributedState> {

    private static final float TOLERANCE = .01f;
    private DistributedForce dl;
    private DistributedForceObject dlObj;
    private DistanceMeasurement measure;

    public boolean isSolved() {
        return getCurrentState().isLocked();
    }

    @Override
    public void activate() {
        super.activate();
        updateResultant();
    }

    public void setPosition(String text) {
        SetPositionValue action = new SetPositionValue(text);
        performAction(action);
    }

    public void setMagnitude(String text) {
        SetMagnitudeValue action = new SetMagnitudeValue(text);
        performAction(action);
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        List<SimulationObject> baseObjects = new ArrayList<SimulationObject>();

        baseObjects.add(dlObj);
        baseObjects.add(dl.getSurface());
        baseObjects.add(dl.getEndPoint());
        baseObjects.add(dl.getStartPoint());

        Force resultant = dlObj.getResultantForce();

        baseObjects.add(resultant);
        baseObjects.add(resultant.getAnchor());

        baseObjects.add(measure);

        for (Measurement measurement : getSchematic().getMeasurements(new BodySubset(dl.getSurface()))) {
            baseObjects.add(measurement);
        }

        return baseObjects;
    }

    public DistributedDiagram(DistributedForce dl) {
        this.dl = dl;

        // pick out the actual distributed load object from the schematic
        for (SimulationObject obj : Exercise.getExercise().getSchematic().allObjects()) {
            if (obj instanceof DistributedForceObject) {
                DistributedForceObject dlObjTest = (DistributedForceObject) obj;
                if (dlObjTest.getDistributedForce().equals(dl)) {
                    dlObj = dlObjTest;
                }
            }
        }

        Force resultant = dlObj.getResultantForce();

        //DistanceMeasurement measure = new DistanceMeasurement(dl.getStartPoint(), resultant.getAnchor());
        //String pointName = dl.getName()+" pos";
        measure = new DistanceMeasurement(
                new Point(dl.getName() + " end1", dl.getSurface().getEndpoint1()), resultant.getAnchor());
        measure.setKnown(false);
        measure.setSymbol("pos");
        measure.createDefaultSchematicRepresentation(2f);
    }

    @Override
    protected void stateChanged() {
        super.stateChanged();
        updateResultant();
    }

    public boolean check(String positionValue, String magnitudeValue) {

        //AffineQuantity userMagnitude = Parser.evaluateSymbol(magnitudeValue);
        //AffineQuantity userPosition = Parser.evaluateSymbol(positionValue);

        BigDecimal userMagnitude = Parser.evaluate(magnitudeValue);
        BigDecimal userPosition = Parser.evaluate(positionValue);
        if (userMagnitude == null || userPosition == null) {
            return false;
        }

        DistributedForce force = getForce();
        BigDecimal resultantMagnitude = force.getResultantMagnitude();
        BigDecimal resultantOffset = force.getResultantOffset(); // need to get the distance somehow

        // debugging messages
        System.out.println("user pos: \"" + positionValue + "\" " + userPosition);
        System.out.println("user mag: \"" + magnitudeValue + "\" " + userMagnitude);
        System.out.println("pos: " + resultantOffset);
        System.out.println("mag: " + resultantMagnitude);

        boolean success = true;

        // we want to estimate the accuracy of the result, but if the scale is large, we do not want a very very close solution
        // to be cut out due to rounding errors. So, we form the tolerance as a float value, which may stretch if the values are large.
        float magnitudeTolerance = TOLERANCE * Math.max(1, Math.abs(resultantMagnitude.floatValue()));
        float positionTolerance = TOLERANCE * Math.max(1, Math.abs(resultantOffset.floatValue()));

        //System.out.println("mag tolerance: "+magnitudeTolerance);
        //System.out.println("pos tolerance: "+positionTolerance);

        success &= Math.abs(resultantMagnitude.subtract(userMagnitude).floatValue()) < magnitudeTolerance;
        success &= Math.abs(resultantOffset.subtract(userPosition).floatValue()) < positionTolerance;

        System.out.println("success: " + success);

        return success;
    }

    /**
     * This method should be called after the diagram is checked, and also when an exercise is loaded.
     * This will make sure that the Force from the DistributedForceObject and its Point anchor both are
     * correctly placed in the diagram.
     *
     * This is somewhat dangerous because the anchor must be added to the underlying beam, ordinarily breaking
     * the structure of the exercise data. The exercise data is not persisted because it is common to the
     * exercise itself, and should not be changed. However we do need to change it in order to work with the distributed loads
     * properly. The solution is to have this method called during these sorts of state changes so that the changes
     * to exercise data happen consistently and in the background.
     */
    public void updateResultant() {

        Force resultant = dlObj.getResultantForce();
        if (isSolved()) {
            dlObj.setDisplayGrayed(true);
            resultant.setDisplayGrayed(false);

            // ************ NON STATE CHANGE
            dl.getSurface().addObject(resultant);

            //dl.setSolved(true);

            //AffineQuantity resultantMagnitude = dl.getResultantMagnitude();
            //AffineQuantity resultantPosition = dl.getResultantPosition();

            //resultant.setKnown(true);

            // IMPORTANT THINGS TO NOTE:
            // THIS CODE DOES NOT WORK 100% JUST YET
            // The following things remain to be done:
            // 1) accomodate what happens when the affine value for the resultantMagnitude is symbolic.
            // 2) accomodate what happens when the affine value for the resultantPosition is symbolic.

            //if (resultantMagnitude.isSymbolic() || resultantPosition.isSymbolic()) {
            //    throw new UnsupportedOperationException("Symbolic values not supported yet in the resultant");
            //}

            measure.setKnown(true);
            resultant.getVector().setDiagramValue(dl.getResultantMagnitude());

            getSchematic().add(resultant);
            getSchematic().add(resultant.getAnchor());
            getSchematic().add(measure);
            dl.getSurface().addObject(resultant);
            dl.getSurface().addObject(resultant.getAnchor());

            // update our UI view now that the resultants are found

            for (SolveListener listener : StaticsApplication.getApp().getSolveListeners()) {
                listener.onLoadSolved(resultant);
            }
        } else {
            dlObj.setDisplayGrayed(false);
            resultant.setDisplayGrayed(true);
        }
    }

    protected DistributedForce getForce() {
        return dl;
    }

    @Override
    public Mode getMode() {
        return DistributedMode.instance;
    }

    @Override
    public DiagramKey getKey() {
        return dl;
    }

    @Override
    protected DistributedState createInitialState() {
        DistributedState.Builder builder = new DistributedState.Builder();
        return builder.build();
    }

    @Override
    public void completed() {
        // do nothing yet
    }
}
