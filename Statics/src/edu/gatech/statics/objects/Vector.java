/*
 * Vector.java
 *
 * Created on June 7, 2007, 4:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.*;
import edu.gatech.statics.objects.representations.ArrowRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Vector extends SimulationObject {
    
    //private Vector3f anchor;
    private Point anchor;
    private Vector3f value;
    private float magnitude;
    
    private boolean symbol = false;
    public boolean isSymbol() {return symbol;}
    public void setSymbol(boolean symbol) {this.symbol = symbol;}
    
    public Point getAnchor() {return anchor;} 
    public void setAnchor(Point anchor) {this.anchor = anchor;}
    
    public Vector3f getTranslation() {return anchor.getTranslation();}
    public void setTranslation(Vector3f v) {
        anchor.setTranslation(v);
    }
    
    public float getMagnitude() {return magnitude;}
    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
        setValue( value.normalize().mult(magnitude) );
    }
    
    public Vector negate() {
        return new Vector(anchor, value.negate());
    }
    
    public Matrix3f getRotation() {
        Matrix3f mat = new Matrix3f();
        if(value.length() == 0)
            return mat;
        
        mat.fromStartEndVectors(Vector3f.UNIT_Z, value.normalize());
        return mat;
    }
    
    public void setRotation(Matrix3f mat) {
        Vector3f v = mat.mult(Vector3f.UNIT_Z);
        v.multLocal(magnitude);
        //if(!v.equals(value))
        value = v;
    }
    
    //public void setAnchor(Vector3f anchor) {this.anchor = anchor;}
    //public Vector3f getAnchor() {return anchor;}
    
    public void setValue(Vector3f value) {
        this.value = value;
        magnitude = value.length();
    }
    public Vector3f getValue() {return value;}

    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentation(this);
        addRepresentation(rep);
    }
    
    /** Creates a new instance of Vector */
    public Vector(Point anchor, Vector3f value) {
        this.anchor = anchor;
        this.value = value;
        
        magnitude = value.length();
        
        //setPosition()
        //this(new Vector3f(), new Vector3f());
    }

    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        Vector v = (Vector) obj;
        
        if(v.anchor != anchor)
            return false;
        
        // this is the low tech, ghetto way of doing things
        // but it should suffice for now.
        return  Math.abs(v.value.x - value.x) <= .001f &&
                Math.abs(v.value.y - value.y) <= .001f &&
                Math.abs(v.value.z - value.z) <= .001f;
        
        //return v.anchor == anchor && v.value.equals(value);
    }

    public String getLabelText() {
        if(isSymbol())
            return getName();
        else return ""+getMagnitude();
    }

    public String getLabelTextNoUnits() {
        if(isSymbol())
            return getName();
        else return ""+getMagnitude();
    }
    
    
}
