/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicycle;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.PointAngleMeasurement;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class BicycleExercise extends FrameExercise {

    public BicycleExercise() {
    }

    @Override
    public void initExercise() {
        setName("Bicycle");

        setDescription(
                "Someone pushing against a wedge on a bike!");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal("10"));

        getDisplayConstants().setDrawScale(.5f);
        getDisplayConstants().setForceLabelDistance(1);
    }
    private Point A,  B,  C,  D,  E,  F,  G;
    private Pin2d rollerA;
    private Roller2d rollerD;
    //private Connector2ForceMember2d twoForceMiddleB,  twoForceTopB,  twoForceBackC,  twoForceTopC,  twoForceSeatC,  twoForceBottomE,  twoForceFrontE,  twoForceSeatE,  twoForceFrontG;
    private Bar CB_topBar,  CD_backBar,  DE_bottomBar,  GE_frontBar,  BE_middleBar,  CE_seatPoleBar;
    private Body FA_handlebarBeam,  jointAtD,  jointAtE,  jointAtC;

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.6f, .7f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));

        A = new Point("A", "0", "0", "0");
        B = new Point("B", "3", "5.196", "0");
        C = new Point("C", "9", "5.196", "0");
        D = new Point("D", "12", "0", "0");
        E = new Point("E", "6", "0", "0");
        F = new Point("F", "4", "6.928", "0");
        G = new Point("G", "1.5", "2.598", "0");

        FA_handlebarBeam = new Beam("Handle Bar", F, A);
        CE_seatPoleBar = new Bar("Seat Pole", C, E);
        CB_topBar = new Bar("Top Bar", C, B);
        CD_backBar = new Bar("Back Bar", C, D);
        DE_bottomBar = new Bar("Bottom Bar", D, E);
        GE_frontBar = new Bar("Front Bar", G, E);
        BE_middleBar = new Bar("Middle Bar", B, E);
        jointAtD = new PointBody("Joint D", D);
        jointAtC = new PointBody("Joint C", C);
        jointAtE = new PointBody("Joint E", E);

        DistanceMeasurement distance1 = new DistanceMeasurement(F, A);
        distance1.setName("Measure FA");
        distance1.createDefaultSchematicRepresentation(0.5f);
        distance1.forceVertical();
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(A, E);
        distance2.setName("Measure AE");
        distance2.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(E, D);
        distance3.setName("Measure ED");
        distance3.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance3);

        DistanceMeasurement distance4 = new DistanceMeasurement(B, A);
        distance4.setName("Measure AB");
        distance4.createDefaultSchematicRepresentation(0.5f);
        distance4.forceVertical();
        schematic.add(distance4);

        DistanceMeasurement distance5 = new DistanceMeasurement(F, A);
        distance5.setName("Measure AF");
        distance5.createDefaultSchematicRepresentation(0.5f);
        distance5.forceHorizontal();
        schematic.add(distance5);

        PointAngleMeasurement angle1 = new PointAngleMeasurement(B, G, E);
        angle1.setName("Angle GBE");
        angle1.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle1);

        PointAngleMeasurement angle2 = new PointAngleMeasurement(B, E, C);
        angle2.setName("Angle EBC");
        angle2.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle2);

        PointAngleMeasurement angle3 = new PointAngleMeasurement(C, B, E);
        angle3.setName("Angle BCE");
        angle3.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle3);

        PointAngleMeasurement angle4 = new PointAngleMeasurement(D, C, E);
        angle4.setName("Angle CDE");
        angle4.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle4);

        PointAngleMeasurement angle6 = new PointAngleMeasurement(E, C, D);
        angle6.setName("Angle CED");
        angle6.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle6);

        PointAngleMeasurement angle7 = new PointAngleMeasurement(E, G, A);
        angle7.setName("Angle GEA");
        angle7.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle7);

        PointAngleMeasurement angle8 = new PointAngleMeasurement(A, G, E);
        angle8.setName("Angle GAE");
        angle8.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle8);

        Force seatWeight = new Force(C, Vector3bd.UNIT_Y.negate(), new BigDecimal(500));
        seatWeight.setName("Seat");
        CE_seatPoleBar.addObject(seatWeight);

        Force pedalWeight = new Force(E, Vector3bd.UNIT_Y.negate(), new BigDecimal(150));
        pedalWeight.setName("Pedals");
        CE_seatPoleBar.addObject(pedalWeight);

        Force wedgeForce = new Force(C, Vector3bd.UNIT_X, new BigDecimal(17.3205));
        wedgeForce.setName("Wedge Force");
        CE_seatPoleBar.addObject(wedgeForce);

        Vector3bd handleForceDirection = new Vector3bd(
                new BigDecimal(-Math.cos(Math.PI / 6)),
                new BigDecimal(-Math.sin(Math.PI / 6)), BigDecimal.ZERO);
        handleForceDirection = handleForceDirection.normalize();

        FixedAngleMeasurement angle5 = new FixedAngleMeasurement(F, Vector3bd.UNIT_X.negate(), handleForceDirection.toVector3f());
        angle5.setName("Angle F");
        angle5.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle5);

        Force handleWeight = new Force(F, handleForceDirection, new BigDecimal(20));
        handleWeight.setName("Handlebar Force");
        FA_handlebarBeam.addObject(handleWeight);

        // **** CONNECTORS ***
        // *** all connectors that attach to the beam part of the frame.
        Connector2ForceMember2d c2fm_GE_G = new Connector2ForceMember2d(G, GE_frontBar);
        Connector2ForceMember2d c2fm_BE_B = new Connector2ForceMember2d(B, BE_middleBar);
        Connector2ForceMember2d c2fm_CB_B = new Connector2ForceMember2d(B, CB_topBar);
        c2fm_GE_G.attach(GE_frontBar, FA_handlebarBeam);
        c2fm_BE_B.attach(BE_middleBar, FA_handlebarBeam);
        c2fm_CB_B.attach(CB_topBar, FA_handlebarBeam);

        //rollerA = new Roller2d(A);
        rollerA = new Pin2d(A);
        rollerA.attachToWorld(FA_handlebarBeam);
        //rollerA.setDirection(Vector3bd.UNIT_Y);
        rollerA.setName("Roller A");

        // *** all connectors that attach to C
        Connector2ForceMember2d c2fm_CB_C = new Connector2ForceMember2d(C, CB_topBar);
        Connector2ForceMember2d c2fm_CE_C = new Connector2ForceMember2d(C, CE_seatPoleBar);
        Connector2ForceMember2d c2fm_CD_C = new Connector2ForceMember2d(C, CD_backBar);
        c2fm_CB_C.attach(CB_topBar, jointAtC);
        c2fm_CE_C.attach(CE_seatPoleBar, jointAtC);
        c2fm_CD_C.attach(CD_backBar, jointAtC);

        // *** all connectors that attach to E
        Connector2ForceMember2d c2fm_GE_E = new Connector2ForceMember2d(E, GE_frontBar);
        Connector2ForceMember2d c2fm_BE_E = new Connector2ForceMember2d(E, BE_middleBar);
        Connector2ForceMember2d c2fm_CE_E = new Connector2ForceMember2d(E, CE_seatPoleBar);
        Connector2ForceMember2d c2fm_DE_E = new Connector2ForceMember2d(E, DE_bottomBar);
        c2fm_BE_E.attach(BE_middleBar, jointAtE);
        c2fm_GE_E.attach(GE_frontBar, jointAtE);
        c2fm_CE_E.attach(CE_seatPoleBar, jointAtE);
        c2fm_DE_E.attach(DE_bottomBar, jointAtE);

        // *** all connectors that attach to D
        Connector2ForceMember2d c2fm_DE_D = new Connector2ForceMember2d(D, DE_bottomBar);
        Connector2ForceMember2d c2fm_CD_D = new Connector2ForceMember2d(D, CD_backBar);
        c2fm_DE_D.attach(DE_bottomBar, jointAtD);
        c2fm_CD_D.attach(CD_backBar, jointAtD);

        rollerD = new Roller2d(D);
        rollerD.setName("Roller D");
        rollerD.setDirection(Vector3bd.UNIT_Y);
        rollerD.attachToWorld(jointAtD);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        F.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        jointAtD.createDefaultSchematicRepresentation();
        jointAtC.createDefaultSchematicRepresentation();
        jointAtE.createDefaultSchematicRepresentation();

        seatWeight.createDefaultSchematicRepresentation();
        pedalWeight.createDefaultSchematicRepresentation();
        handleWeight.createDefaultSchematicRepresentation();
        wedgeForce.createDefaultSchematicRepresentation();

        schematic.add(FA_handlebarBeam);
        schematic.add(CB_topBar);
        schematic.add(CE_seatPoleBar);
        schematic.add(GE_frontBar);
        schematic.add(CD_backBar);
        schematic.add(BE_middleBar);
        schematic.add(DE_bottomBar);
        schematic.add(jointAtD);
        schematic.add(jointAtC);
        schematic.add(jointAtE);

        float scale = 1.0f;
        Vector3f modelTranslation = new Vector3f(4.25f, -3.5f, 0);

        ModelNode modelNode = ModelNode.load("bicycle/assets/", "bicycle/assets/bicycle.dae");
        modelNode.extractLights();

        ModelRepresentation rep;

        Matrix3f matrix = new Matrix3f();
        matrix.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_X.negate());

        rep = modelNode.extractElement(CD_backBar, "VisualSceneNode/model/bike/beam3");
        CD_backBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(DE_bottomBar, "VisualSceneNode/model/bike/beam4");
        DE_bottomBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(FA_handlebarBeam, "VisualSceneNode/model/bike/beam6");
        FA_handlebarBeam.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(GE_frontBar, "VisualSceneNode/model/bike/beam1");
        GE_frontBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(CE_seatPoleBar, "VisualSceneNode/model/bike/beam5");
        CE_seatPoleBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(CE_seatPoleBar, "VisualSceneNode/model/bike/seat");
        CE_seatPoleBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(CB_topBar, "VisualSceneNode/model/bike/beam2");
        CB_topBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(BE_middleBar, "VisualSceneNode/model/bike/beam7");
        BE_middleBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);
    }
}
