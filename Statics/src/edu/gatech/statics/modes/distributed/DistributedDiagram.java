/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedDiagram extends Diagram {

    private DistributedForce dl;
    
    public DistributedDiagram(DistributedForce dl) {
        this.dl = dl;
        
        add(dl);
        add(dl.getSurface());
        add(dl.getEndPoint());
        add(dl.getStartPoint());
        
        Force resultant = dl.getResultant();
        resultant.createDefaultSchematicRepresentation();
        resultant.getAnchor().createDefaultSchematicRepresentation();
        
        add(resultant);
        add(resultant.getAnchor());
        
        resultant.setDisplayGrayed(true);
        
        //DistanceMeasurement measure = new DistanceMeasurement(dl.getStartPoint(), resultant.getAnchor());
        DistanceMeasurement measure = new DistanceMeasurement(new Point(dl.getSurface().getEndpoint1()), resultant.getAnchor());
        measure.setKnown(false);
        measure.setSymbol("pos");
        measure.createDefaultSchematicRepresentation(2f);
        add(measure);
        
        for (Measurement measurement : getSchematic().getMeasurements(new BodySubset(dl.getSurface()))) {
            add(measurement);
        }
    }
    
    
    public boolean check(String positionValue, String magnitudeValue) {

        AffineQuantity userMagnitude  = Parser.evaluateSymbol(magnitudeValue);
        AffineQuantity userPosition = Parser.evaluateSymbol(positionValue);
        
        System.out.println("user pos: \""+positionValue+"\" "+userPosition);
        System.out.println("user mag: \""+magnitudeValue+"\" "+userMagnitude);
        
        DistributedForce force = getForce();
        AffineQuantity resultantMagnitude = force.getResultantMagnitude();
        AffineQuantity resultantPosition = force.getResultantPosition();
        
        System.out.println("pos: "+resultantPosition);
        System.out.println("mag: "+resultantMagnitude);
        
        boolean success = true;
        
        success &= resultantMagnitude.equalsWithinTolerance(userMagnitude,.1f);
        success &= resultantPosition.equalsWithinTolerance(userPosition,.1f);
        return success;
    }

    public void updateResultant() {
        Force resultant = dl.getResultant();
        dl.setDisplayGrayed(true);
        resultant.setDisplayGrayed(false);
        dl.getSurface().addObject(resultant);
        getSchematic().add(resultant);
    }
    
    protected DistributedForce getForce() {
        return dl;
    }

    @Override
    public Mode getMode() {
        return DistributedMode.instance;
    }

}
