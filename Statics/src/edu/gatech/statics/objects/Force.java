/*
 * Force.java
 *
 * Created on June 9, 2007, 3:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.representations.ArrowRepresentationFixedLength;
import edu.gatech.statics.objects.representations.LabelRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Force extends Vector {
    
    /** Creates a new instance of Force */
    public Force(Point anchor, Vector3f value) {
        super(anchor, value);
        setName("F");
    }

    public Vector3f getDisplayCenter() {
        // this is kind of a hack, since we expect forces to have equal sizes
        // workaround how?
        return getTranslation().add( getValue().normalize().mult(6.0f) );
    }
    

    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentationFixedLength(this);
        addRepresentation(rep);
        
        rep.setDiffuse(ColorRGBA.red);
        rep.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
        
        LabelRepresentation label = new LabelRepresentation(this);
        label.setDiffuse(ColorRGBA.red);
        label.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
        addRepresentation(label);
    }
    
    public String getDescription() {
        return "Force: "+getName()+"<br>" +
                "Magnitude: "+ getValue().length() + " "+StaticsApplication.getApp().getUnits().getForce();
    }
    
    public Force negate() {
        return new Force(getAnchor(), getValue().negate());
    }

    public String getLabelText() {
        if(isSymbol())
            return getName();
        else return getMagnitude() + " "+StaticsApplication.getApp().getUnits().getForce();
    }
    
}
