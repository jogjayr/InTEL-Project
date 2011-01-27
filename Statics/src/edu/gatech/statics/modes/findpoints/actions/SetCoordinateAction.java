/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.findpoints.FindPointsState;
import edu.gatech.statics.modes.findpoints.FindPointsState.Builder;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author gtg126z
 */
public class SetCoordinateAction implements DiagramAction<FindPointsState> {

    private Point point;
    private String value;
    private Coord coord;

    public enum Coord {

        x, y, z
    }

    /**
     * @param point
     * @param coord
     * @param value
     */
    public SetCoordinateAction(Point point, Coord coord, String value) {
        this.point = point;
        this.coord = coord;
        this.value = value;
    }

    public FindPointsState performAction(FindPointsState oldState) {
        Builder builder = oldState.getBuilder();

        if (coord == Coord.x) {
            builder.setX(point, value);
        } else if (coord == Coord.y) {
            builder.setY(point, value);
        } else if (coord == Coord.z) {
            builder.setZ(point, value);
        }

        return builder.build();
    }
}
