/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.representations.CurveUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionDiagram extends Diagram<TrussSectionState> {

    private SectionTool sectionTool;
    private SectionCut currentCut;

    public SectionCut getCurrentCut() {
        return currentCut;
    }

    public TrussSectionDiagram() {
        super(null);
        sectionTool = new SectionTool();
    }

    @Override
    protected TrussSectionState createInitialState() {
        return new TrussSectionState();
    }

    @Override
    public void completed() {
        // do nothing.
    }

    @Override
    public Mode getMode() {
        return TrussSectionMode.instance;
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        return getSchematic().allObjects();
    }

    @Override
    public void activate() {
        super.activate();
        StaticsApplication.getApp().enableDrag(false);
        StaticsApplication.getApp().setCurrentTool(sectionTool);
        sectionTool.setEnabled(true);
        currentCut = null;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        StaticsApplication.getApp().enableDrag(true);
        sectionTool.cancel();
    }

    @Override
    public void render(Renderer r) {
        super.render(r);

        if (sectionTool.isEnabled() && sectionTool.isMouseDown()) {
            SectionCut sectionCut = sectionTool.getSectionCut();
            drawCut(r, sectionCut);
        } else if (currentCut != null) {
            drawCut(r, currentCut);
        }
    }

    void onCancel() {
        currentCut = null;
    }

    private void drawCut(Renderer r, SectionCut sectionCut) {
        CurveUtil.renderLine(r, ColorRGBA.blue, sectionCut.getSectionStart3d(), sectionCut.getSectionEnd3d());
    }

    public void onCreateSection(SectionCut section) {
        //System.out.println("***** Drawing a section");

        List<TwoForceMember> cutMembers = getAllCutMembers(section);

        if (cutMembers.size() > 0) {
            currentCut = section;
        } else {
            currentCut = null;
        }
    }

    private List<TwoForceMember> getAllCutMembers(SectionCut section) {

        List<TwoForceMember> cutMembers = new ArrayList<TwoForceMember>();

        for (Body body : allBodies()) {
            if (body instanceof TwoForceMember) {
                if (intersects2FM(section, (TwoForceMember) body)) {
                    cutMembers.add((TwoForceMember) body);
                }
            }
        }
        return cutMembers;
    }

    /**
     * Checks to see if the section cut intersects the given 2 force member.
     * @param section
     * @return
     */
    private boolean intersects2FM(SectionCut section, TwoForceMember member) {
        // first project the ends of the 2fm onto the 2d camera plane.
        Vector3f end1_3d = StaticsApplication.getApp().getCamera().getScreenCoordinates(member.getEndpoint1().toVector3f());
        Vector3f end2_3d = StaticsApplication.getApp().getCamera().getScreenCoordinates(member.getEndpoint2().toVector3f());
        Vector2f end1 = new Vector2f(end1_3d.x, end1_3d.y);
        Vector2f end2 = new Vector2f(end2_3d.x, end2_3d.y);

        return lineSegmentIntersection(end1, end2, section.getSectionStart(), section.getSectionEnd()) &&
                lineSegmentIntersection(section.getSectionStart(), section.getSectionEnd(), end1, end2);
    }

    /**
     * Calculates whether the line and the segment intersect. The line is assumed to be infinite,
     * and the segment is finite.
     * @return
     */
    private boolean lineSegmentIntersection(Vector2f linePoint1, Vector2f linePoint2, Vector2f segmentPoint1, Vector2f segmentPoint2) {

        // The line defining the cut is infinite,
        // so we check to see if the ends of the member are on opposite sides of the line.
        // this fomula calculates the distance between the endpoints of the member and the line
        // if they have opposite signs, then they overlap.
        Vector2f lineDirection = linePoint2.subtract(linePoint1).normalize();
        Vector2f linePerp = new Vector2f(lineDirection.y, -lineDirection.x);

        float distance1 = linePerp.dot(segmentPoint1.subtract(linePoint1));
        float distance2 = linePerp.dot(segmentPoint2.subtract(linePoint1));

        // if the values are on opposite sides, they will have opposite signs,
        // and the product will be negative. Otherwise, the product will be positive.
        return distance1 * distance2 < 0;
    }
}
