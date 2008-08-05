/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

/**
 * This is a marker interface to represent whether the marked class should be
 * saved as part of state persistence. 
 * This is intended to be applied to SimulationObjects.
 * @author Calvin Ashmore
 */
public interface ResolvableByName {

    String getName();
}
