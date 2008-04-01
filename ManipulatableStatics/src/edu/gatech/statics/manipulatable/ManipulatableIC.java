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
