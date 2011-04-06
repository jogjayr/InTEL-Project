/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Ordinary3DExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.CoordinateSystem;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.CompleteFBDTask;
import edu.gatech.statics.tasks.SolveFBDTask;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.ViewDiagramState;
import java.math.BigDecimal;

/**
 *
 * @author gtg126z
 */
public class PanelExercise extends Ordinary3DExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = super.createInterfaceConfiguration();
        ViewDiagramState defaultViewState = ic.getDisplayCalculator().getDefaultState();
        defaultViewState.setCameraFrame(new Vector3f(0, 1, 2), Vector3f.ZERO);
        ic.getDisplayCalculator().setRadiusMultiplier(2f);

        return ic;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Panel");
        description.setNarrative(
                "Three Georgia Tech students, Alison, Andy and Mark, "
                + "have decided to volunteer for Habitat for Humanity and help "
                + "build homes in the Atlanta area.  Over this past weekend "
                + "they carried a panel for the side of a house. How much "
                + "lifting force did each of them exert in order to carry the"
                + " panel?.");

        description.setGoals("Assume the panel is homogeneous and weighs 120 lb."
                + " Alison, Andy, and Mark are positioned at points A, B, and"
                + " C respectively.");

        description.setProblemStatement(
                "Calculate the lifting force exerted by each worker.  "
                + "If you have back pain, which position among the three would you prefer?");

        description.addImage("panel/assets/panel1.png");
        description.addImage("panel/assets/panel3.jpg");
        description.addImage("panel/assets/panel2.jpg");

        return description;
    }

    @Override
    public void initExercise() {
        super.initExercise();

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.force, " lbs");
        Unit.setSuffix(Unit.moment, " ft*lbs");

        getDisplayConstants().setMomentSize(0.3f);
        getDisplayConstants().setForceSize(0.3f);
        getDisplayConstants().setPointSize(0.3f);
        getDisplayConstants().setCylinderRadius(0.3f);
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        Potato panel = new Potato("panel");

//        panel.addRepresentation(new BoxRepresentation(panel, 2, .1f, 1));


        Point D = new Point("D", "5", "0", "-3");
        Point A = new Point("A", "3", "0", "0");
        Point B = new Point("B", "6", "0", "-6");
        Point C = new Point("C", "10", "0", "-4");

        Force forceG = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal("150"));
        forceG.setName("weight");
        Force forceA = new Force(A, Vector3bd.UNIT_Y, "Alison");
        forceA.setName("Alison");
        Force forceB = new Force(B, Vector3bd.UNIT_Y, "Andy");
        forceB.setName("Andy");
        Force forceC = new Force(C, Vector3bd.UNIT_Y, "Mark");
        forceC.setName("Mark");

        panel.addObject(D);
        panel.addObject(A);
        panel.addObject(B);
        panel.addObject(C);
        panel.addObject(forceG);
        panel.addObject(forceA);
        panel.addObject(forceB);
        panel.addObject(forceC);

        D.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        forceG.createDefaultSchematicRepresentation();
        forceA.createDefaultSchematicRepresentation();
        forceB.createDefaultSchematicRepresentation();
        forceC.createDefaultSchematicRepresentation();

        schematic.add(panel);


        // measurements

        CoordinateSystem coords = new CoordinateSystem(true);
        coords.createDefaultSchematicRepresentation();
        schematic.add(coords);

        Point origin = new Point("origin", "0", "0", "0");
        Point midl = new Point("midl", "0", "0", "-3");
        Point ul = new Point("ul", "0", "0", "-6");
        Point bmid = new Point("bmid", "5","0","0");
        Point br = new Point("br", "10","0","0");

        float distanceOffset = .1f;

        DistanceMeasurement measure;

        measure = new DistanceMeasurement(origin, midl);
        measure.createDefaultSchematicRepresentation(-distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(midl, ul);
        measure.createDefaultSchematicRepresentation(-distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(ul, B);
        measure.createDefaultSchematicRepresentation(-distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(origin, A);
        measure.createDefaultSchematicRepresentation(distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(A, bmid);
        measure.createDefaultSchematicRepresentation(distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(bmid, br);
        measure.createDefaultSchematicRepresentation(distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        measure = new DistanceMeasurement(br, C);
        measure.createDefaultSchematicRepresentation(distanceOffset);
        panel.addObject(measure);
        schematic.add(measure);

        // model

        ModelNode modelNode = ModelNode.load("panel/assets/", "panel/assets/panel.dae");
        modelNode.extractLights();

        float scale = 2;

        ModelRepresentation rep;
        rep = modelNode.extractElement(panel, "VisualSceneNode/scene/panel");
        panel.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setLocalScale(scale);

        // re orient the light that is used for highlights so that the flat panel is illuminated correctly
        DirectionalLight light = (DirectionalLight) rep.getLight();
        light.setDirection(Vector3f.UNIT_Y.negate());

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setLocalScale(scale);
        schematic.getBackground().addRepresentation(rep);


        addTask(new SolveFBDTask("Solve FBD", new BodySubset(panel)));
    }
}
