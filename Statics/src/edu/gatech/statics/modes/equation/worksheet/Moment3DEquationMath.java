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

        ArrayList<AnchoredVector> loadsNotThruMomentPoint = new ArrayList<AnchoredVector>();
        //Discarding loads whose vectors pass through the moment point
        for (AnchoredVector load : allLoads) {

            if (load.getAnchor() != state.getMomentPoint()) {
                loadsNotThruMomentPoint.add(load);
            }

        }
        if (allLoads.isEmpty()) {
            return false;
        }
        Map<AnchoredVector, String> terms = state.getTerms();
        for (AnchoredVector load : loadsNotThruMomentPoint) {
            //state.getTerms().get(load);

            if (!terms.containsKey(load)) {
                return false;
            }

            String momentArm = state.getTerms().get(load);

            if (momentArm == null) {
                return false;
            }
            // this is the value that the moment arm String should be, equal to the name of the moment point
            // appended to the name of the anchor
            String comparison = state.getMomentPoint().getName() + load.getAnchor().getName();

            if (!comparison.equalsIgnoreCase(momentArm)) {
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
*********

        return true;
    }

//    protected TermError checkTerm(AnchoredVector force, AnchoredVector rVector) {
//        return TermError.none;
//    }
}
