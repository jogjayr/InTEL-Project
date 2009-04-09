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
import edu.gatech.statics.objects.Point;
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
        vc.setPositionConstraints(-100f, 100f, -40f, 40f);
        vc.setZoomConstraints(0.1f, 8.0f);
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

        float lowerHeights[] = new float[]{
            30f, 31.2083333f, 32.41666f, 36.083333f, 39.75f, 45.875f, 52f, 60f,
            51.33333f, 45.583333f, 39.8333f, 37.916666f, 36, 36};

        for (int i = 0; i < 29; i++) {

            String name;
            if (i > 14) {
                name = "" + (28 - i) + "'";
            } else {
                name = "" + i;
            }

            float xPosition = i * 3.8f - 64.6f;
            float yPosition = 13.3f;
            float zPosition = 3.8f;

            // create upper point
            Point point = new Point("U" + name, "" + xPosition, "" + yPosition, "" + zPosition);
            point.createDefaultSchematicRepresentation();
            schematic.add(point);

            if (i > 0 && i < 28) {
                // create lower point
                int lowerIndex = i <= 14 ? i - 1 : 28 - i-1;
                float yPositionLower = yPosition - lowerHeights[lowerIndex]/10;

                Point lowerPoint = new Point("L" + name, "" + xPosition, "" + yPositionLower, "" + zPosition);
                lowerPoint.createDefaultSchematicRepresentation();
                schematic.add(lowerPoint);
            }
        }

        ModelNode modelNode = ModelNode.load("bridge/assets/", "bridge/assets/bridge.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0f, 0, 0);
        Matrix3f modelRotation = new Matrix3f();
        modelRotation.fromAngleAxis(-(float) Math.PI / 2, Vector3f.UNIT_Y);
        float modelScale = 19f / 10f;
        ModelRepresentation rep;

        // extract something that will be discarded from the actual view
        rep = modelNode.extractElement(new Point("discard"), "VisualSceneNode/group36");

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);

        schematic.getBackground().addRepresentation(rep);
    }
}
