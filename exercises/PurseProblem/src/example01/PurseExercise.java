/*
 * PurseExercise.java
 *
 * Created on August 4, 2007, 11:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package example01;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.PointAngleMeasurement;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.representations.ImageRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExercise extends FrameExercise {

    @Override
    public void initExercise() {
        setName("Holding a Purse");
        StaticsApplication.getApp().createDisplayGroup("Bones", "bones");

        BigDecimal bdForearmWeight = new BigDecimal(forearmWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
        BigDecimal bdPurseWeight = new BigDecimal(purseWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);

        setDescription(
                "Here is a simplified model of the human arm. " +
                "Please solve for the reactions at each of the points: B, C, and E. " +
                "C and E are both pins, but there is a couple due to the shoulder exerting a moment at E. " +
                "You can treat the bicep (BD) as a cable, but you do not need to build a diagram for it alone. " +
                "The weight of the forearm is " + bdForearmWeight + " N at G, and the weight of the purse is " + bdPurseWeight + " N at A.");
                //"Here is a simplified version of the human arm. " +
                //"Please build a Free Body Diagram of the Forearm, and solve for the tension in the tendon. " +
                //"The weight of the forearm is 9 N and its center of mass is at G. " +
                //"The weight of the purse is 19.6 N.");

        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*mm");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));
        getDisplayConstants().setMomentSize(2f);
        getDisplayConstants().setForceSize(2f);
        getDisplayConstants().setPointSize(2f);
        getDisplayConstants().setCylinderRadius(2f);
        getDisplayConstants().setForceLabelDistance(9f);
        getDisplayConstants().setMomentLabelDistance(40f);
        getDisplayConstants().setMeasurementSize(0.5f);
    //Unit.setDisplayScale(Unit.force, new BigDecimal(".1")); // this doesn't work yet
    }
    protected float handPoint = -17;
    protected float tendonAnchorB = 13;
    protected float tendonAnchorD = 13;
    protected float shoulderHeight = 16;
    protected float forearmWeight = 9;
    protected float purseWeight = 19.6f;
    protected float centerGravityOffset = 0;
    Point A, B, C, D, E, G;
    Connector2ForceMember2d jointB, jointD;
    Pin2d jointC, jointE;
    Cable tendon;
    Body upperArm, forearm;

    @Override
    public void loadExercise() {

        Schematic world = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.96f, 0.98f, 0.90f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));

        A = new Point("" + handPoint, "-10", "0");
        B = new Point("" + tendonAnchorB, "-10", "0");
        C = new Point("18", "-10", "0");
        D = new Point("18", "" + (tendonAnchorD + 6), "0");
        E = new Point("18", "" + (shoulderHeight + 6), "0");

        upperArm = new Beam(E, C);
        forearm = new Beam(C, A);
        tendon = new Cable(D, B);

        upperArm.setName("Upper Arm");
        forearm.setName("Forearm");
        tendon.setName("Bicep Muscle");

        jointB = new Connector2ForceMember2d(B, tendon);
        jointD = new Connector2ForceMember2d(D, tendon);
        jointC = new Pin2d(C);
        jointE = new Pin2d(E);
        
        jointB.setName("connector B");
        jointD.setName("connector D");
        jointC.setName("pin C");
        jointE.setName("pin E");

        G = new Point("" + (centerGravityOffset + 3), "-10", "0");

        DistanceMeasurement distance1 = new DistanceMeasurement(A, C);
        distance1.setName("measure AC");
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(G, B);
        distance2.setName("measure GB");
        distance2.createDefaultSchematicRepresentation(3f);
        world.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(B, C);
        distance3.setName("measure BC");
        distance3.createDefaultSchematicRepresentation(3f);
        world.add(distance3);

        DistanceMeasurement distance4 = new DistanceMeasurement(C, D);
        distance4.setName("measure CD");
        distance4.createDefaultSchematicRepresentation(5f);
        world.add(distance4);

        DistanceMeasurement distance5 = new DistanceMeasurement(D, E);
        distance5.setName("measure DE");
        distance5.createDefaultSchematicRepresentation(5f);
        world.add(distance5);

        PointAngleMeasurement angle1 = new PointAngleMeasurement(B, D, C);
        angle1.setName("Angle BDC");
        angle1.createDefaultSchematicRepresentation(2f);
        world.add(angle1);

        //AngleMeasurement angle2 = new AngleMeasurement(A, B, E);
        //angle2.createDefaultSchematicRepresentation(2f);
        //world.add(angle2);

        PointAngleMeasurement angle3 = new PointAngleMeasurement(D, B, C);
        angle3.setName("Angle DBC");
        angle3.createDefaultSchematicRepresentation(2f);
        world.add(angle3);

        Force purse = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal(purseWeight));
        purse.setName("Purse");
        forearm.addObject(purse);

        Moment shoulder = new Moment(E, Vector3bd.UNIT_Z.negate(), "M shoulder"); // use symbol here

        shoulder.getAnchoredVector().setSymbol("Shoulder");
        upperArm.addObject(shoulder);

        jointB.attach(forearm, tendon);
        jointC.attach(forearm, upperArm);
        jointD.attach(upperArm, tendon);
        jointE.attachToWorld(upperArm);

        //jointC.createDefaultSchematicRepresentation();
        //jointE.createDefaultSchematicRepresentation();

        E.setName("E");
        D.setName("D");
        C.setName("C");
        B.setName("B");
        A.setName("A");
        G.setName("G");

        E.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        upperArm.createDefaultSchematicRepresentation();
        forearm.createDefaultSchematicRepresentation();
        tendon.createDefaultSchematicRepresentation();
        purse.createDefaultSchematicRepresentation();
        shoulder.createDefaultSchematicRepresentation();
        //weight.createDefaultSchematicRepresentation();

        forearm.getWeight().setDiagramValue(new BigDecimal(forearmWeight)); // ???

        forearm.setCenterOfMassPoint(G);

        world.add(upperArm);
        world.add(forearm);
        world.add(tendon);

        RepresentationLayer bones = new RepresentationLayer("bones", RepresentationLayer.modelBodies.getPriority() - 1);
        RepresentationLayer.addLayer(bones);

        ImageRepresentation imageRep;
        float repScale = .025f;

        imageRep = new ImageRepresentation(forearm, loadTexture("example01/assets/lowerArm.png"));
        imageRep.setScale(repScale * 1738, repScale * 501f);
        imageRep.setTranslation(-1, 1.75f, 0);
        forearm.addRepresentation(imageRep);

        imageRep = new ImageRepresentation(forearm, loadTexture("example01/assets/lowerArm_bone.png"));
        imageRep.setLayer(bones);
        imageRep.setScale(repScale * 1617, repScale * 310f);
        imageRep.setTranslation(-2.25f, -0.5f, .02f);
        forearm.addRepresentation(imageRep);


        imageRep = new ImageRepresentation(upperArm, loadTexture("example01/assets/upperArm.png"));
        imageRep.setScale(repScale * 483f, repScale * 1539f);
        imageRep.setTranslation(-0.75f, 1.0f, 0);
        upperArm.addRepresentation(imageRep);

        imageRep = new ImageRepresentation(upperArm, loadTexture("example01/assets/upperArm_bone.png"));
        imageRep.setLayer(bones);
        imageRep.setScale(repScale * 191f, repScale * 1453f);
        imageRep.setTranslation(-0.5f, 1, 0);
        upperArm.addRepresentation(imageRep);


        imageRep = new ImageRepresentation(A, loadTexture("example01/assets/purse.png"));
        imageRep.setScale(repScale * 538f, repScale * 835f);
        imageRep.setTranslation(0, -11.0f, .02f);
        A.addRepresentation(imageRep);

        imageRep = new ImageRepresentation(tendon, loadTexture("example01/assets/muscle.png"));
        imageRep.setLayer(bones);
        imageRep.setRotation(-.075f);
        //imageRep.setScale(repScale * 121f,repScale * 347f);
        imageRep.setScale(.9f * repScale * 213f, .9f * repScale * 1331f);
        imageRep.setTranslation(0.0f, 0.5f, .05f);
        tendon.addRepresentation(imageRep);
    }
}
