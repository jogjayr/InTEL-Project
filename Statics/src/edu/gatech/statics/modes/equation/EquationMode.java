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

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof BodySubset) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get an EquationDiagram with a key that is not a BodySubset: " + key);
        }
    }

//    @Override
//    public void preLoad(DiagramKey key) {
//        EquationDiagram eq = (EquationDiagram) Exercise.getExercise().getDiagram(key, getDiagramType());
//    }

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
        EquationDiagram eq = (EquationDiagram) Exercise.getExercise().getDiagram(key, getDiagramType());

        FBDChecker fbdChecker = fbd.getChecker();
        fbdChecker.setVerbose(false);

        // okay, the check fails, now we clear check flags and
        // load up the fbd mode.
        if (!eq.isLocked() && !fbdChecker.checkDiagram()) {

            boolean fbdWasSolved = fbd.isSolved();
            //System.out.println("*** wasSolved: " + fbdWasSolved);

            fbd.setSolved(false);
            eq.resetSolve();
            FBDMode.instance.load(key);

            if (fbdWasSolved) {
                FBDRedirectPopup popup = new FBDRedirectPopup(key);
                popup.popup(0, 0, true);
                popup.center();
            }
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
