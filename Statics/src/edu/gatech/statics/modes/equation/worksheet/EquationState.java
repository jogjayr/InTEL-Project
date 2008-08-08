/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import java.util.List;

/**
 * 
 * @author Calvin Ashmore
 */
final public class EquationState {

    final private String name;
    final private List<TermState> terms;
    
    public String getName() {
        return name;
    }

    private EquationState(Builder builder) {
        this.name = builder.getName();
    }

    public class Builder implements edu.gatech.statics.util.Builder<EquationState> {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public EquationState build() {
            return new EquationState(this);
        }
    }
}
