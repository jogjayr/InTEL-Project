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
import edu.gatech.statics.util.SelectionFilter;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Tool extends InputHandler {
    
    private boolean active;

    public SelectionFilter getSelectionFilter() {
        return null;
    }
    
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
    
    //private List<ToolFinishListener> finishListeners = new ArrayList<ToolFinishListener>();
    //public void addFinishListener(ToolFinishListener listener) {finishListeners.add(listener);}
    //public void removeFinishListener(ToolFinishListener listener) {finishListeners.remove(listener);}
    
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
        StaticsApplication.getApp().resetUIFeedback();
        
        //StaticsApplication.getApp().getInput().removeFromAttachedHandlers(this);
        onFinish();
        //for(ToolFinishListener listener : finishListeners)
        //    listener.finished();
    }
    
}
