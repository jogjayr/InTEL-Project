/*
 * EquationMoments.java
 *
 * Created on July 26, 2007, 1:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.equation.worksheet.EquationMath.TermError;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
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
    
    @Override
    public String getName() {
        return "M[P]";
    }
    
    protected class MomentTerm extends Term {
        
        private Point anchor;
        
        MomentTerm(Vector vector) {
            super(vector);
            anchor = getWorld().getLoad(vector).getAnchor();
        }
        
        @Override
        boolean check() {
            /*if(vector.source instanceof Moment) {
                if(!coefficient.parse()) {
                    error = TermError.badCoefficient;
                    return false;
                }
                error = TermError.none;
                coefficientValue = coefficient.getValue();
                return coefficientValue == 1.0f || coefficientValue == -1.0f;
            }*/
            
            if(getSource().getUnit() == Unit.moment) {
                
                // this is a moment
                Vector3f vectorOrient = getSource().getVectorValue();
                targetValue = vectorOrient.dot(getObservationDirection());
                
            } else {
            
                // this is a force
                Vector3f vectorOrient = getSource().getVectorValue();
                Vector3f distance = anchor.getTranslation().subtract(observationPoint);

                targetValue = -vectorOrient.cross(distance).dot(getObservationDirection());
                //targetValue *= StaticsApplication.getApp().getWorldScale();
            }
            
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
    
    @Override
    public Term createTerm(Vector source) {
        return new MomentTerm(source);
    }
    
    /** Creates a new instance of EquationMoments */
    public EquationMathMoments(EquationDiagram world) {
        super(world);
    }
    

    @Override
    public boolean check() {
        
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
            Term term = getTerm(force.getVector());
            
            // clear off things that would not add via cross product
            float contribution = force.getVectorValue().cross( force.getAnchor().getTranslation().subtract(getObservationPoint()) ).length();
            contribution *= force.getVector().doubleValue();
            
            if(contribution == 0)
                if(term != null) {
                    System.out.println("check: equation has unnecessary term: "+term.getSource());
                    System.out.println("check: FAILED");

                    StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unnecessary");
                    return false;
                } else
                    continue;
            
            if(term == null) {
                System.out.println("check: equation has not added all terms: "+force.getVector());
                System.out.println("check: FAILED");
                
                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_missing_moments");
                return false;
            }
        }
        
        for(Moment moment : allMoments) {
            
            Term term = getTerm(moment.getVector());
            if(term == null) {
                System.out.println("check: equation has not added all terms: "+moment.getVector());
                System.out.println("check: FAILED");
                
                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_couples");
                return false;
            }
        }
        
        
        for(Term term : allTerms()) {
            if(!term.check()) {
                
                System.out.println("check: term does not evaluate correctly: "+term.getCoefficient());
                
                switch(term.error) {
                    case none:
                        // ??? should not be here
                        System.out.println("check: unknown error?");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unknown");
                        return false;
                    case badCoefficient:
                        System.out.println("check: bad coefficient");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_momentCoefficient");
                        return false;
                    case parse:
                        System.out.println("check: for "+term.getSource());//.getLabelText());
                        System.out.println("check: parse error");
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_parse", term.getCoefficient());
                                //"Note: I can't understand your coefficient: \""+term.getCoefficient()+"\"");
                        return false;
                    case incorrect:
                        System.out.println("check: for "+term.getSource());//.getLabelText());
                        System.out.println("check: incorrect value: "+term.coefficientValue);
                        System.out.println("check: should be: "+term.targetValue);
                        System.out.println("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_coefficient", term.getSource());//.getLabelText()));
                                //"Note: Your coefficient is not correct for "+term.getVector().getLabelText());
                        return false;
                }
            }
        }
        
        setLocked(true);
        
        System.out.println("check: PASSED!");
        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_success");
        return true;
    }
}
