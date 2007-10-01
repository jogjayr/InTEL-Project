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
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.PointBody;

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
        
        
        Point A = new Point(new Vector3f(-20,-12,0));
        Point B = new Point(new Vector3f(-10,12,0));
        Point C = new Point(new Vector3f(0,-12,0));
        Point D = new Point(new Vector3f(10,12,0));
        Point E = new Point(new Vector3f(20,-12,0));
        
        
        /*Point A = new Point(new Vector3f(-17,-16+6,0));
        Connector2ForceMember2d B = new Connector2ForceMember2d(new Vector3f(13,-16+6,0));
        Pin2d C = new Pin2d(new Vector3f(18,-16+6,0));
        Connector2ForceMember2d D = new Connector2ForceMember2d(new Vector3f(18,13+6,0));
        Pin2d E = new Pin2d(new Vector3f(18f,16+6,0));
        
        Point G = new Point(new Vector3f(3.0f,-16+6,0));
        */
        
        /*DistanceMeasurement distance1 = new DistanceMeasurement(A.getTranslation(), C.getTranslation());
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);*/
        
        Body AB = new Beam(A,B);
        Body BC = new Beam(B,C);
        Body CD = new Beam(C,D);
        Body DE = new Beam(D,E);
        Body AC = new Beam(A,C);
        Body BD = new Beam(B,D);
        Body CE = new Beam(C,E);
        
        Body BodyA = new PointBody(A);
        Body BodyB = new PointBody(B);
        Body BodyC = new PointBody(C);
        Body BodyD = new PointBody(D);
        Body BodyE = new PointBody(E);
        
        Force loadD = new Force(D,new Vector3f(0, -200, 0));
        loadD.createDefaultSchematicRepresentation();
        BodyD.addObject(loadD);
        
        A.setName("A");
        B.setName("B");
        C.setName("C");
        D.setName("D");
        E.setName("E");
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        E.createDefaultSchematicRepresentation();
        
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
