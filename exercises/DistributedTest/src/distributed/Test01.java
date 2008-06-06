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
import edu.gatech.statics.modes.distributed.objects.QuarterEllipseDistributedForce;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
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
    }

    @Override
    public void loadExercise() {
        Schematic schematic = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        
        Point A = new Point("-5", "0", "0");
        Point B = new Point("5", "0", "0");

        Beam beam = new Beam(A, B);
        beam.setName("beam");

        QuarterEllipseDistributedForce distributedForce = new QuarterEllipseDistributedForce(beam, A, B,
                new Vector(Unit.forceOverDistance, Vector3bd.UNIT_Y, new BigDecimal(100)));
        beam.addObject(distributedForce);

        Pin2d pin = new Pin2d(A);
        pin.attachToWorld(beam);
        Roller2d roller = new Roller2d(B);
        roller.setDirection(Vector3bd.UNIT_Y);
        roller.attachToWorld(beam);
        
        schematic.add(beam);
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        beam.createDefaultSchematicRepresentation();
        distributedForce.createDefaultSchematicRepresentation(4,10);
        
        pin.createDefaultSchematicRepresentation();
        roller.createDefaultSchematicRepresentation();
    }
}
