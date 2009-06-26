/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.actions.RemoveLoad;
import edu.gatech.statics.modes.fbd.tools.OrientationHandler;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDInput extends InputHandler {

    private FreeBodyDiagram diagram;

    public FBDInput(FreeBodyDiagram diagram) {
        this.diagram = diagram;


        InputActionInterface action = new InputActionInterface() {

            public void performAction(InputActionEvent evt) {
                performDelete();
            }
        };

        addAction(action, "delete", KeyInput.KEY_DELETE, false);
        addAction(action, "backspace", KeyInput.KEY_BACK, false);
    }

    public void cancelOrientationHandler() {
        if (orientationHandler != null && orientationHandler.isEnabled()) {
            orientationHandler.stop();
            orientationHandler = null;
        }
    }

    private void performDelete() {
        if (diagram.isSolved()) {
            return;
        }
        if (diagram.getSelection() != null) {
            Load selection = diagram.getSelection();
            RemoveLoad removeAction = new RemoveLoad(selection.getAnchoredVector());
            diagram.performAction(removeAction);
            diagram.onClick(null);
        }
    }

    void onSelect(Load selection) {
        if (diagram.isSolved()) {
            return;
        }
        if (selection instanceof Force) {
            if (MouseInput.get().isButtonDown(0)) {
                enableOrientationManipulator((Force) selection);
            }
        }
    }
    //private Orientation2DSnapManipulator orientationManipulator;
    //private Force orientationForce;
    private OrientationHandler orientationHandler;
    private long orientationPress;

    /**
     * This should probably be changed to represent an actual tool.
     * @param force
     */
    protected void enableOrientationManipulator(Force force) {
        //System.out.println("enabling orientation manipulator: "+force);

        cancelOrientationHandler();

        orientationHandler = new OrientationHandler(diagram, this, force);
        orientationPress = System.currentTimeMillis();

        StaticsApplication.getApp().setAdviceKey("fbd_tools_createForce2");
    }
    protected static final int clickThreshold = 200;
    // NOTE: 
    // TODO: this is not a good means for handling releasing the orientation 
    // manipulator. We need something that is slightly more responsive.

    @Override
    public void update(float time) {
        super.update(time);

        if (orientationHandler != null && orientationHandler.isEnabled()) {
            if (orientationHandler.release()) {

                // we keep track of the time between mouse down and up, and if
                // it is brief enough, we do not deselect the force we are rotating
                if (System.currentTimeMillis() - orientationPress > clickThreshold) {
                    diagram.onClick(null);
                }
            }
        }
    }
}
