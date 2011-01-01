/*
 * The purpose of this class is to create and solve equations of the R x F format
 * Where TermEquation had a corresponding state (TermEquationMathState) that stored the
 * equation state as a hashmap of string (coefficient) and vector (force), this shall have
 * a corresponding state (MomentEquationMathState) that will store equation state as a
 * hashmap of vector (from point about which moment is calculated to point of application of force)
 * and vector (force acting at that point)
 */

package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationDiagram;

/**
 *
 * @author Jayraj
 *
 * It is possible to confuse this with EquationMathMoments. This class
 * deals with RxF moment equations. The EquationMathMoments class deals with
 * SigmaMx, SigmaMy and SigmaMz equations
 */
public class MomentEquationMath extends EquationMath {

    protected final String name;
    protected final EquationDiagram diagram;

    public MomentEquationMath(String name, EquationDiagram world) {
        super(name, world);
        this.name = name;
        this.diagram = world;
    }

   
    
    public MomentEquationMathState getState() {
        return (MomentEquationMathState)super.getState();
    }
    public boolean check() {

        MomentEquationMathState state = (MomentEquationMathState)getState();

        return true;
    }

    protected TermError checkTerm(AnchoredVector force, AnchoredVector rVector) {
        return TermError.none;
    }

}
