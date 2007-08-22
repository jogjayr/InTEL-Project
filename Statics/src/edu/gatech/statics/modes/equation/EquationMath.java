/*
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jme.math.Vector3f;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.parser.Parser;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMath {
    
    protected static final float accuracy = .01f;
    
    private boolean locked = false;
    void setLocked(boolean locked) {this.locked = locked;}
    boolean isLocked() {return locked;}
    
    private EquationWorld world;
    protected EquationWorld getWorld() {return world;}
    
    private Vector3f observationDirection;
    public Vector3f getObservationDirection() {return observationDirection;}
    public void setObservationDirection(Vector3f direction) {this.observationDirection = direction;}
    
    private Map<Vector, Term> terms = new HashMap();
    List<Term> allTerms() {return new ArrayList(terms.values());}
    
    String getName() {
        if(observationDirection.dot(Vector3f.UNIT_X) != 0)
            return "F[X]";
        else return "F[Y]";
    }
    
    abstract protected class Element {
        abstract String getText();
        abstract boolean isKnown();
    }
    
    protected class VectorElement extends Element {
        VectorElement(Vector source) {this.source = source;}
        
        final Vector source;
        String getText() {return source.getLabelText();}
        boolean isKnown() {return !source.isSymbol();}
    }
    
    protected class CoefficientElement extends Element {
        CoefficientElement() {expression = "";}
        
        private String expression;
        float value;
        
        void setText(String text) {
            expression = text.trim();
        }
        String getText() {return expression;}
        boolean parse() {
            if(expression.equals("")) {
                value = 1.0f;
                return true;
            }
            value = Parser.evaluate(expression);
            return !Float.isNaN(value);
        }
        
        // only works if quantity is known.
        float getValue() {
            if(parse()) return value;
            else return Float.NaN;
        }
        boolean isKnown() {return true;} // may wish to change this later on...
    }
    
    protected enum TermError {
        none, parse, incorrect, badCoefficient
    };
    
    protected class Term {
        
        Term(Vector source) {
            vector = new VectorElement(source);
            coefficient = new CoefficientElement();
        }
        
        void setCoefficientText(String s) {
            coefficient.setText(s);
        }
        
        final VectorElement vector;
        final CoefficientElement coefficient;
        
        TermError error;
        float coefficientValue;
        float targetValue;
        
        boolean check() {
            Vector3f vectorOrient = vector.source.getValue().normalize();
            targetValue = vectorOrient.dot(observationDirection);
            
            if(!coefficient.parse()) {
                error = TermError.parse;
                return false;
            }
            
            coefficientValue = coefficient.getValue();
            
            if (Math.abs(coefficientValue - targetValue) < accuracy) {
                error = TermError.none;
                return true;
            } else {
                error = TermError.incorrect;
                return false;
            }
        }

        Vector getVector() {return vector.source;}
        String getCoefficient() {return coefficient.expression;}
    }
    
    void setCoefficient(Vector target, String coefficientExpression) {
        getTerm(target).coefficient.setText(coefficientExpression);
    }
    
    Term createTerm(Vector source) {
        return new Term(source);
    }
    
    Term getTerm(Vector target) {return terms.get(target);}
    
    Term addTerm(Vector source) {
        if(terms.get(source) != null)
            return getTerm(source);
        
        terms.put(source, createTerm(source));
        return getTerm(source);
    }

    void removeTerm(Vector target) {
        terms.remove(target);
    }
    
    /** Creates a new instance of Equation */
    public EquationMath(EquationWorld world) {
        this.world = world;
    }
    
    /**
     * Returns a map of String symbol names to floats according to their values.
     * Will return null if the set of equations is not deemed solvable.
     */
    static Map<String, Float> solve(EquationMath ... equations) {
        
        return null;
    }

    boolean check() {
        
        // first, make sure all of the necessary terms are added to the equation.
        List<Force> allForces = new ArrayList();
        for(SimulationObject obj : world.allObjects()) {
            if(obj instanceof Force && !obj.isDisplayGrayed()) // should not be grayed anyway, but just in case.
                allForces.add((Force) obj);
        }
        
        for(Force force : allForces) {
            Term term = terms.get(force);
            
            if(force.getValue().dot(getObservationDirection()) == 0)
                if(term != null) {
                    System.out.println("check: equation has unnecessary term");
                    System.out.println("check: FAILED");

                    StaticsApplication.getApp().setAdvice(
                            "Equilibrium Check: Your equation is not yet correct. " +
                            "You have added a force that doesn't belong in the equation.");
                    return false;
                } else
                    continue;
            
            if(term == null) {
                System.out.println("check: equation has not added all terms");
                System.out.println("check: FAILED");
                
                StaticsApplication.getApp().setAdvice(
                        "Equilibrium Check: Your equation is not yet correct. " +
                        "It must contain all forces in the direction of observation.");
                return false;
            }
        }
        
        for(Term term : allTerms()) {
            
            if(term.getVector() instanceof Moment) {
                System.out.println("check: equation has unnecessary moment term");
                System.out.println("check: FAILED");
                
                StaticsApplication.getApp().setAdvice(
                        "Equilibrium Check: Your equation is not yet correct. " +
                        "You do not add moments to the force equations.");
            }
            
            if(!term.check()) {
                
                System.out.println("check: term does not evaluate correctly");
                
                switch(term.error) {
                    case none:
                    case badCoefficient:
                        // ??? should not be here
                        System.out.println("check: unknown error?");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdvice(
                                "Equilibrium Check: Your equation is not yet correct. " +
                                "Unknown term error.");
                        return false;
                    case parse:
                        System.out.println("check: parse error");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdvice(
                                "Equilibrium Check: " +
                                "I can't understand your coefficient: \""+term.getCoefficient()+"\"");
                        return false;
                    case incorrect:
                        System.out.println("check: for "+term.getVector().getLabelText());
                        System.out.println("check: incorrect value: "+term.coefficientValue);
                        System.out.println("check: should be: "+term.targetValue);
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdvice(
                                "Equilibrium Check: " +
                                "Your coefficient is not correct for ("+term.getVector().getLabelText()+")");
                        return false;
                }
                
            }
        }
        
        System.out.println("check: PASSED!");
        StaticsApplication.getApp().setAdvice(
                "Equilibrium Check: Contratulations! " +
                "Your equation is correct! Now you can finish the other equations and solve for the unknowns.");
        return true;
    }
}