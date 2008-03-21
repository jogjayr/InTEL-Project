/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package manipulatableexercises;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.manipulatable.ManipulatableExercise;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.UnitUtils;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;

/**
 *
 * @author Calvin Ashmore
 */
public class Manipulate01 extends ManipulatableExercise {

    
    @Override
    public void initExercise() {
        setName("Manipulatable Exercise 01");

        setDescription("No Description");
        Unit.setUtils(new UnitUtils() {

            @Override
            public String getSuffix(Unit unit) {
                switch (unit) {
                    case angle:
                        return "°";
                    case distance:
                        return " m";
                    case force:
                        return " N";
                    case moment:
                        return " N*m";
                    case none:
                        return "";
                    default:
                        throw new IllegalArgumentException("Unrecognized unit: " + unit);
                }
            }
        });
    }
    
    @Override
    public void loadExercise() {
        
        Schematic schematic = getSchematic();
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
        
        Point A = new Point(new Vector3f(1, -5, 0));
        Point B = new Point(new Vector3f(-.5f, 2.5f, 0));
        Point C = new Point(new Vector3f(-1, 5, 0));
        
        Body bar = new Beam(A,C);
        bar.setName("bar");
        
        bar.addObject(B);
        bar.createDefaultSchematicRepresentation();
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        
        Force f = new Force(B, new Vector3f(5,0,0));
        f.createDefaultSchematicRepresentation();
        bar.addObject(f);
        
        schematic.add(bar);
        
    }
    
}
