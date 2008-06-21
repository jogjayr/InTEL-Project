/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keyboard;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Connector2ForceMember2d;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class KeyboardExercise extends FBDExercise {

    public KeyboardExercise() {
        super(new Schematic());
    }

    @Override
    public void initExercise() {
        setName("Keyboard Stand");
        
        setDescription(
                "This is a keyboard stand!"
                );
        
        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal("5"));
        getDisplayConstants().setMomentSize(0.2f);
        getDisplayConstants().setForceSize(0.2f);
        getDisplayConstants().setPointSize(0.2f);
        getDisplayConstants().setCylinderRadius(0.2f);
        getDisplayConstants().setForceLabelDistance(1f);
        getDisplayConstants().setMomentLabelDistance(0f);
        getDisplayConstants().setMeasurementSize(0.1f);
    }
    
    Point A, B, C, D, E, P, Q;
    Pin2d jointC;
    Connector2ForceMember2d jointP, jointQ;
    Roller2d jointB, jointE;
    
    @Override
    public void loadExercise() { 
        Schematic schematic = getSchematic();
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        
        A = new Point("-2.9","4.3","0");
        D = new Point("2.9","4.3","0");
        B = new Point("2.9","0","0");
        E = new Point("-2.9","0","0");       
        C = new Point("0","2.15","0");
        P = new Point("-1","2.9","0");
        Q = new Point("1","2.9","0");
        
        Body leftLeg = new Beam(B,A);
        Bar bar = new Bar(P,Q);
        Body rightLeg = new Beam(E,D);
        
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
        distance1.createDefaultSchematicRepresentation(0.5f);
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(Q, D);
        distance2.createDefaultSchematicRepresentation(0.5f);
        distance2.forceVertical();
        schematic.add(distance2);        
        
        DistanceMeasurement distance3 = new DistanceMeasurement(C, Q);
        distance3.createDefaultSchematicRepresentation(2.4f);
        distance3.forceVertical();
        schematic.add(distance3);
        
        DistanceMeasurement distance4 = new DistanceMeasurement(B, D);
        distance4.createDefaultSchematicRepresentation(2.4f);
        schematic.add(distance4);
        
        Force keyboardLeft = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal(50));
        keyboardLeft.setName("Keyboard Left");
        leftLeg.addObject(keyboardLeft);

        Force keyboardRight = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal(50));
        keyboardRight.setName("Keyboard Right");
        rightLeg.addObject(keyboardRight);
        
        jointC.attach(leftLeg, rightLeg);
        jointP.attach(leftLeg, bar);
        jointQ.attach(bar, rightLeg);
        jointE.attachToWorld(rightLeg);
        jointB.attachToWorld(leftLeg);
        
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
        leftLeg.createDefaultSchematicRepresentation();
        bar.createDefaultSchematicRepresentation();
        rightLeg.createDefaultSchematicRepresentation();
        
        schematic.add(leftLeg);
        schematic.add(bar);
        schematic.add(rightLeg);
        
        // THIS DOES NOT ACTUALLY SEEM TO DO ANYTHING
        float scale = .5f;
        
        Representation rep = new ModelRepresentation(leftLeg, "keyboard/assets/", "keyboard/assets/strut2.dae");
        rep.setLocalScale(scale);
        leftLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(rightLeg, "keyboard/assets/", "keyboard/assets/strut1.dae");
        rep.setLocalScale(scale);
        rightLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(bar, "keyboard/assets/", "keyboard/assets/strut3.dae");
        rep.setLocalScale(scale);
        bar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(rightLeg, "keyboard/assets/", "keyboard/assets/background.dae");
        rep.setLocalScale(scale);
        leftLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
    }
}