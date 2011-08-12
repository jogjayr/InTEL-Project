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
package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.fbd.actions.LabelLoad;
import edu.gatech.statics.objects.Load;

/**
 * 
 * @author Calvin Ashmore
 */
public class LoadLabelListener implements LabelListener {

    private Load myLoad;

    /**
     * Constructor
     * @param load
     */
    public LoadLabelListener(Load load) {
        this.myLoad = load;
    }

    /**
     * Called when load is labeled. Creates and performs a labelLoadAction
     * @param label
     * @return Action performed successfully?
     */
    public boolean onLabel(String label) {
        if (label.length() == 0) {
            return false;
        }

        // constructing a Label 
        try {
            LabelLoad labelLoadAction = new LabelLoad(myLoad.getAnchoredVector(), label);
            // try to get the current FBD
            FreeBodyDiagram diagram = (FreeBodyDiagram) StaticsApplication.getApp().getCurrentDiagram();
            diagram.performAction(labelLoadAction);
            
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
}
