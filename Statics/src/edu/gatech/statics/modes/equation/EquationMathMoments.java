/*
 * EquationMoments.java
 *
 * Created on July 26, 2007, 1:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jme.math.Vector3f;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.EquationMath.TermError;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Vector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMathMoments extends EquationMath {
    
    private boolean observationPointSet = false;
    private Vector3f observationPoint = new Vector3f();
    public Vector3f getObservationPoint() {return observationPoint;}
    public void setObservationPoint(Vector3f point) {this.observationPoint = point; observationPointSet = true;}
    public boolean getObservationPointSet() {return observationPointSet;}
    
    String getName() {
        return "M[P]";
    }
    
    protected class MomentTerm extends Term {
        
        MomentTerm(Vector vector) {super(vector);}
        
        boolean check() {
            if(vector.source instanceof Moment) {
                if(!coefficient.parse()) {
                    error = TermError.badCoefficient;
                    return false;
                }
                error = TermError.none;
                return coefficient.getValue() == 1.0f;
            }
            
            // this is a force
            Vector3f vectorOrient = vector.source.getValue().normalize();
            Vector3f distance = vector.source.getAnchor().getTranslation().subtract(observationPoint);
            
            targetValue = -vectorOrient.cross(distance).dot(getObservationDirection());
            targetValue *= StaticsApplication.getApp().getUnits().getWorldDistanceMultiplier();
            
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
    }
    
    Term createTerm(Vector source) {
        return new MomentTerm(source);
    }
    
    /** Creates a new instance of EquationMoments */
    public EquationMathMoments(EquationWorld world) {
        super(world);
    }
    

    boolean check() {
        
        // first, make sure all of the necessary terms are added to the equation.
        List<Force> allForces = new ArrayList();
        List<Moment> allMoments = new ArrayList();
        
        for(SimulationObject obj : getWorld().allObjects()) {
            if(obj instanceof Force && !obj.isDisplayGrayed()) // should not be grayed anyway, but just in case.
                allForces.add((Force) obj);
            if(obj instanceof Moment && !obj.isDisplayGrayed())
                allMoments.add((Moment) obj);
        }
        
        for(Force force : allForces) {
            Term term = getTerm(force);
            
            // clear off things that would not add via cross product
            float contribution = force.getValue().cross( force.getAnchor().getTranslation().subtract(getObservationPoint()) ).length();
            
            if(contribution == 0)
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
                        "It must contain all forces about the observation point.");
                return false;
            }
        }
        
        for(Moment moment : allMoments) {
            
            Term term = getTerm(moment);
            if(term == null) {
                System.out.println("check: equation has not added all terms");
                System.out.println("check: FAILED");
                
                StaticsApplication.getApp().setAdvice(
                        "Equilibrium Check: Your equation is not yet correct. " +
                        "It must contain couples visible in the system");
                return false;
            }
        }
        
        
        for(Term term : allTerms()) {
            if(!term.check()) {
                
                System.out.println("check: term does not evaluate correctly");
                
                switch(term.error) {
                    case none:
                        // ??? should not be here
                        System.out.println("check: unknown error?");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdvice(
                                "Equilibrium Check: Your equation is not yet correct. " +
                                "Unknown term error.");
                        return false;
                    case badCoefficient:
                        System.out.println("check: bad coefficient");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdvice(
                                "Equilibrium Check: " +
                                "You should not have a coefficient on a moment.");
                        return false;
                    case parse:
                        System.out.println("check: for "+term.getVector().getLabelText());
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
                                "Your coefficient is not correct for "+term.getVector().getLabelText());
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
