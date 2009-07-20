
/*
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;

/**
 * EquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
abstract public class EquationMath {

    //protected static final float TEST_ACCURACY = .022f;
    private final String name;
    private final EquationDiagram diagram;

    public EquationDiagram getDiagram() {
        return diagram;
    }

    public String getName() {
        return name;
    }

    /**
     * Delegates to the state. This method is equivalent to getState().isLocked()
     * @return
     */
    public boolean isLocked() {
        return getState().isLocked();
    }

    /**
     * Returns the state that corresponds to this instance of EquationMath.
     * @return
     */
    public EquationMathState getState() {
        return getDiagram().getCurrentState().getEquationStates().get(getName());
    }

    /** Creates a new instance of Equation */
    public EquationMath(String name, EquationDiagram world) {
        this.name = name;
        this.diagram = world;
    }

    public abstract boolean check();

    /**
     * This yields the precision to be used in value comparisons. Moments equations
     * must compare values with respect to the precision of distance. Non moment equations
     * may use a fixed value.
     * @return
     */
    abstract protected float valueComparePrecision();

    /**
     * This method should be overridden by the subclasses of EquationMath.
     * This checks the given load and coefficient, and will return an error code.
     * If the check passes, checkTerm should return TermError.none
     * @param load
     * @param coefficient
     * @return
     */
    protected abstract TermError checkTerm(AnchoredVector load, String coefficient);
}
