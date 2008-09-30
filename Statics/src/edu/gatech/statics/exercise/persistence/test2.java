/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lccstudent
 */
public class test2 {

    public static class A {

        String name;

        public A() {
        }

        public A(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class B {

        String name;

        public B() {
        }

        public B(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ACapsule extends A {

        public ACapsule(String name) {
            super(name);
        }

        public ACapsule() {
        }

        public ACapsule(A a) {
            this(a.name);
        }
    }

    public static class AB extends A {

        int a, b, c;

        public AB() {
        }

        public AB(String name) {
            super(name);
            Random rand = new Random();
            a = rand.nextInt();
            b = rand.nextInt();
            c = rand.nextInt();
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }
    }

    private static class APersistenceDelegate extends PersistenceDelegate {

        @Override
        protected Expression instantiate(Object oldInstance, Encoder out) {
            A a = (A) oldInstance;

            Expression expression = new Expression(A.class, "new", new Object[]{a.getName()});
            return expression;
        }
    }

    public static void main(String args[]) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(out);

        encoder.setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });

        encoder.setPersistenceDelegate(A.class, new APersistenceDelegate());
        encoder.setPersistenceDelegate(AB.class, new APersistenceDelegate());

        //encoder.writeObject(new A("the sky is falling!"));

        List things = new ArrayList();
        things.add(new AB("helloo?"));
        //things.add(new A("test"));
        encoder.writeObject(things);

        encoder.close();
        System.out.println(new String(out.toByteArray()));
    }
}
