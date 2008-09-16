/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.select;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 *
 * @author Trudetski
 */
public class SelectStatePersistenceDelegate extends PersistenceDelegate {
    public SelectStatePersistenceDelegate() {
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        SelectState state = (SelectState) oldInstance;
        SelectState.Builder builder = new SelectState.Builder(state);

        Expression expression = new Expression(oldInstance, builder, "build", new String[]{});
        return expression;
    }
}
