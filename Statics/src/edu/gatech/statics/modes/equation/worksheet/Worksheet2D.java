/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationDiagram;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet2D extends Worksheet {

    public Worksheet2D(EquationDiagram diagram) {
        super(diagram, 3);

        EquationMath sumFx = new EquationMathForces("F[X]", Vector3bd.UNIT_X, diagram);
        EquationMath sumFy = new EquationMathForces("F[Y]", Vector3bd.UNIT_Y, diagram);
        EquationMath sumMp = new EquationMathMoments("M[P]", Vector3bd.UNIT_Z, diagram);

        addEquation(sumFx);
        addEquation(sumFy);
        addEquation(sumMp);
    }
}
