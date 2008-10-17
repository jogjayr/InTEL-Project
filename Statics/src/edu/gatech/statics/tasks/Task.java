/*
 * Task.java
 * 
 * Created on Nov 12, 2007, 3:45:24 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.persistence.ResolvableByName;

/**
 * @author Calvin Ashmore
 */
abstract public class Task implements ResolvableByName {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public boolean isSatisfied();

    abstract public String getDescription();
}
