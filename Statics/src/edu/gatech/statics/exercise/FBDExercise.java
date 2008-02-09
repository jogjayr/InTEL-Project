/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.*;
import edu.gatech.statics.modes.select.SelectMode;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class FBDExercise extends Exercise {

    public FBDExercise(Schematic schematic) {
        super(schematic);
    }

    
    @Override
    public Mode getStartingMode() {
        return new SelectMode();
    }
    
}
