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
import edu.gatech.statics.modes.description.DescriptionMode;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.modes.fbd.SimpleFreeBodyDiagram;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.Default3DInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class SimpleFBDExercise extends OrdinaryExercise {

    public SimpleFBDExercise(Schematic world) {
        super(world);
    }

    public SimpleFBDExercise() {
    }

    /**
     * Loads the SelectMode
     * @return SelectMode.instance
     */
    @Override
    public Mode loadStartingMode() {
        SelectMode.instance.load();
        return SelectMode.instance;
    }

    /**
     * Returns a SimpleFreeBodyDiagram instead of a regular FreeBodyDiagram.
     * @param bodySubset
     * @return
     */
    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodySubset) {
        return new SimpleFreeBodyDiagram(bodySubset);
    }

    /**
     * Ordinary exercises support the Select, FBD, and Equation modes.
     * @param type
     * @return
     */
    @Override
    public boolean supportsType(DiagramType type) {
        if (type == DescriptionMode.instance.getDiagramType()) {
            return true;
        } else if (type == SelectMode.instance.getDiagramType()) {
            return true;
        } else if (type == FBDMode.instance.getDiagramType()) {
            return true;
        }

        return false;
    }

    /**
     * Returns an interface configuration without the EquationModePanel.
     * @return
     */
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new SimpleInterfaceConfiguration();
    }

    private class SimpleInterfaceConfiguration extends Default3DInterfaceConfiguration {

        public SimpleInterfaceConfiguration() {
        }

        @Override
        public void createModePanels() {
            List<ApplicationModePanel> r = getModePanels();
            r.add(new SelectModePanel());
            r.add(new FBDModePanel());
        }
    }
}
