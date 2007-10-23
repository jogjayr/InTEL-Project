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
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.VectorListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CreateForceTool2D extends Tool implements ClickListener {
    
    protected Point forceAnchor;
    protected Force force;
    protected World world;
    
    protected DragSnapManipulator dragManipulator;
    protected Orientation2DSnapManipulator orientationManipulator;
    
    /** Creates a new instance of CreateForceTool */
    public CreateForceTool2D(World world) {
        this.world = world;
        forceAnchor = new Point(new Vector3f());
        force = new Force(forceAnchor,new Vector3f(1.5f,1f,0).normalize());
        force.setSymbol(true);
        force.createDefaultSchematicRepresentation();
        
        VectorListener forceListener = new VectorOverlapDetector(world, force);
        force.addListener(forceListener);
        
    }
    
    protected void onActivate() {
        
        world.add(force);
        world.updateNodes();
        
        enableDragManipulator();
        
        StaticsApplication.getApp().enableSelection(false);
    }

    protected void onCancel() {
        world.remove(force);
        world.updateNodes();
        
        if(dragManipulator != null)
            dragManipulator.setEnabled(false);
        
        if(orientationManipulator != null)
            orientationManipulator.setEnabled(false);
    }

    protected void onFinish() {
        StaticsApplication.getApp().enableSelection(true);
    }
    
    protected void finishForce() {
        
        // add things to force that will be relevant...
        
        final Orientation2DSnapManipulator runtimeOrientationManipulator = orientationManipulator;
        
        runtimeOrientationManipulator.removeClickListener(this);
        runtimeOrientationManipulator.setEnabled(false);
        force.addManipulator(runtimeOrientationManipulator);
        
        // so we can delete the force if we don't like it
        final Manipulator runtimeDeletionManipulator = new DeletableManipulator(world, force) {
            // we override this method so that when the force is deleted, the other forces will snap appropriately.
            public void performDelete() {
                super.performDelete();
                force.setValue(force.getValue());
            }
        };
        runtimeDeletionManipulator.setEnabled(false);
        force.addManipulator(runtimeDeletionManipulator);
        
        final Manipulator labelManipulator = new LabelManipulator(force);
        labelManipulator.setEnabled(true);
        force.addManipulator(labelManipulator);
        
        LabelSelector labelTool = new LabelSelector(world, StaticsApplication.getApp().getCurrentInterface().getToolbar());
        labelTool.setHintText("");
        labelTool.activate();
        labelTool.onClick(force);
    }
    
    protected void enableDragManipulator() {
        
        List<Point> pointList = new ArrayList();
        for(SimulationObject obj : world.allObjects())
            if(obj instanceof Point)
                pointList.add((Point)obj);
        
        dragManipulator = new DragSnapManipulator(force, pointList);
        dragManipulator.addClickListener(this);
        addToAttachedHandlers(dragManipulator);
        
        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_tools_createForce1"));
    }
    
    protected void enableOrientationManipulator(Point point) {
        
        final List<Vector3f> snapDirections = world.getSensibleDirections(point);
        orientationManipulator = new Orientation2DSnapManipulator(force, Vector3f.UNIT_Z, snapDirections);
        orientationManipulator.addClickListener(this);
        addToAttachedHandlers(orientationManipulator);
        
        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_tools_createForce2"));
    }

    public void onClick(Manipulator m) {
        
        if(dragManipulator != null) {
            if(dragManipulator.getCurrentSnap() != null) {
                
                // snap at the drag manipulator and terminate,
                // enable the orientation manipulator
                Point point = dragManipulator.getCurrentSnap();
                force.setAnchor(point);
                
                dragManipulator.setEnabled(false);
                removeFromAttachedHandlers(dragManipulator);
                dragManipulator = null;
                
                enableOrientationManipulator(point);
            }
        }
        
        if(orientationManipulator != null) {
            if(orientationManipulator.getCurrentSnap() != null) {
                
                force.setValue(orientationManipulator.getCurrentSnap());

                orientationManipulator.setEnabled(false);
                removeFromAttachedHandlers(orientationManipulator);
                //orientationManipulator = null;

                finish();
                finishForce();
            }
        }
    }

    public void onRelease(Manipulator m) {}
    
}
