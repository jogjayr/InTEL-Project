/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.fbd.test.FBDStateProvider;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.ContactPoint;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Trudetski
 */
public class FrictionTestingBoilerplate {

    protected Exercise exercise;
    protected FreeBodyDiagram diagram;
    //protected FBDChecker check;
    protected Body body;

    protected void initialSetup() {
        // **************************
        // ALL OF THIS IS BOILERPLATE

        exercise = createSimpleExercise();
        FBDStateProvider stateProvider = new FBDStateProvider() {

            public FBDState createState(Map<String, SimulationObject> objects, Builder builder) {
                return builder.build();
            }
        };

        // set up the StaticsApplication
        new StaticsApplication();
        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();
        StaticsApplication.getApp().initExercise();

        // get the first body, and build the FBD out of that.
        List<Body> allBodies = exercise.getSchematic().allBodies();
        body = allBodies.get(0);

        // build the diagram and its initial state
        diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();

        // construct the state to check with the state provider
        FBDState stateToCheck = stateProvider.createState(exercise.getSchematic().getAllObjectsByName(), stateBuilder);
        diagram.pushState(stateToCheck);
        //StaticsApplication.getApp().setCurrentDiagram(diagram);
        // actually perform the check
        //check = diagram.getChecker();

        // END BOILERPLATE
        /// ****************************
    }

    protected Exercise createSimpleExercise() {
        return new FrameExercise() {

            @Override
            public void loadExercise() {
                // construct the points, bodies, and whatnot
                Point A = new Point("A", "[-1,0,0]");
                Point B = new Point("B", "[1,0,0]");
//                Force givenForce = new Force(B, new Vector3bd("[0,1,0]"), new BigDecimal(5));
//                givenForce.setName("wombat");
//                Moment givenMoment = new Moment(A, new Vector3bd("[1,0,0]"), "foo");
//                givenMoment.setName("wombat2");
                Body body = new Beam("test", A, B);

                ConstantObject frictionCoefficient = new ConstantObject("coefficient", new BigDecimal("0.3"), Unit.none);
                ContactPoint jointB = new ContactPoint(B, frictionCoefficient);
                jointB.setNormalDirection(new Vector3bd("0.0", "1.0", "0.0"));
                jointB.setFrictionDirection(new Vector3bd("-1.0", "0.0", "0.0"));
                jointB.attachToWorld(body);
                jointB.setName("Joint B");
                // add given loads to the bodies
//                body.addObject(givenForce);
//                body.addObject(givenMoment);
                // add all of the above objects to the schematic
                getSchematic().add(A);
                getSchematic().add(B);
                getSchematic().add(body);
            }

            @Override
            public Description getDescription() {
                return null;
            }
        };
    }
}
