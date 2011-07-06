/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The main state class for the centroid mode. It contains the x,y,z, surface
 * area, and solved status for the main centroid body as well as a map
 * containing the states for the specific CentroidParts. Provides a method by
 * which it is possible to tell if all the CentroidParts have been solved for.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidState implements DiagramState<CentroidDiagram> {

    //final private Map<AnchoredVector, String> terms;
    final private boolean locked;
    final private String area;
    final private String xPosition;
    final private String yPosition;
    final private Map<String, CentroidPartState> myParts;

    /**
     * Constructor
     * @param builder
     */
    private CentroidState(Builder builder) {
        this.locked = builder.locked;
        this.area = builder.area;
        this.xPosition = builder.xPosition;
        this.yPosition = builder.yPosition;
        this.myParts = Collections.unmodifiableMap(builder.getMyParts());
    }

    /**
     * Getter
     * @return
     */
    public Map<String, CentroidPartState> getMyParts() {
        return myParts;
    }

    /**
     * Get state for part identified by partName
     * @param partName
     * @return
     */
    public CentroidPartState getMyPartState(String partName) {
        return myParts.get(partName);
    }

    /**
     * Getter
     * @return
     */
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
     * Returns a boolean that represents if all the CentroidParts in a
     * CentroidDiagram have been solved for.
     * @param diagram
     * @return
     */
    public boolean allPartsSolved(CentroidDiagram diagram) {
        int totalSolved = 0;
        for (CentroidPartState cps : getBuilder().getMyParts().values()) {
            if (!cps.isLocked()) {
                continue;
            }
            totalSolved++;
        }
        if (diagram.getBody().getParts().size() == totalSolved) {
            return true;
        } else {
            return false;
        }
    }

    public static class Builder implements edu.gatech.statics.util.Builder<CentroidState> {

        private boolean locked;
        private String area;
        private String xPosition;
        private String yPosition;
        private Map<String, CentroidPartState> myParts;

        public Builder() {
            this.locked = false;
            this.area = "";
            this.xPosition = "";
            this.yPosition = "";
            this.myParts = new HashMap<String, CentroidPartState>();
        }

        public Builder(CentroidState state) {
            this.locked = state.locked;
            this.area = state.area;
            this.xPosition = state.xPosition;
            this.yPosition = state.yPosition;
            this.myParts = new HashMap<String, CentroidPartState>(state.getMyParts());
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

        public Map<String, CentroidPartState> getMyParts() {
            return myParts;
        }

        public void putEquationState(CentroidPartState myPartState) {
            myParts.put(myPartState.getMyPart(), myPartState);
        }

        public void setMyParts(Map<String, CentroidPartState> myPartStates) {
            this.myParts.clear();
            this.myParts.putAll(myPartStates);
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

        public CentroidState build() {
            return new CentroidState(this);
        }
    }

    @Override
    public String toString() {
        return "CentroidState: {solved=" + locked + ", area=\"" + area + "\", xPosition=\"" + xPosition + "\", yPosition=\"" + yPosition + "\", myPart=\"" + myParts + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
