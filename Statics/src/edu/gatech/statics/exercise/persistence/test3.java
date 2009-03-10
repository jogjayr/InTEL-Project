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
public class test3 {

    public static class B {
    }

    public static class A {

        private int bleh;
        private Helper helper;

        public A() {
            helper = new Helper();
        }
        
        public int getBleh() {
            return bleh;
        }

        public void setBleh(int bleh) {
            this.bleh = bleh;
        }

        public Helper getHelper() {
            return helper;
        }

        public void setHelper(Helper helper) {
            this.helper = helper;
        }
        
        
    }
    //public static final Helper helper = new Helper();

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
                
                out.writeStatement(new Statement(a.getHelper(), "println", new Object[]{"oh noes!!"}));
            }
        });


        A a = new A();
        a.setBleh(1235);

        encoder.writeObject(a);

        encoder.close();
        System.out.println(new String(bOut.toByteArray()));
    }
}
