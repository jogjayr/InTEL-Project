/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knifechopstick;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author vignesh
 */
public class KnifeChopstickExercise extends OrdinaryExercise {
    
    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Debbie is an aerospace engineering major and is in her third year.  " +
                "She was invited to the Women in Engineering Excellence Awards Banquet this year.  The banquet" +
                " is held every year to celebrate the academic excellence and leadership of female students in " +
                "the College of Engineering.  The banquet honors the accomplishments of female engineering students" +
                " who have achieved “high honors” status by earning a cumulative GPA of 3.35 and above.  " +
                "The evening consists of having a nice dinner at the Georgia Tech Hotel and Conference Center while " +
                "being seated with a corporate sponsor to the institute and then having scholarships and awards handed out.");

        description.setProblemStatement("After everyone finished dinner at Debbie’s table, a waiter came to take the empty plates away. " +
                " The waiter had a knife on top of the first plate that he was trying to pick up and every time he lifted it off " +
                "the table, the knife fell off backwards, which can be very dangerous.  The mass of the blade of the knife was 20 " +
                "grams and the mass of the handle was 30 grams.  The length of the blade was 120 mm and the length of the handle was 100 mm. " +
                " The width of the widest portion of the blade was 30 mm and the width of the handle was 20 mm.");

        description.setGoals("Solve for the minimum distance of the contact of the knife and the plate from the tip of the knife so that" +
                " the knife does not fall off the plate.");

        description.addImage("knifechopstick/assets/knife1.jpg");
        description.addImage("knifechopstick/assets/knife2.jpg");

    return description;

    }

    @Override
    public void initExercise() {

        Unit.setSuffix(Unit.distance, "mm");
        Unit.setSuffix(Unit.mass, "g");
        
    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "120", "0", "0");
        Point C = new Point("C", "220", "0", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);

        DistanceMeasurement measureFullAB = new DistanceMeasurement(A, B);
        measureFullAB.createDefaultSchematicRepresentation();
        schematic.add(measureFullAB);

        DistanceMeasurement measureFullBC = new DistanceMeasurement(B, C);
        measureFullBC.createDefaultSchematicRepresentation();
        schematic.add(measureFullBC);

        DistanceMeasurement measureFullAC = new DistanceMeasurement(A, C);
        measureFullAC.createDefaultSchematicRepresentation();
        schematic.add(measureFullAC);


    }


}
