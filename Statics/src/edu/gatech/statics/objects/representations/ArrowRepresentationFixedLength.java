/*
 * ArrowRepresentationFixedLength.java
 *
 * Created on June 30, 2007, 12:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import edu.gatech.statics.objects.Vector;

/**
 *
 * @author Calvin Ashmore
 */
public class ArrowRepresentationFixedLength extends ArrowRepresentation {
    
    private float length = 2.5f;
    public void setLength(float length) {this.length = length;}
    
    /** Creates a new instance of ArrowRepresentationFixedLength */
    public ArrowRepresentationFixedLength(Vector target) {
        super(target);
        
        update();
    }
    
    protected void setMagnitude(float magnitude) {
        super.setMagnitude(length);
    }
}
