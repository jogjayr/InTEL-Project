/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.*;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class FBDExercise extends Exercise {

    public FBDExercise(Schematic schematic) {
        super(schematic);
    }

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        return new DefaultInterfaceConfiguration();
    }

    @Override
    public Mode loadStartingMode() {
        SelectMode.instance.load();
        return SelectMode.instance;
    }
}
