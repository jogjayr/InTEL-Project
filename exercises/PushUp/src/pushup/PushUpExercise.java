/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pushup;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.SolveConnectorTask;
import java.math.BigDecimal;




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
        Unit.setSuffix(Unit.moment, "kg*ft");
        Unit.setSuffix(Unit.force, "kg");

    }
    
    
    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "1.61", "0", "0");
        Point B = new Point("B", "3.02", "0", "0");
        Point C = new Point("C", "5.83", "0", "0");

        Point D = new Point("D", "8.18", "0", "0");
        Point E = new Point("E", "9.04", "0", "0");
        Point F = new Point("F", "10.77", "0", "0");
        Point G = new Point("G", "11.2", "0", "0");

        Point H = new Point("H", "14.27", "0", "0");
        Point I = new Point("I", "15.41", "0", "0");
        Point J = new Point("J", "18.83", "0", "0");

        DistanceMeasurement measureAB = new DistanceMeasurement(A, B);
        measureAB.createDefaultSchematicRepresentation();
        schematic.add(measureAB);

    
        DistanceMeasurement measureBC = new DistanceMeasurement(B, C);
        measureBC.createDefaultSchematicRepresentation();
        schematic.add(measureBC);


        DistanceMeasurement measureDE = new DistanceMeasurement(D, E);
        measureDE.createDefaultSchematicRepresentation();
        schematic.add(measureDE);
        

        DistanceMeasurement measureEF = new DistanceMeasurement(E, F);
        measureEF.createDefaultSchematicRepresentation();
        schematic.add(measureEF);

        DistanceMeasurement measureFG = new DistanceMeasurement(F, G);
        measureFG.createDefaultSchematicRepresentation();
        schematic.add(measureFG);

        DistanceMeasurement measureHI = new DistanceMeasurement(H, I);
        measureHI.createDefaultSchematicRepresentation();
        schematic.add(measureHI);

        DistanceMeasurement measureIJ = new DistanceMeasurement(I, J);
        measureIJ.createDefaultSchematicRepresentation();
        schematic.add(measureIJ);

        Roller2d rollerA = new Roller2d(A);
        Roller2d rollerC = new Roller2d(C);
        Roller2d rollerD = new Roller2d(D);
        Roller2d rollerF = new Roller2d(F);
        Roller2d rollerH = new Roller2d(H);
        Roller2d rollerJ = new Roller2d(J);

        rollerA.setName("Reaction A");
        rollerC.setName("Reaction C");
        rollerD.setName("Reaction D");
        rollerF.setName("Reaction F");
        rollerH.setName("Reaction H");
        rollerJ.setName("Reaction J");

        rollerA.setDirection(Vector3bd.UNIT_Y);
        rollerC.setDirection(Vector3bd.UNIT_Y);
        rollerD.setDirection(Vector3bd.UNIT_Y);
        rollerF.setDirection(Vector3bd.UNIT_Y);
        rollerH.setDirection(Vector3bd.UNIT_Y);
        rollerJ.setDirection(Vector3bd.UNIT_Y);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        F.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        H.createDefaultSchematicRepresentation();
        I.createDefaultSchematicRepresentation();
        J.createDefaultSchematicRepresentation();


        schematic.add(A);
        schematic.add(B);
        schematic.add(C);
        schematic.add(D);
        schematic.add(E);
        schematic.add(F);
        schematic.add(G);
        schematic.add(H);
        schematic.add(I);
        schematic.add(J);

        Force Bf = new Force(B, Vector3bd.UNIT_Y.negate(), new BigDecimal(60));
        Force Ef = new Force(E, Vector3bd.UNIT_Y.negate(), new BigDecimal(54));
        Force Gf = new Force(G, Vector3bd.UNIT_Y.negate(), new BigDecimal(6));
        Force If = new Force(I, Vector3bd.UNIT_Y.negate(), new BigDecimal(60));

        Bf.setName("Force B");
        Ef.setName("Force E");
        Gf.setName("Force G");
        If.setName("Force I");

        Bf.createDefaultSchematicRepresentation();
        Ef.createDefaultSchematicRepresentation();
        Gf.createDefaultSchematicRepresentation();
        If.createDefaultSchematicRepresentation();

        Body kid1 = new Potato("kid1");
        Body kid2 = new Potato("kid2");
        Body kid3 = new Potato("kid3");

        kid1.addObject(A);
        kid1.addObject(B);
        kid1.addObject(C);
        kid1.addObject(Bf);
        rollerA.attachToWorld(kid1);
        rollerC.attachToWorld(kid1);

        kid2.addObject(D);
        kid2.addObject(E);
        kid2.addObject(F);
        kid2.addObject(G);
        kid2.addObject(Ef);
        kid2.addObject(Gf);
        rollerD.attachToWorld(kid2);
        rollerF.attachToWorld(kid2);


        kid3.addObject(H);
        kid3.addObject(I);
        kid3.addObject(J);
        kid3.addObject(If);
        rollerH.attachToWorld(kid3);
        rollerJ.attachToWorld(kid3);

        schematic.add(kid1);
        schematic.add(kid2);
        schematic.add(kid3);


        ModelNode modelNode = ModelNode.load("pushupexercise/assets/", "pushupexercise/assets/pushup.dae");
        modelNode.extractLights();


        ModelRepresentation rep;
        String prefix = "VisualSceneNode/";

        float scale = 1;//50f;
        Vector3f translation = new Vector3f();//(45, 5, 0);

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);

        rep = modelNode.extractElement(kid1, prefix + "group1/completebody");
        kid1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalScale(scale);
        rep.getRelativeNode().setLocalTranslation(translation);

        rep = modelNode.extractElement(kid2, prefix + "girl_group2");
        kid2.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalScale(scale);
        rep.getRelativeNode().setLocalTranslation(translation);

        rep = modelNode.extractElement(kid3, prefix + "group3/character");
        kid3.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalScale(scale);
        rep.getRelativeNode().setLocalTranslation(translation);
        
        rep = modelNode.getRemainder(schematic.getBackground());
        rep.getRelativeNode().setLocalScale(scale);
        schematic.getBackground().addRepresentation(rep);
        rep.getRelativeNode().setLocalTranslation(translation);
        
        addTask(new SolveConnectorTask("Solve forces at A", rollerA));
        addTask(new SolveConnectorTask("Solve forces at C", rollerC));
        addTask(new SolveConnectorTask("Solve forces at D", rollerD));
        addTask(new SolveConnectorTask("Solve forces at F", rollerF));
        addTask(new SolveConnectorTask("Solve forces at H", rollerH));
        addTask(new SolveConnectorTask("Solve forces at J", rollerJ));




        
    }

}
