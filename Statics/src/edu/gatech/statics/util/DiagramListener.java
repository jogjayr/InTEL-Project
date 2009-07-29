/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.util;

import edu.gatech.statics.exercise.Diagram;

/**
 *
 * @author Calvin Ashmore
 */
public interface DiagramListener {

    /**
     * Called when the given diagram is created
     * @param diagram
     */
    public void onDiagramCreated(Diagram diagram);

    /**
     * Called when the application switches to the given diagram.
     * @param newDiagram
     */
    public void onDiagramChanged(Diagram newDiagram);
}
