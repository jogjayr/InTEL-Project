/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.persistence.ResolvableByName;

/**
 * CentroidPart serves as a data class that contains the x, y, z, name, and
 * surface area of each part of the centroid body.
 * @author Jimmy Truesdell
 */
public class CentroidPart implements DiagramKey {

    final String xPosition;
    final String yPosition;
    final String zPosition;
    final String partName;
    final String surfaceArea;
    enum PartType {

        CIRCLE, RECTANGLE, TRIANGLE;
    }

//    public String getName() {
//        return partName;
//    }
//
//    /**
//     * For persistence, do not call directly.
//     * @param name
//     * @deprecated
//     */
//    @Deprecated
//    public CentroidPart(String name) {
//        this.partName = name;
//        xPosition = null;
//        yPosition = null;
//        zPosition = null;
//        surfaceArea = null;
//    }

    public CentroidPart(String xPosition, String yPosition, String zPosition, String partName, String surfaceArea) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
        this.partName = partName;
        this.surfaceArea = surfaceArea;

    }

    public String getPartName() {
        return partName;
    }

    public String getxPosition() {
        return xPosition;
    }

    public String getyPosition() {
        return yPosition;
    }

    public String getzPosition() {
        return zPosition;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }
}
