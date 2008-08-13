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
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.math.BigDecimal;

/**
 * This denotes the object representation of a vector. It contains listeners as 
 * well as the representation and manipulation handles for the quantity.
 * @author Calvin Ashmore
 */
public class VectorObject extends SimulationObject {//implements Quantified {

    private AnchoredVector myAnchoredVector;
    private String name;

    @Override
    public String getName() {
        if (name != null) {
            return name;
        }
        return getVector().getSymbolName();
    }

    @Override
    public String getLabelText() {
        return getVector().getQuantity().toString();
    }

    public AnchoredVector getAnchoredVector() {
        return myAnchoredVector;
    }

    /**
     * Returns a reference to the VectorObject's backing vector
     * @return
     */
    public Vector getVector() {
        return myAnchoredVector.getVector();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    //vector.setSymbol(name);
    }

    @Override
    public Vector3f getTranslation() {
        return getAnchor().getTranslation();
    }

    @Override
    public void setTranslation(Vector3f v) {
        getAnchor().setTranslation(v);
    }

    public String getSymbolName() {
        return myAnchoredVector.getSymbolName();
    }

    public Point getAnchor() {
        return myAnchoredVector.getAnchor();
    }

    public void setAnchor(Point anchor) {
        myAnchoredVector.setAnchor(anchor);
    }

    //private void setVector(Vector vector) {
    //    myAnchoredVector.setVector(vector);
    //}
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
        return getVector().getVectorValue();
    }

    public void setVectorValue(Vector3bd value) {
        getVector().setVectorValue(value);
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentation(this);
        addRepresentation(rep);
    }

    public VectorObject(AnchoredVector vector) {
        this.myAnchoredVector = new AnchoredVector(vector);
    }

    //public VectorObject(Point anchor, Vector vector) {
        //myAnchoredVector = new AnchoredVector(anchor, vector);
//        this.setAnchor(anchor);
//        this.setVector(vector);
    //}

    public VectorObject(VectorObject obj) {
        myAnchoredVector = new AnchoredVector(obj.myAnchoredVector);
        //new AnchoredVector(obj.getAnchor(), new Vector(obj.getVector()));
        setName(obj.name);
//        this.setAnchor(obj.getAnchor());
//        this.setVector(new Vector(obj.getVector()));
//        this.name = obj.name;
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
        return getTranslation().add(getVector().getVectorValue().toVector3f().mult(distance));
    }

    @Override
    public String toString() {
        String r = getClass().getSimpleName() + " @ " + getAnchor().getName() + " : " + getAnchor().toString();
        return r;
    }

    public Unit getUnit() {
        return myAnchoredVector.getUnit();
    }

    public boolean isSymbol() {
        return myAnchoredVector.isSymbol();
    }

    /*public double doubleValue() {
        return myAnchoredVector.doubleValue();
    }

    public boolean isKnown() {
        return myAnchoredVector.isKnown();
    }

    public void setKnown(boolean known) {
        myAnchoredVector.setKnown(known);
    }

    public void setSymbol(String symbolName) {
        setName(symbolName);
        myAnchoredVector.setSymbol(symbolName);
    }

    public void setDiagramValue(BigDecimal v) {
        myAnchoredVector.setDiagramValue(v);
    }

    public BigDecimal getDiagramValue() {
        return myAnchoredVector.getDiagramValue();
    }*/

    /**
     * Returns a String representation of the vector without a unit.
     * Will return 1.0 for symbolic quantities.
     * @return
     */
    public String toStringDecimal() {
        return getVector().getQuantity().toStringDecimal();
    }

    /*@Override
    public boolean equals(Object obj) {
    if (obj == null) {
    return false;
    }
    if (getClass() != obj.getClass()) {
    return false;
    }
    final VectorObject other = (VectorObject) obj;
    if (getAnchor() != other.getAnchor() && (getAnchor() == null || !getAnchor().equals(other.getAnchor()))) {
    return false;
    }
    if (getVector() != other.getVector() && (getVector() == null || !getVector().equals(other.getVector()))) {
    return false;
    }
    return true;
    }
    
    @Override
    public int hashCode() {
    int hash = 7;
    hash = 41 * hash + (getAnchor() != null ? getAnchor().hashCode() : 0);
    hash = 41 * hash + (getVector() != null ? getVector().hashCode() : 0);
    return hash;
    }*/
    /**
     * Returns true if the vector objects are equivalent as symbolic vectors. 
     * This tests to make sure that they point in the same direction.
     * @param v
     * @return
     */
    /*public boolean equalsSymbolic(Load v) {
    return getVector().equalsSymbolic(v.getVector()) && getAnchor().equals(v.getAnchor());
    }*/
}
