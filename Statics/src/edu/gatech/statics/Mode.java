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
        InterfaceRoot.getInstance().setModePanel(getModePanelName());
        StaticsApplication.getApp().setCurrentDiagram(getDiagram(bodies));
        postLoad();
    }
    
    protected static final Exercise getExercise() {
        return StaticsApplication.getApp().getExercise();
    }
}
