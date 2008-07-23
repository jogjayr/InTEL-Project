/*
 * CreateForceTool.java
 *
 * Created on July 16, 2007, 3:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.manipulators.*;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.VectorListener;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CreateForceTool2D extends CreateLoadTool /*implements ClickListener*/ {

    protected Force force;
    protected Diagram diagram;
    protected Orientation2DSnapManipulator orientationManipulator;

    /** Creates a new instance of CreateForceTool */
    public CreateForceTool2D(Diagram diagram) {
        super(diagram);
        this.diagram = diagram;
    }

    protected List<Load> createLoad(Point anchor) {
        force = new Force(anchor, new Vector3bd("1.5", "1", "0").normalize(), "F");
        force.createDefaultSchematicRepresentation();
        return Collections.singletonList((Load) force);
    }

    @Override
    protected void onActivate() {
        super.onActivate();

        StaticsApplication.getApp().setAdviceKey("fbd_tools_createForce1");
    }

    @Override
    protected void onFinish() {
        super.onFinish();

        VectorListener forceListener = new VectorOverlapDetector(diagram, force);
        force.addListener(forceListener);
    }

    @Override
    protected void showLabelSelector() {
        LabelSelector labelTool = new LabelSelector(diagram, force, force.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your force");
        labelTool.setUnits(Unit.force.getSuffix());
        labelTool.setHintText("");
        labelTool.setIsCreating(true);
        labelTool.createPopup();
        if (force == null) {
            diagram.removeUserObject(force);
            diagram.onClick(null);
        }
    }

    protected void enableOrientationManipulator() {

        final List<Vector3f> snapDirections = diagram.getSensibleDirections(getSnapPoint());
        orientationManipulator = new Orientation2DSnapManipulator(force.getAnchor(), Vector3f.UNIT_Z, snapDirections);
        orientationManipulator.addListener(new MyOrientationListener());
        addToAttachedHandlers(orientationManipulator);

        StaticsApplication.getApp().setAdviceKey("fbd_tools_createForce2");
    }

    private class MyOrientationListener implements OrientationListener {

        public void onRotate(Matrix3f rotation) {
            force.setRotation(rotation);
        }
    }

    // NOTE: 
    // TODO: this is not a good means for handling releasing the orientation 
    // manipulator. We need something that is slightly more responsive.
    @Override
    public void update(float time) {
        super.update(time);

        if (orientationManipulator != null) {






            if (orientationManipulator.mouseReleased()) {
                releaseOrientationManipulator();
            }
        }
    }

    @Override
    public void onMouseDown() {
        if (getDragManipulator() != null) {






            if (releaseDragManipulator()) {
                enableOrientationManipulator();
            }
        }
    }

    public void releaseOrientationManipulator() {
        if (orientationManipulator.getCurrentSnap() != null) {

            Vector3f currentSnap = orientationManipulator.getCurrentSnap();
            Vector3bd vbd = new Vector3bd(
                    BigDecimal.valueOf(currentSnap.x),
                    BigDecimal.valueOf(currentSnap.y),
                    BigDecimal.valueOf(currentSnap.z));

            force.setVectorValue(vbd);

            orientationManipulator.setEnabled(false);
            removeFromAttachedHandlers(orientationManipulator);
            orientationManipulator = null;

            finish();



            showLabelSelector();
        }
    }
}
