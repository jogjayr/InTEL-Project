/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crcroof;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;

/**
 *
 * @author vignesh
 */
public class CRCRoof extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("The roof of the entrance into the Campus Recreation Center (CRC) " +
                "at Georgia Tech is comprised of several beams and cables.");

        description.setProblemStatement("In this exercise, you will analyze a simplified structure of the " +
                "CRC entrance roof.  The tension in the cable connecting to point D is 2000 N. The angle " +
                "between this cable and the vertical member is 30°.  The angle between beam ABC and the cable " +
                "CE is 30°.  The weight of beam ABC is 1200 N and the weight of the DBE is 2000 N. The necessary " +
                "dimensions are provided in the schematic.");

        description.setGoals("Solve for the tension in cable CE.");

        description.setLayout(new ScrollbarLayout());

        description.addImage("CRCRoof/assets/CRC1.png");
        description.addImage("CRCRoof/assets/CRC2.png");

        return description;


    }


}
