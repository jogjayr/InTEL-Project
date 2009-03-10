/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.truss.TrussSectionDiagram;
import edu.gatech.statics.modes.truss.TrussSectionMode;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussModePanel extends ApplicationModePanel<TrussSectionDiagram> {

    public TrussModePanel() {
        getTitleLabel().setText("Create a Section");
    }

    @Override
    public DiagramType getDiagramType() {
        return TrussSectionMode.instance.getDiagramType();
    }

    @Override
    protected ApplicationTab createTab() {
        ApplicationTab tab = new ApplicationTab("Create Section");
        tab.setPreferredSize(125, -1);
        return tab;
    }
}
