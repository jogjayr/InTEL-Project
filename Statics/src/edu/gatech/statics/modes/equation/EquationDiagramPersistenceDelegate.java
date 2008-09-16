/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 *
 * @author Trudetski
 */
public class EquationDiagramPersistenceDelegate extends PersistenceDelegate {

    public EquationDiagramPersistenceDelegate() {
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        EquationState state = (EquationState) oldInstance;
        EquationState.Builder builder = new EquationState.Builder(state);

        Expression expression = new Expression(oldInstance, builder, "build", new String[]{});
        return expression;
    }
}
