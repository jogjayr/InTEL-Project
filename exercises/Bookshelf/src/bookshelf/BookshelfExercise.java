/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookshelf;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.modes.distributed.objects.ConstantDistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.SolveConnectorTask;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class BookshelfExercise extends DistributedExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-5f, 5f, -4f, 6f);
        vc.setZoomConstraints(0.5f, 1.5f);
        vc.setRotationConstraints(-1, 1);
        interfaceConfiguration.setViewConstraints(vc);
        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Bookshelf");

        description.setNarrative(
                "Cindy is a Mechanical Engineering student at Georgia Tech.  " +
                "Recently one of her bookshelves fell down and she wants to hang " +
                "it back up this weekend.  She went to the hardware store to look " +
                "for different wall hangers that she can use to hang the shelf up " +
                "with, but they have different load ratings.  She needs to find " +
                "out what the loads are at the ends of the bookshelf so that she " +
                "can purchase the appropriate supports for the shelf.");
        
        description.setProblemStatement(
                "The supports she is looking at can only resist vertical forces.  " +
                "The left group of books has a mass per unit length of 20 kg/m, " +
                "the center group of books has 30 kg/m and the right group of " +
                "books has 15 kg/m.  Treat A as a pin and B as a roller.");

        description.setGoals("Solve for the reactions at points A and B.");

        return description;
    }



    @Override
    public void initExercise() {
//        setName("Bookshelf");
//        setDescription("What are the loads on the supports of the bookshelf? " +
//                "Treat A as a pin and B as a roller.");

        // *****
        // UNITS ARE ISSUE
        // problem specifies distribution of mass for the books, not the forces
        // *****

        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*mm");
        Unit.setSuffix(Unit.force, " N");
        Unit.setSuffix(Unit.mass, " kg");
        Unit.setSuffix(Unit.forceOverDistance, " N/mm");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".01"));

        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setMomentLabelDistance(10f);
        getDisplayConstants().setMeasurementBarSize(0.2f);

        Unit.setPrecision(Unit.moment, 2);
        Unit.setPrecision(Unit.force, 2);
        Unit.setPrecision(Unit.mass, 2);
        Unit.setPrecision(Unit.forceOverDistance, 2);
    }

    @Override
    public void loadExercise() {

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.9f, .9f, .9f, 1.0f));

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "10", "0", "0");

        Point mid1 = new Point("mid1", "2", "0", "0");
        Point mid2 = new Point("mid2", "6", "0", "0");
        Point mid3 = new Point("mid3", "7", "0", "0");

        Beam bookshelf = new Beam("Bookshelf", A, B);

        DistributedForce dlBooks1 = new ConstantDistributedForce("books1", bookshelf, A, mid1,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("0.20")));
        DistributedForceObject dlObjectBooks1 = new DistributedForceObject(dlBooks1, "1");
        bookshelf.addObject(dlObjectBooks1);

        DistributedForce dlBooks2 = new ConstantDistributedForce("books2", bookshelf, mid1, mid2,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("0.30")));
        DistributedForceObject dlObjectBooks2 = new DistributedForceObject(dlBooks2, "2");
        bookshelf.addObject(dlObjectBooks2);

        DistributedForce dlBooks3 = new ConstantDistributedForce("books3", bookshelf, mid3, B,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal("0.15")));
        DistributedForceObject dlObjectBooks3 = new DistributedForceObject(dlBooks3, "3");
        bookshelf.addObject(dlObjectBooks3);

        bookshelf.addObject(mid1);
        bookshelf.addObject(mid2);
        bookshelf.addObject(mid3);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        //bookshelf.createDefaultSchematicRepresentation();

        int arrowDensity = 2;
        dlObjectBooks1.createDefaultSchematicRepresentation(18f / 6, 2 * arrowDensity, 1.75f);
        dlObjectBooks2.createDefaultSchematicRepresentation(22f / 6, 4 * arrowDensity, 2.0f);
        dlObjectBooks3.createDefaultSchematicRepresentation(15f / 6, 3 * arrowDensity, 2.25f);

        DistanceMeasurement measureFull = new DistanceMeasurement(A, B);
        measureFull.createDefaultSchematicRepresentation(3);
        schematic.add(measureFull);

        DistanceMeasurement measure1 = new DistanceMeasurement(A, mid1);
        measure1.createDefaultSchematicRepresentation();
        schematic.add(measure1);

        DistanceMeasurement measure2 = new DistanceMeasurement(mid1, mid2);
        measure2.createDefaultSchematicRepresentation();
        schematic.add(measure2);

        DistanceMeasurement measure3 = new DistanceMeasurement(mid3, B);
        measure3.createDefaultSchematicRepresentation();
        schematic.add(measure3);

        Pin2d end1 = new Pin2d(A);
        //end1.setDirection(Vector3bd.UNIT_Y);
        end1.attachToWorld(bookshelf);
        end1.setName("support A");
        end1.createDefaultSchematicRepresentation();

        Roller2d end2 = new Roller2d(B);
        end2.setDirection(Vector3bd.UNIT_Y);
        end2.attachToWorld(bookshelf);
        end2.setName("support B");
        end2.createDefaultSchematicRepresentation();

        schematic.add(bookshelf);

        ModelNode modelNode = ModelNode.load("bookshelf/assets/", "bookshelf/assets/bookshelf.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0, 0, 0);
        float modelScale = .4f;

        ModelRepresentation rep = modelNode.extractElement(bookshelf, "VisualSceneNode/scene/bookshelf2/shelf2");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        bookshelf.addRepresentation(rep);

        rep = modelNode.extractElement(bookshelf, "VisualSceneNode/scene/bookshelf2/books2/bookGroup1");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        dlObjectBooks1.addRepresentation(rep);

        rep = modelNode.extractElement(bookshelf, "VisualSceneNode/scene/bookshelf2/books2/bookGroup2");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        dlObjectBooks2.addRepresentation(rep);

        rep = modelNode.extractElement(bookshelf, "VisualSceneNode/scene/bookshelf2/books2/bookGroup3");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        dlObjectBooks3.addRepresentation(rep);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelScale(modelScale);
        schematic.getBackground().addRepresentation(rep);

        addTask(new SolveConnectorTask("Solve A", end1));
        addTask(new SolveConnectorTask("Solve B", end2));
    }
}
