/*
 * Moment.java
 *
 * Created on June 9, 2007, 3:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.MomentRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Moment extends Vector {
    
    /** Creates a new instance of Moment */
    public Moment(Point anchor, Vector3f value) {
        super(anchor, value);
        setName("M");
    }

    public Vector3f getDisplayCenter() {
        // this is kind of a hack, since we expect forces to have equal sizes
        // workaround how?
        return getTranslation().add(0,1,0 );
    }    

    public void createDefaultSchematicRepresentation() {
        Representation rep = new MomentRepresentation(this);
        addRepresentation(rep);
        
        rep.setDiffuse(ColorRGBA.red);
        rep.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
        
        LabelRepresentation label = new LabelRepresentation(this);
        label.setDiffuse(ColorRGBA.red);
        label.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
        addRepresentation(label);
    }
    
    public String getDescription() {
        return "Moment: "+getName()+"<br>" +
                "Magnitude: "+ getValue().length() + " "+StaticsApplication.getApp().getUnits().getMoment();
    }
    
    public Moment negate() {
        return new Moment(getAnchor(), getValue().negate());
    }

    public String getLabelText() {
        if(isSymbol())
            return getName();
        else return getMagnitude() + " "+StaticsApplication.getApp().getUnits().getMoment();
    }
}