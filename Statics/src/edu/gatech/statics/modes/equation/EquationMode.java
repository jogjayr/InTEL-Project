/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Load;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMode extends Mode {

    public static final EquationMode instance = new EquationMode();

    @Override
    public String getModePanelName() {
        return EquationModePanel.panelName;
    }

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        BodySubset bodies = (BodySubset) key;
        return StaticsApplication.getApp().getExercise().getEquationDiagram(bodies);
    }

    /**
     * Here we override postLoad to check that the FBD is still OK. If the FBD
     * needs to be updated because another diagram was solved, then the equation
     * mode will detect this change and force the user to update their diagram.
     * Here, postLoad will return the user to the FBDMode if changes need to be made.
     * @param key
     */
    @Override
    public void postLoad(DiagramKey key) {

        FreeBodyDiagram fbd = Exercise.getExercise().getFreeBodyDiagram((BodySubset) key);

        FBDChecker fbdChecker = fbd.getChecker();
        fbdChecker.setVerbose(false);

        /*System.out.println("****************");
        System.out.println("*** postLoad ***");
        System.out.println("****************");
        System.out.println(Exercise.getExercise().getSymbolManager().getSymbols());
        for (Load load : Exercise.getExercise().getSymbolManager().allLoads()) {
            System.out.println("  " + load);
        }*/

        // okay, the check fails, no we load up the fbd mode.
        if (!fbdChecker.checkDiagram()) {
            fbd.setSolved(false);
            FBDMode.instance.load(key);
        }
    }
}
