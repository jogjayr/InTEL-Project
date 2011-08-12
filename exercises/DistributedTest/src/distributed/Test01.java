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
package distributed;

import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.distributed.DistributedExercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.QuarterEllipseDistributedForce;
import edu.gatech.statics.modes.distributed.objects.TriangularDistributedForce;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class Test01 extends DistributedExercise {

    @Override
    public void initExercise() {
        setName("Distributed Test 01");
        setDescription("A simple test for distributed loads. Solve for the unknowns in the FBD.");
        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setJointSize(0.5f);
        getDisplayConstants().setForceLabelDistance(1f);
        getDisplayConstants().setMomentLabelDistance(0f);
        getDisplayConstants().setMeasurementSize(0.1f);
        
        Unit.setPrecision(Unit.distance, 2);
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.8f, .8f, .8f, 1.0f));

        Point A = new Point("-4", "-3", "0");
        Point B = new Point("0", "0", "0");
        Point C = new Point("4", "3", "0");
        //Point D = new Point("-4", "-1", "-4");
        //Point E = new Point("-4", "-1", "-8");

        A.setName("A");
        B.setName("B");
        C.setName("C");

        Beam beam = new Beam(A, C);
        beam.addObject(B);
        beam.setName("beam 1");

        //Beam beam2 = new Beam(A, E);
        //beam.addObject(D);
        //beam.setName("beam 2");

        //Vector3bd direction = Vector3bd.UNIT_Y.negate();
        Vector3bd direction = new Vector3bd("3", "-4", "0");


        DistributedForce distributedForce1 = new QuarterEllipseDistributedForce(beam, A, B,
                new Vector(Unit.forceOverDistance, direction, new BigDecimal(100)));

        DistributedForce distributedForce2 = new TriangularDistributedForce(beam, C, B,
                new Vector(Unit.forceOverDistance, direction, new BigDecimal(100)));

        beam.addObject(distributedForce1);
        beam.addObject(distributedForce2);
        
        DistanceMeasurement measure1 = new DistanceMeasurement(A, B);
        measure1.createDefaultSchematicRepresentation();
        schematic.add(measure1);
        
        DistanceMeasurement measure2 = new DistanceMeasurement(B,C);
        measure2.createDefaultSchematicRepresentation();
        schematic.add(measure2);
        
        /*DistributedForce distributedForce3 = new QuarterEllipseDistributedForce(beam, A, D,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal(100)));

        DistributedForce distributedForce4 = new TriangularDistributedForce(beam, E, D,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y.negate(), new BigDecimal(100)));*/

        beam.addObject(distributedForce1);
        beam.addObject(distributedForce2);
        
        //beam2.addObject(distributedForce3);
        //beam2.addObject(distributedForce4);

        Pin2d pin = new Pin2d(A);
        pin.attachToWorld(beam);
        Roller2d roller = new Roller2d(C);
        roller.setDirection(Vector3bd.UNIT_Y);
        roller.attachToWorld(beam);

        schematic.add(beam);
        //schematic.add(beam2);

        A.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        beam.createDefaultSchematicRepresentation();
        distributedForce1.createDefaultSchematicRepresentation(4, 10);
        distributedForce2.createDefaultSchematicRepresentation(4, 10);
        //distributedForce.setDisplayGrayed(true);
        
        /*D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        beam2.createDefaultSchematicRepresentation();
        distributedForce3.createDefaultSchematicRepresentation(4, 10);
        distributedForce4.createDefaultSchematicRepresentation(4, 10);*/

        pin.createDefaultSchematicRepresentation();
        roller.createDefaultSchematicRepresentation();
    }
}
