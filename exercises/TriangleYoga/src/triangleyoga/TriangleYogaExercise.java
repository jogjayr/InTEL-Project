/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package triangleyoga;

import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author vignesh
 */
public class TriangleYogaExercise {

    
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("");

        description.setProblemStatement("");

        description.setGoals("");


        description.addImage("triangleyoga/assets/girlworkout_black.png");

        return description;

    }

    public void loadExercise() {

//        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "45", "60", "0");
        Point C = new Point("C", "90", "0", "0");
        Point D = new Point("D", "90", "60", "0");
        Point E = new Point("E", "90", "120", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();

  

        

        

    }


}
