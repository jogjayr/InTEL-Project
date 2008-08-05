/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import java.beans.XMLDecoder;
import java.io.InputStream;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsXMLDecoder extends XMLDecoder {

    public StaticsXMLDecoder(InputStream in) {
        super(in);
    }

    /**
     * modify readObject to resolved named objects.
     * @return
     */
    @Override
    public Object readObject() {
        return super.readObject();
    }
}
