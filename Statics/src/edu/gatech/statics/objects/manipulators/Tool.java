/*
 * Tool.java
 *
 * Created on July 16, 2007, 3:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.ToolFinishListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Tool extends InputHandler {
    
    private boolean active;
    public boolean isActive() {return active;}
    
    /** Creates a new instance of Tool */
    public Tool() {
        
        InputActionInterface inputInterface = new InputActionInterface() {
            public void performAction(InputActionEvent evt) {
                if(evt.getTriggerName().equals("return"))
                    onKeyOK();
                if(evt.getTriggerName().equals("escape"))
                    onKeyEscape();
            }
        };
        
        addAction(inputInterface, "return", KeyInput.KEY_RETURN, false);
        addAction(inputInterface, "escape", KeyInput.KEY_ESCAPE, false);
    }

    public void onClick(SimulationObject obj) {
        
    }

    public void onHover(SimulationObject obj) {
        
    }
    
    /** called whenever the user presses RETURN or ENTER */
    protected void onKeyOK() {}
    
    /** called whenever the user presses ESC */
    protected void onKeyEscape() {cancel();}
    
    abstract protected void onActivate();
    abstract protected void onCancel();
    abstract protected void onFinish();
    
    private List<ToolFinishListener> finishListeners = new ArrayList<ToolFinishListener>();
    public void addFinishListener(ToolFinishListener listener) {finishListeners.add(listener);}
    public void removeFinishListener(ToolFinishListener listener) {finishListeners.remove(listener);}
    
    public final void activate() {
        active = true;
        StaticsApplication.getApp().setCurrentTool(this);
        //StaticsApplication.getApp().getInput().addToAttachedHandlers(this);
        onActivate();
        setEnabled(true);
    }
    
    public final void cancel() {
        onCancel();
        finish();
    }
    
    public final void finish() {
        setEnabled(false);
        active = false;
        StaticsApplication.getApp().setCurrentTool(null);
        StaticsApplication.getApp().resetAdvice();
        
        //StaticsApplication.getApp().getInput().removeFromAttachedHandlers(this);
        onFinish();
        for(ToolFinishListener listener : finishListeners)
            listener.finished();
        
    }
    
}
