/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

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
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.Solve2FMTask;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class KeyboardExercise extends FrameExercise {

    @Override
    public void initExercise() {
        setName("Keyboard Stand");

        setDescription(
                "This is a keyboard stand supported by two beams and a cross bar, PQ. " +
                "Find the force in PQ and define whether it is in tension or compression.");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal("10"));
        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setForceLabelDistance(1f);
        getDisplayConstants().setMomentLabelDistance(0f);
        getDisplayConstants().setMeasurementSize(0.1f);
    }
    Point A, B, C, D, E, P, Q;
    Pin2d jointC;
    Connector2ForceMember2d jointP, jointQ;
    Roller2d jointB, jointE;
    Body leftLeg, rightLeg;
    Bar bar;

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.7f, .7f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));

        A = new Point("0", "6", "0");
        D = new Point("8", "6", "0");
        B = new Point("8", "0", "0");
        E = new Point("0", "0", "0");
        C = new Point("4", "3", "0");
        P = new Point("2.7", "4", "0");
        Q = new Point("5.3", "4", "0");

        leftLeg = new Beam(B, A);
        bar = new Bar(P, Q);
        rightLeg = new Beam(E, D);

        leftLeg.setName("Left Leg");
        bar.setName("Bar");
        rightLeg.setName("Right Leg");

        jointC = new Pin2d(C);
        jointP = new Connector2ForceMember2d(P, bar);  //Pin2d(P);
        jointQ = new Connector2ForceMember2d(Q, bar); //new Pin2d(Q);

        jointB = new Roller2d(B);
        jointE = new Roller2d(E);

        jointB.setDirection(Vector3bd.UNIT_Y);
        jointE.setDirection(Vector3bd.UNIT_Y);

        DistanceMeasurement distance1 = new DistanceMeasurement(D, A);
        distance1.setName("Measure AD");
        distance1.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(C, D);
        distance2.setName("Measure CD");
        distance2.createDefaultSchematicRepresentation(0.5f);
        distance2.forceVertical();
        schematic.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(C, Q);
        distance3.setName("Measure CQ");
        distance3.createDefaultSchematicRepresentation(1f);
        distance3.forceVertical();
        schematic.add(distance3);

        DistanceMeasurement distance4 = new DistanceMeasurement(B, D);
        distance4.setName("Measure BD");
        distance4.createDefaultSchematicRepresentation(2.4f);
        schematic.add(distance4);

        Force keyboardLeft = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal(50));
        keyboardLeft.setName("Keyboard Left");
        leftLeg.addObject(keyboardLeft);

        Force keyboardRight = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal(50));
        keyboardRight.setName("Keyboard Right");
        rightLeg.addObject(keyboardRight);

        jointC.attach(leftLeg, rightLeg);
        jointC.setName("Joint C");
        jointP.attach(leftLeg, bar);
        jointP.setName("Joint P");
        jointQ.attach(bar, rightLeg);
        jointQ.setName("Joint Q");
        jointE.attachToWorld(rightLeg);
        jointE.setName("Joint E");
        jointB.attachToWorld(leftLeg);
        jointB.setName("Joint B");
        
        A.setName("A");
        B.setName("B");
        C.setName("C");
        D.setName("D");
        E.setName("E");
        P.setName("P");
        Q.setName("Q");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        P.createDefaultSchematicRepresentation();
        Q.createDefaultSchematicRepresentation();

        keyboardLeft.createDefaultSchematicRepresentation();
        keyboardRight.createDefaultSchematicRepresentation();
        //leftLeg.createDefaultSchematicRepresentation();
        //bar.createDefaultSchematicRepresentation();
        //rightLeg.createDefaultSchematicRepresentation();

        schematic.add(leftLeg);
        schematic.add(bar);
        schematic.add(rightLeg);

        ModelNode modelNode = ModelNode.load("keyboard/assets/", "keyboard/assets/keyboard.dae");

        float scale = .28f;

        ModelRepresentation rep = modelNode.extractElement(leftLeg, "VisualSceneNode/stand/leg1");
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        leftLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(rightLeg, "VisualSceneNode/stand/leg2");
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        rightLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(bar, "VisualSceneNode/stand/middle_support");
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        bar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.getRemainder(schematic.getBackground());

        schematic.getBackground().addRepresentation(rep);
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        addTask(new Solve2FMTask(bar, jointP));
    }
}
