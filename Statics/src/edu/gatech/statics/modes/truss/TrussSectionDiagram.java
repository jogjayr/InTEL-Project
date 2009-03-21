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
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.representations.CurveUtil;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionDiagram extends Diagram<TrussSectionState> {

    private SectionTool sectionTool;

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
            Vector2f mouseCurrent = sectionTool.getMouseCurrent();
            Vector2f mouseStart = sectionTool.getMouseStart();

            Vector3f worldCurrent = StaticsApplication.getApp().getCamera().getWorldCoordinates(mouseCurrent, 0.1f);
            Vector3f worldStart = StaticsApplication.getApp().getCamera().getWorldCoordinates(mouseStart, 0.1f);

            CurveUtil.renderLine(r, ColorRGBA.blue, worldCurrent, worldStart);
        }
    }

    void onDrawSection() {
        System.out.println("***** Drawing a section");
    }
}
