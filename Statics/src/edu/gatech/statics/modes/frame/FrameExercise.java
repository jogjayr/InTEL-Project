/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.frame;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;

/**
 * A frame exercise is like an ordinary exercise, but it provides some UI elements to simplify the 
 * process working with frames.
 * @author Calvin Ashmore
 */
public class FrameExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();

        // replace the mode default select mode panel with the frame one.
        ic.replaceModePanel(SelectModePanel.class, new FrameSelectModePanel());

        return ic;
    }

    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodies) {

        // set up the special name for the whole frame, in frame problems
        if (FrameUtil.isWholeDiagram(bodies)) {
            bodies.setSpecialName(FrameUtil.whatToCallTheWholeDiagram);
        }

        return super.createFreeBodyDiagram(bodies);
    }

    @Override
    protected SelectDiagram createSelectDiagram() {
        return new FrameSelectDiagram();
    }
}
