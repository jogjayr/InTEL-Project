/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.applicationbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel extends BContainer {

    private ApplicationTab tab;
    private BLabel titleLabel;
    
    public abstract String getPanelName();
    
    protected BLabel getTitleLabel() {
        return titleLabel;
    }
    
    public ApplicationModePanel() {
        super(new BorderLayout());
        tab = createTab();
        titleLabel = new BLabel("", "mode_title");
        add(titleLabel,BorderLayout.NORTH);
    }
    
    abstract public void activate();
    
    public ApplicationTab getTab() {return tab;}

    abstract protected ApplicationTab createTab();
    
    /**
     * This is a utiliy method for subclasses of ApplicationModePanel to access 
     * the current diagram.
     * @return
     */
    public final Diagram getDiagram() {
        return StaticsApplication.getApp().getCurrentDiagram();
    }
}
