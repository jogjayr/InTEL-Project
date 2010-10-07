/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ladderDrill;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.description.Description;


/**
 *
 * @author vignesh
 */
public class LadderDrillExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Lori Jones has recently started her own engineering firm and is trying to hang up " +
                "a sign in front of her business.  The front of the building is brick and she needs to drill some holes " +
                "into the brick to hang the sign up.  She leans a 5 m ladder up against the building and the ladder makes " +
                "an angle of 70° with the ground. She then climbs up the ladder and begins to drill a hole with a power drill.  " +
                "The force from the power drill is a strictly horizontal force.");

        description.setProblemStatement("She weighs 550 N, which acts at point D, and the weight of the ladder is 200 N, which acts " +
                "at the midpoint (point C) of the ladder.");

        description.setGoals("Draw a free body diagram of the ladder. Find the maximum force she can apply with the power drill before" +
                "the ladder begins to slip.");

        description.addImage("ladderDrill/assets/ladderDrill.png");

        return description();


    }

}
