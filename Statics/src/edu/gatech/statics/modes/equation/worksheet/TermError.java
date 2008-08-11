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
    missedALoad;
}
