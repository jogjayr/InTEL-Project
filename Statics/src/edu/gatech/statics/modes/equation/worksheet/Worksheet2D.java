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
        super(3);
        
        EquationMath sumFx = new EquationMath("F[X]",diagram);
        EquationMath sumFy = new EquationMath("F[X]",diagram);
        EquationMath sumMp = new EquationMathMoments("M[P]",diagram);
        sumFx.setObservationDirection(Vector3bd.UNIT_X);
        sumFy.setObservationDirection(Vector3bd.UNIT_Y);
        sumMp.setObservationDirection(Vector3bd.UNIT_Z);
        
        addEquation(sumFx);
        addEquation(sumFy);
        addEquation(sumMp);
    }

    
}
