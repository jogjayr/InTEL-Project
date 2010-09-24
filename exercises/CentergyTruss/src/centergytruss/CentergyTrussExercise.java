    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centergytruss;

import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.modes.truss.TrussExercise;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.Solve2FMTask;
import java.math.BigDecimal;

/**
 *
 * @author Vignesh 
 */
public class CentergyTrussExercise extends TrussExercise {

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setNarrative("As you may have heard, there was a partial collapse of the parking deck behind the Centergy"
                + "Building in Technology Square on Monday, June 29, 2009, around noon time. Though not on"
                + "Georgia Tech campus, some Tech employees do lease parking spaces within this deck."
                + "Thankfully, nobody was injured.");

        description.setProblemStatement("The first picture shows two different supports that the firefighters built to maintain stability of "
                + "the floors adjacent to where the floors collapsed. We want to analyze the top structure in this"
                + "problem. To simplify the analysis, let’s only look at the front plane which can be simplified as "
                + "shown in the bottom picture. We can also simplify the analysis by assuming the entire structur"
                + "is supporting 30 kips, which is the approximate weight of the slab of concrete and cars on top of"
                + "it.  We can simplify the drawings slightly more by looking at strictly the bottom half which will"
                + "help in our analysis of the truss. We can translate the forces from the top of the structure down to"
                + "points B and C.");

        description.setGoals(
                "Solve for the forces in BA, BD, and CD using either the method of joints or the method of "
                + "sections. Specify whether each member is in tension, compression or is a zero force member.");

        description.setLayout(new ScrollbarLayout());

        description.addImage("centergytruss/assets/screenshot.png");
        description.addImage("centergytruss/assets/mainpicture.png");
        description.addImage("centergytruss/assets/outsidebeam.png");
        description.addImage("centergytruss/assets/insidedeck.png");

        return description;
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "0", "5", "0");
        Point C = new Point("C", "4", "5", "0");
        Point D = new Point("D", "4", "0", "0");

        PointBody Ab, Bb, Cb, Db;
        Ab = new PointBody("Joint A", A);
        Bb = new PointBody("Joint B", B);
        Cb = new PointBody("Joint C", C);
        Db = new PointBody("Joint D", D);

        Bar AB = new Bar("AB", A, B);
        Bar BC = new Bar("BC", B, C);
        Bar CD = new Bar("CD", C, D);
        Bar DA = new Bar("DA", D, A);
        Bar BD = new Bar("BD", B, D);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();

        Ab.createDefaultSchematicRepresentation();
        Bb.createDefaultSchematicRepresentation();
        Cb.createDefaultSchematicRepresentation();
        Db.createDefaultSchematicRepresentation();

        Force f1 = new Force(B, Vector3bd.UNIT_Y.negate(), new BigDecimal("15"));
        Force f2 = new Force(C, Vector3bd.UNIT_Y.negate(), new BigDecimal("15"));
        f1.setName("f1");
        f2.setName("f2");
        f1.createDefaultSchematicRepresentation();
        f2.createDefaultSchematicRepresentation();

        Bb.addObject(f1);
        Cb.addObject(f2);

        Connector2ForceMember2d connector;
        connector = new Connector2ForceMember2d(A, AB);
        connector.attach(AB, Ab);
        connector = new Connector2ForceMember2d(A, DA);
        connector.attach(DA, Ab);
        connector = new Connector2ForceMember2d(B, AB);
        connector.attach(AB, Bb);
        connector = new Connector2ForceMember2d(B, BD);
        connector.attach(BD, Bb);
        connector = new Connector2ForceMember2d(B, BC);
        connector.attach(BC, Bb);
        connector = new Connector2ForceMember2d(C, BC);
        connector.attach(BC, Cb);
        connector = new Connector2ForceMember2d(C, CD);
        connector.attach(CD, Cb);
        connector = new Connector2ForceMember2d(D, CD);
        connector.attach(CD, Db);
        connector = new Connector2ForceMember2d(D, BD);
        connector.attach(BD, Db);
        connector = new Connector2ForceMember2d(D, DA);
        connector.attach(DA, Db);

        Pin2d pinA = new Pin2d(A);
        Roller2d rollerD = new Roller2d(D);
        pinA.setName("pin A");
        rollerD.setName("pin D");
        pinA.attachToWorld(Ab);
        rollerD.attachToWorld(Db);
        rollerD.setDirection(Vector3bd.UNIT_Y);
        
        schematic.add(Ab);
        schematic.add(Bb);
        schematic.add(Cb);
        schematic.add(Db);
        schematic.add(AB);
        schematic.add(BC);
        schematic.add(CD);
        schematic.add(DA);
        schematic.add(BD);

        ModelNode modelNode = ModelNode.load("centergytruss/assets/", "centergytruss/assets/centergyDeck3.dae");
        modelNode.extractLights();

        ModelRepresentation rep;
        String prefix = "RootNode/group1/Scene/completeStructure/";

        rep = modelNode.extractElement(AB, prefix + "pCube3");
        AB.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(BC, prefix + "pCube27");
        BC.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(CD, prefix + "pCube2");
        CD.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(DA, prefix + "pCube16");
        DA.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(BD, prefix + "pCube20");
        BD.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);


        addTask(new Solve2FMTask("Solve ABˆ", AB, AB.getConnector1()));
        addTask(new Solve2FMTask("Solve BD", BD, BD.getConnector1()));
        addTask(new Solve2FMTask("Solve CD", CD, CD.getConnector1()));
    }
}
