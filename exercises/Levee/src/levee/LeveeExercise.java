/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levee;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
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
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class LeveeExercise extends DistributedExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        return interfaceConfiguration;
    }

    @Override
    public void initExercise() {
        setName("Levee");

        setDescription(
                "What is the loading at the base of the levee?");

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.moment, " kip*ft");
        Unit.setSuffix(Unit.force, " kip");
        Unit.setSuffix(Unit.forceOverDistance, " kip/ft");

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
        Point B = new Point("B", "0", "12", "0");

        Beam levee = new Beam("Levee", A, B);


        DistributedForce waterForce = new TriangularDistributedForce("water", levee, A, B,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_X, new BigDecimal("748.8")));
        DistributedForceObject waterForceObject = new DistributedForceObject(waterForce, "");

        levee.addObject(waterForceObject);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        levee.createDefaultSchematicRepresentation();
        waterForceObject.createDefaultSchematicRepresentation(5, 15, 2f);

        DistanceMeasurement measure = new DistanceMeasurement(A, B);
        measure.createDefaultSchematicRepresentation();
        schematic.add(measure);

        Connector base = new Fix2d(A);
        base.attachToWorld(levee);
        base.setName("fix A");

        schematic.add(levee);

        ModelNode modelNode = ModelNode.load("levee/assets/", "levee/assets/levee.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(7, 0, 0);

        ModelRepresentation rep = modelNode.extractElement(levee, "VisualSceneNode/levee_Earth/levee");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelOffset(modelTranslation);
        levee.addRepresentation(rep);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        schematic.getBackground().addRepresentation(rep);
    }
}
