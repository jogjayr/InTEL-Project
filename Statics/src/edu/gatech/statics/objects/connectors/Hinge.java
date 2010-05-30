/*
 * Hinge.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.objects.Connector;
import com.jme.math.Vector3f;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Hinge extends Connector {

    private Vector3f axis;

    public Vector3f getAxis() {
        return axis;
    }

    public void setAxis(Vector3f axis) {
        this.axis = axis;
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Hinge(String name) {
        super(name);
    }

    /** Creates a new instance of Hinge */
    public Hinge(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    /*return Arrays.asList(
    new Vector(Unit.force, Vector3f.UNIT_X),
    new Vector(Unit.force, Vector3f.UNIT_Y),
    new Vector(Unit.moment, Vector3f.UNIT_Z));*/
    }

    @Override
    public String connectorName() {
        return "hinge";
    }
}
