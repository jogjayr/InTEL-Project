/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.util.ArrayList;
import java.util.List;

/**
 * This denotes the object representation of a vector. It contains listeners as 
 * well as the representation and manipulation handles for the quantity.
 * @author Calvin Ashmore
 */
public class VectorObject extends SimulationObject implements Quantified {

    private Point anchor;
    private Vector vector;

    @Override
    public String getName() {
        return vector.getSymbolName();
    }

    @Override
    public String getLabelText() {
        return vector.getQuantity().toString();
    }
    
    /**
     * Returns a reference to the VectorObject's backing vector
     * @return
     */
    public Vector getVector() {
        return vector;
    }

    @Override
    public void setName(String name) {
        // ???
        vector.setSymbol(name);
    }

    @Override
    public Vector3f getTranslation() {
        return anchor.getTranslation();
    }

    @Override
    public void setTranslation(Vector3f v) {
        anchor.setTranslation(v);
    }

    public String getSymbolName() {
        return vector.getSymbolName();
    }

    public Point getAnchor() {
        return anchor;
    }

    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }

    @Override
    public Matrix3f getRotation() {
        Matrix3f mat = new Matrix3f();

        mat.fromStartEndVectors(Vector3f.UNIT_Z, getVectorValue());
        return mat;
    }

    @Override
    public void setRotation(Matrix3f mat) {
        Vector3f v = mat.mult(Vector3f.UNIT_Z);
        setVectorValue(v);
    }

    public Vector3f getVectorValue() {
        return vector.getVectorValue();
    }

    public void setVectorValue(Vector3f value) {
        vector.setVectorValue(value);
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentation(this);
        addRepresentation(rep);
    }

    public VectorObject(Point anchor, Vector vector) {
        this.anchor = anchor;
        this.vector = vector;
    }

    public VectorObject(VectorObject obj) {
        this.anchor = obj.anchor;
        this.vector = new Vector(obj.vector);
    }

    public ArrowRepresentation getArrow() {
        for (Representation rep : getRepresentation(RepresentationLayer.vectors)) {
            if (rep instanceof ArrowRepresentation) {
                return (ArrowRepresentation) rep;
            }
        }
        return null;
    }

    @Override
    public Vector3f getDisplayCenter() {

        ArrowRepresentation arrow = getArrow();
        if (arrow == null) {
            return super.getDisplayCenter();
        }

        float distance = 1 + 2 * (arrow.getLength() + arrow.getAxisOffset());
        return getTranslation().add(vector.getVectorValue().mult(distance));
    }

    @Override
    public String toString() {
        String r = getClass().getSimpleName() + " @ " + getAnchor().getName() + " : " + vector.toString();
        return r;
    }

    public Unit getUnit() {
        return vector.getUnit();
    }

    public float getValue() {
        return vector.getValue();
    }

    public boolean isKnown() {
        return vector.isKnown();
    }

    public boolean isSymbol() {
        return vector.isSymbol();
    }

    public void setKnown(boolean known) {
        vector.setKnown(known);
    }

    public void setSymbol(String symbolName) {
        vector.setSymbol(symbolName);
    }

    public void setValue(double v) {
        vector.setValue(v);
    }

    /**
     * Returns a String representation of the vector without a unit.
     * Will return 1.0 for symbolic quantities.
     * @return
     */
    public String toStringDecimal() {
        return vector.getQuantity().toStringDecimal();
    }
}
