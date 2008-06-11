/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bicycle;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Connector2ForceMember2d;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class BicycleExercise extends FBDExercise {

    public BicycleExercise() {
        super(new Schematic());
    }

    @Override
    public void initExercise() {
        setName("Bicycle");
        
        setDescription(
                "Someone pushing against a wedge on a bike!"
                );
        
        Unit.setSuffix(Unit.distance, " mm");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));       
    }
    
    Point A, I;
    
    @Override
    public float getDrawScale() {
        return 0.2f;
    }

    @Override
    public void loadExercise() { 
        Schematic schematic = getSchematic();
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        
        A = new Point("-2.9","4.3","0");
        I = new Point("2.9","4.3","0");
        Body handlebar = new Beam(I, A);
        handlebar.setName("Handle Bar");
        
        DistanceMeasurement distance1 = new DistanceMeasurement(I, A);
        distance1.createDefaultSchematicRepresentation(0.5f);
        distance1.forceVertical();
        schematic.add(distance1);
        
        Moment handle = new Moment(I, Vector3bd.UNIT_Z.negate(), "I handlebar"); // use symbol here
        handle.setSymbol("Handlebar");
        handlebar.addObject(handle);
        
        A.setName("A");
        I.setName("I");
        
        A.createDefaultSchematicRepresentation();
        I.createDefaultSchematicRepresentation();
        handlebar.createDefaultSchematicRepresentation();
        
        schematic.add(handlebar);
        
        float scale = 0.1f;
        
        Representation rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/handlebar.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
    }
}