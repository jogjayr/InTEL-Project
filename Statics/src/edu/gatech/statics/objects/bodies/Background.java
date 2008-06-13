/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;

/**
 * The background is a special type of body which represents the "world" in an exercise.
 * The background should show up in Select mode, but not in most others.
 * @author Calvin Ashmore
 */
public class Background extends Body{

    /**
     * This method should not be called, the background should not have a default representation.
     * @deprecated
     */
    @Override
    @Deprecated
    public void createDefaultSchematicRepresentation() {
        throw new UnsupportedOperationException("The background should not have a default representation.");
    }

}
