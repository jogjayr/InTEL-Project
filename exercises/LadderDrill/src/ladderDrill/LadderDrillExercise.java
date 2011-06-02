/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ladderDrill;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.ContactPoint;
import java.math.BigDecimal;

import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;



/**
 *
 * @author vignesh
 */
public class LadderDrillExercise extends OrdinaryExercise {

    
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-3f, 3f, -1f, 4f);
        vc.setZoomConstraints(0.35f, 2f);
        vc.setRotationConstraints(-1, 1,-.5f,.5f);
        interfaceConfiguration.setViewConstraints(vc);
        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Lori Jones has recently started her own engineering firm and is trying to hang up " +
                "a sign in front of her business.  The front of the building is brick and she needs to drill some holes " +
                "into the brick to hang the sign up.  She leans a 5 m ladder up against the building and the ladder makes " +
                "an angle of 70° with the ground. She then climbs up the ladder and begins to drill a hole with a power drill.  " +
                "The force from the power drill is a strictly horizontal force.");

        description.setProblemStatement("She weighs 550 N, which acts at point D, and the weight of the ladder is 200 N, which acts " +
                "at the midpoint (point C) of the ladder.");

        description.setGoals("Draw a free body diagram of the ladder. Find the maximum force she can apply with the power drill before " +
                "the ladder begins to slip.");

        description.addImage("ladderDrill/assets/drill0.png");
        description.addImage("ladderDrill/assets/ladderDrill.png");

        return description;
    }

    public void initExercise() {

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.force, " N");

        Unit.setPrecision(Unit.distance, 2);
    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "4.6984631", "0");
        Point B = new Point("B", "1.71010072", "0", "0");
        Point C = new Point("C", "0.85505036", "2.34923155", "0");
        Point D = new Point("D", "0.615636261", "3.00701639", "0");
        Point E = new Point("E", "0.342020147", "3.75877048", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(E);

        Body ladder = new Beam("Ladder Body",A,B);
        
        ladder.addObject(A);
        ladder.addObject(B);
        ladder.addObject(C);
        ladder.addObject(D);
        ladder.addObject(E);

        Force forceD = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal(550));
        forceD.setName("Weight D");
        forceD.createDefaultSchematicRepresentation();

        ladder.addObject(forceD);


//        Vector3bd ladAngle = new Vector3bd(
//                new BigDecimal(Math.cos(Math.PI / 3)),
//                new BigDecimal(Math.sin(Math.PI / 3)),
//                BigDecimal.ZERO);

        AngleMeasurement ladderAngle = new FixedAngleMeasurement(B, A,Vector3f.UNIT_X.negate());
//        AngleMeasurement ladderAngle = new FixedAngleMeasurement(B, Vector3bd.UNIT_X.negate(), ladAngle.toVector3f());
        ladderAngle.setName("Ladder Angle");
        getSchematic().add(ladderAngle);
        ladderAngle.createDefaultSchematicRepresentation();

// Ladder Weight should be WEIGHT andnot force
        BigDecimal Weight = new BigDecimal(200);
        ladder.getWeight().setDiagramValue(Weight);
        ladder.setCenterOfMassPoint(C);

        Force forceE = new Force(E, Vector3bd.UNIT_X, "Drill Force");
        forceE.setName("Force E");
        forceE.createDefaultSchematicRepresentation();


        ladder.addObject(forceE);

        
        

        ConstantObject frictionObjectB = new ConstantObject("Mu B", new BigDecimal(".5"), Unit.none);
        schematic.add(frictionObjectB);

        ConstantObject frictionObjectA = new ConstantObject("Mu A", new BigDecimal(".2"), Unit.none);
        schematic.add(frictionObjectA);

        ContactPoint jointB;
        jointB = new ContactPoint(B, frictionObjectB);
        jointB.setName("Contact B");
        jointB.setNormalDirection(Vector3bd.UNIT_Y);
        jointB.setFrictionDirection(Vector3bd.UNIT_X.negate());
        jointB.createDefaultSchematicRepresentation();
        jointB.attachToWorld(ladder);

        ContactPoint jointA;
        jointA = new ContactPoint(A, frictionObjectA);
        jointA.setName("Contact A");
        jointA.setNormalDirection(Vector3bd.UNIT_X);
        jointA.setFrictionDirection(Vector3bd.UNIT_Y);
        jointA.createDefaultSchematicRepresentation();
        jointA.attachToWorld(ladder);

        schematic.add(ladder);

        DistanceMeasurement measureAB = new DistanceMeasurement(A, B);
        measureAB.createDefaultSchematicRepresentation();
        schematic.add(measureAB);

        DistanceMeasurement measureBC = new DistanceMeasurement(B, C);
        measureBC.createDefaultSchematicRepresentation(1.5f);
        schematic.add(measureBC);

        DistanceMeasurement measureBD = new DistanceMeasurement(B, D);
        measureBD.createDefaultSchematicRepresentation(1f);
        schematic.add(measureBD);

        DistanceMeasurement measureBE = new DistanceMeasurement(B, E);
        measureBE.createDefaultSchematicRepresentation(.5f);
        schematic.add(measureBE);

        float scale = 1.94f;
        Vector3f translation = new Vector3f(.06f, .06f, -5.6f);


        ModelNode modelNode = ModelNode.load("ladderDrill/assets/", "ladderDrill/assets/ladderDrill.dae");
        modelNode.extractLights();

        ModelRepresentation rep;
        String prefix = "VisualSceneNode/CompleteScene/";



        rep = modelNode.extractElement(ladder, prefix + "sceneObjects/ladder/");
        rep.getRelativeNode().setLocalScale(scale);
        rep.getRelativeNode().setLocalTranslation(translation);

        ladder.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);


        rep = modelNode.getRemainder(schematic.getBackground());
        rep.getRelativeNode().setLocalScale(scale);
        rep.getRelativeNode().setLocalTranslation(translation);
        schematic.getBackground().addRepresentation(rep);
    }

}
