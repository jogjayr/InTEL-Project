/*
 * PointBody.java
 * 
 * Created on Sep 30, 2007, 11:38:39 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class PointBody extends Body {

    private Point myPoint;
    public PointBody(Point myPoint) {
        this.myPoint = myPoint;
        addObject(myPoint);
    }

    public void createDefaultSchematicRepresentation() {
        
    }

}
