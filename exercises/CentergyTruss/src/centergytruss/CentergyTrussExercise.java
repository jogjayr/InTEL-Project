/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centergytruss;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;

/**
 *
 * @author tflock
 */
public class CentergyTrussExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setNarrative("As you may have heard, there was a partial collapse of the parking deck behind the Centergy" +
                "Building in Technology Square on Monday, June 29, 2009, around noon time. Though not on" +
                "Georgia Tech campus, some Tech employees do lease parking spaces within this deck." +
                "Thankfully, nobody was injured.");

        description.setProblemStatement("The first picture shows two different supports that the firefighters built to maintain stability of " +
                "the floors adjacent to where the floors collapsed. We want to analyze the top structure in this" +
                "problem. To simplify the analysis, let’s only look at the front plane which can be simplified as " +
                "shown in the bottom picture. We can also simplify the analysis by assuming the entire structur" +
                "is supporting 30 kips, which is the approximate weight of the slab of concrete and cars on top of" +
                "it.  We can simplify the drawings slightly more by looking at strictly the bottom half which will" +
                "help in our analysis of the truss. We can translate the forces from the top of the structure down to" +
                "points B and C.");

        description.setGoals(
                "Solve for the forces in BA, BD, and CD using either the method of joints or the method of " +
                "sections. Specify whether each member is in tension, compression or is a zero force member.");

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

        Bar AB = new Bar("AB", A, B);
        Bar BC = new Bar("BC", B, C);
        Bar CD = new Bar("CD", C, D);
        Bar DA = new Bar("DA", D, A);
        Bar BD = new Bar("BD", B, D);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
//        AB.createDefaultSchematicRepresentation();
//        BC.createDefaultSchematicRepresentation();
//        CD.createDefaultSchematicRepresentation();
//        DA.createDefaultSchematicRepresentation();
//        BD.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(AB);
        schematic.add(BC);
        schematic.add(CD);
        schematic.add(DA);
        schematic.add(BD);

        ModelNode modelNode = ModelNode.load("centergytruss/assets/", "centergytruss/assets/centergyDeck3.dae");
        modelNode.extractLights();

        ModelRepresentation rep;
        String prefix = "RootNode/group1/Scene/completeStructure/";

        rep = modelNode.extractElement(AB, prefix+"pCube3");
        AB.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(BC, prefix+"pCube27");
        BC.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(CD, prefix+"pCube2");
        CD.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(DA, prefix+"pCube16");
        DA.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(BD, prefix+"pCube20");
        BD.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);
    }

}
