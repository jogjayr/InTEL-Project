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

        EquationMath sumFx = new EquationMathForces("F[X]", diagram);
        EquationMath sumFy = new EquationMathForces("F[Y]", diagram);
        EquationMath sumMp = new EquationMathMoments("M[P]", diagram);

//        addEquation(sumFx);
//        addEquation(sumFy);
//        addEquation(sumMp);
    }
}
