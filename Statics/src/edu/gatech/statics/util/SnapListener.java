/*
 * SnapListener.java
 *
 * Created on July 15, 2007, 2:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.util;

import edu.gatech.statics.objects.manipulators.*;
import edu.gatech.statics.objects.manipulators.Manipulator;

/**
 *
 * @author Calvin Ashmore
 */
public interface SnapListener {
    
    public void onSnap(Manipulator m);
    
}
