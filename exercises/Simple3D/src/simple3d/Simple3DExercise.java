/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple3d;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.equation.Equation3DDiagram;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.ui.Equation3DModePanel;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.bodies.representations.BoxRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.math.BigDecimal;

/**
 *
 * @author gtg126z
 */
public class Simple3DExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        interfaceConfiguration.getViewConstraints().setRotationConstraints(-4, 4, -1, 1);
        interfaceConfiguration.replaceModePanel(EquationModePanel.class, new Equation3DModePanel());

        return interfaceConfiguration;
    }

    @Override
    protected EquationDiagram createEquationDiagram(BodySubset bodies) {
        //return super.createEquationDiagram(bodies);
        // instead of returning the basic EquationDiagram, returns EquationDiagram3d
        return new Equation3DDiagram(bodies);
    }



    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Simple 3D");
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

        Potato potato = new Potato("board");

        potato.addRepresentation(new BoxRepresentation(potato, 2, .1f, 1));


        Point G = new Point("G", "0", "0", "0");
        Point A = new Point("A", "-1", "0", "-1");
        Point B = new Point("B", "0", "0", "1");
        Point C = new Point("C", "2", "0", "0");

        Force forceG = new Force(G, Vector3bd.UNIT_Y.negate(), new BigDecimal("500"));
        forceG.setName("weight");
        Force forceA = new Force(A, Vector3bd.UNIT_Y, "Alice");
        forceA.setName("Alice");
        Force forceB = new Force(B, Vector3bd.UNIT_Y, "Bob");
        forceB.setName("Bob");
        Force forceC = new Force(C, Vector3bd.UNIT_Y, "Cathy");
        forceC.setName("Cathy");

        potato.addObject(G);
        potato.addObject(A);
        potato.addObject(B);
        potato.addObject(C);
        potato.addObject(forceG);
        potato.addObject(forceA);
        potato.addObject(forceB);
        potato.addObject(forceC);

        G.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        forceG.createDefaultSchematicRepresentation();
        forceA.createDefaultSchematicRepresentation();
        forceB.createDefaultSchematicRepresentation();
        forceC.createDefaultSchematicRepresentation();

        schematic.add(potato);
//        schematic.add(G);
//        schematic.add(A);
//        schematic.add(B);
//        schematic.add(C);


    }
}
