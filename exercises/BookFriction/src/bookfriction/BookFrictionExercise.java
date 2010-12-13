/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bookfriction;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author vignesh
 */
public class BookFrictionExercise extends OrdinaryExercise {

    @Override
    public Description getDescription() {

        Description description = new Description();

        description.setNarrative("A student is trying to understand the concept of static friction.");

        description.setProblemStatement("She has a stack of three books " +
        "and is pulling on the middle one, which is her statics book.");

        description.setGoals("Find the maximum force she can pull " +
        " on her statics book before slipping occurs.");

        description.addImage("bookfriction/assets/Thermodynamics.png");

        return description;

    }

    @Override
    public void initExercise() {

        Unit.setSuffix(Unit.force, "N");
    }


    @Override
    public void loadExercise() {

        Schematic schematic = getSchematic();

        Point A = new Point("A", "0.125", "1.93", "0.7");
        Point B = new Point("B", "0.229", "2.028", "0.7");
        Point C = new Point("C", "0.133", "2.118", "0.7");

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        schematic.add(A);
        schematic.add(B);
        schematic.add(C);

        ConstantObject frictionObjectA = new ConstantObject("mu A", new BigDecimal("0.2"), Unit.none);
        schematic.add(frictionObjectA);
        
        ConstantObject frictionObjectB = new ConstantObject("mu B", new BigDecimal("0.4"), Unit.none);
        schematic.add(frictionObjectB);
        
        ConstantObject frictionObjectC = new ConstantObject("mu C", new BigDecimal("0.3"), Unit.none);
        schematic.add(frictionObjectC);

        Body book1 = new Potato("Book 1");
        Body book2 = new Potato("Book 2");
        Body book3 = new Potato("Book 3");

        book1.addObject(A);
        book2.addObject(B);
        book3.addObject(C);

        schematic.add(book1);
        schematic.add(book2);
        schematic.add(book3);

        ContactPoint jointA = new ContactPoint(A, frictionObjectA);
        jointA.setName("ContactA");
        jointA.setNormalDirection(Vector3bd.UNIT_X.negate());
        jointA.setFrictionDirection(Vector3bd.UNIT_Y);
        jointA.attachToWorld(book1);

        ContactPoint jointB = new ContactPoint(B, frictionObjectB);
        jointB.setName("ContactB");
        jointB.setNormalDirection(Vector3bd.UNIT_X.negate());
        jointB.setFrictionDirection(Vector3bd.UNIT_Y);
        jointB.attach(book1, book2);
        

        ContactPoint jointC = new ContactPoint(C, frictionObjectC);
        jointC.setName("ContactC");
        jointC.setNormalDirection(Vector3bd.UNIT_X.negate());
        jointC.setFrictionDirection(Vector3bd.UNIT_Y);
        jointC.attach(book2, book3);
        
        
        Force weightA = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal("18"));
        weightA.setName("Weight A");
        weightA.createDefaultSchematicRepresentation();
        schematic.add(weightA);

        Force weightB = new Force(B, Vector3bd.UNIT_Y.negate(), new BigDecimal("15"));
        weightB.setName("Weight B");
        weightB.createDefaultSchematicRepresentation();
        schematic.add(weightB);

        Force weightC = new Force(C, Vector3bd.UNIT_Y.negate(), new BigDecimal("25"));
        weightC.setName("Weight C");
        weightC.createDefaultSchematicRepresentation();
        schematic.add(weightC);

        ModelNode modelNode = ModelNode.load("bookfriction/assets/", "bookfriction/assets/bookfriction4.dae");
        modelNode.extractLights();

        ModelRepresentation rep;
        String prefix = "VisualSceneNode/scene/";

        rep = modelNode.getRemainder(schematic.getBackground());
        schematic.getBackground().addRepresentation(rep);

        rep = modelNode.extractElement(book1, prefix + "furnture/books/books_physics_book/");
        book1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(book2, prefix + "furnture/books/books_thermodynamics_book/");
        book2.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(book3, prefix + "furnture/books/books_statics_book/");
        book3.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);


    }

}
