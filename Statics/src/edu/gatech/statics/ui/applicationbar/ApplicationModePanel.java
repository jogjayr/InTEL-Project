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
package edu.gatech.statics.ui.applicationbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel<T extends Diagram> extends BContainer {

    //private ApplicationTab tab;
    //private BLabel titleLabel;
    /** TODO Ask Calvin how to resolve this
     * It is obviously wrong to make this member protected, but the grandchild class Equation3DModePanel's activate() method
     * has no way of calling this class's activated method without using super.activate() which would cause the child class's
     * EquationModePanel().activate() to be called. This intermediate call is undesirable since the grandchild class's implementation
     * of activate() is desired to be completely overriding the child class's implementation of activate()
     *
     *
     */
    protected boolean active;

    /**
     * Returns the type of diagram that this panel is supposed to represent.
     * Panels and tabs should correspond to diagrams.
     * @return
     */
    abstract public DiagramType getDiagramType();

    /**
     * Getter
     * @return 
     */
    public final String getPanelName() {
        return getDiagramType().getName();
    }

    /**
     * Getter
     * @return 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This is called when the state changes, so that the mode can update its interface.
     * This by default automatically updates the undo and redo buttons so that they appear enabled correctly.
     * So, remember to call super.stateChanged() when overriding this method.
     */
    public void stateChanged() {
        ApplicationBar appBar = InterfaceRoot.getInstance().getApplicationBar();
        appBar.updateUndoRedoState();
    }

    //public abstract String getPanelName();
//    protected BLabel getTitleLabel() {
//        return titleLabel;
//    }
    /**
     * Constructor. Creates a new ApplicationModePanel with BorderLayour
     */
    public ApplicationModePanel() {
        super(new BorderLayout());
        //tab = createTab();
        //titleLabel = new BLabel("", "mode_title");
        //add(titleLabel, BorderLayout.NORTH);
    }

    //abstract public void activate();
    /**
     * Activates ApplicationModePanel
     */
    public void activate() {
        active = true;
        stateChanged();
    }
    /**
     * Deactivates ApplicationModePanel
     */
    public void deactivate() {
        active = false;
    }

//    public ApplicationTab getTab() {
//        return tab;
//    }
    //abstract protected ApplicationTab createTab();
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

    /**
     * Whether to show the undo/redo box or not.
     * Note that functionality is still there, but the box may be hidden.
     * @return
     */
    public boolean isUndoVisible() {
        return true;
    }
}
