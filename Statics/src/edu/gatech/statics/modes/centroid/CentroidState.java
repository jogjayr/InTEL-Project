/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.util.Builder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidState implements DiagramState<CentroidDiagram> {

    //final private Map<AnchoredVector, String> terms;
    final private boolean solved;
    final private String area;
    final private String xPosition;
    final private String yPosition;
    final private Map<CentroidPart, CentroidPartState> myParts;

    private CentroidState(Builder builder) {
        this.solved = builder.solved;
        this.area = builder.area;
        this.xPosition = builder.xPosition;
        this.yPosition = builder.yPosition;
        this.myParts = Collections.unmodifiableMap(builder.getMyParts());
    }

    public Map<CentroidPart, CentroidPartState> getEquationStates() {
        return myParts;
    }

    public CentroidPartState getMyPartState(CentroidPart part) {
        return myParts.get(part);
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

        private boolean solved;
        private String area;
        private String xPosition;
        private String yPosition;
        private Map<CentroidPart, CentroidPartState> myParts;

        public Builder() {
            this.solved = false;
            this.area = "";
            this.xPosition = "";
            this.yPosition = "";
            this.myParts = new HashMap<CentroidPart, CentroidPartState>();
        }

        public Builder(CentroidState state) {
            this.solved = state.solved;
            this.area = state.area;
            this.xPosition = state.xPosition;
            this.yPosition = state.yPosition;
            this.myParts = new HashMap<CentroidPart, CentroidPartState>(state.getEquationStates());
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

        public Map<CentroidPart, CentroidPartState> getMyParts() {
            return myParts;
        }

        public void putEquationState(CentroidPartState myPartState) {
            myParts.put(myPartState.getMyPart(), myPartState);
        }

        public void setEquationStates(Map<CentroidPart, CentroidPartState> myPartStates) {
            this.myParts.clear();
            this.myParts.putAll(myPartStates);
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

        public CentroidState build() {
            return new CentroidState(this);
        }
    }

    @Override
    public String toString() {
        return "CentroidState: {solved=" + solved + ", area=\"" + area + "\", xPosition=\"" + xPosition + "\", yPosition=\"" + yPosition + "\", myPart=\"" + myParts + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
