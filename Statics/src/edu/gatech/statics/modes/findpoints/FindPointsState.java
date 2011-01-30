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
    private final Map<Point, Boolean> pointLocks;
    private final boolean locked;

    private FindPointsState(Builder builder) {
        this.pointValues = Collections.unmodifiableMap(new HashMap<Point, String>(builder.pointValues));
        this.pointLocks = Collections.unmodifiableMap(new HashMap<Point, Boolean>(builder.pointLocks));
        this.locked = builder.locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isLocked(Point point) {
        Boolean pointLocked = pointLocks.get(point);
        if (pointLocked == null) {
            return false;
        }
        return pointLocked;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public String getX(Point point) {
        String value = pointValues.get(point);
        if (value == null) {
            return "";
        }
        return parse(value).x;
    }

    public String getY(Point point) {
        String value = pointValues.get(point);
        if (value == null) {
            return "";
        }
        return parse(value).y;
    }

    public String getZ(Point point) {
        String value = pointValues.get(point);
        if (value == null) {
            return "";
        }
        return parse(value).z;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<FindPointsState> {

        private Map<Point, String> pointValues = new HashMap<Point, String>();
        private Map<Point, Boolean> pointLocks = new HashMap<Point, Boolean>();
        private boolean locked;

        public Builder() {
        }

        private Builder(FindPointsState state) {
            this.pointValues.putAll(state.pointValues);
            this.pointLocks.putAll(state.pointLocks);
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

        public Map<Point, Boolean> getPointLocks() {
            return pointLocks;
        }

        public void setPointValues(Map<Point, String> pointValues) {
            this.pointValues = pointValues;
        }

        public void setPointLocks(Map<Point, Boolean> pointLocks) {
            this.pointLocks = pointLocks;
        }

        public FindPointsState build() {
            return new FindPointsState(this);
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
    }

    private static final class LabelTriple {

        String x = "", y = "", z = "";

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

    private static LabelTriple parse(String s) {
        String[] split = s.split(":", 3);
        LabelTriple lt = new LabelTriple();

        lt.x = split[0];
        lt.y = split[1];
        lt.z = split[2];
        return lt;
    }
}
