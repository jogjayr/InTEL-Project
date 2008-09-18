/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 *
 * @author lccstudent
 */
public class DiagramStatePersistenceDelegate extends PersistenceDelegate {

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        // casts the object into a DiagramState
        DiagramState<?> diagramState = (DiagramState<?>) oldInstance;

        // fetch the builder from the state.
        // all DiagramStates can create a builder that will construct a new
        // equivalent state.
        Builder<? extends DiagramState<?>> builder = diagramState.getBuilder();

        Expression expression = new Expression(oldInstance, builder, "build", new String[]{});
        return expression;
    }
}
