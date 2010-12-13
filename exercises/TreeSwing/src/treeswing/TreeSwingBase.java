/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package treeswing;

import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.distributed.objects.TrapezoidalDistributedForce;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author vignesh
 */
public abstract class TreeSwingBase extends DistributedExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Jared is a Civil Engineering Student at Georgia Tech "
                + "and he built a tree swing for his little sister Andrea.  He is a little concerned "
                + "that the branch might not be strong enough to handle his sister swinging on it.  "
                + "Before letting her try it out, he wants to make sure that he placed it appropriately "
                + "along the length of the branch and he wants to calculate the forces that the branch will be exposed to.");

        description.setProblemStatement("Part I: Assume the branch is prismatic (has the same cross sectional are through its "
                + "entire length) and has a mass per unit length is 20 kg/m.  Assume that the leaves on the branch are evenly distributed "
                + "through the entire length and have a mass per unit length of 2 kg/m.  Andrea’s mass is 30 kg and neglect the weight of the swing."
                + "Part II: Assume the branch is not prismatic (varies along the length of the branch) and its mass per unit length "
                + "can be expressed by F(x) = -5x + 30, where x denotes the position along the branch measured from where the branch connects "
                + "to the trunk of tree.  Assume that the leaves on the branch are evenly distributed through the entire length and have "
                + "a mass per unit length of 2 kg/m.  Andrea’s mass is 30 kg and neglect the weight of the swing.");

        description.setGoals("Draw a FBD of the branch and solve for the reaction forces.  Solve for the total equivalent concentrated force and find its location.");


        description.setLayout(new ScrollbarLayout());



        description.addImage("treeswing/assets/swing1.jpg");
        description.addImage("treeswing/assets/swing2.jpg");

        return description;

    }

    @Override
    public void initExercise() {

        Unit.setSuffix(Unit.distance, "m");
        Unit.setSuffix(Unit.mass, "g");
        Unit.setSuffix(Unit.force, "g/m");

    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "4", "0", "0");
        Point C = new Point("C", "2", "0", "0");
        Point D = new Point("D", "1", "0", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);

        Beam AB = new Beam("AB", A, B);

        schematic.add(AB);

        AB.createDefaultSchematicRepresentation();

        AB.addObject(A);
        AB.addObject(B);
        AB.addObject(C);
        AB.addObject(D);

        Force forceC = new Force(C, Vector3bd.UNIT_Y.negate(), new BigDecimal(15));
        forceC.setName("Force C");
        forceC.createDefaultSchematicRepresentation();

        Force forceD = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal(15));
        forceD.setName("Force D");
        forceD.createDefaultSchematicRepresentation();

        AB.addObject(forceC);
        AB.addObject(forceD);

        Fix2d fixA = new Fix2d(A);
        fixA.setName("fix A");
        fixA.attachToWorld(AB);

        schematic.add(AB);
//
//        DistributedForce distributedtreeswing = new ConstantDistributedForce("treeSwing", AB, A, B,
//                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("15")));

        DistributedForce distributedtreeswing = createDistributedForce(AB, A, B);

        DistributedForceObject distributedtreeswingObject = new DistributedForceObject(distributedtreeswing, "1");
        AB.addObject(distributedtreeswingObject);

//        distributedtreeswingObject.createDefaultSchematicRepresentation();


        int arrowDensity = 2;
        distributedtreeswingObject.createDefaultSchematicRepresentation(18f / 6, 2 * arrowDensity, 1.75f);
        schematic.add(distributedtreeswingObject);

        DistanceMeasurement measureFull = new DistanceMeasurement(A, B);
        measureFull.createDefaultSchematicRepresentation();
        schematic.add(measureFull);

//        ModelNode modelNode = ModelNode.load("treeswing/assets/", "treeswing/assets/swing.mb");
//        modelNode.extractLights();
//
//        ModelRepresentation rep;
//        String prefix = "/VisualSceneNode/scene/";
//
//        rep = modelNode.getRemainder(schematic.getBackground());
//        schematic.getBackground().addRepresentation(rep);




    }

    abstract protected DistributedForce createDistributedForce(Beam AB, Point A, Point B);
}
