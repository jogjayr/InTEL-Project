/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.truss.zfm.ZFMState.Builder;

/**
 *
 * @author Calvin Ashmore
 */
public class ClearAction implements DiagramAction<ZFMState> {

    /**
     * Clears oldState
     * @param oldState
     * @return
     */
    public ZFMState performAction(ZFMState oldState) {
        Builder builder = oldState.getBuilder();
        builder.getSelectedZFMs().clear();

        return builder.build();
    }

}
