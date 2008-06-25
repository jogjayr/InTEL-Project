
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
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.VectorObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMath {

    protected static final float TEST_ACCURACY = .01f;
    private boolean locked = false;

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }
    private EquationDiagram world;

    public EquationDiagram getWorld() {
        return world;
    }
    private Vector3bd observationDirection;

    public Vector3bd getObservationDirection() {
        return observationDirection;
    }

    public void setObservationDirection(Vector3bd direction) {
        this.observationDirection = direction;
    }
    private Map<VectorObject, Term> terms = new HashMap();

    public List<Term> allTerms() {
        return new ArrayList(terms.values());
    }

    public String getName() {
        if (observationDirection.dot(Vector3bd.UNIT_X).floatValue() != 0) {
            return "F[X]";
        } else {
            return "F[Y]";
        }
    }

    public String getAxis() {
        if (observationDirection.dot(Vector3bd.UNIT_X).floatValue() != 0) {
            return "X";
        } else {
            return "Y";
        }
    }

    public void setCoefficient(VectorObject target, String coefficientExpression) {
        getTerm(target).coefficient.setText(coefficientExpression);
    }

    public Term createTerm(VectorObject source) {
        return new Term(source, this);
    }

    public Term getTerm(VectorObject target) {
        return terms.get(target);
    }

    public Term addTerm(VectorObject source) {
        if (terms.get(source) != null) {
            return getTerm(source);
        }

        terms.put(source, createTerm(source));
        return getTerm(source);
    }

    public void removeTerm(VectorObject target) {
        //terms.remove(world.getLoad(target));
        terms.remove(target);
    }

    /** Creates a new instance of Equation */
    public EquationMath(EquationDiagram world) {
        this.world = world;
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
        List<Force> allForces = new ArrayList();
        for (SimulationObject obj : world.allObjects()) {
            if (obj instanceof Force && !obj.isDisplayGrayed()) {
                // should not be grayed anyway, but just in case.
                allForces.add((Force) obj);
            }
        }

        Logger.getLogger("Statics").info("check: allForces: " + allForces);

        for (Force force : allForces) {
            Term term = terms.get(force);

            // is this force aligned with our observation direction, even slightly?
            // if no, complain.
            // this condition checks to see if the dot product with the observation
            // direction is close enough to zero to be inappropriate.
            if (Math.abs(force.getVectorValue().dot(getObservationDirection()).floatValue()) <= TEST_ACCURACY) {
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
                Logger.getLogger("Statics").info("check: equation has not added all terms: " + force.getVector());
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
}
