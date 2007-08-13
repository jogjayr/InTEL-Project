/*
 * Units.java
 *
 * Created on July 27, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

/**
 *
 * @author Calvin Ashmore
 */
public class Units {
    
    protected String distance = "m";
    protected String force = "N";
    protected String moment = "N*m";
    
    protected float worldDistanceMultiplier = 1f;
    
    public String getDistance() {return distance;}
    public String getForce() {return force;}
    public String getMoment() {return moment;}
    
    public float getWorldDistanceMultiplier() {return worldDistanceMultiplier;}
}
