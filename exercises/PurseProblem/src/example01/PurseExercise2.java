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
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.representations.ImageRepresentation;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExercise2 extends FBDExercise {

    /** Creates a new instance of PurseExercise */
    public PurseExercise2() {
        super(new Schematic());
    }

    @Override
    public void initExercise() {
        setName("Holding a Purse");
        StaticsApplication.getApp().createDisplayGroup("Bones", "bones");

        setDescription(
                "Here is a simplified version of the human forearm. " +
                "Please build a Free Body Diagram of the Forearm, and solve for the distance X. " +
                "The weight of the forearm is 9 N and its center of mass is at G. " +
                "The point C at the elbow is a <i>pin</i>." +
                "The weight of the purse is 19.6 N.");

        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*mm");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));
    //Unit.setDisplayScale(Unit.force, new BigDecimal(".1")); // this doesn't work yet
    }
    protected float handPoint = -17;
    protected float tendonAnchorB = 13;
    protected float tendonAnchorD = 13;
    protected float shoulderHeight = 16;
    protected float forearmWeight = 9;
    protected float purseWeight = 19.6f;
    protected float centerGravityOffset = 0;
    Point A, C, G;
    UnknownPoint B;
    Connector2ForceMember2d jointB, jointD;
    Pin2d jointC, jointE;

    @Override
    public void loadExercise() {

        Schematic world = getSchematic();


        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .2f, 1.0f));
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.96f, 0.98f, 0.90f, 1.0f));

        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        
        A = new Point("" + handPoint, "-10", "0");
        
        C = new Point("18", "-10", "0");
        B = new UnknownPoint(new Point("13", "-10", "0"), 
                C, Vector3bd.UNIT_X.negate());
        
        Body forearm = new Beam(C, A);
        
        forearm.setName("Forearm");
        
        jointC = new Pin2d(C);
        
        jointC.attachToWorld(forearm);

        G = new Point("" + (centerGravityOffset + 3), "-10", "0");

        DistanceMeasurement distance1 = new DistanceMeasurement(A, C);
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(A, G);
        distance2.createDefaultSchematicRepresentation(3f);
        world.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(B, C);
        distance3.createDefaultSchematicRepresentation(3f);
        distance3.setKnown(false);
        distance3.setSymbol("X");
        world.add(distance3);
        B.setMeasurement(distance3);

        Vector3bd tendonDirection = new Vector3bd("[.13,.8,0]");
        tendonDirection = tendonDirection.normalize();

        FixedAngleMeasurement angle1 = new FixedAngleMeasurement(B, C, tendonDirection.toVector3f());
        angle1.createDefaultSchematicRepresentation(2f);
        world.add(angle1);

        Force purse = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal(purseWeight));
        purse.setName("Purse");
        forearm.addObject(purse);

        Force tendonForce = new Force(B, tendonDirection, new BigDecimal(360));
        tendonForce.setName("Tendon");
        tendonForce.createDefaultSchematicRepresentation();
        forearm.addObject(tendonForce);
        forearm.addObject(B);

        C.setName("C");
        B.setName("B");
        A.setName("A");
        G.setName("G");

        C.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        forearm.createDefaultSchematicRepresentation();
        purse.createDefaultSchematicRepresentation();

        forearm.getWeight().setDiagramValue(new BigDecimal(forearmWeight)); // ???

        forearm.setCenterOfMassPoint(G);

        world.add(forearm);

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

        imageRep = new ImageRepresentation(A, loadTexture("example01/assets/purse.png"));
        imageRep.setScale(repScale * 538f, repScale * 835f);
        imageRep.setTranslation(0, -11.0f, .02f);
        A.addRepresentation(imageRep);
    }
}
