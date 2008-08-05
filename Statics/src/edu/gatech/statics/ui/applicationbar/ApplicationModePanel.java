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
import edu.gatech.statics.exercise.DiagramType;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel<T extends Diagram> extends BContainer {

    private ApplicationTab tab;
    private BLabel titleLabel;

    /**
     * Returns the type of diagram that this panel is supposed to represent.
     * Panels and tabs should correspond to diagrams.
     * @return
     */
    abstract public DiagramType getDiagramType();

    public final String getPanelName() {
        return getDiagramType().getName();
    }
    //public abstract String getPanelName();
    protected BLabel getTitleLabel() {
        return titleLabel;
    }

    public ApplicationModePanel() {
        super(new BorderLayout());
        tab = createTab();
        titleLabel = new BLabel("", "mode_title");
        add(titleLabel, BorderLayout.NORTH);
    }

    abstract public void activate();

    public ApplicationTab getTab() {
        return tab;
    }

    abstract protected ApplicationTab createTab();

    /**
     * This is a utiliy method for subclasses of ApplicationModePanel to access 
     * the current diagram.
     * @return
     */
    public final T getDiagram() {
        Diagram<?> currentDiagram = StaticsApplication.getApp().getCurrentDiagram();
        // attempt to pass this normally. If this fails, then the current diagram in the 
        // StaticsApplication is incorrect.
        return (T) currentDiagram;
    }
}
