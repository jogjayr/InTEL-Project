/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMath;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Worksheet;



/**
 *
 * @author Jayraj
 *
 * Not tested with friction problems. In the Builder for EquationState there is a check that moments are
 * only about Z-axis. This fails for friction problems
 */
public class Equation3DDiagram extends EquationDiagram {

    public Equation3DDiagram(BodySubset bodies) {
        super(bodies);
    }
    
    @Override
    protected EquationState createInitialState() {
        Builder builder = new EquationState.Builder();
        
        builder.getEquationStates().put("F[x]", new TermEquationMathState.Builder("F[x]", false, null, Vector3bd.UNIT_X).build());
        builder.getEquationStates().put("F[y]", new TermEquationMathState.Builder("F[y]", false, null, Vector3bd.UNIT_Y).build());
        builder.getEquationStates().put("F[z]", new TermEquationMathState.Builder("F[z]", false, null, Vector3bd.UNIT_Z).build());
        builder.getEquationStates().put(Moment3DEquationMath.DEFAULT_NAME, new Moment3DEquationMathState.Builder(Moment3DEquationMath.DEFAULT_NAME, null).build());
        builder.getEquationStates().put("M[x]", new TermEquationMathState.Builder("M[x]", true, null, Vector3bd.UNIT_X).build());
        builder.getEquationStates().put("M[y]", new TermEquationMathState.Builder("M[y]", true, null, Vector3bd.UNIT_Y).build());
        builder.getEquationStates().put("M[z]", new TermEquationMathState.Builder("M[z]", true, null, Vector3bd.UNIT_Z).build());

        if(worksheet == null) {
            worksheet = new Worksheet(this);
        }
        return builder.build();
    }



}
