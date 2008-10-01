/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.util;

/**
 *
 * @author lccstudent
 */
public interface Buildable<T> {
    public Builder<? extends T> getBuilder();
}
