/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lccstudent
 */
public class test {

    public static class A {

        int a, b, c;
        String name;

        public A() {
        }

        public A(String name) {
            Random rand = new Random();
            a = rand.nextInt();
            b = rand.nextInt();
            c = rand.nextInt();
            this.name = name;
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

    public static void main(String args[]) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(out) {

            @Override
            public void writeObject(Object o) {
                if (o == null) {
                    super.writeObject(o);
                } else if (o instanceof A) {
                    super.writeObject(new B(((A) o).getName()));
                } else {
                    super.writeObject(o);
                }
            }
        };

        encoder.setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });

        //encoder.writeObject(new A("the sky is falling!"));
        
        List things = new ArrayList();
        things.add(new A("helloo?"));
        //things.add(new A("test"));
        encoder.writeObject(things);

        encoder.close();
        System.out.println(new String(out.toByteArray()));
    }
}
