/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Statement;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class test2 {

    public static class B {
    }

    public static class A {

        private int bleh;

        public int getBleh() {
            return bleh;
        }

        public void setBleh(int bleh) {
            this.bleh = bleh;
        }
    }
    public static final Helper helper = new Helper();

    public static final class Helper {

        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String args[]) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(bOut);

        encoder.setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });

        encoder.setPersistenceDelegate(A.class, new DefaultPersistenceDelegate() {

            @Override
            protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
                super.initialize(type, oldInstance, newInstance, out);
                
                A a = (A) oldInstance;

                //((XMLEncoder)out).writeObject(helper);
                out.writeStatement(new Statement(helper, "println", new Object[]{"oh noes!! "+a.getBleh()}));
            }
        });

        //encoder.writeObject(helper);

        A a = new A();
        a.setBleh(1235);
        A a2 = new A();
        a2.setBleh(784865);

        encoder.writeObject(helper);

        encoder.writeObject(a);
        encoder.writeObject(a2);

        encoder.close();
        System.out.println(new String(bOut.toByteArray()));
    }
}
