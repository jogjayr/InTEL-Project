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
import edu.gatech.statics.objects.Moment;
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

        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));
        /*getDisplayConstants().setMomentSize(0.2f);
        getDisplayConstants().setForceSize(0.2f);
        getDisplayConstants().setPointSize(0.2f);
        getDisplayConstants().setCylinderRadius(0.2f);
        getDisplayConstants().setForceLabelDistance(1f);
        getDisplayConstants().setMomentLabelDistance(0f);
        getDisplayConstants().setMeasurementSize(0.1f);*/
        getDisplayConstants().setDrawScale(.5f);
        getDisplayConstants().setForceLabelDistance(1);
    }
    Point A, I, H, J, G, F, B, K;
    Roller2d rollerB;
    Pin2d pinA;
    Connector2ForceMember2d twoForceF, twoForceH, twoForceJH, twoForceJB, twoForceGF, twoForceGB;
    Bar topBar, frontBar, backBar, bottomBar;

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.6f, .7f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));

        A = new Point("A", "0", "0", "0");
        I = new Point("I", "4", "7", "0");
        H = new Point("H", "3", "5.26", "0");
        J = new Point("J", "9", "5.26", "0");
        G = new Point("G", "6", "0", "0");
        F = new Point("F", "2.2", "3.8", "0");
        B = new Point("B", "12", "0", "0");
        K = new Point("K", "10", "7", "0");

        Body handlebarBeam = new Beam("Handle Bar", I, A);
        topBar = new Bar("Top Bar", J, H);
        Body seatPoleBeam = new Beam("Seat Pole", K, G);
        frontBar = new Bar("Front Bar", F, G);
        backBar = new Bar("Back Bar", J, B);
        bottomBar = new Bar("Bottom Bar", B, G);
        Body jointAtB = new PointBody("Joint B", B);

        pinA = new Pin2d(A);
        pinA.setName("Pin A");
        rollerB = new Roller2d(B);
        rollerB.setName("Roller B");

        twoForceF = new Connector2ForceMember2d(F, frontBar);
        twoForceF.setName("2FM Connector F");
        twoForceGF = new Connector2ForceMember2d(G, frontBar);
        twoForceGF.setName("2FM Connector GF");
        twoForceH = new Connector2ForceMember2d(H, topBar);
        twoForceH.setName("2FM Connector H");
        twoForceJH = new Connector2ForceMember2d(J, topBar);
        twoForceJH.setName("2FM Connector JH");
        twoForceJB = new Connector2ForceMember2d(J, backBar);
        twoForceJB.setName("2FM Connector JB");
        twoForceGB = new Connector2ForceMember2d(G, bottomBar);
        twoForceGB.setName("2FM Connector GB");

        //rollerA.setDirection(Vector3bd.UNIT_Y);
        rollerB.setDirection(Vector3bd.UNIT_Y);

        DistanceMeasurement distance1 = new DistanceMeasurement(I, A);
        distance1.setName("Measure AI");
        distance1.createDefaultSchematicRepresentation(0.5f);
        distance1.forceVertical();
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(I, A);
        distance2.setName("Measure AI2");
        distance2.createDefaultSchematicRepresentation(0.5f);
        distance2.forceHorizontal();
        schematic.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(B, K);
        distance3.setName("Measure BK");
        distance3.createDefaultSchematicRepresentation(4.1f);
        distance3.forceHorizontal();
        schematic.add(distance3);

        DistanceMeasurement distance4 = new DistanceMeasurement(A, G);
        distance4.setName("Measure AG");
        distance4.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance4);

        DistanceMeasurement distance5 = new DistanceMeasurement(G, B);
        distance5.setName("Measure BG");
        distance5.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance5);

        /*DistanceMeasurement distance6 = new DistanceMeasurement(I, H);
        distance6.forceVertical();
        distance6.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance6);
        
        DistanceMeasurement distance7 = new DistanceMeasurement(I, F);
        distance7.forceVertical();
        distance7.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance7);*/

        PointAngleMeasurement angle1 = new PointAngleMeasurement(A, G, I);
        angle1.setName("Angle AGI");
        angle1.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle1);

        PointAngleMeasurement angle2 = new PointAngleMeasurement(G, A, F);
        angle2.setName("Angle GAF");
        angle2.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle2);

        PointAngleMeasurement angle3 = new PointAngleMeasurement(G, K, B);
        angle3.setName("Angle GKB");
        angle3.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle3);

        PointAngleMeasurement angle4 = new PointAngleMeasurement(B, J, G);
        angle4.setName("Angle BJG");
        angle4.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle4);



        Force seatWeight = new Force(K, Vector3bd.UNIT_Y.negate(), new BigDecimal(500));
        seatWeight.setName("Seat");
        seatPoleBeam.addObject(seatWeight);

        Force pedalWeight = new Force(G, Vector3bd.UNIT_Y.negate(), new BigDecimal(150));
        pedalWeight.setName("Pedals");
        seatPoleBeam.addObject(pedalWeight);

        Vector3bd handleForceDirection = new Vector3bd(
                new BigDecimal(-Math.cos(Math.PI / 6)),
                new BigDecimal(-Math.sin(Math.PI / 6)), BigDecimal.ZERO);
        handleForceDirection = handleForceDirection.normalize();

        FixedAngleMeasurement angle5 = new FixedAngleMeasurement(I, Vector3bd.UNIT_X.negate(), handleForceDirection.toVector3f());
        angle5.setName("Angle B");
        angle5.createDefaultSchematicRepresentation(0.5f);
        schematic.add(angle5);

        Force handleWeight = new Force(I, handleForceDirection, new BigDecimal(20));
        handleWeight.setName("Handlebar Force");
        handlebarBeam.addObject(handleWeight);

        Moment handleMoment = new Moment(I, Vector3bd.UNIT_Z, new BigDecimal(5)); // use symbol here
        handleMoment.setName("Handlebar Moment");
        handlebarBeam.addObject(handleMoment);

        pinA.attachToWorld(handlebarBeam);
        //rollerB.attach(backBar, bottomBar);

        // this is terrible in terms of naming conventino
        // and I apologize for it.
        Connector2ForceMember2d GBatB, JBatB;
        GBatB = new Connector2ForceMember2d(B, bottomBar);
        GBatB.setName("2FM Connector GBatB");
        JBatB = new Connector2ForceMember2d(B, backBar);
        GBatB.setName("2FM Connector JBatB");

        rollerB.attachToWorld(jointAtB);
        GBatB.attach(jointAtB, bottomBar);
        JBatB.attach(jointAtB, backBar);

        twoForceF.attach(frontBar, handlebarBeam);
        twoForceH.attach(topBar, handlebarBeam);
        twoForceJH.attach(topBar, seatPoleBeam);
        twoForceJB.attach(backBar, seatPoleBeam);
        twoForceGF.attach(frontBar, seatPoleBeam);
        twoForceGB.attach(bottomBar, seatPoleBeam);

        A.createDefaultSchematicRepresentation();
        I.createDefaultSchematicRepresentation();
        H.createDefaultSchematicRepresentation();
        J.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        F.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        K.createDefaultSchematicRepresentation();
        //handlebar.createDefaultSchematicRepresentation();
        handleMoment.createDefaultSchematicRepresentation();
        //top.createDefaultSchematicRepresentation();
        //seatPole.createDefaultSchematicRepresentation();
        //front.createDefaultSchematicRepresentation();
        //back.createDefaultSchematicRepresentation();
        //bottom.createDefaultSchematicRepresentation();
        seatWeight.createDefaultSchematicRepresentation();
        pedalWeight.createDefaultSchematicRepresentation();
        handleWeight.createDefaultSchematicRepresentation();
        schematic.add(handlebarBeam);
        schematic.add(topBar);
        schematic.add(seatPoleBeam);
        schematic.add(frontBar);
        schematic.add(backBar);
        schematic.add(bottomBar);
        schematic.add(jointAtB);

        float scale = 1.0f;
        Vector3f modelTranslation = new Vector3f(4.25f, -3.5f, 0);

        ModelNode modelNode = ModelNode.load("bicycle/assets/", "bicycle/assets/bicycle.dae");
        modelNode.extractLights();

        ModelRepresentation rep;

        Matrix3f matrix = new Matrix3f();
        matrix.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_X.negate());

        rep = modelNode.extractElement(backBar, "VisualSceneNode/model/bike/beam3");
        backBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(bottomBar, "VisualSceneNode/model/bike/beam4");
        bottomBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(handlebarBeam, "VisualSceneNode/model/bike/beam6");
        handlebarBeam.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(frontBar, "VisualSceneNode/model/bike/beam1");
        frontBar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(seatPoleBeam, "VisualSceneNode/model/bike/beam5");
        seatPoleBeam.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(seatPoleBeam, "VisualSceneNode/model/bike/seat");
        seatPoleBeam.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelRotation(matrix);
        rep.setLocalScale(scale);
        rep.setModelOffset(modelTranslation);

        rep = modelNode.extractElement(topBar, "VisualSceneNode/model/bike/beam2");
        topBar.addRepresentation(rep);
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
