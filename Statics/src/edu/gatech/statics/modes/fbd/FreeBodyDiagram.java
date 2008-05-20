/*
 * FBDWorld.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import com.jme.input.InputHandler;
import com.jme.renderer.Renderer;
import edu.gatech.statics.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.modes.fbd.tools.LabelManipulator;
import edu.gatech.statics.modes.fbd.tools.LabelSelector;
import edu.gatech.statics.modes.fbd.tools.LoadLabelListener;
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
    private FBDInput fbdInput;

    public List<Load> getAddedForces() {
        return Collections.unmodifiableList(addedForces);
    }

    public boolean isSolved() {
        return solved;
    }

    public void reset() {
        for (Load load : addedForces) {
            remove(load);
        }
        addedForces.clear();

        currentHighlight = null;
        currentSelection = null;
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

        fbdInput = new FBDInput(this);
    }

    @Override
    public void add(SimulationObject obj) {
        super.add(obj);
        if (obj instanceof Load) {
            addedForces.add((Load) obj);
            new LabelManipulator((Load) obj);
        }
    }

    @Override
    public InputHandler getInputHandler() {
        return fbdInput;
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
    private Load currentSelection;

    @Override
    public void onHover(SimulationObject obj) {

        if (currentHighlight == obj) {
            return;
        }

        // we are changing our hover, so clear the current
        if (currentHighlight != null) {
            currentHighlight.setDisplayHighlight(false);
        }

        if (obj == null) {
            currentHighlight = null;
            return;
        }

        if (obj != currentSelection) {
            currentHighlight = (Load) obj;
            currentHighlight.setDisplayHighlight(true);
        }
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null) {
            if (currentSelection == null) {
                return;
            }
            currentSelection.setDisplaySelected(false);
            currentSelection = null;
            return;
        }

        if (currentSelection == obj) {
            currentSelection.setDisplaySelected(false);
            currentSelection = null;
        } else {
            if (currentSelection != null) {
                currentSelection.setDisplaySelected(false);
            }
            currentSelection = (Load) obj;
            currentSelection.setDisplaySelected(true);

            fbdInput.onSelect(currentSelection);
        }
    }

    public void onLabel(Load load) {
        if (solved) {
            return;
        }

        LabelSelector labelTool = new LabelSelector(new LoadLabelListener(load), load.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your load");
        labelTool.setUnits(load.getUnit().getSuffix());
        //labelTool.setHintText("");
        if(load.isSymbol())
            labelTool.setHintText(load.getSymbolName());
        else
            labelTool.setHintText(load.toStringDecimal());
        labelTool.setIsCreating(false);
        labelTool.createPopup();
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

    Load getSelection() {
        return currentSelection;
    }

    @Override
    public Mode getMode() {
        return FBDMode.instance;
    }
}