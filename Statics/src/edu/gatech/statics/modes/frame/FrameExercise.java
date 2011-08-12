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
package edu.gatech.statics.modes.frame;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;

/**
 * A frame exercise is like an ordinary exercise, but it provides some UI elements to simplify the 
 * process working with frames.
 * @author Calvin Ashmore
 */
abstract public class FrameExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();

        // replace the mode default select mode panel with the frame one.
        ic.replaceModePanel(SelectModePanel.class, new FrameSelectModePanel());

        return ic;
    }

    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodies) {

        // set up the special name for the whole frame, in frame problems
        if (FrameUtil.isWholeDiagram(bodies)) {
            bodies.setSpecialName(FrameUtil.whatToCallTheWholeDiagram);
        }

        return super.createFreeBodyDiagram(bodies);
    }

    @Override
    protected SelectDiagram createSelectDiagram() {
        return new FrameSelectDiagram();
    }
}
