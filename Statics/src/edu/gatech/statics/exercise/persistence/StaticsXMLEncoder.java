/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.XMLEncoder;
import java.io.OutputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLEncoder extends XMLEncoder {

    public StaticsXMLEncoder(OutputStream out) {
        super(out);
    }

    /**
     * Modify writeObject to handle named objects. 
     * Named objects are ones that are stored by name, but then resolved.
     * @param o
     */
    @Override
    public void writeObject(Object o) {
        super.writeObject(o);
    }
    
}
