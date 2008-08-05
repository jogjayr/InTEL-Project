/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Mode {

    public void preLoad(DiagramKey key) {
    }

    public void postLoad(DiagramKey key) {
    }

    /**
     * Shortcut for getDiagramType().getName();
     * @return
     */
    public final String getModeName() {
        return getDiagramType().getName();
    }

    abstract protected Diagram getDiagram(DiagramKey key);
    private DiagramType diagramType;

    /**
     * Returns the diagram type that is used by this mode.
     * @return
     */
    public final DiagramType getDiagramType() {
        return diagramType;
    }

    protected abstract DiagramType createDiagramType();

    public Mode() {
        diagramType = createDiagramType();
    }

    /**
     * This is the method to call when loading a new exercise, and will load the
     * default mode and diagram, which is usually the Select diagram.
     */
    public final void load() {
        load(null);
    }

    /**
     * This is the method you should call whenever the mode needs to be changed.
     * Pass in a diagram key and this will load up this mode with the appropriate diagram.
     * @param key
     */
    public final void load(DiagramKey key) {
        // load the diagram

        StaticsApplication.getApp().setCurrentDiagram(getDiagram(key));
        preLoad(key);
        // loading the ModePanel occurs after the diagram.
        // the ModePanel may depend on the diagram for labeling, etc
        InterfaceRoot.getInstance().activateModePanel(getModeName());
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
