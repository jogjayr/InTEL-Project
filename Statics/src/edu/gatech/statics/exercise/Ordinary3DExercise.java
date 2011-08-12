/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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

    /**
     * Creates an interface configuration for this type of exercise.
     * Sets navigation window to Navigation3DWindow, rotationConstraints to (-4, 4, -1, 1)
     * replaces EquationModePanel and FBDModePanel with Equation3DModePanel and FBD3DModePanel
     * and adds a FindPointsModePanel
     * @return
     */
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

    /**
     * Overrides parent implementation to handle type FindPointsMode
     * @param key
     * @param type
     * @return
     */
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == FindPointsMode.instance.getDiagramType()) {
            return new FindPointsDiagram();
        }
        return super.createNewDiagramImpl(key, type);
    }

    /**
     * 
     * @param type
     * @return Does exercise contain type
     */
    @Override
    public boolean supportsType(DiagramType type) {
        return type == FindPointsMode.instance.getDiagramType()
                || super.supportsType(type);
    }

    /**
     * Loads FindPointsMode (this is the starting mode of 3D exercises)
     * @return
     */
    @Override
    public Mode loadStartingMode() {
        //return super.loadStartingMode();
        FindPointsMode.instance.load();
        return FindPointsMode.instance;
    }

    /**
     * Creates a new Equation3DDiagram composed of bodies
     * @param bodies
     * @return
     */
    @Override
    protected EquationDiagram createEquationDiagram(BodySubset bodies) {
        //return super.createEquationDiagram(bodies);
        // instead of returning the basic EquationDiagram, returns EquationDiagram3d
        return new Equation3DDiagram(bodies);
    }
}
