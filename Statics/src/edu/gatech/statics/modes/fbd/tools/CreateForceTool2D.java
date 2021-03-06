/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * CreateForceTool.java
 *
 * Created on July 16, 2007, 3:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.VectorListener;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CreateForceTool2D extends CreateLoadTool /*implements ClickListener*/ {

    protected Force force;
    //protected Orientation2DSnapManipulator orientationManipulator;
    protected OrientationHandler orientationHandler;

    /** Creates a new instance of CreateForceTool */
    public CreateForceTool2D(FreeBodyDiagram diagram) {
        super(diagram);
    }

    /**
     * Creates force at anchor
     * @param anchor
     * @return Singleton list with force created
     */
    protected List<Load> createLoads(Point anchor) {
        force = new Force(anchor, new Vector3bd("1.5", "1", "0").normalize(), "F");
        force.createDefaultSchematicRepresentation();
        new LabelManipulator(force);
        return Collections.singletonList((Load) force);
    }

    /**
     *
     */
    @Override
    protected void onActivate() {
        super.onActivate();
        StaticsApplication.getApp().setUIFeedbackKey("fbd_tools_createForce1");
    }
    /**
     * 
     */
    @Override
    protected void onFinish() {
        super.onFinish();
        VectorListener forceListener = new VectorOverlapDetector(getDiagram(), force);
        forceListener.valueChanged(force.getVectorValue());
        force.addListener(forceListener);
    }
    /**
     * 
     * @return
     */
    @Override
    protected LabelSelector createLabelSelector() {
        LabelSelector labelTool = new LabelSelector(getDiagram(), force, force.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your force");
        return labelTool;
    }

    /**
     * Creats an OrientationHandler
     */
    protected void enableOrientationManipulator() {

        orientationHandler = new OrientationHandler(getDiagram(), this, force);
        //final List<Vector3f> snapDirections = getDiagram().getSensibleDirections(getSnapPoint());
        //orientationManipulator = new Orientation2DSnapManipulator(force.getAnchor(), Vector3f.UNIT_Z, snapDirections);
        //orientationManipulator.addListener(new MyOrientationListener());
        //addToAttachedHandlers(orientationManipulator);

        StaticsApplication.getApp().setUIFeedbackKey("fbd_tools_createForce2");
    }

    // NOTE: 
    // TODO: this is not a good means for handling releasing the orientation 
    // manipulator. We need something that is slightly more responsive.
    /**
     * If orientationHandler.release(), label selector is shown
     * @param time
     */
    @Override
    public void update(float time) {
        super.update(time);

        if (orientationHandler != null && orientationHandler.isEnabled()) {
            // attempt to release the handler
            if (orientationHandler.release()) {
                showLabelSelector();
                finish();
            }
        }
    }

    /**
     * Handles mouse down events, calls enableOrientationManipulator
     */
    @Override
    public void onMouseDown() {
        if (getDragManipulator() != null) {
            if (releaseDragManipulator()) {
                enableOrientationManipulator();
            }
        }
    }
}
