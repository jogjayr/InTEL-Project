/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
