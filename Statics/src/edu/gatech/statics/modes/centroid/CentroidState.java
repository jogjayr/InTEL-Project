/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidState implements DiagramState<CentroidDiagram> {

    final private boolean solved;
    final private String area;
    final private String xPosition;
    final private String yPosition;

    private CentroidState(Builder builder) {
        this.solved = builder.solved;
        this.area = builder.area;
        this.xPosition = builder.xPosition;
        this.yPosition = builder.yPosition;
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

    public static class Builder implements edu.gatech.statics.util.Builder<CentroidState> {

        private boolean solved;
        private String area;
        private String xPosition;
        private String yPosition;

        public Builder() {
        }

        public Builder(CentroidState state) {
            this.solved = state.solved;
            this.area = state.area;
            this.xPosition = state.xPosition;
            this.yPosition = state.yPosition;
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

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void xPosition(String xPosition) {
            this.xPosition = xPosition;
        }

        public void yPosition(String yPosition) {
            this.yPosition = yPosition;
        }

        public CentroidState build() {
            return new CentroidState(this);
        }
    }

    @Override
    public String toString() {
        return "CentroidState: {solved=" + solved + ", area=\"" + area + "\", xPosition=\"" + xPosition + "\", yPosition=\"" + yPosition + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
