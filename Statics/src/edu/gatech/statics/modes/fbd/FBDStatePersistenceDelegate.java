/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 *
 * @author Trudetski
 */
public class FBDStatePersistenceDelegate extends PersistenceDelegate {

    public FBDStatePersistenceDelegate() {
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        FBDState state = (FBDState) oldInstance;
        FBDState.Builder builder = new FBDState.Builder(state);

        Expression expression = new Expression(oldInstance, builder, "build", new String[]{});
        return expression;
    }
}
