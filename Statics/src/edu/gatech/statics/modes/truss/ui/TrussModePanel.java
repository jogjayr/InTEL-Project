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

    private SectionPopup popup1,  popup2;

    public TrussModePanel() {
        getTitleLabel().setText("Create a Section");
    }

    @Override
    public DiagramType getDiagramType() {
        return TrussSectionMode.instance.getDiagramType();
    }

    public void showSectionBoxes() {
        popup1.popup(300, 300, true);
        popup2.popup(600, 300, true);
    }

    public void hideSectionBoxes() {
        popup1.dismiss();
        popup2.dismiss();
    }

    @Override
    public void activate() {
        super.activate();

        if (popup1 == null) {
            // initialize the popups if they do not exist yet.
            popup1 = new SectionPopup(this.getWindow(), 1);
            popup2 = new SectionPopup(this.getWindow(), -1);
        }

        hideSectionBoxes();
    }

    @Override
    protected ApplicationTab createTab() {
        ApplicationTab tab = new ApplicationTab("Create Section");
        tab.setPreferredSize(125, -1);
        return tab;
    }
}
