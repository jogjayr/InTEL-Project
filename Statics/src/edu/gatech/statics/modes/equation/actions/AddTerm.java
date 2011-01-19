/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;
import java.util.Map;

/**
 * This adds a term to the worksheet
 * @author Calvin Ashmore
 */
public class AddTerm implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;
    final private String coefficient;
    private AnchoredVector radiusVector; //This is not private because its value needs to be set
                                        //in performAction, where the EquationMathState is passed

    public AddTerm(String equationName, AnchoredVector load, String coefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = coefficient;
        this.radiusVector = null;
    }

    public AddTerm(String equationName, AnchoredVector load, AnchoredVector radiusVector) {
      
        this.equationName = equationName;
        this.load = load;
        this.radiusVector = radiusVector;
        this.coefficient = "";
    }

    public AddTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
       
            // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }

        EquationMathState oldMathState = builder.getEquationStates().get(equationName);
        if(!(oldMathState instanceof Moment3DEquationMathState)) { //Used to check what kind of equation
            //Here found not to be MomentEquationMathState; so either TermEquationMathState (most likely)
            //or ArbitraryEquationMathState (not even sure that exists)
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)(mathState));
            mathBuilder.getTerms().put(load, coefficient);
            builder.putEquationState(mathBuilder.build());
            
        } else { //MomentEquationMathState
            Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState)(mathState));
            Point momentPoint = mathBuilder.getMomentPoint();
            Vector3bd pointOfForceApplication = load.getAnchor().getPosition();
            Vector3bd distanceVector = pointOfForceApplication.subtract(momentPoint.getPosition()) ;//.subtract(momentPoint.getPosition());
            this.radiusVector = new AnchoredVector(momentPoint, new Vector(Unit.distance, distanceVector, new BigDecimal(distanceVector.length())));
            this.radiusVector.setSymbol(momentPoint.getName()+ load.getAnchor().getName());
            mathBuilder.getTerms().put(load, radiusVector);
            builder.putEquationState(mathBuilder.build());
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "AddTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
