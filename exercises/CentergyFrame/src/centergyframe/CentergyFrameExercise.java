/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centergyframe;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
/**
 *
 * @author Vignesh
*/
public class CentergyFrameExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("As you may have heard, there was a partial collapse of the parking deck behind the Centergy Building" +
                                 "in Technology Square on Monday, June 29, 2009, around noon time. " +
                                 "Though not on Georgia Tech campus, some Tech employees do lease parking spaces within this deck. " +
                                 "Thankfully, nobody was injured.");

        description.setProblemStatement("The first picture shows two different supports that the firefighters built to maintain " +
                "stability of the floors adjacent to where the floors collapsed. We want to analyze the top structure in this problem. " +
                "To simplify the analysis, let’s only look at the front plane which can be simplified as shown in the bottom picture." +
                " We can also simplify the analysis by assuming the entire structure is supporting 30 kips, which is the approximate weight " +
                "of the slab of concrete and cars on top of it.");

        description.setGoals("Find the reaction forces at points A and F.");

        description.setLayout(new ScrollbarLayout());

        description.addImage("centergyframe/assets/screenshot.png");
        description.addImage("centergyframe/assets/mainpicture.png");
        description.addImage("centergyframe/assets/outsidebeam.png");
        description.addImage("centergyframe/assets/insidedeck.png");

        return description;
    }


    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "0", "5", "0");
        Point C = new Point("C", "0", "10", "0");
        Point D = new Point("D", "4", "10", "0");
        Point E = new Point("E", "4", "5", "0");
        Point F = new Point("F", "4", "0", "0");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        F.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(E);
        schematic.add(F);

        Bar BE = new Bar("BE", B, E);
        Beam AC = new Beam("AC", A, C);
        Beam CD = new Beam("CD", C, D);
        Beam DF = new Beam("DF", D, F);

        schematic.add(BE);
        schematic.add(AC);
        schematic.add(CD);
        schematic.add(DF);

        //Importing Models
        ModelNode modelNode = ModelNode.load("centergyframe/assets/", "centergyframe/assets/CenturgyB_collada.dae");
        modelNode.extractLights();

        //Specifying the root folder for all the model objects that need to be attached to the exercise elements (points)
        ModelRepresentation rep;
        String prefix = "VisualSceneNode/everything/everything_group1/Scene/";

        //Attaching a model object to it's respective exercise element
        rep = modelNode.extractElement(AC, prefix+"pCube3");
        AC.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(CD, prefix + "pCube16");
        CD.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(DF, prefix + "pCube2");
        DF.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(BE, prefix + "pCube27");
        BE.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);


        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);
    }
   
}




