/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.SimulationObject;
import java.beans.Expression;
import java.beans.XMLDecoder;
import java.io.InputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLDecoder extends ModifiedXMLDecoder {

    public StaticsXMLDecoder(InputStream in) {
        super(in);
    }

    /**
     * modify readObject to resolved named objects.
     * @return
     */
    @Override
    public Object readObject() {
        //Setting up a special case ModifiedObjectHandler
        ModifiedObjectHandler mOH = new ModifiedObjectHandler() {
            @Override
            public Object getValue(Expression exp) {
                Object result = super.getValue(exp);
                if (result instanceof ResolvableByName) {
                    String name = ((ResolvableByName) result).getName();
                    if (result instanceof SimulationObject) {
                        return Exercise.getExercise().getSchematic().getByName(name);
                    }
                }
                return result;
            }
        };
        
        //Sets the XMLDecoder's Handler equal to our ModifiedObjectHandler
        setHandler(mOH);
        
        Object obj = super.readObject();
        
        //We run the decoder as usual except now it is using the ModifiedObjectHandler
        if (obj instanceof ResolvableByName) {
            String name = ((ResolvableByName) obj).getName();
            if (obj instanceof SimulationObject) {
                return Exercise.getExercise().getSchematic().getByName(name);
            }
        }
        return obj;
//        if (obj instanceof NameContainer) {
//            NameContainer nameContainer = (NameContainer) obj;
//            if (SimulationObject.class.isAssignableFrom(nameContainer.getTargetClass())) {
//                // object is a SimulationObject
//                // fetch it from the Exercise.
//                return Exercise.getExercise().getSchematic().getByName(nameContainer.getName());
//            }
//
//            // we cannot resolve the NameContainer!
//            throw new IllegalStateException("Cannot resolve the name container: " + nameContainer.getName() + ", " + nameContainer.getTargetClass());
//        }
    }
}
