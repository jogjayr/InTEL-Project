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
import edu.gatech.statics.application.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
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
                "<html><body>" +
                "<center><font size=\"6\">"+getName()+"</font></center>"+
                "Bridge 01" +
                "</body></html>"
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
        
        
        Point A = new Pin2d(new Vector3f(-20,-12,0));
        Point B = new Point(new Vector3f(-10,12,0));
        Point C = new Point(new Vector3f(0,-12,0));
        Point D = new Point(new Vector3f(10,12,0));
        Point E = new Roller2d(new Vector3f(20,-12,0));
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        
        /*DistanceMeasurement distance1 = new DistanceMeasurement(A.getTranslation(), C.getTranslation());
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);*/
        
        Beam AB = new Beam(A,B);
        Beam BC = new Beam(B,C);
        Beam CD = new Beam(C,D);
        Beam DE = new Beam(D,E);
        Beam AC = new Beam(A,C);
        Beam BD = new Beam(B,D);
        Beam CE = new Beam(C,E);
        
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
        
        ((Pin2d)A).attachToWorld(BodyA);
        ((Roller2d)E).attachToWorld(BodyE);
        ((Roller2d)E).setDirection(Vector3f.UNIT_Y);
        
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
