/*
 * LabelManipulator.java
 * 
 * Created on Oct 22, 2007, 10:56:09 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.tools;

import com.jmex.bui.event.MouseListener;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.representations.LabelRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelManipulator /*extends Manipulator<VectorObject>*/ {

    private LabelRepresentation labelRepresentation;
    private LabelClickListener clickListener;

    //private boolean labelingEnabled = true;
    private Load myLoad;
    
    public LabelManipulator(Load vectorObject) {
        myLoad = vectorObject;
        
        for(Representation rep : vectorObject.getRepresentation(RepresentationLayer.labels))
            if(rep instanceof LabelRepresentation)
                labelRepresentation = (LabelRepresentation)rep;
        
        clickListener = new LabelClickListener();
        labelRepresentation.getLabel().addListener(clickListener);
    }
    
    protected void performSingleClick() {
        //StaticsApplication.getApp().select(getTarget());
        StaticsApplication.getApp().getCurrentDiagram().onClick(myLoad);
    }
    
    protected void performDoubleClick() {
        
        Diagram diagram = StaticsApplication.getApp().getCurrentDiagram();
        if(diagram instanceof FreeBodyDiagram)
            ((FreeBodyDiagram)diagram).onLabel(myLoad);
        
        //if(!labelingEnabled)
        //    return;
        
        //LabelSelector tool = new LabelSelector(
        //        StaticsApplication.getApp().getCurrentWorld(),
        //        StaticsApplication.getApp().getCurrentInterface().getToolbar());
        //tool.activate();
        //tool.onClick(getTarget());
    }

    //public void enableLabeling(boolean enabled) {
    //    labelingEnabled = enabled;
    //}
    
    protected class LabelClickListener implements MouseListener {
        
        protected static final int clickThreshold = 200;
        protected static final int doubleClickThreshold = 600;
        
        long lastPress;
        long lastClick;
        boolean hasClicked;
        
        protected void reset() {
            lastPress = 0;
            lastClick = 0;
            hasClicked = false;
        }

        public void mousePressed(com.jmex.bui.event.MouseEvent event) {
            lastPress = event.getWhen();
        }

        public void mouseReleased(com.jmex.bui.event.MouseEvent event) {
            if(event.getWhen() - lastPress <= clickThreshold) {
                if(hasClicked && event.getWhen() - lastClick <= doubleClickThreshold) {
                    performDoubleClick();
                    reset();
                } else {
                    performSingleClick();
                    lastClick = event.getWhen();
                    hasClicked = true;
                }
            } else {
                reset();
            }
        }

        public void mouseEntered(com.jmex.bui.event.MouseEvent event) {}
        public void mouseExited(com.jmex.bui.event.MouseEvent event) {}
    }
}
