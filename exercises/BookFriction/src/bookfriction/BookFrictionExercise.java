/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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

        description.addImage("bookfriction/assets/bookfriction.png");

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
        jointA.setFrictionDirection(Vector3bd.UNIT_X.negate());
        jointA.setNormalDirection(Vector3bd.UNIT_Y);
        jointA.createDefaultSchematicRepresentation();
        jointA.attachToWorld(book1);
        schematic.add(jointA);

        ContactPoint jointB = new ContactPoint(B, frictionObjectB);
        jointB.setName("ContactB");
        jointB.setFrictionDirection(Vector3bd.UNIT_X.negate());
        jointB.setNormalDirection(Vector3bd.UNIT_Y);
        jointB.createDefaultSchematicRepresentation();
        jointB.attach(book2, book1);
        schematic.add(jointB);
        

        ContactPoint jointC = new ContactPoint(C, frictionObjectC);
        jointC.setName("ContactC");
        jointC.setFrictionDirection(Vector3bd.UNIT_X.negate());
        jointC.setNormalDirection(Vector3bd.UNIT_Y);
        jointC.createDefaultSchematicRepresentation();
        jointC.attach(book3, book2);
        schematic.add(jointC);

        Force forceB = new Force(B, Vector3bd.UNIT_X, "Force B");
        forceB.setName("Force B");
        forceB.createDefaultSchematicRepresentation();
        
        schematic.add(forceB);
        book2.addObject(forceB);
        
        Force weightA = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal("18"));
        weightA.setName("Weight A");
        weightA.createDefaultSchematicRepresentation();
        schematic.add(weightA);
        book1.addObject(weightA);

        Force weightB = new Force(B, Vector3bd.UNIT_Y.negate(), new BigDecimal("15"));
        weightB.setName("Weight B");
        weightB.createDefaultSchematicRepresentation();
        schematic.add(weightB);
        book2.addObject(weightB);

        Force weightC = new Force(C, Vector3bd.UNIT_Y.negate(), new BigDecimal("25"));
        weightC.setName("Weight C");
        weightC.createDefaultSchematicRepresentation();
        schematic.add(weightC);
        book3.addObject(weightC);

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

        rep = modelNode.extractElement(book2, prefix + "furnture/books/books_statics_book/");
        book2.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        rep = modelNode.extractElement(book3, prefix + "furnture/books/books_thermodynamics_book/");
        book3.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);


    }

}
