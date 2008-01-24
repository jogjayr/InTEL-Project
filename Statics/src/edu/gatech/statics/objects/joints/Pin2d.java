/*
 * Pin.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.joints;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
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
                new Vector(Unit.force, Vector3f.UNIT_X),
                new Vector(Unit.force, Vector3f.UNIT_Y));
    }
}
