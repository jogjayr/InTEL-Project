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
 * ConstantNode.java
 *
 * Created on July 29, 2007, 1:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 * Represents a node which is a constant value
 * @author Calvin Ashmore
 */
class ConstantNode extends Node {

    private BigDecimal value;
    /**
     * Getter
     * @return
     */
    BigDecimal evaluate() {
        return value;
    }

    String printout() {
        return "" + value;
    }
    /**
     * Can't add a child since this is a constant value node
     * @param node
     */
    void addChild(Node node) {
        throw new UnsupportedOperationException();
    }

    /** 
     * Creates a new instance of ConstantNode 
     */
    public ConstantNode(BigDecimal value) {
        this.value = value;
    }
    /**
     * Constructor
     * @param value
     */
    public ConstantNode(float value) {
        this.value = new BigDecimal(value);
    }
}
