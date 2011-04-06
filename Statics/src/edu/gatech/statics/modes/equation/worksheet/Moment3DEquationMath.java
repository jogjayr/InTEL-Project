/*
 * The purpose of this class is to create and solve equations of the R x F format
 * Where TermEquation had a corresponding state (TermEquationMathState) that stored the
 * equation state as a hashmap of string (coefficient) and vector (force), this shall have
 * a corresponding state (MomentEquationMathState) that will store equation state as a
 * hashmap of vector (from point about which moment is calculated to point of application of force)
 * and vector (force acting at that point)
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.actions.LockEquation;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jayraj
 *
 * It is possible to confuse this with EquationMathMoments. This class
 * deals with RxF moment equations. The EquationMathMoments class deals with
 * SigmaMx, SigmaMy and SigmaMz equations
 */
public class Moment3DEquationMath extends EquationMath {

    // this is the name that is used internally, it is not seen by users.
    public static final String DEFAULT_NAME = "R X F";
    protected final String name;
    protected final EquationDiagram diagram;

    public Moment3DEquationMath(String name, EquationDiagram world) {
        super(name, world);
        this.name = name;
        this.diagram = world;
    }

    @Override
    public Moment3DEquationMathState getState() {
        return (Moment3DEquationMathState) super.getState();
    }

    public boolean check() {
        //System.out.println("Checking...");
//        Moment3DEquationMathState state = (Moment3DEquationMathState)getState();
//        Map<AnchoredVector, AnchoredVector> terms = state.getTerms();
//        Vector equationValue = new Vector(Unit.distance, Vector3bd.ZERO, new BigDecimal(0));
//        System.out.println("Before calculation: equationValue = " + equationValue.toString());
//        for(Map.Entry<AnchoredVector, AnchoredVector> term : terms.entrySet()) {
//
//            AnchoredVector force = term.getKey();
//            AnchoredVector radius = term.getValue();
//            Vector3bd temp = radius.getVectorValue().cross(force.getVectorValue());
//            System.out.println(force.toString() + "x" + radius.toString() + " = " + temp.toString());
//            equationValue.setVectorValue(temp.add(equationValue.getVectorValue()));
//            System.out.println("Checking: equationValue = " + equationValue.toString());
//        }
//        if(equationValue.getVectorValue() == Vector3bd.ZERO)
//            return true;
        Moment3DEquationMathState state = (Moment3DEquationMathState) getState();
        List<AnchoredVector> allLoads = diagram.getDiagramLoads();

        // make sure the moment point is set.
        if (state.getMomentPoint() == null) {

            StaticsApplication.logger.info("check: Moment point is not set!");
            StaticsApplication.logger.info("check: FAILED");

            StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_moment_point_not_set");
            return false;
        }

        ArrayList<AnchoredVector> loadsNotThruMomentPoint = new ArrayList<AnchoredVector>();
        //Discarding loads whose vectors pass through the moment point
        ArrayList<AnchoredVector> loadsThruMomentPoint = new ArrayList<AnchoredVector>();
        for (AnchoredVector load : allLoads) {

            if (load.getAnchor() == state.getMomentPoint()) {
                loadsThruMomentPoint.add(load);
            } else {
                loadsNotThruMomentPoint.add(load);
            }


        }

        Set<AnchoredVector> equationLoads = state.getTerms().keySet();

        // go through all the loads that are NOT supposed to be there.
        for (AnchoredVector load : loadsThruMomentPoint) {
            if(equationLoads.contains(load)) {
                StaticsApplication.logger.info("check: equation has unnecessary term: " + load);
                StaticsApplication.logger.info("check: FAILED");
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_unnecessary", load.getVector().getPrettyName(), load.getAnchor().getName());
                return false;
            }
        }

        // go through all forces that ARE supposed to be in the diagram
        for (AnchoredVector load : loadsNotThruMomentPoint) {
            if (!equationLoads.contains(load)) {
                StaticsApplication.logger.info("check: equation has not added all terms"+load);
                StaticsApplication.logger.info("check: FAILED");
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_missing_forces_3d");
                return false;
            }

            String momentArm = state.getTerms().get(load);

            if (momentArm == null) {
                StaticsApplication.logger.info("check: no moment arm for " + load);
                StaticsApplication.logger.info("check: FAILED");
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_no_moment_arm_3d", load.getVector().getPrettyName(), load.getAnchor().getName());
                return false;
            }
            // this is the value that the moment arm String should be, equal to the name of the moment point
            // appended to the name of the anchor
            String comparison = state.getMomentPoint().getName() + load.getAnchor().getName();

            if (!comparison.equalsIgnoreCase(momentArm)) {
                StaticsApplication.logger.info("check: no moment arm for " + load);
                StaticsApplication.logger.info("check: FAILED");
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_no_moment_arm_3d", load.getVector().getPrettyName(), load.getAnchor().getName());
                return false;
            }
        }


        // lock the math.
        LockEquation lockEquationAction = new LockEquation(name, true);
        diagram.performAction(lockEquationAction);

        StaticsApplication.logger.info("check: PASSED!");
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_success");

        // refresh the mode panel.
        // this is a kludgy way to do it, but it will get the job done.
        InterfaceRoot.getInstance().getModePanel(getDiagram().getMode().getModeName()).activate();


        return true;
    }
//    protected TermError checkTerm(AnchoredVector force, AnchoredVector rVector) {
//        return TermError.none;
//    }
}
