/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package awning;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.distributed.objects.TriangularDistributedForce;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class AwningExercise extends DistributedExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        return interfaceConfiguration;
    }

    @Override
    public void initExercise() {
        setName("Awning with Snow");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setSuffix(Unit.force, " N");
        Unit.setSuffix(Unit.forceOverDistance, " N/m");

        setDescription("What is the loading at the base of the awning?");

        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setForceLabelDistance(5f);
        getDisplayConstants().setMomentLabelDistance(10f);
        getDisplayConstants().setMeasurementBarSize(0.2f);
    }

    @Override
    public void loadExercise() {

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.7f, .7f, .9f, 1.0f));

        Schematic schematic = getSchematic();


        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "5", "0", "0");

        Beam awning = new Beam("Awning", A, B);

        DistributedForce snowForce = new TriangularDistributedForce("snow", awning, A, B,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), "10"));
        DistributedForceObject snowForceObject = new DistributedForceObject(snowForce, "");

        awning.addObject(snowForceObject);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        awning.createDefaultSchematicRepresentation();
        snowForceObject.createDefaultSchematicRepresentation(5, 15, 2f);


        DistanceMeasurement measure = new DistanceMeasurement(A, B);
        measure.createDefaultSchematicRepresentation();
        schematic.add(measure);

        Connector base = new Fix2d(A);
        base.attachToWorld(awning);
        base.setName("fix A");


        ModelNode modelNode = ModelNode.load("awning/assets/", "awning/assets/awning.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0f, 0, 0);

        ModelRepresentation rep = modelNode.extractElement(awning, "VisualSceneNode/scene/building/awning");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelOffset(modelTranslation);
        awning.addRepresentation(rep);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        schematic.getBackground().addRepresentation(rep);
    }
}
