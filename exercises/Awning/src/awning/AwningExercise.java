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
import edu.gatech.statics.modes.description.Description;
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
import edu.gatech.statics.tasks.SolveConnectorTask;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class AwningExercise extends DistributedExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());

        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-1, 1, -1, 2);
        vc.setZoomConstraints(0.5f, 1.5f);
        vc.setRotationConstraints(0, 1);
        interfaceConfiguration.setViewConstraints(vc);

        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Awning with Snow");
        description.setGoals("What is the loading at the base of the awning?");

        return description;
    }



    @Override
    public void initExercise() {
        //setName("Awning with Snow");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setSuffix(Unit.force, " N");
        Unit.setSuffix(Unit.forceOverDistance, " N/m");

        //setDescription("What is the loading at the base of the awning?");

//        getDisplayConstants().setMomentSize(0.5f);
//        getDisplayConstants().setForceSize(0.5f);
//        getDisplayConstants().setPointSize(0.5f);
//        getDisplayConstants().setCylinderRadius(0.5f);
//        getDisplayConstants().setForceLabelDistance(3f);
//        getDisplayConstants().setMomentLabelDistance(2f);
//        getDisplayConstants().setMeasurementBarSize(0.2f);
//        //getDisplayConstants().setDrawScale(.2f);
//        //getDisplayConstants().setJointSize(.2f);
        getDisplayConstants().setDistributedLabelMultiplier(1.25f);
        getDisplayConstants().setDistributedArrowSize(.5f);
        getDisplayConstants().setMomentCircleRadius(.75f);
    }

    @Override
    public void loadExercise() {

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.7f, .7f, .9f, 1.0f));

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "1.5", "0", "0");

        Beam awning = new Beam("Awning", A, B);

        DistributedForce snowForce = new TriangularDistributedForce("snow", awning, A, B,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("10")));
        DistributedForceObject snowForceObject = new DistributedForceObject(snowForce, "");

        awning.addObject(snowForceObject);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        //awning.createDefaultSchematicRepresentation();
        snowForceObject.createDefaultSchematicRepresentation(.75f, 5, 1.0f);

        DistanceMeasurement measure = new DistanceMeasurement(A, B);
        measure.createDefaultSchematicRepresentation(.5f);
        schematic.add(measure);

        Connector base = new Fix2d(A);
        base.attachToWorld(awning);
        base.setName("fix A");

        schematic.add(awning);

        ModelNode modelNode = ModelNode.load("awning/assets/", "awning/assets/awning.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0f, 0, 0);
        float modelScale = .1f;

        ModelRepresentation rep = modelNode.extractElement(awning, "VisualSceneNode/scene/building/awning");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelOffset(modelTranslation);
        rep.setModelScale(modelScale);
        rep.setSelectLightColor(new ColorRGBA(1f, 1f, 1f, 1));
        rep.setHoverLightColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1));
        awning.addRepresentation(rep);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelScale(modelScale);
        schematic.getBackground().addRepresentation(rep);

        addTask(new SolveConnectorTask("Solve Base", base));
    }
}
