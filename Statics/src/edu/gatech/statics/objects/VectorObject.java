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
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.math.BigDecimal;

/**
 * This denotes the object representation of a vector. It contains listeners as 
 * well as the representation and manipulation handles for the quantity.
 * @author Calvin Ashmore
 */
public class VectorObject extends SimulationObject implements Quantified {

    private Point anchor;
    private Vector vector;
    private String name;

    @Override
    public String getName() {
        if(name != null)
            return name;
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
        this.name = name;
        //vector.setSymbol(name);
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
        mat.fromStartEndVectors(Vector3f.UNIT_Z, getVectorValue().toVector3f());
        return mat;
    }

    /**
     * This method is necessary for orientation and placement of forces,
     * however it also has the effect of slightly comprimising the precision of
     * vectors.
     * @param mat
     */
    @Override
    public void setRotation(Matrix3f mat) {
        Vector3f v = mat.mult(Vector3f.UNIT_Z);
        
        Vector3bd vbd = new Vector3bd(
                BigDecimal.valueOf(v.x), 
                BigDecimal.valueOf(v.y), 
                BigDecimal.valueOf(v.z));
        
        setVectorValue(vbd);
    }
    
    public Vector3bd getVectorValue() {
        return vector.getVectorValue();
    }

    public void setVectorValue(Vector3bd value) {
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
        this.name = obj.name;
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

        float distance = DisplayConstants.getInstance().getForceSize();
//        1 + 2 * (arrow.getLength() + arrow.getAxisOffset());
        return getTranslation().add(vector.getVectorValue().toVector3f().mult(distance));
    }

    @Override
    public String toString() {
        String r = getClass().getSimpleName() + " @ " + getAnchor().getName() + " : " + vector.toString();
        return r;
    }

    public Unit getUnit() {
        return vector.getUnit();
    }

    public double doubleValue() {
        return vector.doubleValue();
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
        setName(symbolName);
        vector.setSymbol(symbolName);
    }

    public void setDiagramValue(BigDecimal v) {
        vector.setDiagramValue(v);
    }
    
    public BigDecimal getDiagramValue() {
        return vector.getDiagramValue();
    }
    
    /**
     * Returns a String representation of the vector without a unit.
     * Will return 1.0 for symbolic quantities.
     * @return
     */
    public String toStringDecimal() {
        return vector.getQuantity().toStringDecimal();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VectorObject other = (VectorObject) obj;
        if (this.anchor != other.anchor && (this.anchor == null || !this.anchor.equals(other.anchor))) {
            return false;
        }
        if (this.vector != other.vector && (this.vector == null || !this.vector.equals(other.vector))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.anchor != null ? this.anchor.hashCode() : 0);
        hash = 41 * hash + (this.vector != null ? this.vector.hashCode() : 0);
        return hash;
    }

    /**
     * Returns true if the vector objects are equivalent as symbolic vectors. 
     * This tests to make sure that they point in the same direction.
     * @param v
     * @return
     */
    public boolean equalsSymbolic(Load v) {
        return vector.equalsSymbolic(v.getVector()) && anchor.equals(v.getAnchor());
    }
}
