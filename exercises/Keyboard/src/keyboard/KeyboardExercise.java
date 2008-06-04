/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keyboard;

import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.ModelRepresentation;

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
    }

    @Override
    public void loadExercise() {
        
        Schematic schematic = getSchematic();
        
        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.2f, .2f, .9f, 1.0f));
        
        // actually add all objects here
        
        Point A = new Point("0","0","0");
        Point B = new Point("10","5","0");
        
        A.setName("A");
        B.setName("B");
        
        Body strut1 = new Beam(A,B);
        
        
        // THIS DOES NOT ACTUALLY SEEM TO DO ANYTHING
        float scale = 1;
        
        Representation rep = new ModelRepresentation(strut1, "keyboard/assets/", "keyboard/assets/strut2.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut1, "keyboard/assets/", "keyboard/assets/strut1.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut1, "keyboard/assets/", "keyboard/assets/strut3.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        rep = new ModelRepresentation(strut1, "keyboard/assets/", "keyboard/assets/background.dae");
        rep.setLocalScale(scale);
        strut1.addRepresentation(rep);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        
        schematic.add(strut1);
        
        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        strut1.createDefaultSchematicRepresentation();
    }
    
    
}
