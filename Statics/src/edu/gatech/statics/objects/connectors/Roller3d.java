/*
 * Roller3d.java
 *
 * Created on June 11, 2007, 12:26 PM
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
public class Roller3d extends Connector {

    private Vector3f normal;

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    /** Creates a new instance of Roller3d */
    public Roller3d(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
