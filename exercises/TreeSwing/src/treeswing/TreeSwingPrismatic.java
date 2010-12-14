/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treeswing;

import edu.gatech.statics.exercise.persistence.test.A;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.modes.distributed.objects.ConstantDistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 *
 * @author vignesh
 */
public class TreeSwingPrismatic extends TreeSwingBase {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Jared is a Civil Engineering Student at Georgia Tech "
                + "and he built a tree swing for his little sister Andrea. He is a little concerned "
                + "that the branch might not be strong enough to handle his sister swinging on it.  "
                + "Before letting her try it out, he wants to make sure that he placed it appropriately "
                + "along the length of the branch and he wants to calculate the forces that the branch will be exposed to.");

        description.setProblemStatement("Assume the branch is prismatic (has the same cross sectional are through its "
                + "entire length) and has a mass per unit length is 20 kg/m.  Assume that the leaves on the branch are evenly distributed "
                + "through the entire length and have a mass per unit length of 2 kg/m.  Andrea’s mass is 30 kg and neglect the weight of the swing.");

        description.setGoals("Draw a FBD of the branch and solve for the reaction forces.  Solve for the total equivalent concentrated force and find its location.");


        //description.setLayout(new ScrollbarLayout());



        description.addImage("treeswing/assets/swing1.jpg");
        description.addImage("treeswing/assets/swing2.jpg");

        return description;

    }
    
    @Override
    protected DistributedForce createDistributedForce(Beam AB, Point A, Point B) {
        DistributedForce distributedtreeswing = new ConstantDistributedForce(
                "treeSwing", AB, A, B, new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("22")));
        return distributedtreeswing;
    }

}