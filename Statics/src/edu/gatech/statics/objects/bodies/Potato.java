/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;

/**
 * A Potato is a body which has some ambiguous shape, (and is often represented
 * by some lumpy blob in diagrams). This is more general, and represents something
 * that has no precise shape or placement. 
 * @author Calvin Ashmore
 */
public class Potato extends Body {

    public Potato(String name) {
        super(name);
    }
    
    @Override
    public void createDefaultSchematicRepresentation() {
    }
}
