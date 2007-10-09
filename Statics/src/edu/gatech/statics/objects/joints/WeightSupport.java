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
import edu.gatech.statics.objects.Point;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class WeightSupport extends Joint {
    
    /** Creates a new instance of Fix */
    public WeightSupport(Point point) {
        super(point);
    }
    
    public List<Force> getReactionForces() {
        return Collections.singletonList(new Force(getPoint(), Vector3f.UNIT_Y.negate()));
    }

    public List<Moment> getReactionMoments() {
        return Collections.EMPTY_LIST;
    }
}
