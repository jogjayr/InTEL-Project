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
import edu.gatech.statics.math.Vector;
import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.ArrowRepresentationFixedLength;
import edu.gatech.statics.objects.representations.LabelRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Force extends Load {
    //protected static final float labelDistance = 9.0f;
    /** Creates a new instance of Force */
    public Force(Point anchor, Vector value) {
        super(anchor, value);
        assert(value.getUnit() == Unit.force);
        //setName("F");
    }

    public Force(Point anchor, Vector3f value) {
        super(anchor, new Vector(Unit.force, value));
    }
    
    public Force(Point anchor, Vector3f value, String symbolName) {
        super(anchor, new Vector(Unit.force, value, symbolName));
    }
    
    public Force(Force force) {
        super(force);
        //setName(force.getName());
    }

    //@Override
    //public Unit getUnit() {
    //    return Unit.force;
    //}
    /*public Vector3f getDisplayCenter() {
    // this is kind of a hack, since we expect forces to have equal sizes
    // workaround how?
    return getTranslation().add( getValue().normalize().mult(labelDistance)  );
    }*/
    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentationFixedLength(this);
        addRepresentation(rep);

        rep.setDiffuse(ColorRGBA.red);
        rep.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));

        LabelRepresentation label = new LabelRepresentation(this, "label_force");
        addRepresentation(label);
    }
    //public String getDescription() {
    //    return "Force: "+getName()+"<br>" +
    //            "Magnitude: "+ getValue() + " "+getUnits();
    //}
    //public Force negate() {
    //    Force r = new Force(this);
    //    r.setValue(-getValue());
        /*Force r = new Force(getAnchor(), getVectorValue().negate());
    r.setSolved(isSolved());
    //r.setFixed(isFixed());
    r.setSymbol(isSymbol());
    return r;*/
    //}
    /*public String getLabelText() {
    if(isSymbol() && !isSolved())
    return getName();
    else return getMagnitude() + " "+StaticsApplication.getApp().getUnits().getForce();
    }*/
}
