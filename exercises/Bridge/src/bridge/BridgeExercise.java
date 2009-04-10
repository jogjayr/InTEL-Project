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
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
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

    private Vector3f modelTranslation;
    private Matrix3f modelRotation;
    private float modelScale;
    private String trussFront = "VisualSceneNode/scene/truss/trussFront";

    public BridgeExercise() {
        modelTranslation = new Vector3f(0f, 0, 0);
        modelRotation = new Matrix3f();
        modelRotation.fromAngleAxis(-(float) Math.PI / 2, Vector3f.UNIT_Y);
        modelScale = 19f / 10f;
    }

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


        ModelNode modelNode = ModelNode.load("bridge/assets/", "bridge/assets/bridge.dae");
        modelNode.extractLights();

        // first joints
        setupJoints(modelNode, lowerHeights);

        // then bars
        for (int i = 0; i < 14; i++) {
            // upper bars
            setupBar(modelNode, "U", i, "U", i + 1);
            setupBar(modelNode, "U", 28 - i, "U", 28 - i - 1);
            if (i > 0) {
                // lower bars
                setupBar(modelNode, "L", i, "L", i + 1);
                setupBar(modelNode, "L", 28 - i, "L", 28 - i - 1);

                // verticals
                setupBar(modelNode, "U", i, "L", i);
                setupBar(modelNode, "U", 28 - i, "L", 28 - i);
            }
            // cross bars
            String crossPrefix1 = i % 2 == 0 ? "U" : "L";
            String crossPrefix2 = i % 2 == 1 ? "U" : "L";
            setupBar(modelNode, crossPrefix1, i, crossPrefix2, i + 1);
            setupBar(modelNode, crossPrefix1, 28 - i, crossPrefix2, 28 - i - 1);
        }
        // get that middle bar
        setupBar(modelNode, "U", 14, "L", 14);


        // extract something that will be discarded from the actual view
        modelNode.extractElement(new Point("discard"), "VisualSceneNode/group36");

        ModelRepresentation background = modelNode.getRemainder(schematic.getBackground());
        background.setModelOffset(modelTranslation);
        background.setModelRotation(modelRotation);
        background.setModelScale(modelScale);

        schematic.getBackground().addRepresentation(background);
    }

    private void setupBar(ModelNode modelNode, String prefix1, int index1, String prefix2, int index2) {
        String joint1Name = getJointName(prefix1, index1);
        String joint2Name = getJointName(prefix2, index2);
        PointBody joint1 = (PointBody) getSchematic().getByName("Joint " + joint1Name);
        PointBody joint2 = (PointBody) getSchematic().getByName("Joint " + joint2Name);

        if (joint1 == null) {
            throw new NullPointerException("Could not find joint: \"Joint " + joint1Name + "\"");
        }
        if (joint2 == null) {
            throw new NullPointerException("Could not find joint: \"Joint " + joint2Name + "\"");
        }

        Bar bar = new Bar("Bar " + joint1Name + "-" + joint2Name, joint1.getAnchor(), joint2.getAnchor());
        //bar.createDefaultSchematicRepresentation();
        getSchematic().add(bar);
        setupBarModelRepresentation(bar, modelNode, prefix1, index1, prefix2, index2);
    }

    private void setupBarModelRepresentation(Bar bar, ModelNode modelNode, String prefix1, int index1, String prefix2, int index2) {

        int location1 = index1 > 14 ? 28 - index1 : index1;
        int location2 = index2 > 14 ? 28 - index2 : index2;
        String side = (index1 <= 14 ? "left" : "right");
        String modelPath = trussFront + "/trussFront_" + side +
                "/trussFront_" + side + "_members" +
                "/trussFront_" + side + "_members_" + prefix1.toLowerCase() + location1 + "_" + prefix2.toLowerCase() + location2;

        //System.out.println("DEBUG: Extracting... " + modelPath);
        ModelRepresentation rep = modelNode.extractElement(bar, modelPath);
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        bar.addRepresentation(rep);
    }

    private String getJointName(String prefix, int index) {
        boolean prime = false;
        if (index > 14) {
            index = 28 - index;
            prime = true;
        }
        return prefix + index + (prime ? "'" : "");
    }

    private void createJoint(String prefix, String name, float x, float y, float z, ModelNode modelNode, int index) {
        Point lowerPoint = new Point(prefix + name, "" + x, "" + y, "" + z);
        lowerPoint.createDefaultSchematicRepresentation();
        getSchematic().add(lowerPoint);

        PointBody lowerPointBody = new PointBody("Joint " + lowerPoint.getName(), lowerPoint);
        lowerPointBody.createDefaultSchematicRepresentation();
        getSchematic().add(lowerPointBody);

        int location = index > 14 ? 28 - index : index;
        String side = (index <= 14 ? "left" : "right");
        String modelPath = trussFront + "/trussFront_" + side +
                "/trussFront_" + side + "_joints" +
                "/trussFront_" + side + "_joints_" + prefix.toLowerCase() + location;
        ModelRepresentation rep = modelNode.extractElement(lowerPointBody, modelPath);
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        lowerPointBody.addRepresentation(rep);
    }

    private void setupJoints(ModelNode modelNode, float[] lowerHeights) {
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
            createJoint("U", name, xPosition, yPosition, zPosition, modelNode, i);
            if (i > 0 && i < 28) {
                // create lower point
                int lowerIndex = i <= 14 ? i - 1 : 28 - i - 1;
                float yPositionLower = yPosition - lowerHeights[lowerIndex] / 10;
                createJoint("L", name, xPosition, yPositionLower, zPosition, modelNode, i);
            }
        }
    }
}
