/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.ui;

import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMath;


/**
 *
 * @author Jayraj
 */
class Equation3DModePanel extends EquationModePanel  {

  public Equation3DModePanel() {
      super();
      System.out.println("Equation3DModePanel was created");
  }


  protected void addMoment3DEquationRow(EquationMathMoments math) {
      System.out.println("Here we would create the new equation row that has R x F");
  }
    @Override
  public void activate() {
        //super.activate();

        /**
         * The following two lines of code duplicate what is written in the ApplicationModePanel.activate()
         * This is because there is no way to reference that method using super without invoking
         * EquationModePanel.activate (the intermediate class which is the parent of this class)
         *
         * Similar comment has been addded to ApplicationModePanel
         *
         * Ultimate solution may be to make Equation3DModePanel a child of ApplicationModePanel instead of EquationModePanel
         *
         * Start code from ApplicationModePanel.activate()
         */

        active = true;
        stateChanged();

        /* End code from ApplicationModePanel.activate() */
        
        if (!uiMap.isEmpty()) {
            clear();
        }

        EquationDiagram diagram = (EquationDiagram) getDiagram();

        if (diagram.getBodySubset().getSpecialName() != null) {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset().getSpecialName());
        } else {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());
        }

        diagram.getWorksheet().updateEquations();

        for (String mathName : diagram.getWorksheet().getEquationNames()) {

            EquationMath math = diagram.getWorksheet().getMath(mathName);
            if (math instanceof EquationMathMoments) {
                addMoment3DEquationRow((EquationMathMoments)math);
            } else if (math instanceof TermEquationMath) {
                addTermEquationRow((TermEquationMath) math);
                //System.out.println("TermEquationRow added for equation: " + math.getName());
            } else if (math instanceof ArbitraryEquationMath) {
                addArbitraryEquationRow((ArbitraryEquationMath) math);
                //System.out.println("ArbitraryEquationRow added for equation: " + math.getName());
            } else {
                throw new IllegalArgumentException("Unknown math type: " + math);
            }
        }

        // only add the button to create extra rows if the diagram has friction in it.
        if (shouldAddRowCreator()) {
            addRowCreator();
        }

        stateChanged();
        performSolve(false); 

        refreshRows();
        invalidate();
    }

}
