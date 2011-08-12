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
package edu.gatech.statics.manipulatable;

import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableIC extends DefaultInterfaceConfiguration {

    @Override
    public List<ApplicationModePanel> createModePanels() {
        List<ApplicationModePanel> panels = super.createModePanels();
        panels.add(new ManipulatableModePanel());
        return panels;
    }

    @Override
    public void setupCameraControl(CameraControl cameraControl) {
        super.setupCameraControl(cameraControl);
        float panSpeed = .07f;
        float rotateSpeed = .05f;
        float zoomSpeed = .02f;
        cameraControl.setMovementSpeed(panSpeed, zoomSpeed, rotateSpeed);
    }

    @Override
    public ViewConstraints createViewConstraints() {
        ViewConstraints constraints = new ViewConstraints();

        constraints.setPositionConstraints(-2, 2, -2, 2);
        constraints.setZoomConstraints(.25f, 2);
        constraints.setRotationConstraints(-2f, 2f);

        return constraints;
    }
}
