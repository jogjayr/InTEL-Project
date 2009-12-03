/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centergytruss;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
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

        description.setNarrative("blah blah blah Centergy deck collapse");
        description.setGoals(
                "Please solve for the forces BA, BD, CD using either the " +
                "method of joints or the method of sections.");

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
