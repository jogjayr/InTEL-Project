/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Mode {
    
    public void postLoad() {}
    
    abstract protected String getModePanelName();
    abstract protected Diagram getDiagram(BodySubset bodies);
    
    public final void load() {
        load(null);
    }
    
    public final void load(BodySubset bodies) {
        // load the diagram
        StaticsApplication.getApp().setCurrentDiagram(getDiagram(bodies));
        
        // loading the ModePanel occurs after the diagram.
        // the ModePanel may depend on the diagram for labeling, etc
        InterfaceRoot.getInstance().setModePanel(getModePanelName());
        postLoad();
    }
    
    /**
     * This is a shortcut method for subclasses of Mode. It returns
     * Exercise.getExercise()
     * @return
     */
    protected static final Exercise getExercise() {
        return Exercise.getExercise();
    }
}
