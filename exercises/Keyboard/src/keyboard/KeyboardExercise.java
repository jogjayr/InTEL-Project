/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keyboard;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.joints.Connector2ForceMember2d;
import edu.gatech.statics.objects.joints.Fix2d;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class KeyboardExercise extends FBDExercise {

    public KeyboardExercise() {
        super(new Schematic());
    }

    @Override
    public void initExercise() {
        setName("Keyboard Stand");
        
        setDescription(
                "This is a keyboard stand!"
                );
        
        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.moment, " N*m");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));       
    }
    
    Point A, B, C, D, E, P, Q;
    Pin2d jointA, jointC, jointD;
    Roller2d jointB, jointE;
    Fix2d jointP, jointQ;
    
    @Override
    public float getDrawScale() {
        return 0.2f;
    }

    @Override
    public void loadExercise() { 
        Schematic schematic = getSchematic();
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .9f, 1.0f));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 0.0f, 65.0f));
        
        A = new Point("-3","0","0");
        B = new Point("3","4.3","0");
        C = new Point("0","2.15","0");
        D = new Point("-3","4.3","0");
        E = new Point("3","0","0");
        P = new Point("1","2.9","0");
        Q = new Point("-1","2.9","0");
        
        Body strut1 = new Beam(B,A);
        Body strut2 = new Beam(D,B);
        Body strut3 = new Beam(P,Q);
        Body strut4 = new Beam(E,D);
        
        strut1.setName("Strut 1");
        strut2.setName("Strut 2");
        strut3.setName("Strut 3");
        strut4.setName("Strut 4");
        
        jointC = new Pin2d(C);
        jointD = new Pin2d(D);
        
        jointB = new Roller2d(B);
                
        jointP = new Fix2d(P);
        jointQ = new Fix2d(Q);
        
        DistanceMeasurement distance2 = new DistanceMeasurement(B, D);
        distance2.createDefaultSchematicRepresentation(.5f);
        schematic.add(distance2);
        
        jointB.attach(strut1, strut2);
        jointC.attach(strut1, strut4);
        jointD.attach(strut2, strut3);
        jointP.attach(strut1, strut3);
        jointQ.attach(strut3, strut4);
        
        A.setName("A");
        B.setName("B");
        C.setName("C");
        D.setName("D");
        E.setName("E");
        P.setName("P");
        Q.setName("Q");
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        P.createDefaultSchematicRepresentation();
        Q.createDefaultSchematicRepresentation();
        
        strut1.createDefaultSchematicRepresentation();
        strut1.setCenterOfMassPoint(C);
        strut2.createDefaultSchematicRepresentation();
        strut3.createDefaultSchematicRepresentation();
        strut4.createDefaultSchematicRepresentation();
        
        schematic.add(strut1);
        schematic.add(strut2);
        schematic.add(strut3);
        schematic.add(strut4);
        
        // THIS DOES NOT ACTUALLY SEEM TO DO ANYTHING
        float scale = 0.1f;
        
        Representation rep = new ModelRepresentation(strut1, "keyboard/assets/", "keyboard/assets/strut1.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut4, "keyboard/assets/", "keyboard/assets/strut2.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut3, "keyboard/assets/", "keyboard/assets/strut3.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut4, "keyboard/assets/", "keyboard/assets/background.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
    }
}