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

    //Descriptions in individual parts of the exercise

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
        
        DistanceMeasurement measureAD = new DistanceMeasurement(A, D);
        measureAD.createDefaultSchematicRepresentation();
        schematic.add(measureAD);

        DistanceMeasurement measureBC = new DistanceMeasurement(B, C);
        measureBC.createDefaultSchematicRepresentation();
        schematic.add(measureBC);



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
