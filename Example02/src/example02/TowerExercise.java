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
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import edu.gatech.newcollada.ColladaImporter;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.UnitUtils;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Fix2d;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class TowerExercise extends FBDExercise {
    
    public InterfaceConfiguration createInterfaceConfiguration() {
        return new DefaultInterfaceConfiguration();
    }

    /** Creates a new instance of TowerExercise */
    public TowerExercise() {
        super(new Schematic());
    }
    
    @Override
    public void initExercise() {
        setName("Tower of Pisa");
        
        setDescription(
                "This is a model of the tower of Pisa, solve for the reaction forces at its base. " +
                "The tower's weight is 14700 tons."
                );
        Unit.setUtils(new UnitUtils() {

            @Override
            public String getSuffix(Unit unit) {
                switch (unit) {
                    case angle:
                        return "�";
                    case distance:
                        return " cm";
                    case force:
                        return " N";
                    case moment:
                        return " N*cm";
                    case none:
                        return "";
                    default:
                        throw new IllegalArgumentException("Unrecognized unit: " + unit);
                }
            }
        });
    }

    @Override
    public float getDrawScale() {
        return 3.5f;
    }
    
    @Override
    public void loadExercise() {
        //ExerciseWorld world = getWorld();
        Schematic world = getSchematic();
        
        
        //DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.9f, .9f, .9f, 1.0f));
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.0f, .0f, .0f, 1.0f));
        
        //StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 10.0f, 60.0f ));
        StaticsApplication.getApp().getCamera().setLocation(new Vector3f( 0.0f, 24.0f, 100.0f ));
        //StaticsApplication.getApp().setDrawScale(3.5f);
        
        Point A = new Point(new Vector3f(0,0,0));
        Point B = new Point(new Vector3f(2.5f,55,0));
        Point G = new Point(A.getTranslation().add(B.getTranslation()).mult(.5f));
        A.setName("A");
        B.setName("B");
        G.setName("G");
        
        Fix2d jointA = new Fix2d(A);
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();
        
        Body tower = new Beam(A,B);
        tower.setCenterOfMassPoint(G);
        tower.createDefaultSchematicRepresentation();
        tower.getWeight().setValue(14700f);
        world.add(tower);
        
        jointA.attachToWorld(tower);
        
        float scale = 4f;
        
        ModelRepresentation rep;
        rep = new ModelRepresentation(A, "example02/assets/", "example02/assets/pisa1_background.dae");
        //rep.setLocalScale(scale);
        A.addRepresentation(rep);
        
        rep = new ModelRepresentation(tower, "example02/assets/", "example02/assets/pisa3_tower.dae");
        rep.setModelOffset(new Vector3f(0, -22.0f/scale, 0));
        //rep.setLocalScale(scale*1.5f);
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
