/*
 * Manipulator.java
 *
 * Created on June 4, 2007, 3:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.InputHandler;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Manipulator<SimType extends SimulationObject> extends InputHandler {
    
    //private InputHandler rootInput;
    //public InputHandler getRootInput() {return rootInput;}
    
    private SimType target;
    public SimType getTarget() {return target;}
    
    /*protected List<ClickListener> clickListeners = new ArrayList();
    public void addClickListener(ClickListener listener) {
        clickListeners.add(listener);
    }
    public void removeClickListener(ClickListener listener) {
        clickListeners.remove(listener);
    }
    
    
    protected void clickEvent(boolean isClick) {

        List<ClickListener> clickListeners1 = new ArrayList<ClickListener>(clickListeners);
        for(ClickListener listener : clickListeners1)
            if(isClick)
                listener.onMousePress(this);
            else
                listener.onMouseRelease(this);
    }*/
    
    /*public void setEnabledGlobally(boolean enabled) {
        if(enabled)
            //StaticsApplication.getApp().getInput().addToAttachedHandlers(this);
            setParent(StaticsApplication.getApp().getInput());
        else
            setParent(null);
            //StaticsApplication.getApp().getInput().removeFromAttachedHandlers(this);
    }*/
    
    //private boolean enabled;
    //public boolean isEnabled() {return enabled;}
    /** may enable manipulator, or enable manipulator handle */
    /*public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        StaticsApplication.getApp().enableSelection(!enabled);
        if(enabled) {
            StaticsApplication.getApp().getInput().addToAttachedHandlers(this);
        } else {
            StaticsApplication.getApp().getInput().removeFromAttachedHandlers(this);
        }
    }*/
    
    /** Creates a new instance of Manipulator */
    public Manipulator(SimType target) {
        this.target = target;
        
        //addAction(new ClickAction());
    }
    
    /*private class ClickAction extends MouseInputAction {
        
        boolean buttonWasDown;
        
        public ClickAction() {
            mouse = StaticsApplication.getApp().getMouse();
            buttonWasDown = MouseInput.get().isButtonDown(0);
        }
        
        public void performAction(InputActionEvent evt) {
            
            boolean buttonIsDown = MouseInput.get().isButtonDown(0);
            
            if(buttonIsDown && !buttonWasDown) clickEvent(true);
            if(!buttonIsDown && buttonWasDown) clickEvent(false);
            
            buttonWasDown = buttonIsDown;
        }
    }*/
}
