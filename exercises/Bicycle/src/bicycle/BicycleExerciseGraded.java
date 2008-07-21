/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicycle;

import edu.gatech.statics.exercise.ExerciseLogger;
import edu.gatech.statics.exercise.ExerciseUtilities;
import edu.gatech.statics.tasks.Solve2FMTask;
import edu.gatech.statics.tasks.SolveConnectorTask;

/**
 *
 * @author Calvin Ashmore
 */
public class BicycleExerciseGraded extends BicycleExercise {

    //private String studentName = "No Name";
    //private ExerciseLogger logger;

    public BicycleExerciseGraded() {
        //logger = new ExerciseLogger("loggerPost.php");
        //logger.scheduleLogger();
    }

    @Override
    public void initExercise() {
        super.initExercise();

        setDescription(
                "Tara is sitting on the bike but it is not moving because there is a stopper at the front wheel. " +
                "Because of her weight, 500N and 150N are exerted to K and G respectively, which are the location of seat and pedal. Also she is slightly leaning toward the handle by 20N with 30 degrees angle. She is rotating the handle counter clockwise with 5Nm moment. " +
                "Assume that member AI and GK are beam and member FG, GB, HJ, and JB are two force members. (Note that two force members are pin connected to other structures.) " +
                "Since node A and B are connected to wheels, let’s simplify the bicycle and consider these as roller supports with a stopper at node A.");
    }

    @Override
    public void loadExercise() {
        super.loadExercise();

        addTask(new SolveConnectorTask(pinA));
        addTask(new SolveConnectorTask(rollerB));
        addTask(new Solve2FMTask(topBar, twoForceJH));
        addTask(new Solve2FMTask(frontBar, twoForceGF));
        addTask(new Solve2FMTask(backBar, twoForceJB));
        addTask(new Solve2FMTask(bottomBar, twoForceGB));
    }

    @Override
    public void postLoadExercise() {
        //studentName = ExerciseUtilities.showNamePopup();
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