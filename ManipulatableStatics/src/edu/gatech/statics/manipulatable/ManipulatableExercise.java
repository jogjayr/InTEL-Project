/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.manipulatable;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.InterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableExercise extends Exercise {
    
    @Override
    public Mode loadStartingMode() {
        ManipulatableMode.instance.load();
        return ManipulatableMode.instance;
    }

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        return new ManipulatableIC();
    }


    /**
     * THIS IS A DEMO HACK!
     * Replace this with something more generalizable later.
     * @param selectedValue
     * @deprecated
     */
    @Deprecated
    public void setJoint(String selectedValue) {
    }
}
