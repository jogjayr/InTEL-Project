/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.SimulationObject;
import java.beans.XMLDecoder;
import java.io.InputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLDecoder extends XMLDecoder {

    public StaticsXMLDecoder(InputStream in) {
        super(in);
    }

    /**
     * modify readObject to resolved named objects.
     * @return
     */
    @Override
    public Object readObject() {
        Object obj = super.readObject();
        if (obj instanceof NameContainer) {
            NameContainer nameContainer = (NameContainer) obj;
            if (SimulationObject.class.isAssignableFrom(nameContainer.getTargetClass())) {
                // object is a SimulationObject
                // fetch it from the Exercise.
                return Exercise.getExercise().getSchematic().getByName(nameContainer.getName());
            }

            // we cannot resolve the NameContainer!
            throw new IllegalStateException("Cannot resolve the name container: " + nameContainer.getName() + ", " + nameContainer.getTargetClass());
        }
        return obj;
    }
}
