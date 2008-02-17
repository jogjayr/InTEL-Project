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
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.manipulators.Orientation2DSnapManipulator;
import edu.gatech.statics.objects.manipulators.OrientationListener;
import java.util.List;

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
    
    private void performDelete() {
        if(diagram.isSolved())
            return;
        
        if(diagram.getSelection() != null) {
            diagram.remove(diagram.getSelection());
            diagram.onClick(null);
        }
    }

    void onSelect(Load selection) {
        if (selection instanceof Force) {
            if(MouseInput.get().isButtonDown(0))
                enableOrientationManipulator((Force) selection);
        }
    }
    private Orientation2DSnapManipulator orientationManipulator;
    private Force orientationForce;

    private long orientationPress;
    protected void enableOrientationManipulator(Force force) {

        final List<Vector3f> snapDirections = diagram.getSensibleDirections(force.getAnchor());
        orientationManipulator = new Orientation2DSnapManipulator(force.getAnchor(), Vector3f.UNIT_Z, snapDirections);
        orientationManipulator.addListener(new MyOrientationListener());
        addToAttachedHandlers(orientationManipulator);

        orientationForce = force;
        orientationPress = System.currentTimeMillis();

        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_tools_createForce2"));
    }

    private class MyOrientationListener implements OrientationListener {
        public void onRotate(Matrix3f rotation) {
            orientationForce.setRotation(rotation);
        }
    }

    protected static final int clickThreshold = 200;
    // NOTE: 
    // TODO: this is not a good means for handling releasing the orientation 
    // manipulator. We need something that is slightly more responsive.
    @Override
    public void update(float time) {
        super.update(time);

        if (orientationManipulator != null) {
            if (orientationManipulator.mouseReleased()) {
                releaseOrientationManipulator();
                
                // we keep track of the time between mouse down and up, and if
                // it is brief enough, we do not deselect the force we are rotating
                if(System.currentTimeMillis() - orientationPress > clickThreshold)
                    diagram.onClick(null);
            }
        }
    }

    public void releaseOrientationManipulator() {
        if (orientationManipulator.getCurrentSnap() != null) {

            orientationForce.setVectorValue(orientationManipulator.getCurrentSnap());

            orientationManipulator.setEnabled(false);
            removeFromAttachedHandlers(orientationManipulator);
            orientationManipulator = null;
        }
    }
}
