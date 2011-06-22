/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.description;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionDiagram extends Diagram<DescriptionState> {

    private DescriptionUI ui;
    private Description description;

    /**
     * Constructor
     */
    public DescriptionDiagram() {
        super(null);

        ui = new DescriptionUI();
    }

    /**
     * 
     * @return "Description"
     */
    @Override
    public String getName() {
        return "Description";
    }

    /**
     * 
     * @return ""
     */
    @Override
    public String getDescriptionText() {
        return "";
        //return Exercise.getExercise().getName();
    }

    /**
     * 
     * @return A new description state
     */
    @Override
    protected DescriptionState createInitialState() {
        return new DescriptionState();
    }

    /**
     * load the starting mode for the exercise once the user has finished reading
     * the description
     */
    @Override
    public void completed() {
        // load the starting mode for the exercise once the user has finished reading
        // the description
        Exercise.getExercise().loadStartingMode();
    }

    /**
     * 
     * @return DescriptionMode that the diagram belongs to
     */
    @Override
    public Mode getMode() {
        return DescriptionMode.instance;
    }

    /**
     * 
     * @return 
     */
    @Override
    protected List<SimulationObject> getBaseObjects() {
        //return getSchematic().allObjects();
        return Collections.emptyList();
    }

    /**
     * Runs when diagram is activated. Loads description (once)
     */
    @Override
    public void activate() {
        super.activate();

        // display UI
        InterfaceRoot.getInstance().getBuiNode().addWindow(ui);

        // layout the description
        if (description == null) {
            // ONLY LOAD THE DESCRIPTION ONCE!
            description = Exercise.getExercise().getDescription();
        }
        ui.layout(description);

        // put it in place
        ui.updatePlacement();
    }

    /**
     * Deactivates description diagram
     */
    @Override
    public void deactivate() {
        super.deactivate();

        // hide UI
        InterfaceRoot.getInstance().getBuiNode().removeWindow(ui);
    }
}
