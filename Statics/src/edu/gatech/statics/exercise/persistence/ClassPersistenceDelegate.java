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
 *
 * @author Calvin Ashmore
 */
public class ClassPersistenceDelegate extends PersistenceDelegate {

    public static Class typeToClass(Class type) {
        return type.isPrimitive() ? ObjectHandler.typeNameToClass(type.getName()) : type;
    }

    @Override
    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        return oldInstance.equals(newInstance);
    }

    @Override
    public void writeObject(Object oldInstance, Encoder out) {
//super.writeObject(oldInstance, out);
//        System.out.println("********** Writing: "+oldInstance);
        Object newInstance;
        try {
            newInstance = out.get(oldInstance);
        } catch (Exception ex) {
//            System.out.println("************* CLASS NOT FOUND " + oldInstance + " " + ex);
            newInstance = oldInstance;
        }
//Object newInstance = oldInstance;
        if (!mutatesTo(oldInstance, newInstance)) {
            out.remove(oldInstance);
            out.writeExpression(instantiate(oldInstance, out));
        } else {
            initialize(oldInstance.getClass(), oldInstance, newInstance, out);
        }
    }

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
//            System.out.println("************* classForName: "+getArguments()[0]);
            //Object value = ModifiedObjectHandler.classForName((String) getArguments()[0]);
            Object value = super.getValue();
//            System.out.println("************* returns OK: "+value);
            setValue(value);
            return value;
        }
    }
}
