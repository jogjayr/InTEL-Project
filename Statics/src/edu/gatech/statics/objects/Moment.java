/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.math.AnchoredVector;
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
    public Moment(AnchoredVector vector) {
        super(vector);
        assert (vector.getUnit() == Unit.moment);
    //setName("F");
    }

    public Moment(Point anchor, Vector3bd value, BigDecimal magnitude) {
        this(new AnchoredVector(anchor, new Vector(Unit.moment, value, magnitude)));
    //super(anchor, new Vector(Unit.force, value, magnitude));
    }

    public Moment(Point anchor, Vector3bd value, String symbolName) {
        this(new AnchoredVector(anchor, new Vector(Unit.moment, value, symbolName)));
    //super(anchor, new Vector(Unit.force, value, symbolName));
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
        label.setOffset(0, DisplayConstants.getInstance().getMomentLabelDistance());
        addRepresentation(label);
    }

    @Override
    public Moment clone() {
        return new Moment(this);
    }
}
