/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.tasks.Task;
import java.beans.Expression;
import java.io.InputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLDecoder extends ModifiedXMLDecoder {

    //ModifiedObjectHandler mOH = null;
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
        return obj;
    }

    /**
     * Finds value of the object expression. Uses the name of the
     * object to create a SimulationObject or Exercise
     * @return
     */
    @Override
    ModifiedObjectHandler createObjectHandler() {
        return new ModifiedObjectHandler(this, getClassLoader()) {

            @Override
            public Object getValue(Expression exp) {
                // *******
                //System.err.println("getValue: (" + exp + ") target:" + exp.getTarget());
                Object result = super.getValue(exp);
                //System.err.println("*** getValue result: " + result + (result == null ? "" : "(" + result.getClass() + ")"));

                if (result instanceof ResolvableByName) {
                    String name = ((ResolvableByName) result).getName();
                    if (result instanceof SimulationObject) {
                        SimulationObject obj = Exercise.getExercise().getSchematic().getByName(name);
                        if(obj == null) {
                            StaticsApplication.logger.warning("Could not find an object by name: \""+name+"\"");
                        }
                        return obj;
                    } else if(result instanceof Task) {
                        return Exercise.getExercise().getTask(name);
                    } else {
                        StaticsApplication.logger.warning("Could not find an object by name: \""+name+"\"");
                    }
                }
                return result;
            }
        };
    }
}
