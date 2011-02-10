/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example02;

import edu.gatech.statics.exercise.SimpleFBDExercise;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.CompleteFBDTask;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class SimpleTowerExercise extends SimpleFBDExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        return interfaceConfiguration;
    }

    /** Creates a new instance of TowerExercise */
    public SimpleTowerExercise() {
        super(new Schematic());
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Tower of Pisa");
        description.setProblemStatement(
                "Stephanie is a Civil Engineering student and is studying abroad " +
                "at Georgia Tech Lorraine in France and at the end of the semester " +
                "she stays in Europe for a few weeks and travels around Europe.  " +
                "She stops in Italy and sees the Leaning Tower of Pisa.  " +
                "She wonders how it stays upright with how heavy it is and decides " +
                "to construct a free body diagram of the tower.");

        description.setGoals("Build a free body diagram of the tower.  " +
                "You do not need to solve for the reactions at the fixed support at the base.  " +
                "The weight of the tower is 14700 tons.");

        description.addImage("example02/assets/pisa-0.png");
        description.addImage("example02/assets/pisa-1.jpg");
        description.addImage("example02/assets/pisa-2.jpg");
        description.addImage("example02/assets/pisa-3.jpg");

        return description;
    }

    @Override
    public void initExercise() {
//        setName("Tower of Pisa");
//
//        setDescription(
//                "This is a model of the tower of Pisa, solve for the reaction forces at its base. " +
//                "The tower's weight is 14700 tons.");

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.force, " ton");
        Unit.setSuffix(Unit.moment, " ton*ft");

        //getDisplayConstants().setDrawScale(3.5f);
        getDisplayConstants().setPointSize(3.5f);
        getDisplayConstants().setForceSize(3.5f);
        getDisplayConstants().setMomentSize(3.5f);
        getDisplayConstants().setCylinderRadius(3.5f);
        getDisplayConstants().setMeasurementBarSize(3.5f);
    }

    @Override
    public void loadExercise() {
        Schematic world = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.0f, .5f, 1.0f, 1.0f));

        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 24.0f, 100.0f));

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "2.5", "55", "0");
        Point G = new Point("G", A.getPosition().add(B.getPosition()).mult(new BigDecimal(".5")));

        Point underG = new Point("underG", "" + G.getPosition().getX(), "0", "0");

        DistanceMeasurement horizontalDistance = new DistanceMeasurement(A, underG);
        horizontalDistance.createDefaultSchematicRepresentation();
        world.add(horizontalDistance);

        Force weightB = new Force(G, Vector3bd.UNIT_Y.negate(), new BigDecimal(14700));
        weightB.setName("Weight B");
        weightB.createDefaultSchematicRepresentation();


        Fix2d jointA = new Fix2d(A);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        Body tower = new Beam("Tower", B, A);
        tower.setCenterOfMassPoint(G);
        tower.createDefaultSchematicRepresentation();
        tower.getWeight().setDiagramValue(new BigDecimal("14700"));
        
        tower.addObject(weightB);

        world.add(tower);

        

        jointA.attachToWorld(tower);

        float scale = 3.25f;

        ModelNode modelNode = ModelNode.load("example02/assets/", "example02/assets/pisaTower.dae");

        modelNode.extractLights();

        ModelRepresentation rep = modelNode.extractElement(tower, "VisualSceneNode/group27");
        //rep.setModelOffset(new Vector3f(0, 0, -22.0f / scale));

        Matrix3f rotation = new Matrix3f();
        rotation.fromAngleAxis(-.24f, Vector3f.UNIT_Z);
        rep.setModelRotation(rotation);
        rep.setLocalScale(scale * 1.5f);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        tower.addRepresentation(rep);

        rep = modelNode.getRemainder(world.getBackground());
        rep.setLocalScale(scale);
        world.getBackground().addRepresentation(rep);

        addTask(new CompleteFBDTask("FBD tower", new BodySubset(tower)));
    }
}
