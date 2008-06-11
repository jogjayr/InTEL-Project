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
    
    Point A, I, H, J;
    
    @Override
    public float getDrawScale() {
        return 0.2f;
    }

    @Override
    public void loadExercise() { 
        Schematic schematic = getSchematic();
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        
        A = new Point("-2.2","1.7","0");
        I = new Point("-0.1","5.3","0");
        H = new Point("-0.65","4.35","0");
        J = new Point("2.35","4.35","0");
        Body handlebar = new Beam(I, A);
        handlebar.setName("Handle Bar");
        Body top = new Beam(J, H);
        top.setName("Top Bar");
        
        DistanceMeasurement distance1 = new DistanceMeasurement(I, A);
        distance1.createDefaultSchematicRepresentation(0.5f);
        distance1.forceVertical();
        schematic.add(distance1);
        
        Moment handle = new Moment(I, Vector3bd.UNIT_Z.negate(), "I handlebar"); // use symbol here
        handle.setSymbol("Handlebar");
        handlebar.addObject(handle);
        
        A.setName("A");
        I.setName("I");
        H.setName("H");
        J.setName("J");
        
        A.createDefaultSchematicRepresentation();
        I.createDefaultSchematicRepresentation();
        H.createDefaultSchematicRepresentation();
        J.createDefaultSchematicRepresentation();
        handlebar.createDefaultSchematicRepresentation();
        handle.createDefaultSchematicRepresentation();
        top.createDefaultSchematicRepresentation();
        
        schematic.add(handlebar);
        schematic.add(top);
        
        float scale = 0.1f;
        
        Representation rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/handlepole.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/handlebar.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/back.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
//        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/body.dae");
//        rep.setLocalScale(scale);
//        handlebar.addRepresentation(rep);
//        rep.setSynchronizeRotation(false);
//        rep.setSynchronizeTranslation(false);
//        
//        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/bike.dae");
//        rep.setLocalScale(scale);
//        handlebar.addRepresentation(rep);
//        rep.setSynchronizeRotation(false);
//        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/bottom.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/front.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/seat.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/seatpole.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(handlebar, "bicycle/assets/", "bicycle/assets/top.dae");
        rep.setLocalScale(scale);
        handlebar.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
    }
}