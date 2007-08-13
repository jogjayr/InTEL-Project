/*
 * Measurement.java
 *
 * Created on June 9, 2007, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import edu.gatech.statics.*;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Measurement extends SimulationObject {
    
    private boolean known = true;
    public void setKnown(boolean known) {this.known = known;}
    public boolean isKnown() {return known;}
    
    /** Creates a new instance of Measurement */
    public Measurement() {
    }
}
