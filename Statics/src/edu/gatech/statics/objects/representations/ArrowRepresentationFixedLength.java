/*
 * ArrowRepresentationFixedLength.java
 *
 * Created on June 30, 2007, 12:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import edu.gatech.statics.objects.VectorObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ArrowRepresentationFixedLength extends ArrowRepresentation {
    
    private float fixedLength = 4.0f;
    public void setFixedLength(float length) {this.fixedLength = length;}
    
    /** Creates a new instance of ArrowRepresentationFixedLength */
    public ArrowRepresentationFixedLength(VectorObject target) {
        super(target);
        
        update();
    }
    
    @Override
    protected void setMagnitude(float magnitude) {
        super.setMagnitude(fixedLength);
    }
}
