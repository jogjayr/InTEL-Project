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
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveZFMTask extends Task {

    /**
     * For persistence, do not use.
     * @param name
     */
    public SolveZFMTask(String name) {
        super(name);
    }

    public boolean isSatisfied() {

        ZFMDiagram diagram = (ZFMDiagram) Exercise.getExercise().getDiagram(null, ZFMMode.instance.getDiagramType());
        
        // terminate early if the diagram does not yet exist.
        if (diagram == null) {
            return false;
        }
        return diagram.isLocked();
    }

    public String getDescription() {
        return "Find all zero force members";
    }
}
