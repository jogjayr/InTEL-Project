
/*
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Load;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * EquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
public class EquationMath {

    protected static final float TEST_ACCURACY = .02f;
    //private boolean locked = false;
    private String name;
    private Vector3bd observationDirection;
    
    /*public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }*/
    private EquationDiagram diagram;

    public EquationDiagram getDiagram() {
        return diagram;
    }

    public Vector3bd getObservationDirection() {
        return observationDirection;
    }

    /*public void setObservationDirection(Vector3bd direction) {
        this.observationDirection = direction;
    }*/
    private Map<AnchoredVector, Term> terms = new HashMap();

    public List<Term> allTerms() {
        return new ArrayList(terms.values());
    }

    public String getName() {
        return name;
    }
    
    /*public String getName() {
        if (observationDirection.dot(Vector3bd.UNIT_X).floatValue() != 0) {
            return "F[X]";
        } else {
            return "F[Y]";
        }
    }*/

    public String getAxis() {
        if (observationDirection.dot(Vector3bd.UNIT_X).floatValue() != 0) {
            return "X";
        } else {
            return "Y";
        }
    }

    /*public void setCoefficient(AnchoredVector target, String coefficientExpression) {
        getTerm(target).coefficient.setText(coefficientExpression);
    }*/

    Term createTerm(AnchoredVector source) {
        return new Term(source, this);
    }

    Term getTerm(AnchoredVector target) {
        return terms.get(target);
    }

    /*public Term addTerm(AnchoredVector source) {
        if (terms.get(source) != null) {
            return getTerm(source);
        }

        terms.put(source, createTerm(source));
        return getTerm(source);
    }*/

    /*public void removeTerm(AnchoredVector target) {
        //terms.remove(world.getLoad(target));
        terms.remove(target);
    }*/

    /** Creates a new instance of Equation */
    public EquationMath(String name, EquationDiagram world) {
        this.name = name;
        this.diagram = world;
    }

    /**
     * update method works to update the terms in the equation,
     * making sure that the the math correctly reflects the diagram.
     * This method will also remove forces that are present in the terms, but
     * no longer exist in the diagram.
     */
    void update() {
        // this will need to be overridden by EquationMathMoments, or 
        // alternately have getDiagramForces turn into getDiagramLoads
        List<Load> loads = getDiagramLoads();

        // make sure that terms are up to date
        // use a list because the set would be backed by the map, and then cleared
        List<Entry<AnchoredVector, Term>> allTerms =
                new ArrayList<Entry<AnchoredVector, Term>>(terms.entrySet());
        terms.clear();
        for (Entry<AnchoredVector, Term> entry : allTerms) {

            if (loads.contains(entry.getKey())) {
                terms.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Returns a map of String symbol names to floats according to their values.
     * Will return null if the set of equations is not deemed solvable.
     */
    //static Map<String, Float> solve(EquationMath... equations) {
    //    return null;
    //}
    public boolean check() {

        // first, make sure all of the necessary terms are added to the equation.
        List<Load> allLoads = getDiagramLoads();

        Logger.getLogger("Statics").info("check: allForces: " + allLoads);

        for (Load load : allLoads) {

            // ignore moments that may appear
            if (load.getUnit() == Unit.moment) {
                continue;
            }
            Term term = terms.get(load);

            // is this force aligned with our observation direction, even slightly?
            // if no, complain.
            // this condition checks to see if the dot product with the observation
            // direction is close enough to zero to be inappropriate.
            if (Math.abs(load.getVectorValue().dot(getObservationDirection()).floatValue()) <= TEST_ACCURACY) {
                if (term != null) {
                    Logger.getLogger("Statics").info("check: equation has unnecessary term: " + term.getSource());
                    Logger.getLogger("Statics").info("check: FAILED");

                    StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unnecessary", term.getSource().getVector().getPrettyName());
                    return false;
                } else {
                    continue;
                }
            }

            // a term that we expected has not been added.
            if (term == null) {
                Logger.getLogger("Statics").info("check: equation has not added all terms: " + load.getVector());
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_missing_forces", getAxis());
                return false;
            }
        }

        for (Term term : allTerms()) {

            //if(term.getVector() instanceof Moment) {
            if (term.getSource().getUnit() == Unit.moment) {
                Logger.getLogger("Statics").info("check: equation has unnecessary moment term: " + term.getSource());
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unnecessaryMoment", term.getSource().getVector().getPrettyName());
                return false;
            }

            if (!term.check()) {

                Logger.getLogger("Statics").info("check: term does not evaluate correctly: " + term.getCoefficient());
                Logger.getLogger("Statics").info("check: for vector: \"" + term.getSource().toString() + "\"");
                Logger.getLogger("Statics").info("check: evaluates to: " + (term.coefficientAffineValue == null ? term.coefficientValue : term.coefficientAffineValue));
                Logger.getLogger("Statics").info("check: should be: " + (term.targetValue == null ? term.targetAffineValue : term.targetValue));

                switch (term.error) {
                    case none:
                    case internal:
                    case shouldBeSymbolic:
                    case wrongSymbol:
                    case missingInclination:
                        // ??? should not be here
                        Logger.getLogger("Statics").info("check: unknown error?");
                        Logger.getLogger("Statics").info("check: got inappropriate error code: " + term.error);
                        Logger.getLogger("Statics").info("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unknown");
                        return false;

                    case cannotHandle:
                        Logger.getLogger("Statics").info("check: cannot handle term");
                        Logger.getLogger("Statics").info("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_cannot_handle", term.getCoefficient(), term.getSource().getVector().getPrettyName());
                        return false;

                    case shouldNotBeSymbolic:
                        Logger.getLogger("Statics").info("check: should not be symbolic");
                        Logger.getLogger("Statics").info("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_should_not_be_symbolic", term.getSource().getVector().getPrettyName());
                        return false;

                    case badSign:
                        Logger.getLogger("Statics").info("check: wrong sign");
                        Logger.getLogger("Statics").info("check: FAILED");
                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_wrong_sign", term.getSource().getVector().getPrettyName());
                        return false;

                    case parse:
                        Logger.getLogger("Statics").info("check: parse error");
                        Logger.getLogger("Statics").info("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_parse", term.getCoefficient(), term.getSource().getVector().getPrettyName());
                        return false;

                    case incorrect:
                        Logger.getLogger("Statics").info("check: term is incorrect");
                        Logger.getLogger("Statics").info("check: FAILED");

                        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_coefficient", term.getCoefficient(), term.getSource().getVector().getPrettyName());
                        return false;
                }

            }
        }

        setLocked(true);

        Logger.getLogger("Statics").info("check: PASSED!");
        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_success");
        return true;
    }

    protected List<Load> getDiagramLoads() {
        List<Load> allLoads = new ArrayList();
        for (SimulationObject obj : diagram.allObjects()) {
            if (obj instanceof Load && !obj.isDisplayGrayed()) {
                // should not be grayed anyway, but just in case.
                allLoads.add((Load) obj);
            }
        }

        return allLoads;
    }
}
