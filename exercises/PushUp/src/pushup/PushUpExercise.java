/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pushup;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.description.Description;




/**
 *
 * @author vignesh
 */
public class PushUpExercise extends OrdinaryExercise{

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Michael and Kathy are doing a push-up exercise.  " +
                "Michael wants to strengthen his arms and have an intense workout " +
                "while Kathy wants to have a light workout.  There are three different " +
                "types of push-ups that they can do which involve shifting the person’s " +
                "center of gravity.  The first is a standard push-up, the second is a knee " +
                "push-up, and the third is an elevated push-up.");

        description.setProblemStatement("As you know from your statics class, the center of gravity " +
                "of a person may change when the orientation of the person changes.  The horizontal " +
                "locations of center of gravity for each style of push-up are described on each diagram " +
                "as proportions.  Both Michael and Kathy have a mass of 60 kg.  Assume that they push the" +
                "ground vertically, not horizontally, so you can neglect friction.");

        description.setGoals("Solve for the reactions at their hands and feet. Then pick the exercise that " +
                "Michael would likely do and pick one that Kathy is likely to do");


        description.addImage("PushUp/assets/pushup.png");
        
        return description();

    }
    


}
