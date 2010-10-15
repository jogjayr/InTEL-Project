/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crcroof;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.representations.ModelNode;

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

    
    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "0", "4.5", "0");
        Point C = new Point("C", "0", "6", "0");
        Point D = new Point("D", "-2", "4", "0");
        Point E = new Point("E", "6", "5.47506802", "0");

        PointBody Bb;
        Bb = new PointBody("Joint B", B );

        Bb.createDefaultSchematicRepresentation();

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(E);

        Beam DE = new Beam("DE", D, E);
        Beam AC = new Beam("AC", A, C);

        Cable CE = new Cable("CE", C, E);
        Cable DA = new Cable("DA", D, A);

        schematic.add(DE);
        schematic.add(AC);

        schematic.add(CE);
        schematic.add(DA);

        ModelNode modelNode = ModelNode.load("crcroof/assets/", "crcroof/assets/centergyDeck3.dae");
        modelNode.extractLights();


    }




}
