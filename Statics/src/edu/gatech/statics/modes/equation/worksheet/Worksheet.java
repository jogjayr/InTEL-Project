/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet {

    private List<EquationMath> equations = new ArrayList<EquationMath>();

    public List<EquationMath> getEquations() {
        return Collections.unmodifiableList(equations);
    }

    protected void addEquation(EquationMath math) {
        equations.add(math);
    }
}
