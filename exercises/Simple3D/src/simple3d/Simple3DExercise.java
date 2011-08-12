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
package simple3d;

import edu.gatech.statics.exercise.Ordinary3DExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.CoordinateSystem;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Potato;
import edu.gatech.statics.objects.bodies.representations.BoxRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author gtg126z
 */
public class Simple3DExercise extends Ordinary3DExercise {

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Simple 3D");
        description.setNarrative("This is a simple 3d exercise.");

        return description;
    }

    @Override
    public void initExercise() {
        super.initExercise();
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        Potato potato = new Potato("board");

        potato.addRepresentation(new BoxRepresentation(potato, 2, .1f, 1));


        Point G = new Point("G", "0", "0", "0");
        Point A = new Point("A", "-1", "0", "-1");
        Point B = new Point("B", "0", "0", "1");
        Point C = new Point("C", "2", "0", "0");

        Force forceG = new Force(G, Vector3bd.UNIT_Y.negate(), new BigDecimal("500"));
        forceG.setName("weight");
        Force forceA = new Force(A, Vector3bd.UNIT_Y, "Alice");
        forceA.setName("Alice");
        Force forceB = new Force(B, Vector3bd.UNIT_Y, "Bob");
        forceB.setName("Bob");
        Force forceC = new Force(C, Vector3bd.UNIT_Y, "Cathy");
        forceC.setName("Cathy");

        potato.addObject(G);
        potato.addObject(A);
        potato.addObject(B);
        potato.addObject(C);
        potato.addObject(forceG);
        potato.addObject(forceA);
        potato.addObject(forceB);
        potato.addObject(forceC);

        G.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();

        forceG.createDefaultSchematicRepresentation();
        forceA.createDefaultSchematicRepresentation();
        forceB.createDefaultSchematicRepresentation();
        forceC.createDefaultSchematicRepresentation();

        schematic.add(potato);
//        schematic.add(G);
//        schematic.add(A);
//        schematic.add(B);
//        schematic.add(C);


        CoordinateSystem coords = new CoordinateSystem(true);
        coords.createDefaultSchematicRepresentation();
        schematic.add(coords);
    }
}
