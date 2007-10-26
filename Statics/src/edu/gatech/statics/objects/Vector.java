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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Vector extends SimulationObject {
    
    //private Vector3f anchor;
    private Point anchor;
    private Vector3f value;
    private float magnitude;
    
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
        this.magnitude = magnitude;
        setValue( value.normalize().mult(magnitude) );
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
        if(value.length() == 0)
            return mat;
        
        mat.fromStartEndVectors(Vector3f.UNIT_Z, value.normalize());
        return mat;
    }
    
    public void setRotation(Matrix3f mat) {
        Vector3f v = mat.mult(Vector3f.UNIT_Z);
        v.multLocal(magnitude);
        //if(!v.equals(value))
        //value = v;
        setValue(v);
    }
    
    //public void setAnchor(Vector3f anchor) {this.anchor = anchor;}
    //public Vector3f getAnchor() {return anchor;}
    
    public void setValue(Vector3f value) {
        
        Vector3f oldValue = this.value;
        this.value = value;
        magnitude = value.length();
        
        for(VectorListener listener : listeners)
            listener.valueChanged(oldValue);
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
        
        //if(v.anchor != anchor)
        //    return false;
        
        // operate on vector3f translation equivalence instead of point equality
        //if(!v.anchor.getTranslation().equals(anchor.getTranslation()))
        //    return false;
        
        // this is the low tech, ghetto way of doing things
        // but it should suffice for now.
        return  v.anchor == anchor &&
                Math.abs(v.value.x - value.x) <= .0001f &&
                Math.abs(v.value.y - value.y) <= .0001f &&
                Math.abs(v.value.z - value.z) <= .0001f;
        
        //return v.anchor == anchor && v.value.equals(value);
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
        return getTranslation().add( getValue().normalize().mult(distance) );
    }

    public String getLabelText() {
        if(isSymbol() && !isSolved())
            return getName();
        else return ""+getMagnitude();
    }

    public String getLabelTextNoUnits() {
        if(isSymbol() && !isSolved())
            return getName();
        else return ""+getMagnitude();
    }
    
    public String toString() {
        String r = getClass().getSimpleName()+" @ "+getAnchor().getName()+" : <"+value.x+", "+value.y+", "+value.z+">";
        if(isSymbol())
            r += " \""+getName()+"\"";
        if(isSolved())
            r += " SOLVED";
        
        return r;
    }
}
