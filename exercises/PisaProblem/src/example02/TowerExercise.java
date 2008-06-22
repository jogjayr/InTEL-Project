/*
 * TowerExercise.java
 *
 * Created on August 11, 2007, 10:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package example02;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Fix2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class TowerExercise extends FBDExercise {

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = new DefaultInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        return interfaceConfiguration;
    }

    /** Creates a new instance of TowerExercise */
    public TowerExercise() {
        super(new Schematic());
    }

    @Override
    public void initExercise() {
        setName("Tower of Pisa");

        setDescription(
                "This is a model of the tower of Pisa, solve for the reaction forces at its base. " +
                "The tower's weight is 14700 tons.");

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.force, " ton");
        Unit.setSuffix(Unit.moment, " ton*ft");
    }

    @Override
    public void loadExercise() {
        //ExerciseWorld world = getWorld();
        Schematic world = getSchematic();

        getDisplayConstants().setDrawScale(3.5f);

        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.9f, .9f, .9f, 1.0f));
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.0f, .0f, .0f, 1.0f));

        //StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 10.0f, 60.0f ));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 24.0f, 100.0f));
        //StaticsApplication.getApp().setDrawScale(3.5f);

        Point A = new Point("0", "0", "0");
        Point B = new Point("2.5", "55", "0");
        Point G = new Point(A.getPosition().add(B.getPosition()).mult(new BigDecimal(".5")));
        A.setName("A");
        B.setName("B");
        G.setName("G");

        Point underG = new Point("" + G.getPosition().getX(), "0", "0");

        DistanceMeasurement horizontalDistance = new DistanceMeasurement(A, underG);
        horizontalDistance.createDefaultSchematicRepresentation();
        world.add(horizontalDistance);

        Fix2d jointA = new Fix2d(A);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        Body tower = new Beam(A, B);
        tower.setName("Tower");
        tower.setCenterOfMassPoint(G);
        tower.createDefaultSchematicRepresentation();
        tower.getWeight().setDiagramValue(new BigDecimal("14700"));
        world.add(tower);

        jointA.attachToWorld(tower);

        float scale = 4f;

        ModelNode modelNode = ModelNode.load("example02/assets/", "example02/assets/pisaTower.dae");

        modelNode.extractLights();

        ModelRepresentation rep = modelNode.extractElement(tower, "VisualSceneNode/group27");
        //new ModelRepresentation(tower, "example02/assets/", "example02/assets/pisa3_tower.dae");
        rep.setModelOffset(new Vector3f(0, 0, -22.0f / scale));
        Matrix3f rotation = new Matrix3f(
                -1, 0, 0,
                0, 0, 1,
                0, 1, 0);
        
        
        //rotation.fromStartEndVectors(Vector3f.UNIT_Y, Vector3f.UNIT_Z);
        
        rep.setModelRotation(rotation);
        rep.setLocalScale(scale*1.5f);
        //rep.setSynchronizeRotation(false);
        //rep.setSynchronizeTranslation(false);
        
        //rotation = new Matrix3f();
        //rotation.fromStartEndVectors(Vector3f.UNIT_Y, Vector3f.UNIT_Y.negate());
        
        //rep.getRelativeNode().setLocalRotation(rotation);
        
        tower.addRepresentation(rep);


        rep = modelNode.getRemainder(world.getBackground());
        //new ModelRepresentation(world.getBackground(), "example02/assets/", "example02/assets/pisa1_background.dae");
        rep.setLocalScale(scale);
        world.getBackground().addRepresentation(rep);

    }
}
