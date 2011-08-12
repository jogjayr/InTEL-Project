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
