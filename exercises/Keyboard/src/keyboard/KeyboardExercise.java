/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
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
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class KeyboardExercise extends FrameExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {

        AbstractInterfaceConfiguration ic = super.createInterfaceConfiguration();
        ic.setNavigationWindow(new Navigation3DWindow());
        ic.setCameraSpeed(.2f, 0.02f, .05f);

        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-2, 2, -1, 4);
        vc.setZoomConstraints(0.5f, 1.5f);
        vc.setRotationConstraints(-5, 5, 0, 5);
        ic.setViewConstraints(vc);
        
        return ic;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Keyboard Stand");
        description.setNarrative(
                "Kasiem Hill is in a music group comprised of Civil Engineering " +
                "students from Georgia Tech, in which he plays the keyboard.  " +
                "For his birthday, he received a new keyboard, but it is much bigger " +
                "(both in size and weight) than his last one, so he needs to buy a " +
                "new keyboard stand.  He finds one he really likes from a local " +
                "dealer and is unsure if the connections will be able to support " +
                "the weight of the new keyboard.  He measures the dimensions of " +
                "the stand and he wants to calculate how much force he can expect " +
                "at each connection in the cross bar before he makes the investment.");

        description.setProblemStatement(
                "The stand can be modeled as a frame and is supported by two beams and a cross bar PQ. " +
                "The supports at B and E are rollers and the floor is frictionless.");

        description.setGoals(
                "Find the force in PQ and define whether it is in tension or compression.");

        description.addImage("keyboard/assets/keyboard 1.png");
        description.addImage("keyboard/assets/keyboard 2.jpg");
        description.addImage("keyboard/assets/keyboard 3.jpg");

        return description;
    }

    @Override
    public void initExercise() {
//        setName("Keyboard Stand");
//
//        setDescription(
//                "This is a keyboard stand supported by two beams and a cross bar, PQ. " +
//                "Find the force in PQ and define whether it is in tension or compression. " +
//                "The supports at B and E are rollers, and the floor is frictionless.");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal("10"));
        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        //getDisplayConstants().setForceLabelDistance(1f);
        //getDisplayConstants().setMomentLabelDistance(0f);
        //getDisplayConstants().setMeasurementBarSize(0.1f);


        // 10/21/2010 HOTFIX: THIS CORRECTS AN ISSUE IN WHICH OBSERVATION DIRECTION IS SET TO NULL IN EQUATIONS
        for (Map<DiagramType, Diagram> diagramMap : getState().allDiagrams().values()) {
            EquationDiagram eqDiagram = (EquationDiagram) diagramMap.get(EquationMode.instance.getDiagramType());
            if(eqDiagram == null) continue;
            EquationState.Builder builder = new EquationState.Builder(eqDiagram.getCurrentState());

            TermEquationMathState.Builder xBuilder = new TermEquationMathState.Builder((TermEquationMathState) builder.getEquationStates().get("F[x]"));
            xBuilder.setObservationDirection(Vector3bd.UNIT_X);

            TermEquationMathState.Builder yBuilder = new TermEquationMathState.Builder((TermEquationMathState) builder.getEquationStates().get("F[y]"));
            yBuilder.setObservationDirection(Vector3bd.UNIT_Y);

            TermEquationMathState.Builder zBuilder = new TermEquationMathState.Builder((TermEquationMathState) builder.getEquationStates().get("M[p]"));
            zBuilder.setObservationDirection(Vector3bd.UNIT_Z);

            builder.putEquationState(xBuilder.build());
            builder.putEquationState(yBuilder.build());
            builder.putEquationState(zBuilder.build());
            eqDiagram.pushState(builder.build());
            eqDiagram.clearStateStack();
        }


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

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.7f, .8f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));

        A = new Point("A", "0", "6", "0");
        D = new Point("D", "8", "6", "0");
        B = new Point("B", "8", "0", "0");
        E = new Point("E", "0", "0", "0");
        C = new Point("C", "4", "3", "0");
        P = new Point("P", "2.7", "4", "0");
        Q = new Point("Q", "5.3", "4", "0");

        leftLeg = new Beam("Left Leg", B, A);
        bar = new Bar("Bar", P, Q);
        rightLeg = new Beam("Right Leg", E, D);

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
        distance1.addPoint(E);
        distance1.addPoint(B);
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(C, D);
        distance2.setName("Measure CD");
        distance2.createDefaultSchematicRepresentation(0.5f);
        distance2.forceVertical();
        distance2.addPoint(A);
        schematic.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(C, Q);
        distance3.setName("Measure CQ");
        distance3.createDefaultSchematicRepresentation(1f);
        distance3.forceVertical();
        distance3.addPoint(P);
        schematic.add(distance3);

        DistanceMeasurement distance4 = new DistanceMeasurement(B, D);
        distance4.setName("Measure BD");
        distance4.createDefaultSchematicRepresentation(2.4f);
        distance4.addPoint(A);
        distance4.addPoint(E);
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
        rep.setHoverLightColor(ColorRGBA.yellow);
        rep.setSelectLightColor(ColorRGBA.yellow);

        rep = modelNode.extractElement(rightLeg, "VisualSceneNode/stand/leg2");
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        rightLeg.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setHoverLightColor(ColorRGBA.yellow);
        rep.setSelectLightColor(ColorRGBA.yellow);

        rep = modelNode.extractElement(bar, "VisualSceneNode/stand/middle_support");
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        bar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setHoverLightColor(ColorRGBA.yellow);
        rep.setSelectLightColor(ColorRGBA.yellow);

        rep = modelNode.getRemainder(schematic.getBackground());

        schematic.getBackground().addRepresentation(rep);
        rep.setLocalScale(scale);
        rep.setModelOffset(new Vector3f(14f, 0, 0));
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        addTask(new Solve2FMTask("Solve PQ", bar, jointP));
    }
}
