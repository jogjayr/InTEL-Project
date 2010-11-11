/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crcroof;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.layouts.ScrollbarLayout;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;

/**
 *
 * @author vignesh
 */
public class CRCRoof extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("The roof of the entrance into the Campus Recreation Center (CRC) "
                + "at Georgia Tech is comprised of several beams and cables.");

        description.setProblemStatement("In this exercise, you will analyze a simplified structure of the "
                + "CRC entrance roof.  The tension in the cable connecting to point D is 2000 N. The angle "
                + "between this cable and the vertical member is 30°.  The angle between beam ABC and the cable "
                + "CE is 30°.  The weight of beam ABC is 1200 N and the weight of the DBE is 2000 N. The necessary "
                + "dimensions are provided in the schematic.");

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
        Point F = new Point("F", "0", "0.535898385", "0");

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

        Beam DE = new Beam("DE", D, E);
        Beam AC = new Beam("AC", A, C);

        Cable CE = new Cable("CE", C, E);
        Cable DF = new Cable("DA", D, F);

        Pin2d pinB = new Pin2d(B);

        Fix2d fixedA = new Fix2d(A);

        fixedA.setName("Fixed A");

        fixedA.attachToWorld(AC);

        Connector2ForceMember2d connectorC = new Connector2ForceMember2d(C, CE);
        Connector2ForceMember2d connectorE = new Connector2ForceMember2d(E, CE);

        Connector2ForceMember2d connectorD = new Connector2ForceMember2d(D, DF);
        Connector2ForceMember2d connectorF = new Connector2ForceMember2d(F, DF);

        connectorC.attach(CE, AC);
        connectorE.attach(CE, DE);
        
        connectorD.attach(DE, DF);
        connectorF.attach(DF, AC);

        pinB.setName("Pin B");

        pinB.attach(AC, DE);

        schematic.add(DE);
        schematic.add(AC);
        schematic.add(CE);
        schematic.add(DF);


        ModelNode modelNode = ModelNode.load("crcroof/assets/", "crcroof/assets/crcRoof.dae");
        modelNode.extractLights();


        ModelRepresentation rep;
        String prefix = "VisualSceneNode/totalScene/";


        Matrix3f rotation = new Matrix3f();
        rotation.fromAngleAxis((float) -Math.PI / 2, Vector3f.UNIT_Y);
        float scale = .44444f;


        rep = modelNode.extractElement(CE, prefix + "CE");//pCylinder11
        CE.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalRotation(rotation);
        rep.getRelativeNode().setLocalScale(scale);

        rep = modelNode.extractElement(DF, prefix + "DF");
        DF.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalRotation(rotation);
        rep.getRelativeNode().setLocalScale(scale);

        rep = modelNode.extractElement(AC, prefix + "column");
        AC.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalRotation(rotation);
        rep.getRelativeNode().setLocalScale(scale);

        rep = modelNode.extractElement(DE, prefix + "totalScene_roof");
        DE.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.getRelativeNode().setLocalRotation(rotation);
        rep.getRelativeNode().setLocalScale(scale);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.getRelativeNode().setLocalRotation(rotation);
        rep.getRelativeNode().setLocalScale(scale);
        schematic.getBackground().addRepresentation(rep);

    }
}
