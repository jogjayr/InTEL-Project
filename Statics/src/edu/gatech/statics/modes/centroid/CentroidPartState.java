/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.util.Buildable;
import edu.gatech.statics.util.Builder;

/**
 * This is the state class that manages the information for each specific
 * CentroidPart.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidPartState implements Buildable<CentroidPartState>{

    final private boolean locked;
    final private String area;
    final private String xPosition;
    final private String yPosition;
    final private String myPart;

    /**
     * Constructor
     * @param builder Object factory
     */
    private CentroidPartState(Builder builder) {
        this.locked = builder.locked;
        this.area = builder.area;
        this.xPosition = builder.xPosition;
        this.yPosition = builder.yPosition;
        this.myPart = builder.myPart;
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Getter
     * @return
     */
    public String getArea() {
        return area;
    }

    /**
     * Getter
     * @return
     */
    public String getXPosition() {
        return xPosition;
    }

    /**
     * Getter
     * @return
     */
    public String getYPosition() {
        return yPosition;
    }

    /**
     * Getter
     * @return
     */
    public String getMyPart() {
        return myPart;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<CentroidPartState> {

        private boolean locked;
        private String area;
        private String xPosition;
        private String yPosition;
        private String myPart;

        public Builder() {
        }

        public Builder(CentroidPartState state) {
            this.locked = state.locked;
            this.area = state.area;
            this.xPosition = state.xPosition;
            this.yPosition = state.yPosition;
            this.myPart = state.myPart;
        }

        public boolean isLocked() {
            return locked;
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

        public String getMyPart() {
            return myPart;
        }

        public void setLocked(boolean solved) {
            this.locked = solved;
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

        public void setMyPart(String myPart) {
            this.myPart = myPart;
        }

        public CentroidPartState build() {
            return new CentroidPartState(this);
        }
    }

    @Override
    public String toString() {
        return "CentroidPartState: {solved=" + locked + ", area=\"" + area + "\", xPosition=\"" + xPosition + "\", yPosition=\"" + yPosition + "\", myPart=\"" + myPart + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}