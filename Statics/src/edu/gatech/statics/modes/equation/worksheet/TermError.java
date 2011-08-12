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
package edu.gatech.statics.modes.equation.worksheet;

public enum TermError {

    /**
     * none denotes that there is no error. If a check fails, and the error is
     * none, then something is very very wrong.
     */
    none,
    /**
     * This is an internal error that is a result of some incorrect internal behavior.
     */
    internal,
    /**
     * The term represents something that the system cannot adequately handle
     * for example, multiplying a symbolic force by a symbolic coefficient
     */
    cannotHandle,
    /**
     * The coefficient failed to parse correctly. Right now we can not give more
     * detailed information, but we may use this and decode common user mistakes
     * for example 50sin(80.2) or sin80.2. 
     */
    parse,
    /**
     * The coefficient for a force in the moment equation is missing the inclination
     * of the force, but does have the distance. 
     */
    missingInclination,
    /**
     * The parse simply has the wrong value. Common mistakes are omitting the 
     * distance or force inclination in the moment equation, or just simple 
     * mathematical errors.
     */
    incorrect,
    /**
     * The term is correct, except for the sign.
     */
    badSign,
    /**
     * The term is symbolic when it should not be.
     */
    shouldNotBeSymbolic,
    /**
     * The term is not symbolic when it should be.
     */
    shouldBeSymbolic,
    /**
     * The user has entered the wrong symbol for a symbolic coefficient
     */
    wrongSymbol,
    /**
     * The vector to which this term is attached does not belong in the equation.
     */
    doesNotBelong,
    /**
     * The user has missed a load in the equation
     */
    missedALoad,
    /**
     * User has entered something that uses the wrong units in the moment equation.
     * This is frequently caused by a student using meters when the system expects mm.
     */
    wrongUnits
    ;
}
