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
package edu.gatech.statics.modes.fbd.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import java.math.BigDecimal;

/**
 * Changes the label on a load
 * @author Calvin Ashmore
 */
public class LabelLoad implements DiagramAction<FBDState> {

    AnchoredVector load;
    //String newLabel;
    String symbol;
    BigDecimal value;

    /**
     * Creates a LabelLoad action. This will throw a NumberFormatException if 
     * the label appears to be numeric but cannot be parsed.
     * @param load
     * @param label
     * @throws NumberFormatException
     */
    public LabelLoad(AnchoredVector load, String label) {
        this.load = load;

        if (Character.isDigit(label.charAt(0)) ||
                label.charAt(0) == '.' ||
                label.charAt(0) == '-' ||
                label.charAt(0) == '+') {
            // numerical, try to lop off tail end
            String[] split = label.split(" ");
            value = new BigDecimal(split[0]);
        } else {
            symbol = label;
        }
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        if (symbol != null) {
            builder.setLabel(load, symbol);
        } else {
            builder.setLabel(load, value);
        }
        return builder.build();
    }

    @Override
    public String toString() {
        if (symbol != null) {
            return "LabelLoad [" + load + ", \"" + symbol + "\"]";
        } else {
            return "LabelLoad [" + load + ", " + value + "]";
        }
    }
}
