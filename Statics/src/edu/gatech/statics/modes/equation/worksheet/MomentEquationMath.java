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
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationDiagram;
import java.math.BigDecimal;
import java.util.Map;

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

   
    
    @Override
    public MomentEquationMathState getState() {
        return (MomentEquationMathState)super.getState();
    }
    public boolean check() {
        //System.out.println("Checking...");
        MomentEquationMathState state = (MomentEquationMathState)getState();
        Map<AnchoredVector, AnchoredVector> terms = state.getTerms();
        Vector equationValue = new Vector(Unit.distance, Vector3bd.ZERO, new BigDecimal(0));
        for(Map.Entry<AnchoredVector, AnchoredVector> term : terms.entrySet()) {
            AnchoredVector force = term.getKey();
            AnchoredVector radius = term.getValue();
            equationValue.setVectorValue((radius.getVectorValue().cross(force.getVectorValue())).add(equationValue.getVectorValue()));
        }
        if(equationValue.getVectorValue() == Vector3bd.ZERO)
            return true;

        return false;
    }

    protected TermError checkTerm(AnchoredVector force, AnchoredVector rVector) {
        return TermError.none;
    }

}
