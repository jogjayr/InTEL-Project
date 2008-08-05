/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

/**
 * This is a protected class used by the persistence package to store a named object.
 * This should ONLY be implemented by classes that will be referenced by state but should
 * not be serialized. 
 * @author Calvin Ashmore
 */
class NameContainer {

    private String name;
    private Class targetClass;

    public NameContainer(String name, Class targetClass) {
        this.name = name;
        this.targetClass = targetClass;
    }

    public NameContainer(ResolvableByName resolvable) {
        this.name = resolvable.getName();
        this.targetClass = resolvable.getClass();
    }

    public String getName() {
        return name;
    }

    public Class getTargetClass() {
        return targetClass;
    }
}
