/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import com.jme.math.Vector2f;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.truss.SectionCut;
import edu.gatech.statics.modes.truss.TrussSectionDiagram;
import edu.gatech.statics.modes.truss.TrussSectionMode;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;
import edu.gatech.statics.util.Pair;

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

    public void showSectionBoxes(SectionCut section) {

        Pair<Vector2f, Vector2f> popupCoordinates = getPopupCoordinates(section);
        int x1 = (int) popupCoordinates.getLeft().x;
        int y1 = (int) popupCoordinates.getLeft().y;
        int x2 = (int) popupCoordinates.getRight().x;
        int y2 = (int) popupCoordinates.getRight().y;

        popup1.popup(x1, y1, true);
        popup2.popup(x2, y2, true);
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

    private Pair<Vector2f, Vector2f> getPopupCoordinates(SectionCut section) {
        // we need to position the popup coordinates according to the section.
        Vector2f sectionDirection = section.getSectionEnd().subtract(section.getSectionStart()).normalize();
        Vector2f sectionPerp = new Vector2f(sectionDirection.y, -sectionDirection.x);

        // the line produced by sectionPerp goes through the center of the screen.
        Vector2f screenCenter = new Vector2f(
                DisplaySystem.getDisplaySystem().getWidth() / 2,
                DisplaySystem.getDisplaySystem().getHeight() / 2 - 100);

        Vector2f intersectionPoint = calculateIntersectionPoint(
                section.getSectionStart(), section.getSectionEnd(),
                screenCenter, screenCenter.add(sectionPerp));

        float offsetDistance = 125;
        Vector2f popupLocation1 = intersectionPoint.add(sectionPerp.mult(offsetDistance));
        Vector2f popupLocation2 = intersectionPoint.add(sectionPerp.mult(-offsetDistance));
        return new Pair<Vector2f, Vector2f>(popupLocation1, popupLocation2);
    }

    /**
     * Calculates the intersection point between two lines, the arguments are the endpoints of the lines.
     * Arguments p1 and p2 are on the first line, p3 and p4 are on the second line.
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    private Vector2f calculateIntersectionPoint(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
        float denominator = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
        float xpart = (p1.x * p2.y - p1.y * p2.x) * (p3.x - p4.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x);
        float ypart = (p1.x * p2.y - p1.y * p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x);
        return new Vector2f(xpart / denominator, ypart / denominator);
    }
}
