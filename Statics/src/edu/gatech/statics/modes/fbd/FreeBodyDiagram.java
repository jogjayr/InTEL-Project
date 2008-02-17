/*
 * FBDWorld.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import com.jme.renderer.Renderer;
import edu.gatech.statics.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FreeBodyDiagram extends SubDiagram {

    private boolean solved = false;
    private List<Load> addedForces = new ArrayList<Load>();

    public List<Load> getAddedForces() {
        return Collections.unmodifiableList(addedForces);
    }

    public boolean isSolved() {
        return solved;
    }

    public void reset() {
        addedForces.clear();
    }

    public void setSolved() {
        solved = true;

        String advice = java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_feedback_check_success");
        StaticsApplication.getApp().setAdvice(advice);
        StaticsApplication.getApp().setDefaultAdvice(advice);

        StaticsApplication.getApp().resetAdvice();
    }

    /** Creates a new instance of FBDWorld */
    public FreeBodyDiagram(BodySubset bodies) {
        super(bodies);

        for (Body body : bodies.getBodies()) {
            add(body);
            for (SimulationObject obj : body.getAttachedObjects()) {
                if (!(obj instanceof Load)) {
                    add(obj);
                }
            }
        }

        for (Measurement measurement : getSchematic().getMeasurements(bodies)) {
            add(measurement);
        }
    }
    private static final SelectionFilter filter = new SelectionFilter() {
        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }
    private Load currentHighlight;

    @Override
    public void onHover(SimulationObject obj) {

        if (currentHighlight == obj) {
            return;
        }

        if (currentHighlight != null) // we are changing our hover, so clear the current
        {
            currentHighlight.setDisplayHighlight(false);
        }

        if (obj == null) {
            currentHighlight = null;
            return;
        }

        currentHighlight = (Load) obj;
        currentHighlight.setDisplayHighlight(true);
    }

    @Override
    public void render(Renderer r) {
        super.render(r);
    //renderIcon();
    }

    @Override
    public void activate() {
        super.activate();

        StaticsApplication.getApp().setDefaultAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_feedback_welcome"));
        StaticsApplication.getApp().resetAdvice();

        if (solved) {
            setSolved();
        }
    }
}
