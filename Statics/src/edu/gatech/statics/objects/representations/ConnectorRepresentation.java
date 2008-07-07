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

    public ConnectorRepresentation(SimulationObject target, String imagePath) {
        super(target, imagePath);
    }

    public ConnectorRepresentation(SimulationObject target) {
        super(target);
    }
    private boolean enabled = true;

    @Override
    public void update() {
        super.update();

        // we should enable the representation if the diagram is a select diagram
        boolean shouldEnable = StaticsApplication.getApp().getCurrentDiagram() instanceof SelectDiagram;

        // states are consistent, no need to do anything
        if(shouldEnable == enabled)
            return;
        
        enabled = shouldEnable;
        
        if (enabled) {
            getRelativeNode().attachChild(getQuad());
        } else {
            getRelativeNode().detachChild(getQuad());
        }
    }
}
