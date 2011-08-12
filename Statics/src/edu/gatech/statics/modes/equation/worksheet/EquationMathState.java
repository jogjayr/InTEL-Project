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
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.util.Buildable;

/**
 * 
 * @author Calvin Ashmore
 */
abstract public class EquationMathState implements Buildable<EquationMathState> {

    //final private String name;
    //final private boolean locked;

    abstract public String getName();

    abstract public boolean isLocked() ;

    public EquationMathState() {
//        name = "";
//        locked = false;
    }

//    protected EquationMathState(String name, boolean locked) {
//        this.name = name;
//        this.locked = locked;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final EquationMathState other = (EquationMathState) obj;
//        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
//            return false;
//        }
//        if (this.locked != other.locked) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 11 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 11 * hash + (this.locked ? 1 : 0);
//        return hash;
//    }
//
//    @Override
//    public String toString() {
//        return "EquationMathState: {name=" + name + ", locked=" + locked + "}";
//    }

//    public Builder getBuilder() {
//        return new Builder(this);
//    }
}
