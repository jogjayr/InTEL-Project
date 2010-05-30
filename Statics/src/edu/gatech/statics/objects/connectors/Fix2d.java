/*
 * Fix.java
 *
 * Created on June 11, 2007, 12:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Fix2d extends Connector {

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Fix2d(String name) {
        super(name);
    }

    /** Creates a new instance of Fix */
    public Fix2d(Point point) {
        super(point);
    }

    @Override
    public boolean isForceDirectionNegatable() {
        return true;
    }

    /*public List<Force> getReactionForces() {
    List<Force> r = new LinkedList();
    r.add(new Force(getPoint(), Vector3f.UNIT_X));
    r.add(new Force(getPoint(), Vector3f.UNIT_Y));
    return r;
    }
    public List<Moment> getReactionMoments() {
    return Collections.singletonList(new Moment(getPoint(), Vector3f.UNIT_Z));
    }*/
    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, Vector3bd.UNIT_X, ""),
                new Vector(Unit.force, Vector3bd.UNIT_Y, ""),
                new Vector(Unit.moment, Vector3bd.UNIT_Z, ""));
    }

    @Override
    public String connectorName() {
        return "fix";
    }
}
