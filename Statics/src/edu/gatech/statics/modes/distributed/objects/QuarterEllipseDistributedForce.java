/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class QuarterEllipseDistributedForce extends DistributedForce {

    /**
     * Start point is the point of the peak.
     * So points must be provided in reverse order to get the slope in the other direction
     * @param surface
     * @param startPoint
     * @param endPoint
     * @param peak
     */
    public QuarterEllipseDistributedForce(Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(surface, startPoint, endPoint, peak);
    }
    
    @Override
    public void createDefaultSchematicRepresentation() {
        // only one sample is necessary here.
        createDefaultSchematicRepresentation(1, 10);
    }

    public void createDefaultSchematicRepresentation(float displayScale, int arrows) {
        addRepresentation(new DistributedForceRepresentation(this, 30, displayScale, arrows));
        
        LabelRepresentation label = new LabelRepresentation(this, "label_force");
        addRepresentation(label);
    }

    @Override
    float getCurveValue( float x) {
        return (float) Math.sqrt(1 - x * x);
    }

    @Override
    protected BigDecimal getPositionMultiplier() {
        return new BigDecimal(4/(3*Math.PI));
    }

    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        return new BigDecimal(Math.PI/4);
    }
}