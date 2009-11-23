/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathForces;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jimmy Truesdell
 */
public class Initial2DEquationList {

    final private Map<String, EquationMath> equations = new HashMap<String, EquationMath>();

    public Initial2DEquationList(EquationDiagram diagram) {
        EquationMath sumFx = new EquationMathForces("F[X]", diagram);
        EquationMath sumFy = new EquationMathForces("F[Y]", diagram);
        EquationMath sumMp = new EquationMathMoments("M[P]", diagram);
        equations.put(sumFx.getName(), sumFx);
        equations.put(sumFy.getName(), sumFy);
        equations.put(sumMp.getName(), sumMp);
    }

    public Map<String, EquationMath> getEquations() {
        return equations;
    }
}