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
