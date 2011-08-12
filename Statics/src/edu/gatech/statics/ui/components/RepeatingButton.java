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
package edu.gatech.statics.ui.components;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.BEvent;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.BIcon;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * This is a BButton which fires its action as long as the user presses it.
 * @author Calvin Ashmore
 */
public class RepeatingButton extends BButton {

    private static final int period = 50;
    private static final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    /**
     * Constructor. Set button label to text
     * @param text 
     */
    public RepeatingButton(String text) {
        super(text);
    }
    /**
     * Constructor. Set button label to text and action to action
     * @param text
     * @param action 
     */
    public RepeatingButton(String text, String action) {
        super(text, action);
    }
    /**
     * Constructor. Set button label to text, listener listens for action
     * @param text
     * @param listener
     * @param action 
     */
    public RepeatingButton(String text, ActionListener listener, String action) {
        super(text, listener, action);
    }
    /**
     * Constructor. Set button icon to icon, and perform action
     * @param icon
     * @param action 
     */
    public RepeatingButton(BIcon icon, String action) {
        super(icon, action);
    }
    /**
     * Constructor. Set button icon to icon, listener listens for action
     * @param icon
     * @param listener
     * @param action 
     */
    public RepeatingButton(BIcon icon, ActionListener listener, String action) {
        super(icon, listener, action);
    }
    /**
     * intercept mouse presses and use that to start our repeater
     * @param event
     * @return 
     */
    @Override
    public boolean dispatchEvent (BEvent event) {
        boolean result = super.dispatchEvent(event);
        
        // intercept mouse presses and use that to start our repeater
        // this occurs after the super execution so that the pressed flag may be set.
        if (isEnabled() && event instanceof MouseEvent) {
            MouseEvent mev = (MouseEvent)event;
            if(mev.getType() == MouseEvent.MOUSE_PRESSED)
                onPress();
        }
        return result;
    }
    
    private ScheduledFuture future;
    /**
     * Handles the button press event
     */
    private void onPress() {
        if(future != null)
            return; // should not occur, but for safety
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ActionCaller caller = new ActionCaller();
        future = executor.scheduleAtFixedRate(caller, 0, period, timeUnit);
    }
    /**
     * Handles button release. Sets future button presses to false
     */
    private void onRelease() {
        future.cancel(false);
        future = null;
    }

    private class ActionCaller implements Runnable {
        
        public void run() {
            if(!_pressed) {
                onRelease();
                return;
            }
            
            fireAction(InterfaceRoot.getInstance().getBuiNode().getTickStamp(), 0);
        }
    }
}
