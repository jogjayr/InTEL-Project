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
    
    public RepeatingButton(String text) {
        super(text);
    }

    public RepeatingButton(String text, String action) {
        super(text, action);
    }

    public RepeatingButton(String text, ActionListener listener, String action) {
        super(text, listener, action);
    }

    public RepeatingButton(BIcon icon, String action) {
        super(icon, action);
    }

    public RepeatingButton(BIcon icon, ActionListener listener, String action) {
        super(icon, listener, action);
    }
    
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

    private void onPress() {
        if(future != null)
            return; // should not occur, but for safety
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ActionCaller caller = new ActionCaller();
        future = executor.scheduleAtFixedRate(caller, 0, period, timeUnit);
    }
    
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
