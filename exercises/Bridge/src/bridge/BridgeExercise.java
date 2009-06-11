/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.truss.TrussExercise;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.MimicRepresentation;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setForceLabelDistance(5f);
        getDisplayConstants().setMomentLabelDistance(10f);
        getDisplayConstants().setMeasurementBarSize(0.2f);


        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));
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

        // setup all of the bars
        setupBars(modelNode);

        setupMeasurements();

        // create the base supports.
        PointBody pinJoint = (PointBody) getSchematic().getByName("Joint " + getJointName("L", 1));
        Pin2d pinBase = new Pin2d(pinJoint.getAnchor());
        pinBase.setName("pin base");
        pinBase.attachToWorld(pinJoint);
        getSchematic().add(pinBase);
        pinBase.createDefaultSchematicRepresentation();

        PointBody rollerJoint = (PointBody) getSchematic().getByName("Joint " + getJointName("L", 8));
        Roller2d rollerBase = new Roller2d(rollerJoint.getAnchor());
        rollerBase.setName("roller base");
        rollerBase.setDirection(Vector3bd.UNIT_Y);
        rollerBase.attachToWorld(rollerJoint);
        getSchematic().add(rollerBase);
        rollerBase.createDefaultSchematicRepresentation();

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

        // calculate whether this is a ZFM
        boolean isZfm = false;
        if (index1 == index2 && index1 % 2 == 0 && index1 != 8) {
            isZfm = true;
        }
        if (index1 == 13 && index2 == 14 && prefix1.equals("L") && prefix2.equals("L")) {
            isZfm = true;
        }

        // calculate whether this is a redundant bar (used to create potentials, but not for use in the diagram)
        //boolean isRedundant = false;
        //if (prefix1.equals("L") && prefix2.equals("L") && index2 == index1 + 1 && !(index1 == 8 || index2 == 8 || index2 == 14)) {
        //    isRedundant = true;
        //}

        // calculate whether to create a potential zfm for this member
        boolean createPotential = true;
        if (index2 - index1 == 2) {
            createPotential = false;
        }

        TwoForceMember bar = null;
        String barName = "Bar " + joint1Name + "-" + joint2Name;
        if (isZfm) {
            Point anchor1 = (Point) getSchematic().getByName(joint1Name);
            Point anchor2 = (Point) getSchematic().getByName(joint2Name);

            bar = new ZeroForceMember(barName, anchor1, anchor2);
        } else {
            bar = new Bar(barName, joint1.getAnchor(), joint2.getAnchor());
            //bar.createDefaultSchematicRepresentation();

            Connector2ForceMember2d barconnect1 = new Connector2ForceMember2d(joint1.getAnchor(), bar);
            barconnect1.attach(bar, joint1);

            Connector2ForceMember2d barconnect2 = new Connector2ForceMember2d(joint2.getAnchor(), bar);
            barconnect2.attach(bar, joint2);

        }

        //if (!isRedundant) {
        getSchematic().add(bar);
        setupBarModelRepresentation(bar, modelNode, prefix1, index1, prefix2, index2);
        //}

        if (createPotential) {
            PotentialZFM potential = new PotentialZFM(isZfm);
            potential.setBaseName(barName);
            String modelPath = getModelPath(prefix1, index1, prefix2, index2);

            potential.addRepresentation(new MimicRepresentation(potential, modelRepresentations.get(modelPath)));
            //potential.addRepresentations(bar);
            getSchematic().add(potential);
        }
    }

    private String getModelPath(String prefix1, int index1, String prefix2, int index2) {
        int location1 = index1 > 14 ? 28 - index1 : index1;
        int location2 = index2 > 14 ? 28 - index2 : index2;
        String side = (index1 <= 14 ? "left" : "right");
        String modelPath = trussFront + "/trussFront_" + side +
                "/trussFront_" + side + "_members" +
                "/trussFront_" + side + "_members_" + prefix1.toLowerCase() + location1 + "_" + prefix2.toLowerCase() + location2;
        return modelPath;
    }
    private Map<String, ModelRepresentation> modelRepresentations = new HashMap();

    private void setupBarModelRepresentation(TwoForceMember bar, ModelNode modelNode, String prefix1, int index1, String prefix2, int index2) {

        //if (index2 - index1 == 2) {
        //    // special case for the joined bars.
        //    extractRepresentation(bar, modelNode, prefix1, index1, prefix2, index2 - 1);
        //    extractRepresentation(bar, modelNode, prefix1, index2 - 1, prefix2, index2);
        //} else {
        extractRepresentation(bar, modelNode, prefix1, index1, prefix2, index2);
    //}
    }

    private void extractRepresentation(TwoForceMember bar, ModelNode modelNode, String prefix1, int index1, String prefix2, int index2) {

        String modelPath = getModelPath(prefix1, index1, prefix2, index2);

        //System.out.println("DEBUG: Extracting... " + modelPath);
        ModelRepresentation rep = modelNode.extractElement(bar, modelPath);
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        bar.addRepresentation(rep);

        modelRepresentations.put(modelPath, rep);
    }

    private String getJointName(String prefix, int index) {
        boolean prime = false;
        if (index > 14) {
            index = 28 - index;
            prime = true;
        }
        return prefix + index + (prime ? "'" : "");
    }

    private PointBody createJoint(String prefix, String name, float x, float y, float z, ModelNode modelNode, int index) {
        Point lowerPoint = new Point(prefix + name, "" + x, "" + y, "" + z);
        lowerPoint.createDefaultSchematicRepresentation();
        getSchematic().add(lowerPoint);


        // skip this last joint, it should not actually be included.
        if (prefix.equals("L") && index == 14) {
            return null;
        }

        PointBody pointBody = new PointBody("Joint " + lowerPoint.getName(), lowerPoint);
        pointBody.createDefaultSchematicRepresentation();
        getSchematic().add(pointBody);

        int location = index > 14 ? 28 - index : index;
        String side = (index <= 14 ? "left" : "right");
        String modelPath = trussFront + "/trussFront_" + side +
                "/trussFront_" + side + "_joints" +
                "/trussFront_" + side + "_joints_" + prefix.toLowerCase() + location;
        ModelRepresentation rep = modelNode.extractElement(pointBody, modelPath);
        rep.setModelOffset(modelTranslation);
        rep.setModelRotation(modelRotation);
        rep.setModelScale(modelScale);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        pointBody.addRepresentation(rep);

        return pointBody;
    }

    private void setupBars(ModelNode modelNode) {
        // then the rest
        for (int i = 0; i < 14; i++) {
            // upper bars
            setupBar(modelNode, "U", i, "U", i + 1);
            if (i > 0) {
                // lower bars
                setupBar(modelNode, "L", i, "L", i + 1);
                // verticals
                setupBar(modelNode, "U", i, "L", i);
            }
            // cross bars
            String crossPrefix1 = i % 2 == 0 ? "U" : "L";
            String crossPrefix2 = i % 2 == 1 ? "U" : "L";
            setupBar(modelNode, crossPrefix1, i, crossPrefix2, i + 1);
        }

        // get that middle bar
        setupBar(modelNode, "U", 14, "L", 14);
    }

    private void setupJoints(ModelNode modelNode, float[] lowerHeights) {
        for (int i = 0; i <= 14; i++) {
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
            PointBody upperJoint = createJoint("U", name, xPosition, yPosition, zPosition, modelNode, i);

            int forceAmount = 600;
            if (i == 0 || i == 14) {
                forceAmount = 300;
            }

            Force force = new Force(upperJoint.getAnchor(), Vector3bd.UNIT_Y.negate(), new BigDecimal(forceAmount));
            force.createDefaultSchematicRepresentation();
            force.setName("load-U" + name);
            upperJoint.addObject(force);
            getSchematic().add(force);

            if (i > 0 && i < 28) {
                // create lower point
                int lowerIndex = i <= 14 ? i - 1 : 28 - i - 1;
                float yPositionLower = yPosition - lowerHeights[lowerIndex] / 10;
                createJoint("L", name, xPosition, yPositionLower, zPosition, modelNode, i);
            }
        }
    }

    private void setupMeasurements() {
        for (int i = 0; i < 14; i++) {
            Point p1 = (Point) getSchematic().getByName(getJointName("U", i + 1));
            Point p2 = (Point) getSchematic().getByName(getJointName("U", i));
            DistanceMeasurement measure = new DistanceMeasurement(p1, p2);
            measure.createDefaultSchematicRepresentation();
            getSchematic().add(measure);

            // get the vertical measures
            p2 = (Point) getSchematic().getByName(getJointName("L", i + 1));
            measure = new DistanceMeasurement(p1, p2);
            measure.createDefaultSchematicRepresentation(.5f);
            getSchematic().add(measure);
        }

        for (int i = 0; i <= 14; i += 2) {
            // angle measurements on the UPPER joints
            Point p = (Point) getSchematic().getByName(getJointName("U", i));

            // hind
            if (i > 0) {
                Point p1 = (Point) getSchematic().getByName(getJointName("L", i - 1));
                AngleMeasurement measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X.negate());
                measure.createDefaultSchematicRepresentation();
                measure.setName("angle x-U" + i + "-L" + (i - 1));
                getSchematic().add(measure);
            }
            // fore
            if (i < 14) {
                Point p1 = (Point) getSchematic().getByName(getJointName("L", i + 1));
                AngleMeasurement measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X);
                measure.createDefaultSchematicRepresentation();
                measure.setName("angle x-U" + i + "-L" + (i + 1));
                getSchematic().add(measure);
            }
        }

        for (int i = 1; i < 14; i += 2) {
            // angle measurements on the LOWER joints
            Point p = (Point) getSchematic().getByName(getJointName("L", i));

            // hind upper
            Point p1 = (Point) getSchematic().getByName(getJointName("U", i - 1));
            AngleMeasurement measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X.negate());
            measure.createDefaultSchematicRepresentation();
            measure.setName("angle x-L" + i + "-U" + (i - 1));
            getSchematic().add(measure);

            if (i > 1) {
                // hind lower
                p1 = (Point) getSchematic().getByName(getJointName("L", i - 1));
                measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X.negate());

                if (i < 8) {
                    measure.createDefaultSchematicRepresentation(1.5f);
                } else {
                    measure.createDefaultSchematicRepresentation();
                }

                measure.setName("angle x-L" + i + "-L" + (i - 1));
                getSchematic().add(measure);
            }

            // fore upper
            p1 = (Point) getSchematic().getByName(getJointName("U", i + 1));
            measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X);
            measure.createDefaultSchematicRepresentation();
            measure.setName("angle x-L" + i + "-U" + (i + 1));
            getSchematic().add(measure);

            // fore lower
            p1 = (Point) getSchematic().getByName(getJointName("L", i + 1));
            measure = new FixedAngleMeasurement(p, p1, Vector3f.UNIT_X);
            if (i > 8) {
                measure.createDefaultSchematicRepresentation(1.5f);
            } else {
                measure.createDefaultSchematicRepresentation();
            }
            measure.setName("angle x-L" + i + "-L" + (i + 1));
            getSchematic().add(measure);
        }

        // deal with bar 8:
        Point pl8 = (Point) getSchematic().getByName(getJointName("L", 8));
        Point pl7 = (Point) getSchematic().getByName(getJointName("L", 7));
        Point pl9 = (Point) getSchematic().getByName(getJointName("L", 9));

        AngleMeasurement measure = new FixedAngleMeasurement(pl8, pl7, Vector3f.UNIT_X.negate());
        measure.createDefaultSchematicRepresentation();
        measure.setName("angle x-L8-L7");
        getSchematic().add(measure);

        measure = new FixedAngleMeasurement(pl8, pl9, Vector3f.UNIT_X);
        measure.createDefaultSchematicRepresentation();
        measure.setName("angle x-L8-L9");
        getSchematic().add(measure);
    }
}
