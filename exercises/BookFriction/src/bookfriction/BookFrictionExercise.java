/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bookfriction;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.representations.ModelNode;

/**
 *
 * @author vignesh
 */
public class BookFrictionExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("A student is trying to understand the concept of static friction.");

        description.setProblemStatement("She has a stack of three books " +
        "and is pulling on the middle one, which is her statics book.");

        description.setGoals("Find the maximum force she can pull " +
        " on her statics book before slipping occurs.");

        description.addImage("bookfriction/assets/Thermodynamics.png");

        return description;

    }

    public void loadExercise() {

        Schematic schematic = getSchematic();

        ModelNode modelNode = ModelNode.load("bookfriction/assets/", "bookfriction/assets/bookfriction4.dae");
        modelNode.extractLights();


    }

}
