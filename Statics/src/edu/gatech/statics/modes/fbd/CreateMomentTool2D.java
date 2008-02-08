/*
 * CreateForceTool.java
 *
 * Created on July 16, 2007, 3:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.manipulators.*;
import edu.gatech.statics.util.ClickListener;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CreateMomentTool2D extends Tool implements ClickListener {
    
    protected Point forceAnchor;
    protected Moment moment;
    protected World world;
    
    protected DragSnapManipulator dragManipulator;
    //protected Orientation2DSnapManipulator orientationManipulator;
    
    /** Creates a new instance of CreateForceTool */
    public CreateMomentTool2D(World world) {
        this.world = world;
        forceAnchor = new Point(new Vector3f());
        moment = new Moment(forceAnchor,new Vector3f(0,0,1),"M");
        //moment.setSymbol(true);
        moment.createDefaultSchematicRepresentation();
    }
    
    protected void onActivate() {
        
        world.add(moment);
        //world.updateNodes();
        
        enableDragManipulator();
    }

    protected void onCancel() {
        world.remove(moment);
        //world.updateNodes();
        
        if(dragManipulator != null)
            dragManipulator.setEnabled(false);
    }

    protected void onFinish() {
    }
    
    protected void finishForce() {
        
        // add things to force that will be relevant...
        
        // so we can delete the force if we don't like it
        final Manipulator runtimeDeletionManipulator = new DeletableManipulator(world, moment);
        runtimeDeletionManipulator.setEnabled(false);
        moment.addManipulator(runtimeDeletionManipulator);
        
        final Manipulator labelManipulator = new LabelManipulator(moment);
        labelManipulator.setEnabled(true);
        moment.addManipulator(labelManipulator);
        
        LabelSelector labelTool = new LabelSelector(world, StaticsApplication.getApp().getCurrentInterface().getToolbar());
        labelTool.setHintText("");
        labelTool.setIsCreating(true);
        labelTool.activate();
        labelTool.onClick(moment);
    }
    
    protected void enableDragManipulator() {
        
        List<Point> pointList = new ArrayList();
        for(SimulationObject obj : world.allObjects())
            if(obj instanceof Point)
                pointList.add((Point)obj);
        
        dragManipulator = new DragSnapManipulator(moment, pointList);
        dragManipulator.addClickListener(this);
        addToAttachedHandlers(dragManipulator);
        
        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_tools_createMoment"));
    }

    public void onMousePress(Manipulator m) {
        
        if(dragManipulator != null) {
            if(dragManipulator.getCurrentSnap() != null) {
                
                // snap at the drag manipulator and terminate,
                // enable the orientation manipulator
                Point point = dragManipulator.getCurrentSnap();
                moment.setAnchor(point);
                
                dragManipulator.setEnabled(false);
                removeFromAttachedHandlers(dragManipulator);
                dragManipulator = null;
                
                finish();
                finishForce();
            }
        }
    }

    public void onMouseRelease(Manipulator m) {}
    
}
