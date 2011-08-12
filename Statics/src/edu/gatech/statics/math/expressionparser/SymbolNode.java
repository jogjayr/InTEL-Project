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
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
class SymbolNode extends Node {

    private String symbolName;
    /**
     * Getter
     * @return
     */
    public String getSymbolName() {
        return symbolName;
    }
    /**
     * Constructor
     * @param symbolName
     */
    public SymbolNode(String symbolName) {
        this.symbolName = symbolName;
    }
    /**
     * Symbol nodes cannot be evaluated, since there is no operator or value
     * @return
     */
    @Override
    BigDecimal evaluate() {
        throw new UnsupportedOperationException("Cannot evaluate symbol \"" + symbolName + "\"");
    }
    /**
     * 
     * @return
     */
    @Override
    String printout() {
        return symbolName;
    }
    /**
     * Symbol nodes cannot have children so this operation is unsupported
     * @param node
     */
    @Override
    void addChild( Node node) {
        throw new UnsupportedOperationException();
    }
}
