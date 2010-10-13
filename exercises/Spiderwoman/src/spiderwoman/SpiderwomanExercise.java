/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderwoman;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.SolveConnectorTask;

/**
 *
 * @author Calvin Ashmore
 */
public class SpiderwomanExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setTitle("Georgia Tech Spiderwoman");
        description.setNarrative("Stealing the T off of Tech Tower is a tradition on Georgia Tech’s campus that was first started in " +
                "April of 1969 by a group called the Magnificent Seven, but has since been banned.  " +
                "Spiderwoman has heard some students around campus talking about a group called Disarray " +
                "Duo planning to steal the T off of Tech Tower and she has some leads about the day that they " +
                "plan to steal it.  When she arrived to Tech Tower on the night of the attempt, Disarray Duo was " +
                "already in p   rocess of stealing the T.  She then had to shoot her web up to the top of the building " +
                "and scale up the side.  Will she be able to scale up the side of Tech Tower? ");

        description.setProblemStatement("Her weight is 700 N.  The tension in the spiderweb is 600 N.  The angle between the spiderweb and the " +
                "horizontal is 60 degrees.");

        description.setGoals("Draw a free body diagram of Spiderwoman " +
                "in equilibrium while scaling building.  Solve for the reaction forces at her feet.  Find the minimum " +
                "coefficient of friction so that her feet will not slip while she is scaling the building.");

        description.addImage("spiderwoman/assets/techtower.png");
        description.addImage("spiderwoman/assets/spiderwoman.png");
       //description.addImage("spiderwoman/assets/techtower.png");

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
    //BigDecimal mu;
//pin2dconnector for G, A - 

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA();
        //StaticsApplication.getApp().getCamera().setLocation(new Vector3f();
        //mu = new BigDecimal("2.66");

        frictionObjectB = new ConstantObject("mu B", Unit.none);
        schematic.add(frictionObjectB);

        Vector3bd ropeAngle = new Vector3bd(
                new BigDecimal(Math.cos(Math.PI / 3)),
                new BigDecimal(Math.sin(Math.PI / 3)),
                BigDecimal.ZERO);

        BigDecimal Weight = new BigDecimal(700);

        A = new Point("A", "-5.5", "11.2", "0");
        B = new Point("B", "0", "0", "0");
        G = new Point("G", "-6", "5.5", "0");

        body = new Potato("Spiderwoman");
        //wall = new Potato("wall");
        //rope = new Potato("rope");


        jointB = new ContactPoint(B, frictionObjectB);
        jointB.setName("ContactB");
        jointB.setNormalDirection(Vector3bd.UNIT_X.negate());
        jointB.setFrictionDirection(Vector3bd.UNIT_Y);
        jointB.attachToWorld(body);

        Force tension = new Force(A, ropeAngle, new BigDecimal("600"));
        tension.setName("tension");
        body.addObject(tension);
//        Pin2dKnownDirection connectorTop = new Pin2dKnownDirection(A);
//        connectorTop.setName("Connector A");
//        connectorTop.setDirection(ropeAngle);
//        connectorTop.attachToWorld(body);


        AngleMeasurement measureBody = new FixedAngleMeasurement(A, Vector3bd.UNIT_X, ropeAngle.toVector3f());
        measureBody.setName("Angle Body");

        body.getWeight().setDiagramValue(Weight);

        schematic.add(measureBody);

        measureBody.createDefaultSchematicRepresentation(2.5f);
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        //tension.createDefaultSchematicRepresentation();
        body.addObject(A);


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

        rep = modelNode.extractElement(schematic.getBackground(), "VisualSceneNode/Spiderwoman/rope");
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        schematic.getBackground().addRepresentation(rep);

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

        addTask(new SolveConnectorTask("Solve B", jointB));
    }
}
