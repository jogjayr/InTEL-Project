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
package edu.gatech.statics.modes.truss.ui;

import com.jme.math.Vector2f;
import com.jme.system.DisplaySystem;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.truss.SectionCut;
import edu.gatech.statics.modes.truss.TrussSectionDiagram;
import edu.gatech.statics.modes.truss.TrussSectionMode;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.util.Pair;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussModePanel extends ApplicationModePanel<TrussSectionDiagram> {

    private SectionPopup popup1, popup2;
    private BLabel label;

    /**
     * 
     */
    public TrussModePanel() {
        //getTitleLabel().setText("Create a Section");


        //****** text field for "you have cut through"...
        BContainer container = new BContainer(new BorderLayout());
        label = new BLabel("");
        container.add(label, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);
    }

    /**
     * Getter
     * @return
     */
    @Override
    public DiagramType getDiagramType() {
        return TrussSectionMode.instance.getDiagramType();
    }

    /**
     * Shows section boxes for section
     * @param section
     */
    public void showSectionBoxes(SectionCut section) {

        Pair<Vector2f, Vector2f> popupCoordinates = getPopupCoordinates(section);
        int x1 = (int) popupCoordinates.getLeft().x;
        int y1 = (int) popupCoordinates.getLeft().y;
        int x2 = (int) popupCoordinates.getRight().x;
        int y2 = (int) popupCoordinates.getRight().y;

        popup1.popup(x1, y1, true);
        popup2.popup(x2, y2, true);

        popup1.setLocation(x1 - popup1.getWidth() / 2, y1 - popup1.getHeight() / 2);
        popup2.setLocation(x2 - popup2.getWidth() / 2, y2 - popup2.getHeight() / 2);

        updateText(section);
    }

    /**
     * Updates text around section
     * @param section
     */
    protected void updateText(SectionCut section) {

        if (section == null) {
            // clear
            label.setText("");
            StaticsApplication.getApp().resetUIFeedback();
        } else {
            TrussSectionDiagram diagram = getDiagram();
            List<TwoForceMember> cutMembers = diagram.getAllCutMembers(section);
            StaticsApplication.getApp().setUIFeedback("You have cut through "+cutMembers.size()+" members: ");

            //String contents = "<font size=\"5\" color=\"white\">";
            String contents = "@=b(";
            for (int i = 0; i < cutMembers.size(); i++) {
                TwoForceMember body = cutMembers.get(i);
                if (i > 0) {
                    contents += ", ";
                }
                contents += body.getName();
            }
            //contents += "</font>";
            contents += ")";
            contents += "\nYou may click and drag to create another section.";
            label.setText(contents);

        }
        InterfaceRoot.getInstance().getApplicationBar().updateSize();
    }

    /**
     * Hides section boxes
     */
    public void hideSectionBoxes() {
        popup1.dismiss();
        popup2.dismiss();
        updateText(null);
    }

    /**
     * Initialize popups if they do not exist yet
     */
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

//    @Override
//    protected ApplicationTab createTab() {
//        ApplicationTab tab = new ApplicationTab("Create Section");
//        tab.setPreferredSize(125, -1);
//        return tab;
//    }
    /**
     * Calculates and returns coordinates of section boxes for section
     * @param section
     * @return
     */
    private Pair<Vector2f, Vector2f> getPopupCoordinates(SectionCut section) {
        // we need to position the popup coordinates according to the section.
        Vector2f sectionDirection = section.getSectionEnd().subtract(section.getSectionStart()).normalize();
        Vector2f sectionPerp = new Vector2f(sectionDirection.y, -sectionDirection.x);

        // the line produced by sectionPerp goes through the center of the screen.
        Vector2f screenCenter = new Vector2f(
                DisplaySystem.getDisplaySystem().getWidth() / 2,
                DisplaySystem.getDisplaySystem().getHeight() / 2 + 0);

        Vector2f intersectionPoint = calculateIntersectionPoint(
                section.getSectionStart(), section.getSectionEnd(),
                screenCenter, screenCenter.add(sectionPerp));

        float offsetDistance = 125;

        // stretch the bubble a little,
        // horizontal looks smaller because the actual popup boxes are rectangular.
        sectionPerp.x *= 1.5;

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
