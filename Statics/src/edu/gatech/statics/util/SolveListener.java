/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.util;

import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Load;

/**
 *
 * @author Calvin Ashmore
 */
public interface SolveListener {
    public void onLoadSolved(Load load);
    public void onJointSolved(Joint joint);
}
