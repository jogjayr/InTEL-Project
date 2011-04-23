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
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.PointAngleMeasurement;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.bodies.LongBody;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.representations.ImageRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExercise extends FrameExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {

        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());

        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-1, 1, 1, 10);
        vc.setZoomConstraints(0.5f, 2f);
        vc.setRotationConstraints(-1, 1, -1, 1);
        interfaceConfiguration.setViewConstraints(vc);

        return interfaceConfiguration;

    }


    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Holding a Purse");
        description.setNarrative(
                "Whitney Peterson just got a new puppy, and puts it in a bag so " +
                "she can show it to all her friends at her engineering sorority, " +
                "Alpha Omega Epsilon. At the end of the day, though, her arm is " +
                "quite sore, and she wonders how much force her biceps muscle had " +
                "to provide to keep her arm bent at 90 degrees, compared to the puppy’s light weight.");

        float forearmWeight = (Float) getState().getParameter("forearmWeight");
        float purseWeight = (Float) getState().getParameter("purseWeight");

        BigDecimal bdForearmWeight = new BigDecimal(forearmWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
        BigDecimal bdPurseWeight = new BigDecimal(purseWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);

//        description.setProblemStatement(
//                "Here is a simplified model of the human arm. " +
//                "Please solve for the reactions at each of the points: B, C, and E. " +
//                "C and E are both pins, but there is a couple due to the shoulder exerting a moment at E. " +
//                "You can treat the bicep (BD) as a cable, but you do not need to build a diagram for it alone. " +
//                "The weight of the forearm is " + bdForearmWeight + " N at G, and the weight of the purse is " + bdPurseWeight + " N at A.");
//
//        description.setGoals("Solve for the reactions in the tendon and at the shoulder.");


        description.setProblemStatement(
                "This shows a simplified model of the human arm.  " +
                "Points C and E are both pins, but there is a couple due to the shoulder " +
                "exerting a moment at E (note: in real life there is no moment at the shoulder, " +
                "but on this simplified model a moment is necessary).  " +
                "You can treat the biceps (BD) as a cable.");

        description.setGoals(
                "Solve for the reactions at C and E, " +
                "and for the force in the biceps (BD). " +
                "You will need to do this in 2 steps: first, " +
                "use the FBD of the lower arm to get the reactions at C and biceps force. " +
                "Next, use the FBD of the entire system " +
                "(lower arm + biceps + upper arm) to solve for the reactions at E.");

        description.addImage("example01/assets/arm-purse1.png");
        description.addImage("example01/assets/arm-purse2.png");


//        description.addImage("example01/assets/arm-purse3.jpg");
//        description.addImage("example01/assets/arm-purse4.jpg");
//        description.addImage("example01/assets/arm-purse5.jpg");

        return description;
    }

    @Override
    public void initExercise() {
//        setName("Holding a Purse");
        StaticsApplication.getApp().createDisplayGroup("Bones", "bones");

//        float forearmWeight = (Float) getState().getParameter("forearmWeight");
//        float purseWeight = (Float) getState().getParameter("purseWeight");

//        BigDecimal bdForearmWeight = new BigDecimal(forearmWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
//        BigDecimal bdPurseWeight = new BigDecimal(purseWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);

//        setDescription(
//                "Here is a simplified model of the human arm. " +
//                "Please solve for the reactions at each of the points: B, C, and E. " +
//                "C and E are both pins, but there is a couple due to the shoulder exerting a moment at E. " +
//                "You can treat the bicep (BD) as a cable, but you do not need to build a diagram for it alone. " +
//                "The weight of the forearm is " + bdForearmWeight + " N at G, and the weight of the purse is " + bdPurseWeight + " N at A.");

        //"Here is a simplified version of the human arm. " +
        //"Please build a Free Body Diagram of the Forearm, and solve for the tension in the tendon. " +
        //"The weight of the forearm is 9 N and its center of mass is at G. " +
        //"The weight of the purse is 19.6 N.");

        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*mm");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));
        getDisplayConstants().setMomentSize(4f);
        getDisplayConstants().setForceSize(3f);
        getDisplayConstants().setPointSize(2f);
        getDisplayConstants().setCylinderRadius(3f);
        getDisplayConstants().setMomentLabelDistance(40f);
        getDisplayConstants().setMeasurementBarSize(3f);
       
     
        //Unit.setDisplayScale(Unit.force, new BigDecimal(".1")); // this doesn't work yet
    }

    @Override
    public void initParameters() {
        getState().setParameter("handPoint", -17f);
        getState().setParameter("tendonAnchorB", 13f);
        getState().setParameter("tendonAnchorD", 13f);
        getState().setParameter("shoulderHeight", 16f);
        getState().setParameter("forearmWeight", 9f);
        getState().setParameter("purseWeight", 19.6f);
        getState().setParameter("centerGravityOffset", 0f);
    }

    @Override
    public void applyParameters() {

        // NOTE: this is a very awkward way of constructing parameters.
        // the awkwardness is because of a rushed job in designing and implementing the parameters.
        // future exercises should use care in designing and applying the parameters.

        float handPoint = (Float) getState().getParameter("handPoint");
        float tendonAnchorB = (Float) getState().getParameter("tendonAnchorB");
        float tendonAnchorD = (Float) getState().getParameter("tendonAnchorD");
        float shoulderHeight = (Float) getState().getParameter("shoulderHeight");
        float centerGravityOffset = (Float) getState().getParameter("centerGravityOffset");

        Vector3bd aPos = A.getPosition();
        Vector3bd bPos = B.getPosition();
        Vector3bd dPos = D.getPosition();
        Vector3bd ePos = E.getPosition();
        Vector3bd gPos = G.getPosition();

        aPos.setX(new BigDecimal("" + handPoint));
        bPos.setX(new BigDecimal("" + tendonAnchorB));
        dPos.setY(new BigDecimal("" + tendonAnchorD));
        ePos.setY(new BigDecimal("" + shoulderHeight));
        gPos.setX(new BigDecimal("" + centerGravityOffset));

        A.setPoint(aPos);
        B.setPoint(bPos);
        D.setPoint(dPos);
        E.setPoint(ePos);
        G.setPoint(gPos);

        upperArm.setByEndpoints(E.getPosition(), C.getPosition());
        forearm.setByEndpoints(A.getPosition(), C.getPosition());
        tendon.setByEndpoints(B.getPosition(), D.getPosition());

        purse.getVector().setDiagramValue(new BigDecimal("" + getState().getParameter("purseWeight")));
        forearm.getWeight().setDiagramValue(new BigDecimal((Float) getState().getParameter("forearmWeight"))); // ???
    }
    Point A, B, C, D, E, G;
    Connector2ForceMember2d jointB, jointD;
    Pin2d jointC, jointE;
    Cable tendon;
    LongBody upperArm, forearm;
    Force purse;

    @Override
    public void loadExercise() {

        Schematic world = getSchematic();

        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.96f, 0.98f, 0.90f, 1.0f));
            StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        }

        A = new Point("A", "" + 0, "-16", "0");
        B = new Point("B", "" + 0, "-16", "0");
        C = new Point("C", "18", "-16", "0");
        D = new Point("D", "18", "" + (0 + 6), "0");
        E = new Point("E", "18", "" + (0 + 6), "0");

        upperArm = new Beam("Upper Arm", E, C);
        forearm = new Beam("Forearm", C, A);
        tendon = new Cable("Bicep Muscle", D, B);

