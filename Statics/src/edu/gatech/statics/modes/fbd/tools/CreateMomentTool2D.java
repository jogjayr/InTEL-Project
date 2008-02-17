/*
 * CreateForceTool.java
 *
 * Created on July 16, 2007, 3:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.manipulators.*;
import edu.gatech.statics.exercise.Diagram;
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
    protected Diagram world;

    /** Creates a new instance of CreateForceTool */
    public CreateMomentTool2D(Diagram world) {
        super(world);
        this.world = world;
    }

    @Override
    protected List<Load> createLoad(Point anchor) {

        moment = new Moment(anchor, new Vector3f(0, 0, 1), "M");
        moment.createDefaultSchematicRepresentation();
        return Collections.singletonList((Load) moment);
    }
}
