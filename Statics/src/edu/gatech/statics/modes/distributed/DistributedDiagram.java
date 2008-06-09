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
        
        DistanceMeasurement measure = new DistanceMeasurement(dl.getStartPoint(), resultant.getAnchor());
        measure.setKnown(false);
        measure.setSymbol("pos");
        measure.createDefaultSchematicRepresentation();
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
        success &= resultantMagnitude.equals(userMagnitude);
        success &= resultantPosition.equals(userPosition);
        return success;
    }
    
    protected DistributedForce getForce() {
        return dl;
    }

    @Override
    public Mode getMode() {
        return DistributedMode.instance;
    }

}
