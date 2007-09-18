/*
 * PurseExercise.java
 *
 * Created on August 4, 2007, 11:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example01;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.joints.Connector2ForceMember2d;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.representations.ImageRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExercise extends Exercise {
    
    /** Creates a new instance of PurseExercise */
    public PurseExercise() {
        super(new ExerciseWorld());
    }

    public void initExercise() {
        setName("Holding a Purse");
        StaticsApplication.getApp().createDisplayGroup("Bones", "bones");
        
        setDescription(
                "<html><body>" +
                "<center><font size=\"6\">"+getName()+"</font></center>"+
                "Here is a simplified version of the human arm. Please build a Free Body Diagram of the Forearm, and solve for the tension in the tendon. " +
                "The weight of the forearm is 9 N." +
                "</body></html>"
                );
        setUnits( new Units() { {
                distance = "mm";
                moment = "N*mm";
                worldDistanceMultiplier = 10f;
            }
        });
    }
    
    public void loadExercise() {
        
        ExerciseWorld world = getWorld();
        
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .2f, 1.0f));
        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 0.0f, 55.0f ));
        StaticsApplication.getApp().setDrawScale(2f);
        
        
        
        Point A = new Point(new Vector3f(-17,-16+6,0));
        Connector2ForceMember2d B = new Connector2ForceMember2d(new Vector3f(13,-16+6,0));
        Pin2d C = new Pin2d(new Vector3f(18,-16+6,0));
        Connector2ForceMember2d D = new Connector2ForceMember2d(new Vector3f(18,13+6,0));
        Pin2d E = new Pin2d(new Vector3f(18f,16+6,0));
        
        Point G = new Point(new Vector3f(3.0f,-16+6,0));
        
        DistanceMeasurement distance1 = new DistanceMeasurement(A.getTranslation(), C.getTranslation());
        distance1.createDefaultSchematicRepresentation(6f);
        world.add(distance1);
        
        DistanceMeasurement distance2 = new DistanceMeasurement(G.getTranslation(), B.getTranslation());
        distance2.createDefaultSchematicRepresentation(3f);
        world.add(distance2);
        
        DistanceMeasurement distance3 = new DistanceMeasurement(B.getTranslation(), C.getTranslation());
        distance3.createDefaultSchematicRepresentation(3f);
        world.add(distance3);
        
        DistanceMeasurement distance4 = new DistanceMeasurement(C.getTranslation(), D.getTranslation());
        distance4.createDefaultSchematicRepresentation(3f);
        world.add(distance4);
        
        DistanceMeasurement distance5 = new DistanceMeasurement(D.getTranslation(), E.getTranslation());
        distance5.createDefaultSchematicRepresentation(3f);
        world.add(distance5);

        Body upperArm = new Beam(E,C);
        Body forearm = new Beam(C,A);
        Body tendon = new Cable(D,B);
        forearm.addObject(B);
        forearm.addObject(G);
        upperArm.addObject(D);
        
        Force purse = new Force(A, new Vector3f(0,-19.6f,0));
        forearm.addObject(purse);
        
        Moment shoulder = new Moment(E, new Vector3f(0,0,1)); // use symbol here
        shoulder.setName("M shoulder");
        upperArm.addObject(shoulder);
        shoulder.setSymbol(true);
        
        //Force weight = new Force(G, new Vector3f(0,-50,0));
        //forearm.addObject(weight);
        
        B.attach( forearm, tendon );
        B.setDirection(D.getTranslation().subtract(B.getTranslation())); // points up tendon
        C.attach( forearm, upperArm );
        D.attach( upperArm, tendon );
        D.setDirection(B.getDirection().negate());
        E.attach( upperArm, null );
        
        E.setName("E");
        D.setName("D");
        C.setName("C");
        B.setName("B");
        A.setName("A");
        G.setName("G");
        
        E.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        A.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        upperArm.createDefaultSchematicRepresentation();
        forearm.createDefaultSchematicRepresentation();
        tendon.createDefaultSchematicRepresentation();
        purse.createDefaultSchematicRepresentation();
        shoulder.createDefaultSchematicRepresentation();
        //weight.createDefaultSchematicRepresentation();
        
        forearm.setWeight(9); // ???
        forearm.setCenterOfMassPoint(G);
        
        world.add(upperArm);
        world.add(forearm);
        world.add(tendon);
        
        RepresentationLayer bones = new RepresentationLayer("bones", RepresentationLayer.modelBodies.getPriority()-1);
        RepresentationLayer.addLayer(bones);
        
        ImageRepresentation imageRep;
        //float repScale = .12f;
        float repScale = .025f;
        
        imageRep = new ImageRepresentation(forearm, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/lowerArm.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        //imageRep.setScale(repScale * 368f,repScale * 96f);
        imageRep.setScale(repScale * 1738,repScale * 501f);
        imageRep.setTranslation(-1,1.75f,0);
        forearm.addRepresentation(imageRep);
        
        imageRep = new ImageRepresentation(forearm, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/lowerArm_bone.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        imageRep.setLayer(bones);
        //imageRep.setScale(repScale * 368f,repScale * 96f);
        imageRep.setScale(repScale * 1617,repScale * 310f);
        imageRep.setTranslation(-2.25f,-0.5f,.02f);
        forearm.addRepresentation(imageRep);
        
        //repScale = .11f;
        //repScale = .03f;
        
        imageRep = new ImageRepresentation(upperArm, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/upperArm.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        //imageRep.setScale(repScale * 121f,repScale * 347f);
        imageRep.setScale(repScale * 483f,repScale * 1539f);
        imageRep.setTranslation(-0.75f,1.0f,0);
        upperArm.addRepresentation(imageRep);
        
        imageRep = new ImageRepresentation(upperArm, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/upperArm_bone.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        imageRep.setLayer(bones);
        //imageRep.setScale(repScale * 121f,repScale * 347f);
        imageRep.setScale(repScale * 191f,repScale * 1453f);
        imageRep.setTranslation(-0.5f,1,0);
        upperArm.addRepresentation(imageRep);
        
        //repScale = .04f;
        
        imageRep = new ImageRepresentation(A, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/purse.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        //imageRep.setScale(repScale * 180f,repScale * 240f);
        imageRep.setScale(repScale * 538f,repScale * 835f);
        imageRep.setTranslation(0,-11.0f,.02f);
        A.addRepresentation(imageRep);
        
        imageRep = new ImageRepresentation(tendon, 
                TextureManager.loadTexture(getClass().getClassLoader().getResource("example01/assets/muscle.png"),
                Texture.MM_LINEAR,Texture.FM_LINEAR)
                );
        imageRep.setLayer(bones);
        imageRep.setRotation(-.075f);
        //imageRep.setScale(repScale * 121f,repScale * 347f);
        imageRep.setScale(.9f*repScale * 213f, .9f*repScale * 1331f);
        imageRep.setTranslation(0.0f,0.5f,.05f);
        tendon.addRepresentation(imageRep);
    }
    
}
