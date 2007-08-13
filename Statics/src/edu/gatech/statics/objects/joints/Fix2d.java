/*
 * Fix.java
 *
 * Created on June 11, 2007, 12:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.joints;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Moment;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Fix2d extends Joint {
    
    /** Creates a new instance of Fix */
    public Fix2d(Vector3f position) {
        super(position);
    }
    
    public boolean isForceDirectionNegatable() {return true;}

    public List<Force> getReactionForces() {
        List<Force> r = new LinkedList();
        r.add(new Force(this, Vector3f.UNIT_X));
        r.add(new Force(this, Vector3f.UNIT_Y));
        return r;
    }

    public List<Moment> getReactionMoments() {
        return Collections.singletonList(new Moment(this, Vector3f.UNIT_Z));
    }
    
}
