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
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.MomentRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class Moment extends Load {

    /** Creates a new instance of Moment */
    public Moment(Point anchor, Vector value) {
        super(anchor, value);
    //setName("M");
    }

    public Moment(Point anchor, Vector3bd value, BigDecimal magnitude) {
        super(anchor, new Vector(Unit.moment, value, magnitude));
    }

    public Moment(Point anchor, Vector3bd value, String symbolName) {
        super(anchor, new Vector(Unit.moment, value, symbolName));
    }
    
    public Moment(Moment moment) {
        super(moment);
    }

    @Override
    public Vector3f getDisplayCenter() {
        // this is kind of a hack, since we expect forces to have equal sizes
        // workaround how?
        return getTranslation().add(0, 1, 0);
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new MomentRepresentation(this);
        addRepresentation(rep);

        rep.setDiffuse(ColorRGBA.red);
        rep.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));

        LabelRepresentation label = new LabelRepresentation(this, "label_force");
//        label.setOffset(0, (StaticsApplication.getApp().getMomentScale()*40)/2);
//        label.setOffset(0, 40);
        label.setOffset(0, StaticsApplication.getApp().getMomentLabelScale());
        addRepresentation(label);
    }

    @Override
    public Moment clone() {
        return new Moment(this);
    }
}
