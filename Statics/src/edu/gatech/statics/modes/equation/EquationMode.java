/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.bodies.TwoForceMember;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMode extends Mode {

    public static final EquationMode instance = new EquationMode();

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof BodySubset) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get an EquationDiagram with a key that is not a BodySubset: " + key);
        }
    }

    @Override
    public void preLoad(DiagramKey key) {
        EquationDiagram eq = (EquationDiagram) Exercise.getExercise().getDiagram(key, getDiagramType());
    //EquationDiagram eq = Exercise.getExercise().getEquationDiagram((BodySubset) key);
    //eq.getWorksheet().updateEquations();
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

        FreeBodyDiagram fbd = (FreeBodyDiagram) Exercise.getExercise().getDiagram(key, FBDMode.instance.getDiagramType());
        //Exercise.getExercise().getFreeBodyDiagram((BodySubset) key);
        EquationDiagram eq = (EquationDiagram) Exercise.getExercise().getDiagram(key, getDiagramType());
        //Exercise.getExercise().getEquationDiagram((BodySubset) key);

        FBDChecker fbdChecker = fbd.getChecker();
        fbdChecker.setVerbose(false);
        //eq.getWorksheet().updateEquations();
        /*System.out.println("****************");
        System.out.println("*** postLoad ***");
        System.out.println("****************");
        System.out.println(Exercise.getExercise().getSymbolManager().getSymbols());
        for (Load load : Exercise.getExercise().getSymbolManager().allLoads()) {
        System.out.println("  " + load);
        }*/

        // okay, the check fails, now we clear check flags and
        // load up the fbd mode.
        if (!fbdChecker.checkDiagram()) {
            fbd.setSolved(false);
            //eq.getWorksheet().resetSolve();
            eq.stateChanged();
            FBDMode.instance.load(key);

            FBDRedirectPopup popup = new FBDRedirectPopup(key);
            popup.popup(0, 0, true);
            popup.center();
        }
        //warning that gets displayed if the user gets to the equation mode
        //of a 2FM. Maybe this should be shown only once?
        if (fbd.getBodySubset().getBodies().size() == 1) {
            for (Body b : fbd.getBodySubset().getBodies()) {
                if (b instanceof TwoForceMember) {
                    EquationMode2FMPopup popup = new EquationMode2FMPopup();
                    popup.popup(0, 0, true);
                    popup.center();
                }
            }
        }
    }

    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("equation", 300);
    }
}
