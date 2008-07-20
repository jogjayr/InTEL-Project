/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seesaw;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.exercise.SimpleFBDExercise;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.tasks.CompleteFBDTask;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class SeeSawExercise extends SimpleFBDExercise {//OrdinaryExercise {

    @Override
    public void initExercise() {
        setName("See Saw");
        setDescription("Two children are on a see saw. Build a free body diagram of the see saw.");
        
        Unit.setPrecision(Unit.distance, 2);
    }

    @Override
    public void loadExercise() {
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.6f, .6f, .6f, 1.0f));

        Schematic schematic = getSchematic();

        Point end1 = new Point("-2.5", "0", "0");
        Point base = new Point("0", "0", "0");
        Point end2 = new Point("2.5", "0", "0");

        UnknownPoint child1Point = new UnknownPoint(new Point("-2.0", "0", "0"), base, Vector3bd.UNIT_X.negate());
        Point child2Point = new Point("1.75", "0", "0");

        Force child1Force = new Force(child1Point, Vector3bd.UNIT_Y.negate(), new BigDecimal(15 * 9.8f));
        Force child2Force = new Force(child2Point, Vector3bd.UNIT_Y.negate(), "W");
        child1Force.setName("girl");
        
        DistanceMeasurement measure1 = new DistanceMeasurement(child1Point, base);
        DistanceMeasurement measure2 = new DistanceMeasurement(base, child2Point);

        measure1.setKnown(false);
        measure1.setSymbol("X");

        schematic.add(measure1);
        schematic.add(measure2);

        base.setName("B");
        //end1.setName("end1");
        //end2.setName("end2");
        child1Point.setName("A");
        child2Point.setName("C");

        child1Point.setMeasurement(measure1);

        Body seesaw = new Beam(end1, end2);
        seesaw.setName("See Saw");

        seesaw.addObject(base);
        seesaw.addObject(child1Force);
        seesaw.addObject(child2Force);
        seesaw.addObject(child1Point);
        seesaw.addObject(child2Point);

        Connector pin = new Pin2d(base);
        pin.attachToWorld(seesaw);

        //end1.createDefaultSchematicRepresentation();
        base.createDefaultSchematicRepresentation();
        //end2.createDefaultSchematicRepresentation();
        child1Force.createDefaultSchematicRepresentation();
        child1Point.createDefaultSchematicRepresentation();
        child2Force.createDefaultSchematicRepresentation();
        child2Point.createDefaultSchematicRepresentation();
        //seesaw.createDefaultSchematicRepresentation();
        measure1.createDefaultSchematicRepresentation();
        measure2.createDefaultSchematicRepresentation();

        schematic.add(seesaw);

        Vector3f modelOffset = new Vector3f(0, -1.25f, 0);

        ModelRepresentation rep;
        ModelNode modelNode = ModelNode.load("seesaw/assets", "seesaw/assets/seesaw.dae");

        modelNode.extractLights();

        rep = modelNode.extractElement(seesaw, "VisualSceneNode/board");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelOffset(modelOffset);
        seesaw.addRepresentation(rep);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelOffset);
        schematic.getBackground().addRepresentation(rep);
        
        addTask(new CompleteFBDTask(new BodySubset(seesaw)));
    }
}