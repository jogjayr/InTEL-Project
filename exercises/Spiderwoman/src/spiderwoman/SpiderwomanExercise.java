/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderwoman;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.connectors.Pin2dKnownDirection;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigInteger;

/**
 *
 * @author Calvin Ashmore
 */
public class SpiderwomanExercise extends FrameExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setTitle("Georgia Tech Spiderwoman");
        description.setNarrative("...");

        return description;
    }

    @Override
    public void initExercise() {

        Unit.setSuffix(Unit.distance, "m");
        Unit.setSuffix(Unit.angle, "");
        Unit.setSuffix(Unit.force, "N");
        Unit.setDisplayScale(Unit.distance, new BigDecimal("10"));

        getDisplayConstants().setForceSize(1);
        getDisplayConstants().setMomentSize(1);
        getDisplayConstants().setPointSize(1);

    }
    //check with dae file for joint variables
    Point A, B, G;
    ContactPoint jointB;
    Potato body;
    ConstantObject frictionObjectB;
    BigDecimal mu;
//pin2dconnector for G, A - 

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA();
        //StaticsApplication.getApp().getCamera().setLocation(new Vector3f();
        mu = new BigDecimal("2.66");
        frictionObjectB = new ConstantObject("mu B", mu, Unit.none);
        schematic.add(frictionObjectB);

        Vector3bd ropeAngle = new Vector3bd(
                new BigDecimal(Math.cos(Math.PI / 3)),
                new BigDecimal(Math.sin(Math.PI / 3)),
                BigDecimal.ZERO);

        BigDecimal Weight = new BigDecimal(700);

        A = new Point("A", "0", "20.5", "0");
        B = new Point("B", "0", "0", "0");
        G = new Point("G", "-6", "5.5", "0");

        body = new Potato("body");
        //wall = new Potato("wall");
        //rope = new Potato("rope");


        jointB = new ContactPoint(B, frictionObjectB);
        jointB.setName("ContactB");
        jointB.setNormalDirection(Vector3bd.UNIT_X.negate());
        jointB.setFrictionDirection(Vector3bd.UNIT_Y);
        jointB.attachToWorld(body);


        Pin2dKnownDirection connectorTop = new Pin2dKnownDirection(A);
        connectorTop.setName("Connector A");
        connectorTop.setDirection(ropeAngle);
        connectorTop.attachToWorld(body);


        AngleMeasurement measureBody = new FixedAngleMeasurement(A, Vector3bd.UNIT_X, ropeAngle.toVector3f());
        measureBody.setName("Angle Body");

        body.getWeight().setDiagramValue(Weight);

        schematic.add(measureBody);

        measureBody.createDefaultSchematicRepresentation(2.5f);
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();


        schematic.add(A);

        jointB.createDefaultSchematicRepresentation();

        jointB.attachToWorld(body);
        body.setCenterOfMassPoint(G);

        schematic.add(body);

        //creating 3D nodes 
        ModelNode modelNode = ModelNode.load("spiderwoman/assets/", "spiderwoman/assets/spiderwoman.dae");
        modelNode.extractLights();

        ModelRepresentation rep;

        float scale = 1;
        Vector3f modelOffset = new Vector3f();

        rep = modelNode.extractElement(body, "VisualSceneNode/Spiderwoman");
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        body.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        schematic.getBackground().addRepresentation(rep);
    }
}