//        upperArm.setName("Upper Arm");
//        forearm.setName("Forearm");
//        tendon.setName("Bicep Muscle");

        jointB = new Connector2ForceMember2d(B, tendon);
        jointD = new Connector2ForceMember2d(D, tendon);
        jointC = new Pin2d(C);
        jointE = new Pin2d(E);

        jointB.setName("connector B");
        jointD.setName("connector D");
        jointC.setName("pin C");
        jointE.setName("pin E");

        G = new Point("G", "" + (0 + 3), "-16", "0");
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
        angle1.createDefaultSchematicRepresentation(4f);
        world.add(angle1);

        //AngleMeasurement angle2 = new AngleMeasurement(A, B, E);
        //angle2.createDefaultSchematicRepresentation(2f);
        //world.add(angle2);

        PointAngleMeasurement angle3 = new PointAngleMeasurement(D, B, C);
        angle3.setName("Angle DBC");
        angle3.createDefaultSchematicRepresentation(14f);
        world.add(angle3);

        purse = new Force(A, Vector3bd.UNIT_Y.negate(), BigDecimal.ONE);
        purse.setName("Purse");
        forearm.addObject(purse);

        Moment shoulder = new Moment(E, Vector3bd.UNIT_Z.negate(), "M shoulder"); // use symbol here
        shoulder.setName("M Shoulder");

        shoulder.getAnchoredVector().setSymbol("Shoulder");
        upperArm.addObject(shoulder);

        jointB.attach(forearm, tendon);
        jointC.attach(forearm, upperArm);
        jointD.attach(upperArm, tendon);
        jointE.attachToWorld(upperArm);

        //jointC.createDefaultSchematicRepresentation();
        //jointE.createDefaultSchematicRepresentation();

//        E.setName("E");
//        D.setName("D");
//        C.setName("C");
//        B.setName("B");
//        A.setName("A");
//        G.setName("G");

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

        //forearm.getWeight().setDiagramValue(new BigDecimal(forearmWeight)); // ???

        forearm.setCenterOfMassPoint(G);

        world.add(upperArm);
        world.add(forearm);
        world.add(tendon);

        RepresentationLayer bones = new RepresentationLayer("bones", RepresentationLayer.modelBodies.getPriority() - 1);
        RepresentationLayer.addLayer(bones);

        // do not load these if there is no display
        // this is unusual: most exercises should not need to worry about this,
        // but we check here because the purse problem is a good baseline to test on.
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {

            ImageRepresentation imageRep;
            float repScale = .025f;

            imageRep = new ImageRepresentation(forearm, loadTexture("example01/assets/lowerArm.png"));
            imageRep.setScale(repScale * 1738, repScale * 501f);
            imageRep.setTranslation(-1, 1.75f, 0);
            

            imageRep.setGrayColors(new ColorRGBA(.5f, .5f, .5f, .25f),
                new ColorRGBA(.4f, .4f, .4f, .25f));
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
            //A.addRepresentation(imageRep);
            getSchematic().getBackground().addRepresentation(imageRep);

            imageRep = new ImageRepresentation(tendon, loadTexture("example01/assets/muscle.png"));
            imageRep.setLayer(bones);
            imageRep.setRotation(-.075f);
            //imageRep.setScale(repScale * 121f,repScale * 347f);
            imageRep.setScale(.9f * repScale * 213f, .9f * repScale * 1331f);
            imageRep.setTranslation(0.0f, 0.5f, .05f);
            tendon.addRepresentation(imageRep);
        }
    }
}
