/*
 * SelectionListener.java
 *
 * Created on July 18, 2007, 1:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.util;

import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public interface SelectionListener {
    public void onClick(SimulationObject obj);
    //public void onSelect(SimulationObject obj);
    public void onHover(SimulationObject obj);
}
