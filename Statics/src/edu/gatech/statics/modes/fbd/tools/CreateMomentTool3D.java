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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.VectorListener;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Jayraj
 */
public class CreateMomentTool3D extends CreateLoadTool {

    protected Moment moment;
    protected OrientationHandler orientationHandler;

    public CreateMomentTool3D(FreeBodyDiagram diagram) {
        super(diagram);
    }

    

    /**
     * Creates a label selector
     * @return
     */
    @Override
    protected LabelSelector createLabelSelector() {
        LabelSelector labelTool = new LabelSelector(getDiagram(), moment, moment.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your moment");
        return labelTool;
    }

    /**
     * Creates a moment at anchor
     * @param anchor
     * @return SingletonList with moment created
     */
    @Override
    protected List<Load> createLoads(Point anchor) {
        moment = new Moment(anchor, new Vector3bd("0", "0", "1"), "M");
        moment.createDefaultSchematicRepresentation();
        new LabelManipulator(moment);
        return Collections.singletonList((Load) moment);
    }

    /**
     * Perfors setup
     */
    @Override
     protected void onActivate() {
        super.onActivate();
        StaticsApplication.getApp().setUIFeedbackKey("fbd_tools_createMoment");
        //System.out.println("***onActivate in 3DMoment");
    }

    /**
     * 
     */
    @Override
    protected void onFinish() {
        super.onFinish();
        VectorListener momentListener = new VectorOverlapDetector(getDiagram(), moment);
        momentListener.valueChanged(moment.getVectorValue());
        moment.addListener(momentListener);
    }

    /**
     * 
     */
    protected void enableOrientationManipulator() {

        orientationHandler = new OrientationHandler(getDiagram(), this, moment);

    }

    /**
     * Shows label selector tool when the orientationHandler is released
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
     * 
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
