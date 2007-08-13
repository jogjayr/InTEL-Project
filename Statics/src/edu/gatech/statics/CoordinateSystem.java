/*
 * CoordinateSystem.java
 *
 * Created on June 11, 2007, 12:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import com.jme.math.Vector3f;

/**
 *
 * @author Calvin Ashmore
 */
public class CoordinateSystem {
    
    private Vector3f x;
    private Vector3f y;
    private Vector3f z;
    
    public Vector3f getX() {return x;}
    public Vector3f getY() {return y;}
    public Vector3f getZ() {return z;}
    
    /** Creates a new instance of CoordinateSystem */
    public CoordinateSystem() {
        x = new Vector3f(1,0,0);
        y = new Vector3f(0,1,0);
        z = new Vector3f(0,0,1);
    }
    
    public CoordinateSystem(Vector3f x, Vector3f y, Vector3f z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
