/*
 * SelectableFilter.java
 *
 * Created on July 18, 2007, 9:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.util;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.*;

/**
 *
 * @author Calvin Ashmore
 */
public interface SelectionFilter {
    public boolean canSelect(SimulationObject obj);
}
