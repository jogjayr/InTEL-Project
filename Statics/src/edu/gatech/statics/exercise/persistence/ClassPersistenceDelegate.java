/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import com.sun.beans.ObjectHandler;
import edu.gatech.newbeans.Encoder;
import edu.gatech.newbeans.Expression;
import edu.gatech.newbeans.PersistenceDelegate;
import java.lang.reflect.Field;

/**
 * This class is responsible for handling serialization of objects
 * @author Calvin Ashmore
 */
public class ClassPersistenceDelegate extends PersistenceDelegate {

    /**
     * Returns a class object of class type
     * @param type
     * @return
     */
    public static Class typeToClass(Class type) {
        return type.isPrimitive() ? ObjectHandler.typeNameToClass(type.getName()) : type;
    }

    /**
     * 
     * @param oldInstance
     * @param newInstance
     * @return
     */
    @Override
    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        return oldInstance.equals(newInstance);
    }
    private static final boolean LOGGING = false;
    private static long lastLog = 0;

    /**
     * 
     * @param message
     */
    private static void logTime(String message) {
        if (LOGGING) {
            long timestamp = System.nanoTime();
            long delta = timestamp - lastLog;
            lastLog = timestamp;
            System.out.println("********** " + timestamp + " (" + delta + ") " + message);
        }
    }
    /**
     * 
     * @param oldInstance
     * @param out
     */
    @Override
    public void writeObject(Object oldInstance, Encoder out) {
//super.writeObject(oldInstance, out);
        logTime("Writing: " + oldInstance);
        //System.out.println("********** "+System.nanoTime()+" Writing: "+oldInstance);
        Object newInstance;
        try {
            newInstance = out.get(oldInstance);
        } catch (Exception ex) {
            System.out.println("************* CLASS NOT FOUND " + oldInstance + " " + ex);
            newInstance = oldInstance;
        }
        logTime("postget: " + newInstance);
        //System.out.println("********** "+System.nanoTime()+" postget: "+newInstance);

        if (!mutatesTo(oldInstance, newInstance)) {
            out.remove(oldInstance);
            out.writeExpression(instantiate(oldInstance, out));
            logTime("postwrite");
        } else {
            initialize(oldInstance.getClass(), oldInstance, newInstance, out);
            logTime("postinit");
        }
    }

    /**
     * Creates a new Expression with value oldInstance, target the declared field
     * of the class object of oldInstance, and methodName "get" (if oldInstance is a primitive
     * type), "getClass" (if oldInstance is a String or Class) and "forName" if anything else
     * @param oldInstance
     * @param out
     * @return
     */
    protected Expression instantiate(Object oldInstance, Encoder out) {
        Class c = (Class) oldInstance;
// As of 1.3 it is not possible to call Class.forName("int"),
// so we have to generate different code for primitive types.
// This is needed for arrays whose subtype may be primitive.
        if (c.isPrimitive()) {
            Field field = null;
            try {
                field = typeToClass(c).getDeclaredField("TYPE");
            } catch (NoSuchFieldException ex) {
                System.err.println("Unknown primitive type: " + c);
            }
            return new Expression(oldInstance, field, "get", new Object[]{null});
        } else if (oldInstance == String.class) {
            return new Expression(oldInstance, "", "getClass", new Object[]{});
        } else if (oldInstance == Class.class) {
            return new Expression(oldInstance, String.class, "getClass", new Object[]{});
        } else {

            //return new Expression(c, Class.class, "forName", new Object[]{c.getName()});
            return new ClassExpression(c, Class.class, "forName", new Object[]{c.getName()});
        }
    }

    class ClassExpression extends Expression {

        public ClassExpression(Class value, Object target, String methodName, Object[] arguments) {
            super(value, target, methodName, arguments);
        }

        @Override
        public Object getValue() throws Exception {
            logTime("classForName: " + getArguments()[0]);
//            System.out.println("************* "+System.nanoTime()+" classForName: "+getArguments()[0]);
            //Object value = ModifiedObjectHandler.classForName((String) getArguments()[0]);
            Object value = super.getValue();
            logTime("returns OK: " + value);
//            System.out.println("************* "+System.nanoTime()+" returns OK: "+value);
            setValue(value);
            return value;
        }
    }
}
