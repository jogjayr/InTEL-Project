/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.Mode;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 * OrdinaryExercise is the default type of exercise. It uses the DefaultInterfaceConfiguration,
 * and starts with the select mode. Users are able to select subsets of bodies, build FBDs for those
 * bodies, and then allows users to solve for equilibrium. Generally, this most easily allows
 * solutions of problems that might be designated as "frame" problems, but does not include
 * the other interface elements to support them.
 * @author Calvin Ashmore
 */
abstract public class OrdinaryExercise extends Exercise {

    public OrdinaryExercise() {
    }

    public OrdinaryExercise(Schematic schematic) {
        super(schematic);
    }

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new DefaultInterfaceConfiguration();
    }

    @Override
    public Mode loadStartingMode() {
        SelectMode.instance.load();
        return SelectMode.instance;
    }
}
