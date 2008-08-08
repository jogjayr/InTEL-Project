/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.modes.equation.worksheet.Worksheet;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
final public class EquationState implements DiagramState<EquationDiagram> {

    private Worksheet worksheet;
    private Point momentPoint;

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public Point getMomentPoint() {
        return momentPoint;
    }

    private EquationState(Builder builder) {
    }

    public static final class Builder implements edu.gatech.statics.util.Builder<EquationState> {

        private Point momentPoint;

        public Point getMomentPoint() {
            return momentPoint;
        }

        public void setMomentPoint(Point momentPoint) {
            this.momentPoint = momentPoint;
        }

        public Builder() {
        }

        public Builder(EquationState state) {
        }

        public EquationState build() {
            return new EquationState(this);
        }
    }

    public boolean isLocked() {
        return worksheet.isSolved();
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public EquationState restore() {
        // do nothing?
        return this;
    }
}
