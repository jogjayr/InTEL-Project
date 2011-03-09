/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.Mode;
import edu.gatech.statics.modes.equation.Equation3DDiagram;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.ui.Equation3DModePanel;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.ui.FBD3DModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.modes.findpoints.FindPointsDiagram;
import edu.gatech.statics.modes.findpoints.FindPointsMode;
import edu.gatech.statics.modes.findpoints.ui.FindPointsModePanel;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;

/**
 *
 * @author gtg126z
 */
abstract public class Ordinary3DExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        interfaceConfiguration.getViewConstraints().setRotationConstraints(-4, 4, -1, 1);
        interfaceConfiguration.replaceModePanel(EquationModePanel.class, new Equation3DModePanel());
        interfaceConfiguration.replaceModePanel(FBDModePanel.class, new FBD3DModePanel());
        interfaceConfiguration.getModePanels().add(new FindPointsModePanel());
        return interfaceConfiguration;
    }

    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == FindPointsMode.instance.getDiagramType()) {
            return new FindPointsDiagram();
        }
        return super.createNewDiagramImpl(key, type);
    }

    @Override
    public boolean supportsType(DiagramType type) {
        return type == FindPointsMode.instance.getDiagramType()
                || super.supportsType(type);
    }

    @Override
    public Mode loadStartingMode() {
        //return super.loadStartingMode();
        FindPointsMode.instance.load();
        return FindPointsMode.instance;
    }

    @Override
    protected EquationDiagram createEquationDiagram(BodySubset bodies) {
        //return super.createEquationDiagram(bodies);
        // instead of returning the basic EquationDiagram, returns EquationDiagram3d
        return new Equation3DDiagram(bodies);
    }
}
