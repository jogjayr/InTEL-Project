/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.util.Builder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin
 */
public class FindPointsState implements DiagramState<FindPointsDiagram> {

    private final Map<Point, String> pointValues;
    private final boolean locked;

    private FindPointsState(Builder builder) {
        this.pointValues = Collections.unmodifiableMap(new HashMap<Point, String>(builder.pointValues));
        this.locked = builder.locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public static class Builder implements edu.gatech.statics.util.Builder<FindPointsState> {

        private Map<Point, String> pointValues = new HashMap<Point, String>();
        private boolean locked;

        public Builder() {
        }

        private Builder(FindPointsState state) {
            this.pointValues.putAll(state.pointValues);
            this.locked = state.locked;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public Map<Point, String> getPointValues() {
            return pointValues;
        }

        public void setPointValues(Map<Point, String> pointValues) {
            this.pointValues = pointValues;
        }

        public FindPointsState build() {
            return new FindPointsState(this);
        }

        private static final class LabelTriple {

            String x, y, z;

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(x);
                sb.append(":");
                sb.append(y);
                sb.append(":");
                sb.append(z);
                return sb.toString();
            }
        }

        public void setX(Point point, String xValue) {
            xValue = xValue.replaceAll(":", "");
            String coord = pointValues.get(point);
            LabelTriple lt;
            if (coord != null) {
                lt = parse(coord);
            } else {
                lt = new LabelTriple();
            }
            lt.x = xValue;
            pointValues.put(point, lt.toString());
        }

        public void setY(Point point, String yValue) {
            yValue = yValue.replaceAll(":", "");
            String coord = pointValues.get(point);
            LabelTriple lt;
            if (coord != null) {
                lt = parse(coord);
            } else {
                lt = new LabelTriple();
            }
            lt.y = yValue;
            pointValues.put(point, lt.toString());
        }

        public void setZ(Point point, String zValue) {
            zValue = zValue.replaceAll(":", "");
            String coord = pointValues.get(point);
            LabelTriple lt;
            if (coord != null) {
                lt = parse(coord);
            } else {
                lt = new LabelTriple();
            }
            lt.z = zValue;
            pointValues.put(point, lt.toString());
        }

        private LabelTriple parse(String s) {
            String[] split = s.split(":");
            LabelTriple lt = new LabelTriple();
            lt.x = split[0];
            lt.y = split[1];
            lt.z = split[2];
            return lt;
        }
    }
}
