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
 * Node.java
 *
 * Created on July 29, 2007, 12:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract class Node {

    /** Creates a new instance of Node */
    public Node() {
    }
    private Node parent;
    /**
     * Getter
     * @return
     */
    public Node getParent() {
        return parent;
    }
    /**
     * Setter
     * @param parent
     */
    protected void setParent(Node parent) {
        this.parent = parent;
    }
    /**
     * Abstract function. Evaluate the node value
     * @return
     */
    abstract BigDecimal evaluate();

    abstract String printout();
    /**
     * Add a child node
     * @param node
     */
    abstract void addChild(Node node);
}
