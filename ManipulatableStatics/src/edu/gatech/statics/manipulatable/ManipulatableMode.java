/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.manipulatable;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableMode extends Mode {

    public static final ManipulatableMode instance = new ManipulatableMode();
    
    @Override
    protected String getModePanelName() {
        return "Manipulate";
    }

    @Override
    protected Diagram getDiagram(BodySubset bodies) {
        return new ManipulatableDiagram();
    }

}
