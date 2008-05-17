/*
 * Pin.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.joints;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.PointRepresentation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Pin2d extends Joint {

    /** Creates a new instance of Pin */
    public Pin2d(Point point) {
        super(point);
    }

    @Override
    public boolean isForceDirectionNegatable() {
        return true;
    }

    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, Vector3bd.UNIT_X, new BigDecimal(1)),
                new Vector(Unit.force, Vector3bd.UNIT_Y, new BigDecimal(1)));
    }

    @Override
    public void createDefaultSchematicRepresentation() {

        PointRepresentation rep = new PointRepresentation(this, "rsrc/pin.png");
        rep.getQuad().setLocalScale(2);
        rep.getQuad().setLocalTranslation(0, -.5f, 0);
        addRepresentation(rep);
    }
}
