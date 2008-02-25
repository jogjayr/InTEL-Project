/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.worksheet;

import com.jme.math.Vector3f;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet2D extends Worksheet {

    public Worksheet2D(EquationDiagram diagram) {
        super(3);
        
        EquationMath sumFx = new EquationMath(diagram);
        EquationMath sumFy = new EquationMath(diagram);
        EquationMath sumMp = new EquationMathMoments(diagram);
        sumFx.setObservationDirection(Vector3f.UNIT_X);
        sumFy.setObservationDirection(Vector3f.UNIT_Y);
        sumMp.setObservationDirection(Vector3f.UNIT_Z);
        
        addEquation(sumFx);
        addEquation(sumFy);
        addEquation(sumMp);
    }

    
}
