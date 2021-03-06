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
package edu.gatech.statics.modes.equation.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class EquationBar extends BContainer {

    protected EquationModePanel parent;
    protected EquationMath math;
    protected boolean locked = false;
    protected static final ColorRGBA regularBorderColor = new ColorRGBA(0, 0, 0, 0f);
    protected static final ColorRGBA highlightBorderColor = new ColorRGBA(.5f, .5f, 1, 1f);
    protected static final String symbolColor = "ff0000";

    public EquationMath getMath() {
        return math;
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Removes all of the contents of the equation bar. This should be called
     * when the ui is freshly activated.
     */
    abstract void clear();

    /**
     * 
     * @return 
     */
    public EquationModePanel getModePanel() {
        return parent;
    }


    /**
     * Constructor. Makes equation bar given equation math and parent mode panel
     * @param math 
     * @param parent 
     */
    public EquationBar(EquationMath math, EquationModePanel parent) {
        super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        this.math = math;
        this.parent = parent;
    }

//    public EquationBar(EquationMath math, Equation3DModePanel parent) {
//        super(GroupLayout.makeHoriz(GroupLayout.CENTER));
//        this.math = math;
//        this.parent = parent;
//    }
//    /**
//     * This handles the *state change* aspect of removing a term. The UI change occurs in
//     * removeBox, which is called by this method.
//     * @param source
//     */
//    protected void performRemove(AnchoredVector source) {
//        RemoveTerm removeTermAction = new RemoveTerm(getMath().getName(), source);
//        getMath().getDiagram().performAction(removeTermAction);
//    }
//
//    /**
//     * This handles the *state change* aspect of adding a term. The UI change occurs in
//     * addBox, which is called by this method.
//     * @param source
//     */
//    protected void performAdd(AnchoredVector source) {
//        AddTerm addTermAction = new AddTerm(getMath().getName(), source);
//        getMath().getDiagram().performAction(addTermAction);
//    }

    //abstract BContainer makeStartContainer() throws IOException;

    /**
     * This is called when the bar is loaded or the state has changed.
     * Note that this will generally be called after the above methods
     * (performAddTerm and performRemoveTerm) are called.
     */
    abstract void stateChanged();

    //abstract void addBox(AnchoredVector load, String coefficient);

    /**
     * Put the component focus on the load that has been given.
     * Do nothing if the load has not been added to the equation.
     */
    abstract void focusOnTerm(AnchoredVector load);

    abstract void setLocked();

    abstract void setUnlocked();

    abstract void highlightVector(AnchoredVector obj);

    //abstract Vector2f getLineAnchor(VectorObject obj);
}