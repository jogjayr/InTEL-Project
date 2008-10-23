/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import edu.gatech.statics.exercise.state.DiagramState;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerState implements DiagramState<ViewerDiagram> {

    public ViewerState() {
    }

    public boolean isLocked() {
        return false;
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public class Builder implements edu.gatech.statics.util.Builder<ViewerState> {

        public ViewerState build() {
            return new ViewerState();
        }
    }
}
