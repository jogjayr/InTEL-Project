/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.math.Vector3bd;
import java.math.BigDecimal;

/**
 * CentroidPart serves as a data class that contains the width, height,
 * x/y/z position of the centroid, part name, and the surface area of each part
 * of the centroid body.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public abstract class CentroidPart implements DiagramKey {

//    final private Vector3bd centroid;
    final private String partName;

    public CentroidPart(String partName) {
        this.partName = partName;
    }
//    final private BigDecimal width;
//    final private BigDecimal height;
//    final private BigDecimal surfaceArea;
//    final private PartType part;

//    public enum PartType {
//
//        CIRCLE, RECTANGLE, TRIANGLE;
//    }
//    public CentroidPart(Vector3bd centroid, String width, String height, String partName, PartType part) {
//        this.centroid = centroid;
//        this.partName = partName;
//        this.width = new BigDecimal(width);
//        this.height = new BigDecimal(height);
//
//        //automatically generates the surface area value based on the width and height of the part and what type of shape it is
//        if (part == PartType.CIRCLE) { //PI*r^2
//            this.surfaceArea = new BigDecimal(Math.PI).multiply(this.width.divide(new BigDecimal("2.0")).pow(2));
//        } else if (part == PartType.RECTANGLE) { //w*h
//            this.surfaceArea = this.width.multiply(this.height);
//        } else if (part == PartType.TRIANGLE) { //w*h/2
//            this.surfaceArea = this.width.multiply(this.height).divide(new BigDecimal("2.0"));
//        } else {
//            throw new UnsupportedOperationException(part + " is not a valid part type.");
//        }
//
//        this.part = part;
//    }
    public String getPartName() {
        return partName;
    }

    abstract public Vector3bd getCentroid();

    abstract public BigDecimal getSurfaceArea();

    abstract public Representation<CentroidPartObject> createRepresentation(CentroidPartObject obj);
}
