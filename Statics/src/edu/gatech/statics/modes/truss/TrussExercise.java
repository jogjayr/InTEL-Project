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
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.frame.FrameSelectDiagram;
import edu.gatech.statics.modes.frame.FrameUtil;
import edu.gatech.statics.modes.select.SelectAllAction;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.truss.ui.TrussInterfaceConfiguration;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class TrussExercise extends OrdinaryExercise {

    /**
     * creates an interface configuration for the truss exercise
     * @return
     */
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new TrussInterfaceConfiguration();
    }

    /**
     * Constructor
     */
    public TrussExercise() {
        //  set the "Whole Frame" label in FrameUtil to "Whole Truss"
        FrameUtil.whatToCallTheWholeDiagram = "Whole Truss";
    }

    /**
     * Constructor
     * @param schematic
     */
    public TrussExercise(Schematic schematic) {
        super(schematic);
    }

    /**
     * 
     * @return
     */
    @Override
    public Mode loadStartingMode() {
        //return super.loadStartingMode();
        ZFMMode.instance.load();
        return ZFMMode.instance;
    }

    /**
     * Checks type and returns either a section or zero force memeber diagram
     * @param key 
     * @param type
     * @return 
     */
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == TrussSectionMode.instance.getDiagramType()) {
            return createSectionDiagram();
        }
        if (type == ZFMMode.instance.getDiagramType()) {
            return createZFMDiagram();
        }

        return super.createNewDiagramImpl(key, type);
    }

    /**
     * 
     * @return
     */
    protected TrussSectionDiagram createSectionDiagram() {
        return new TrussSectionDiagram();
    }

    /**
     * 
     * @return
     */
    protected ZFMDiagram createZFMDiagram() {
        return new ZFMDiagram();
    }

    /**
     * Creates an FBD from bodies. Sets up the special name for the whole truss, in truss problems
     * @param bodies
     * @return A special truss fbd which handles a few things that aren't covered elsewhere
     */
    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodies) {

        // set up the special name for the whole truss, in truss problems
        if (FrameUtil.isWholeDiagram(bodies)) {
            bodies.setSpecialName(FrameUtil.whatToCallTheWholeDiagram);
        }

        // return a special truss fbd, which handles a few things that are not covered elsewhere.
        return new TrussFreeBodyDiagram(bodies);
    }

    /**
     * Returns a FrameSelectDiagram, but modifies this so that it is not possible for ZFMs to get selected in the select-all call.
     * @return
     */
    @Override
    protected SelectDiagram createSelectDiagram() {
        return new FrameSelectDiagram() {
            @Override
            protected SelectAllAction createSelectAllAction(List<SimulationObject> objects) {
                List<SimulationObject> objectsWithoutZFMs = new ArrayList<SimulationObject>();
                for (SimulationObject simulationObject : objects) {
                    if (simulationObject instanceof ZeroForceMember) {
                        // pass
                    } else {
                        objectsWithoutZFMs.add(simulationObject);
                    }
                }

                return super.createSelectAllAction(objectsWithoutZFMs);
            }
        };
    }
}
