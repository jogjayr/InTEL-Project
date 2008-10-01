/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.math.Vector3bd;
import java.beans.ExceptionListener;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

/**
 *
 * @author lccstudent
 */
public class test3 {

    public static void main(String args[]) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StaticsXMLEncoder encoder = new StaticsXMLEncoder(out);
        encoder.setExceptionListener(new ExceptionListener() {

            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });

        encoder.writeObject(Vector3bd.UNIT_Y);
        //encoder.writeObject("monkeys");

        encoder.close();

        String result = new String(out.toByteArray());
        System.out.println(result);
    }
}
