/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Mode {
    
    public void postLoad(DiagramKey key) {}
    
    abstract public String getModePanelName();
    abstract protected Diagram getDiagram(DiagramKey key);
    
    public final void load() {
        load(null);
    }
    
    public final void load(DiagramKey key) {
        // load the diagram
        StaticsApplication.getApp().setCurrentDiagram(getDiagram(key));
        
        // loading the ModePanel occurs after the diagram.
        // the ModePanel may depend on the diagram for labeling, etc
        InterfaceRoot.getInstance().activateModePanel(getModePanelName());
        postLoad(key);
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
