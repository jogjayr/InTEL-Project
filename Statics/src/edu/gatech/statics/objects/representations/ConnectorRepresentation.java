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
package edu.gatech.statics.objects.representations;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.objects.SimulationObject;

/**
 * ConnectorRepresentation is a special type of point representation that will
 * disappear outside of the selection/schematic mode. Symbols for connectors 
 * (pins, rollers, and the like) are not supposed to appear on free body diagrams.
 * Here we disable the representation in the update method.
 * @author Calvin Ashmore
 */
public class ConnectorRepresentation extends PointRepresentation {

    private boolean enabled = false;

    public ConnectorRepresentation(SimulationObject target, String imagePath) {
        super(target, imagePath);
    }

    public ConnectorRepresentation(SimulationObject target) {
        super(target);
    }

    @Override
    public void update() {

        // return if the the application is not loaded.
        if (StaticsApplication.getApp() == null) {
            return;
        }

        // we should enable the representation if the diagram is a select diagram
        boolean shouldEnable = StaticsApplication.getApp().getCurrentDiagram() instanceof SelectDiagram;

        enabled = shouldEnable;

        if (enabled) {
            setHidden(false);
        } else {
            setHidden(true);
        }
        super.update();
    }
}
