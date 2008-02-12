/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
public interface DragSnapListener {
    /**
     * This is called when a snap is made to a point, or when the manipulator comes loose from the snap.
     * @param point
     */
    public void onSnap(Point point);
}
