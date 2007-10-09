/*
 * Roller3d.java
 *
 * Created on June 11, 2007, 12:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.joints;

import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Roller3d extends Joint {
    
    private Vector3f normal;
    public Vector3f getNormal() {return normal;}
    public void setNormal(Vector3f normal) {this.normal = normal;}
    
    /** Creates a new instance of Roller3d */
    public Roller3d(Point point) {
        super(point);
    }

    public List<Force> getReactionForces() {
        return null;
    }

    public List<Moment> getReactionMoments() {
        return null;
    }
    
}
