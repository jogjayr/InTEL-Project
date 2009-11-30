/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
import java.math.BigDecimal;

/**
 * This denotes an object which has a quantity assocaited with it.
 * @author Calvin Ashmore
 */
public class ConstantObject extends SimulationObject implements ResolvableByName {

    private Quantity quantity;

    public Quantity getQuantity() {
        return quantity;
    }

    @Deprecated
    public ConstantObject(String name) {
        setName(name);
    }

    /**
     * Creates a KNOWN constant
     * @param name
     * @param value
     * @param unit
     */
    public ConstantObject(String name, BigDecimal value, Unit unit) {
        quantity = new Quantity(unit, value);
        quantity.setSymbol(name);
        quantity.setKnown(true);
        quantity.setDiagramValue(value);

        setName(name);
        Exercise.getExercise().getSymbolManager().addSymbol(this);
    }

    /**
     * Creates an UNKNOWN symbolic constant
     * @param name
     * @param value
     * @param unit
     */
    public ConstantObject(String name, Unit unit) {
        quantity = new Quantity(unit, name);

        setName(name);
        Exercise.getExercise().getSymbolManager().addSymbol(this);
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        // no representation
    }
}
