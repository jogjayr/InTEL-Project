/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package highheel;

import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;

/**
 *
 * @author vignesh
 */
public class HighHeelExercise extends DistributedExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("Most women have experienced back pain from the abnormal posture that high heels " +
                "induce. One of the causes of the damage can be explained by how the weight is distributed. " +
                "Concentrated forces are exerted to heels when wearing high heels, while weight of the person can " +
                "be distributed evenly when walking in flat shoes or walking barefoot.");

        description.setProblemStatement("Calculate the weight distribution and draw free body diagrams for feet shown" +
                " in Figure 1 and Figure 2. Force is equally distributed between AB and G'O and there is a concentrated " +
                "force at H. The person weighs 130lb and the length of the foot is as indicated in the picture. Also, " +
                "compare the distributed force between these two models. (Note that only one foot is analyzed here.)");
        
        description.addImage("highheel/assets/highheel1.png");
        description.addImage("highheel/assets/highheel2.png");

    return description;

    }

    @Override
    public void initExercise() {

        Unit.setSuffix(Unit.distance, "m");
        Unit.setSuffix(Unit.mass, "Kg");
        Unit.setSuffix(Unit.force, "N");
        Unit.setSuffix(Unit.forceOverDistance, " N/m");
    }

    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "6.5", "0", "0");

        Point G = new Point("G", "3.25", "0", "0");

        Point H = new Point("H", "10.5", "3", "0");
        Point O = new Point("O", "16.5", "0", "0");
        Point GG = new Point("GG", "13.5", "0", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        H.createDefaultSchematicRepresentation();
        O.createDefaultSchematicRepresentation();

        G.createDefaultSchematicRepresentation();
        GG.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);

        schematic.add(H);
        schematic.add(O);

        schematic.add(G);
        schematic.add(GG);

        Beam AB = new Beam("AB", A, B);
        Beam GGO = new Beam("GGO", GG, O);

        schematic.add(AB);
        schematic.add(GGO);

        DistanceMeasurement measureFullAB = new DistanceMeasurement(A, B);
        measureFullAB.createDefaultSchematicRepresentation();
        schematic.add(measureFullAB);

        DistanceMeasurement measureFullGGO = new DistanceMeasurement(GG, O);
        measureFullGGO.createDefaultSchematicRepresentation();
        schematic.add(measureFullGGO);

        
    }

}
