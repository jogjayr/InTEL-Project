/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treeswing;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.TrapezoidalDistributedForce;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 *
 * @author vignesh
 */
public class TreeSwingNonprismatic extends TreeSwingBase {

    @Override
    protected DistributedForce createDistributedForce(Beam AB, Point A, Point B) {
        DistributedForce distributedtreeswing = new TrapezoidalDistributedForce("treeSwing", AB, A, B, new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("32")), new BigDecimal("12"));
        return distributedtreeswing;
    }

}
