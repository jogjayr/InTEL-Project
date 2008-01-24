/*
 * Bridge01.java
 * 
 * Created on Sep 30, 2007, 12:13:17 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example03;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;

/**
 *
 * @author Calvin Ashmore
 */
public class Bridge01 extends Exercise {
    public Bridge01() {
        super(new ExerciseWorld());
    }

    public void initExercise() {
        setName("Bridge 01");
        //StaticsApplication.getApp().createDisplayGroup("Bones", "bones");
        
        setDescription(
                "Bridge 01"
                );
        setUnits( new Units() { {
                distance = "m";
                moment = "N*m";
                worldDistanceMultiplier = 1f;
            }
        });
    }
    
    
    public void loadExercise() {
        
        ExerciseWorld world = getWorld();
        
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 0.0f, 55.0f ));
        StaticsApplication.getApp().setDrawScale(2f);
        
        
        Point A = new Point(new Vector3f(-20,-12,0));
        Point B = new Point(new Vector3f(-10,12,0));
        Point C = new Point(new Vector3f(0,-12,0));
        Point D = new Point(new Vector3f(10,12,0));
        Point E = new Point(new Vector3f(20,-12,0));
        
        Pin2d jointA = new Pin2d(A);
        Roller2d jointE = new Roller2d(E);
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        
        /*DistanceMeasurement distance1 = new DistanceMeasurement(A.getTranslation(), C.getTranslation());
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);*/
        
        Bar AB = new Bar(A,B);
        Bar BC = new Bar(B,C);
        Bar CD = new Bar(C,D);
        Bar DE = new Bar(D,E);
        Bar AC = new Bar(A,C);
        Bar BD = new Bar(B,D);
        Bar CE = new Bar(C,E);
        
        PointBody BodyA = new PointBody(A);
        PointBody BodyB = new PointBody(B);
        PointBody BodyC = new PointBody(C);
        PointBody BodyD = new PointBody(D);
        PointBody BodyE = new PointBody(E);
        
        BodyA.connectToTwoForceMembers(AB, AC);
        BodyB.connectToTwoForceMembers(AB, BC, BD);
        BodyC.connectToTwoForceMembers(BC, CD, AC, CE);
        BodyD.connectToTwoForceMembers(CD, DE, BD);
        BodyE.connectToTwoForceMembers(DE, CE);
        //BodyA.connectToTwoForceMembers();
        
        BodyA.createDefaultSchematicRepresentation();
        BodyB.createDefaultSchematicRepresentation();
        BodyC.createDefaultSchematicRepresentation();
        BodyD.createDefaultSchematicRepresentation();
        BodyE.createDefaultSchematicRepresentation();
        
        jointA.attachToWorld(BodyA);
        jointE.attachToWorld(BodyE);
        jointE.setDirection(Vector3f.UNIT_Y);
        
        Force loadD = new Force(D,new Vector3f(0, -200, 0));
        loadD.createDefaultSchematicRepresentation();
        BodyD.addObject(loadD);
        
        A.setName("A");
        B.setName("B");
        C.setName("C");
        D.setName("D");
        E.setName("E");
        
        AB.createDefaultSchematicRepresentation();
        BC.createDefaultSchematicRepresentation();
        CD.createDefaultSchematicRepresentation();
        DE.createDefaultSchematicRepresentation();
        AC.createDefaultSchematicRepresentation();
        BD.createDefaultSchematicRepresentation();
        CE.createDefaultSchematicRepresentation();
        
        world.add(BodyA);
        world.add(BodyB);
        world.add(BodyC);
        world.add(BodyD);
        world.add(BodyE);
        world.add(AB);
        world.add(BC);
        world.add(CD);
        world.add(DE);
        world.add(AC);
        world.add(BD);
        world.add(CE);
    }
}
