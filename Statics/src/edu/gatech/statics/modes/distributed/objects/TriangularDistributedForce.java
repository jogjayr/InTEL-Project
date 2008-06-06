/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class TriangularDistributedForce extends DistributedForce {

    /**
     * Start point is the point of the peak.
     * So points must be provided in reverse order to get the slope in the other direction
     * @param surface
     * @param startPoint
     * @param endPoint
     * @param peak
     */
    public TriangularDistributedForce(Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(surface, startPoint, endPoint, peak);
    }

    @Override
    AffineQuantity getResultantPosition() {
        
        // get the direction of the surface
        Vector3bd direction = getSurface().getEndpoint1().subtract(getSurface().getEndpoint2());
        direction = direction.normalize();
        
        // develop an affine vector and then dot it with the beam direction
        UnknownPoint start = new UnknownPoint(getStartPoint());
        UnknownPoint end = new UnknownPoint(getEndPoint());
        AffineQuantity startPosition = start.getDirectionalContribution(direction, getSurface().getEndpoint1());
        AffineQuantity endPosition = end.getDirectionalContribution(direction, getSurface().getEndpoint1());
        
        // construct an average:
        AffineQuantity position = 
                startPosition.multiply(new BigDecimal(2.0/3)).add(
                endPosition.multiply(new BigDecimal(1.0/3)));
        return position;
    }

    @Override
    AffineQuantity getResultantMagnitude() {
        // first get the length of the span
        Vector3bd span = getSurface().getEndpoint1().subtract(getSurface().getEndpoint2());
        BigDecimal length = new BigDecimal(span.length());
        
        Vector peak = getPeak();
        if(peak.isSymbol() && !peak.isKnown()) {
            AffineQuantity result = new AffineQuantity(
                    BigDecimal.ZERO, length.multiply(new BigDecimal(.5)), peak.getSymbolName());
            return result;
        } else {
            AffineQuantity result = new AffineQuantity(
                    length.multiply(peak.getDiagramValue()).multiply(new BigDecimal(.5)), BigDecimal.ZERO, null);
            return result;
        }
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        // only one sample is necessary here.
        addRepresentation(new DistributedForceRepresentation(this, 1, 1, 10));
    }
    
    public void createDefaultSchematicRepresentation(float displayScale, int arrows) {
        addRepresentation(new DistributedForceRepresentation(this, 1, displayScale, arrows));
    }

    @Override
    float getCurveValue(float x) {
        return 1-x;
    }

}
