/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SolveListener;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedDiagram extends Diagram<DistributedState> {

    private DistributedForce dl;
    private DistanceMeasurement measure;

    @Override
    public void activate() {
        super.activate();
        if (dl.isSolved()) {
            dl.setDisplayGrayed(true);
        }
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        List<SimulationObject> baseObjects = new ArrayList<SimulationObject>();

        baseObjects.add(dl);
        baseObjects.add(dl.getSurface());
        baseObjects.add(dl.getEndPoint());
        baseObjects.add(dl.getStartPoint());

        Force resultant = dl.getResultant();

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

        Force resultant = dl.getResultant();
        resultant.createDefaultSchematicRepresentation();
        resultant.getAnchor().createDefaultSchematicRepresentation();

        resultant.setDisplayGrayed(true);

        //DistanceMeasurement measure = new DistanceMeasurement(dl.getStartPoint(), resultant.getAnchor());
        //String pointName = dl.getName()+" pos";
        measure = new DistanceMeasurement(
                new Point(dl.getName()+" end1", dl.getSurface().getEndpoint1()), resultant.getAnchor());
        measure.setKnown(false);
        measure.setSymbol("pos");
        measure.createDefaultSchematicRepresentation(2f);
    }

    public boolean check(String positionValue, String magnitudeValue) {

        AffineQuantity userMagnitude = Parser.evaluateSymbol(magnitudeValue);
        AffineQuantity userPosition = Parser.evaluateSymbol(positionValue);

        System.out.println("user pos: \"" + positionValue + "\" " + userPosition);
        System.out.println("user mag: \"" + magnitudeValue + "\" " + userMagnitude);

        if (userMagnitude == null || userPosition == null) {
            return false;
        }

        DistributedForce force = getForce();
        AffineQuantity resultantMagnitude = force.getResultantMagnitude();
        AffineQuantity resultantPosition = force.getResultantPosition();

        System.out.println("pos: " + resultantPosition);
        System.out.println("mag: " + resultantMagnitude);

        boolean success = true;

        success &= resultantMagnitude.equalsWithinTolerance(userMagnitude, .1f);
        success &= resultantPosition.equalsWithinTolerance(userPosition, .1f);
        return success;
    }
    
    // THIS IS A TEMPORARY SOLUTION
    //private static int count = 1;

    /**
     * This should be called after the user's values for the resultant have been checked.
     * The method adds the resultant (and its supporting objects) to the schematic.
     */
    public void updateResultant() {

        Force resultant = dl.getResultant();
        dl.setDisplayGrayed(true);
        resultant.setDisplayGrayed(false);
        dl.getSurface().addObject(resultant);

        dl.setSolved(true);

        AffineQuantity resultantMagnitude = dl.getResultantMagnitude();
        AffineQuantity resultantPosition = dl.getResultantPosition();

        // set names for the resultant and its anchor?
        //resultant.setName("Resultant "+count);
        //resultant.getAnchor().setName("pos "+count);
        //count++;
        
        measure.setKnown(true);
        //resultant.setKnown(true);

        // ??
        //resultant.setSymbol(null);

        // IMPORTANT THINGS TO NOTE:
        // THIS CODE DOES NOT WORK 100% JUST YET
        // The following things remain to be done:
        // 1) accomodate what happens when the affine value for the resultantMagnitude is symbolic.
        // 2) accomodate what happens when the affine value for the resultantPosition is symbolic.

        if (resultantMagnitude.isSymbolic() || resultantPosition.isSymbolic()) {
            throw new UnsupportedOperationException("Symbolic values not supported yet in the resultant");
        }

        resultant.getVector().setDiagramValue(resultantMagnitude.getConstant());

        getSchematic().add(resultant);
        getSchematic().add(resultant.getAnchor());
        getSchematic().add(measure);
        dl.getSurface().addObject(resultant);
        dl.getSurface().addObject(resultant.getAnchor());

        // update our UI view now that the resultants are found

        for (SolveListener listener : StaticsApplication.getApp().getSolveListeners()) {
            listener.onLoadSolved(resultant);
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
