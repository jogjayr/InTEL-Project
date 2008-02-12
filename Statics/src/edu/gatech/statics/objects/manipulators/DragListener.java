/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

/**
 *
 * @author Calvin Ashmore
 */
public interface DragListener {
    public void onDrag(Vector2f mousePosition, Vector3f worldPosition);
}
