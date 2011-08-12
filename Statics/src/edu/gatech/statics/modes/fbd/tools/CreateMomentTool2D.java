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
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CreateMomentTool2D extends CreateLoadTool { //implements ClickListener {

    protected Moment moment;
    //protected Diagram world;
    /** Creates a new instance of CreateForceTool */
    public CreateMomentTool2D(FreeBodyDiagram world) {
        super(world);
    }

    /**
     * 
     * @return
     */
    @Override
    protected LabelSelector createLabelSelector() {
        LabelSelector labelTool = new LabelSelector(getDiagram(), moment, moment.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your moment");
        return labelTool;
    }

    /**
     * 
     */
    @Override
    protected void onActivate() {
        super.onActivate();
        StaticsApplication.getApp().setUIFeedbackKey("fbd_tools_createMoment");
    }

    /**
     * Creates 2D moment at anchor
     * @param anchor
     * @return Singleton list containing moment created
     */
    @Override
    protected List<Load> createLoads(Point anchor) {

        moment = new Moment(anchor, new Vector3bd("0", "0", "1"), "M");
        moment.createDefaultSchematicRepresentation();
        new LabelManipulator(moment);
        return Collections.singletonList((Load) moment);
    }
}
