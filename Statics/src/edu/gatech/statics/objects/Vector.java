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
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Vector extends SimulationObject {
    
    private Point anchor;
    private Vector3f value; // normalized value
    private float magnitude; // 
    
    private List<VectorListener> listeners = new ArrayList<VectorListener>();
    
    public void addListener(VectorListener listener) {listeners.add(listener);}
    public void removeListener(VectorListener listener) {listeners.remove(listener);}
    
    private boolean fixed;
    public boolean isFixed() {return fixed;}
    public void setFixed(boolean fixed) {this.fixed = fixed;}
    
    private boolean solved;
    public boolean isSolved() {return solved;}
    public void setSolved(boolean solved) {this.solved = solved;}
    
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
        
        if(magnitude < 0) {
            magnitude *= -1;
            setNormalizedValue(value.negate());
        }
        
        double power = Math.pow(10, StaticsApplication.getApp().getUnits().getDecimalPrecisionForces());
        //magnitude = (float) (Math.floor(magnitude*power)/power);
        magnitude = (float)(Math.round(magnitude*power)/power);
        
        this.magnitude = magnitude;
        //setValue( value.normalize().mult(magnitude) );
        
    }
    
    // this should be overridden in general circumstances
    public Vector negate() {
        Vector r = new Vector(anchor, value.negate());
        
        r.fixed = fixed;
        r.solved = solved;
        r.symbol = symbol;
        
        return r;
    }
    
    public Matrix3f getRotation() {
        Matrix3f mat = new Matrix3f();
        //if(value.length() == 0)
        //    return mat;
        
        mat.fromStartEndVectors(Vector3f.UNIT_Z, value);
        return mat;
    }
    
    public void setRotation(Matrix3f mat) {
        Vector3f v = mat.mult(Vector3f.UNIT_Z);
        v.multLocal(magnitude);
        //if(!v.equals(value))
        //value = v;
        setNormalizedValue(v);
    }
    
    //public void setAnchor(Vector3f anchor) {this.anchor = anchor;}
    //public Vector3f getAnchor() {return anchor;}
    
    public void setNormalizedValue(Vector3f value) {
        
        Vector3f oldValue = this.value;
        this.value = value.normalize();
        //magnitude = value.length();
        
        for(VectorListener listener : listeners)
            listener.valueChanged(oldValue);
    }
    public Vector3f getNormalizedValue() {return value;}

    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentation(this);
        addRepresentation(rep);
    }
    
    /** Creates a new instance of Vector */
    public Vector(Point anchor, Vector3f value) {
        this.anchor = anchor;
        
        setMagnitude(value.length());
        setNormalizedValue(value);
        //magnitude = value.length();
        //this.value = value.normalize();
        
        //setPosition()
        //this(new Vector3f(), new Vector3f());
    }

    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        Vector v = (Vector) obj;
        
        // this is the low tech, ghetto way of doing things
        // but it should suffice for now.
        return  v.anchor == anchor && valuesCloseEnough(v.value, value);
    }

    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.anchor != null ? this.anchor.hashCode() : 0);
        return hash;
    }
    
    // returns true if the vectors are close enough
    // close enough means that they have the same values in their decimal places
    // up to the decimal precision for forces in Units.
    private boolean valuesCloseEnough(Vector3f v1, Vector3f v2) {
        double power = Math.pow(10, StaticsApplication.getApp().getUnits().getDecimalPrecisionForces());
        double xdiff = Math.floor(v1.x*power) - Math.floor(v2.x*power);
        double ydiff = Math.floor(v1.y*power) - Math.floor(v2.y*power);
        double zdiff = Math.floor(v1.z*power) - Math.floor(v2.z*power);
        return Math.abs(xdiff) + Math.abs(ydiff) + Math.abs(zdiff) < .1;
    }
    
    /**
     * Compare two vectors accounting for them both being symbols
     * This means that they are equal if they are equal or opposite
     */
    public boolean equalsSymbolic(Vector v) {
        if(v.isSolved() && isSolved())
            return equals(v);
        
        return  v.anchor == anchor && (
                valuesCloseEnough(v.value, value) ||
                valuesCloseEnough(v.value, value.negate())
                );
    }
    
    public ArrowRepresentation getArrow() {
        for(Representation rep : getRepresentation(RepresentationLayer.vectors))
            if(rep instanceof ArrowRepresentation)
                return (ArrowRepresentation) rep;
        return null;
    }
    
    public Vector3f getDisplayCenter() {
        
        ArrowRepresentation arrow = getArrow();
        if(arrow == null)
            return super.getDisplayCenter();
        
        float distance = 1+2*(arrow.getLength() + arrow.getAxisOffset());
        return getTranslation().add( getNormalizedValue().mult(distance)    );
    }
    
    private String getMagnitudeString() {
        Units units = StaticsApplication.getApp().getUnits();
        return String.format("%."+units.getDecimalPrecisionForces()+"f", getMagnitude());
    }

    public String getLabelText() {
        if(isSymbol() && !isSolved())
            return getName();
        else return ""+getMagnitudeString()+" "+getUnits();
    }
    
    public String getUnits() {return "";}

    public String getLabelTextNoUnits() {
        if(isSymbol() && !isSolved())
            return getName();
        else return ""+getMagnitudeString();
    }
    
    public String toString() {
        String r = getClass().getSimpleName()+" @ "+getAnchor().getName()+" : <"+value.x+", "+value.y+", "+value.z+"> ";
        r += ""+magnitude;
        if(isSymbol())
            r += " \""+getName()+"\"";
        if(isSolved())
            r += " SOLVED";
        
        return r;
    }
}
