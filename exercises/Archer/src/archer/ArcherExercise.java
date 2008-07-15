/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archer;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.exercise.SimpleFBDExercise;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.connectors.Pin2dKnownDirection;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.CompleteFBDTask;

/**
 *
 * @author Calvin Ashmore
 */
public class ArcherExercise extends SimpleFBDExercise {

    @Override
    public void initExercise() {
        setName("Archer");
        setDescription("An olympic archer is holding a bow. Build free body diagrams of the bow, the bowstring, and the two together.");
        
        getDisplayConstants().setDrawScale(2);
    }

    
    
    @Override
    public void loadExercise() {
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.7f, .8f, .9f, 1.0f));

        Schematic schematic = getSchematic();

        Body bowString = new Potato();
        Body bow = new Potato();
        
        bowString.setName("Bow String");
        bow.setName("Bow");

        Point bowTop = new Point("0", "5", "0");
        Point stringBack = new Point("-3", "0", "0");
        Point bowBottom = new Point("0", "-5", "0");
        Point bowFront = new Point("3", "0", "0");

        stringBack.setName("A");
        bowTop.setName("B");
        bowFront.setName("C");
        bowBottom.setName("D");

        Vector3bd directionUnitTop = new Vector3bd(".5", "-.866", "0");
        Vector3bd directionUnitBottom = new Vector3bd(".5", ".866", "0");
        
        Pin2dKnownDirection connectorTop = new Pin2dKnownDirection(bowTop);
        connectorTop.setDirection(directionUnitTop);
        connectorTop.attach(bowString, bow);

        Pin2dKnownDirection connectorBottom = new Pin2dKnownDirection(bowBottom);
        connectorBottom.setDirection(directionUnitBottom);
        connectorBottom.attach(bowString, bow);

        AngleMeasurement measureTop = new FixedAngleMeasurement(bowTop, directionUnitTop, Vector3f.UNIT_X);
        AngleMeasurement measureBottom = new FixedAngleMeasurement(bowBottom, directionUnitBottom, Vector3f.UNIT_X);
        
        schematic.add(measureTop);
        schematic.add(measureBottom);
        
        bowTop.createDefaultSchematicRepresentation();
        stringBack.createDefaultSchematicRepresentation();
        bowBottom.createDefaultSchematicRepresentation();
        bowFront.createDefaultSchematicRepresentation();
        
        measureBottom.createDefaultSchematicRepresentation();
        measureTop.createDefaultSchematicRepresentation();

        bow.addObject(bowFront);
        bowString.addObject(stringBack);
        
        schematic.add(bowString);
        schematic.add(bow);

        ModelNode modelNode = ModelNode.load("archer/assets/", "archer/assets/archer.dae");
        modelNode.extractLights();

        ModelRepresentation rep;

        float scale = 1;
        Vector3f modelOffset = new Vector3f(-5, -16, 0);

        rep = modelNode.extractElement(bowString, "VisualSceneNode/BowStringTop");
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        bowString.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(bowString, "VisualSceneNode/BowStringBottom");
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        bowString.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(bow, "VisualSceneNode/Bow");
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        bow.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);


        rep = modelNode.getRemainder(schematic.getBackground());
        rep.getRelativeNode().setLocalScale(scale);
        rep.setModelOffset(modelOffset);
        schematic.getBackground().addRepresentation(rep);
        
        addTask(new CompleteFBDTask(new BodySubset(bow)));
        addTask(new CompleteFBDTask(new BodySubset(bowString)));
        addTask(new CompleteFBDTask(new BodySubset(bow, bowString)));
    }
}
