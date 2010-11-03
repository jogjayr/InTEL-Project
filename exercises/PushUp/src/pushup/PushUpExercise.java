/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pushup;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;




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


        description.addImage("pushupexercise/assets/pushup.png");
        
        return description;

    }

    
    public void initExercise() {

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.moment, " newton*ft");
        Unit.setSuffix(Unit.force, "  newton");
        Unit.setSuffix(Unit.forceOverDistance, " newton/ft");

    }
    
    
    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "5", "0");
        Point B = new Point("B", "5", "5", "0");
        Point C = new Point("C", "15", "5", "0");

        Point D = new Point("D", "0", "20", "0");
        Point E = new Point("E", "5", "20", "0");
        Point F = new Point("F", "15", "20", "0");

        Point G = new Point("G", "25", "5", "0");
        Point H = new Point("H", "30", "5", "0");
        Point I = new Point("I", "40", "5", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        F.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        H.createDefaultSchematicRepresentation();
        I.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(E);
        schematic.add(F);
        schematic.add(G);
        schematic.add(H);
        schematic.add(I);


        
        ModelNode modelNode = ModelNode.load("pushupexercise/assets/", "pushupexercise/assets/male_PushUp.dae");
        modelNode.extractLights();


        ModelRepresentation rep;
        String prefix = "VisualSceneNode/";

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);
         
         
        
    }

}
