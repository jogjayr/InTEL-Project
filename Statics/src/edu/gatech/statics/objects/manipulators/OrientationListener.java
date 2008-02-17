/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.manipulators;

import com.jme.math.Matrix3f;

/**
 *
 * @author Calvin Ashmore
 */
public interface OrientationListener {

    public void onRotate(Matrix3f rotation);
}
