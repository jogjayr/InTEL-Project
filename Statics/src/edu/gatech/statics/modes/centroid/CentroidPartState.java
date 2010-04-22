/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartState implements DiagramState<CentroidDiagram>{

    final private boolean solved;
    final private String area;
    final private String xPosition;
    final private String yPosition;
    final private CentroidPart myPart;

    private CentroidPartState(Builder builder) {
        this.solved = builder.solved;
        this.area = builder.area;
        this.xPosition = builder.xPosition;
        this.yPosition = builder.yPosition;
        this.myPart = builder.myPart;
    }

    public boolean isLocked() {
        return solved;
    }

    public String getArea() {
        return area;
    }

    public String getXPosition() {
        return xPosition;
    }

    public String getYPosition() {
        return yPosition;
    }

    public CentroidPart getMyPart() {
        return myPart;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<CentroidPartState> {

        private boolean solved;
        private String area;
        private String xPosition;
        private String yPosition;
        private CentroidPart myPart;

        public Builder() {
        }

        public Builder(CentroidPartState state) {
            this.solved = state.solved;
            this.area = state.area;
            this.xPosition = state.xPosition;
            this.yPosition = state.yPosition;
            this.myPart = state.myPart;
        }

        public boolean isLocked() {
            return solved;
        }

        public String getArea() {
            return area;
        }

        public String getXPosition() {
            return xPosition;
        }

        public String getYPosition() {
            return yPosition;
        }

        public CentroidPart getMyPart() {
            return myPart;
        }

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setXPosition(String xPosition) {
            this.xPosition = xPosition;
        }

        public void setYPosition(String yPosition) {
            this.yPosition = yPosition;
        }

        public void setMyPart(CentroidPart myPart) {
            this.myPart = myPart;
        }

        public CentroidPartState build() {
            return new CentroidPartState(this);
        }
    }

    @Override
    public String toString() {
        return "CentroidPartState: {solved=" + solved + ", area=\"" + area + "\", xPosition=\"" + xPosition + "\", yPosition=\"" + yPosition + "\", myPart=\"" + myPart + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}