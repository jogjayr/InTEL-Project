/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import edu.gatech.statics.exercise.ExerciseUtilities;
import edu.gatech.statics.tasks.Solve2FMTask;
import edu.gatech.statics.tasks.SolveConnectorTask;

/**
 *
 * @author Calvin Ashmore
 */
public class KeyboardExerciseGraded extends KeyboardExercise {

    //private ExerciseLogger logger;
    public KeyboardExerciseGraded() {
        //logger = new ExerciseLogger("loggerPost.php");
        //logger.scheduleLogger();
    }

    @Override
    public void initExercise() {
        super.initExercise();

        setDescription(
                "A keyboard is lying on a keyboard stand. " +
                "The mass of the keyboard is 100N and it is placed evenly on the stand so that the mass is evenly distributed to point A and D. " +
                "At 0.1m above point C which is the center, a horizontal bar PQ is placed connecting two diagonal components. " +
                "Assume there are no horizontal reaction force at E and B. (E and B are rollers) " +
                "C, P, and Q are pin joints. Unit of dimensions is in meters." +
                "Solve for the axial force of the bar PQ. " +
                "Solve for the reaction forces at point E and B. ");
    }

    @Override
    public void loadExercise() {
        super.loadExercise();

//        addTask(new SolveJointTask(jointP));
//        addTask(new SolveJointTask(jointQ));
        addTask(new Solve2FMTask(bar, jointP));
        addTask(new SolveConnectorTask(jointB));
        addTask(new SolveConnectorTask(jointE));
    }

    @Override
    public void onSubmit() {
        trySubmission();
    }

    @Override
    public void finishExercise() {
        super.finishExercise();
        trySubmission();
    }

    private void trySubmission() {
        boolean navigate = ExerciseUtilities.showCompletionPopup();
        if (navigate) {
            //logger.terminate();
            //ExerciseUtilities.navigateToSubmission(studentName, "problemPost.php");
            ExerciseUtilities.navigateAway("exercises.html");
        }
    }
}
