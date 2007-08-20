/*
 * TowerExercise.java
 *
 * Created on August 11, 2007, 10:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example02;

import com.jme.light.Light;
import com.jme.light.LightNode;
import com.jme.math.Vector3f;
import com.jme.scene.state.LightState;
import edu.gatech.newcollada.ColladaImporter;
import edu.gatech.statics.application.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Fix2d;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class TowerExercise extends Exercise {
    
    /** Creates a new instance of TowerExercise */
    public TowerExercise() {
        super(new ExerciseWorld());
    }
    
    public void initExercise() {
        ExerciseWorld world = getWorld();
        
        
        setDescription(
                "<html><body>" +
                "This is the tower of Pisa, solve for the reaction forces at its base." +
                "</body></html>"
                );
        setUnits( new Units() { {
                distance = "m";
                moment = "N*m";
                worldDistanceMultiplier = 1f;
            }
        });
        
        
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 20.0f, 60.0f ));
        StaticsApplication.getApp().setDrawScale(2f);
        
        Point A = new Fix2d(new Vector3f(0,0,0));
        Point B = new Point(new Vector3f(0,22,0));
        Point G = new Point(A.getTranslation().add(B.getTranslation()).mult(.5f));
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        
        Body tower = new Beam(A,B);
        tower.setCenterOfMassPoint(G);
        tower.createDefaultSchematicRepresentation();
        world.add(tower);
        
        ModelRepresentation rep;
        rep = new ModelRepresentation(A, "example02/assets/", "example02/assets/pisa1_background.dae");
        rep.setLocalScale(.25f);
        A.addRepresentation(rep);
        
        rep = new ModelRepresentation(A, "example02/assets/", "example02/assets/pisa3_tower.dae");
        rep.setLocalScale(.25f);
        //rep.setS
        tower.addRepresentation(rep);
        
        
        
        LightState lightState = rep.getLayer().getLightState();
        lightState.detachAll();
        
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("example02/assets/pisa2_lights.dae");
        URL textureUrl = getClass().getClassLoader().getResource("example02/assets/");
        ColladaImporter.load(inputStream, textureUrl, "lights");
        for(String name : ColladaImporter.getLightNodeNames()) {
            LightNode ln = ColladaImporter.getLightNode(name);
            Light light = ln.getLight();
            lightState.attach(light);
        }
    }
}
