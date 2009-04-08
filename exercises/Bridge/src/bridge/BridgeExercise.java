/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.truss.TrussExercise;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;

/**
 *
 * @author Calvin Ashmore
 */
public class BridgeExercise extends TrussExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-25f, 25f, -10f, 10f);
        vc.setZoomConstraints(1.0f, 8.0f);
        vc.setRotationConstraints(-1, 1);
        interfaceConfiguration.setViewConstraints(vc);
        return interfaceConfiguration;
    }

    @Override
    public void initExercise() {
        setName("Bridge");
        setDescription("Solve for tension or compression in all of the members");

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.moment, " kip*ft");
        Unit.setSuffix(Unit.force, " kip");
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();


        ModelNode modelNode = ModelNode.load("bridge/assets/", "bridge/assets/bridge.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0f, 0, 0);
        Matrix3f modelRotation = new Matrix3f();
        modelRotation.fromAngleAxis(-(float) Math.PI / 2, Vector3f.UNIT_Y);
        float modelScale = 10f/19f;
        ModelRepresentation rep;

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);

        schematic.getBackground().addRepresentation(rep);
    }
}
