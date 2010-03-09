/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import edu.gatech.statics.exercise.DiagramKey;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPart implements DiagramKey {

    final String xPosition;
    final String yPosition;
    final String zPosition;
    final String partName;

    enum PartType {

        CIRCLE, RECTANGLE, TRIANGLE;
    }

    public CentroidPart(String xPosition, String yPosition, String zPosition, String partName) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
        this.partName = partName;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
