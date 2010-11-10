/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.bodies.representations.BoxRepresentation;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.math.BigDecimal;

/**
 *
 * @author gtg126z
 */
public class PanelExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        interfaceConfiguration.getViewConstraints().setRotationConstraints(-4, 4, -1, 1);
        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Panel");
        description.setNarrative("This is a simple 3d exercise.");

        return description;
    }

    @Override
    public void initExercise() {
        super.initExercise();
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        Potato panel = new Potato("board");

//        panel.addRepresentation(new BoxRepresentation(panel, 2, .1f, 1));


        Point D = new Point("D", "5", "0", "-3");
        Point A = new Point("A", "3", "0", "0");
        Point B = new Point("B", "6", "0", "-6");
        Point C = new Point("C", "10", "0", "-4");

        Force forceG = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal("500"));
        forceG.setName("weight");
        Force forceA = new Force(A, Vector3bd.UNIT_Y, "Alice");
        forceA.setName("Alice");
        Force forceB = new Force(B, Vector3bd.UNIT_Y, "Bob");
        forceB.setName("Bob");
        Force forceC = new Force(C, Vector3bd.UNIT_Y, "Cathy");
        forceC.setName("Cathy");

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

        ModelNode modelNode = ModelNode.load("panel/assets/", "panel/assets/panel.dae");
        modelNode.extractLights();

        float scale = 2;

        ModelRepresentation rep;
        rep = modelNode.extractElement(panel, "RootNode/scene/panel");
        panel.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setLocalScale(scale);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setLocalScale(scale);
        schematic.getBackground().addRepresentation(rep);

    }
}
