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
 * Force.java
 *
 * Created on June 9, 2007, 3:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.math.Vector;
import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.ArrowRepresentationFixedLength;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class Force extends Load {
    //protected static final float labelDistance = 9.0f;
    /** Creates a new instance of Force */
    public Force(AnchoredVector vector) {
        super(vector);
        assert (vector.getUnit() == Unit.force);
    //setName("F");
    }

    public Force(Point anchor, Vector3bd value, BigDecimal magnitude) {
        this(new AnchoredVector(anchor, new Vector(Unit.force, value, magnitude)));
    //super(anchor, new Vector(Unit.force, value, magnitude));
    }

    public Force(Point anchor, Vector3bd value, String symbolName) {
        this(new AnchoredVector(anchor, new Vector(Unit.force, value, symbolName)));
    //super(anchor, new Vector(Unit.force, value, symbolName));
    }

    public Force(Force force) {
        super(force);
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new ArrowRepresentationFixedLength(this);
        addRepresentation(rep);

        rep.setDiffuse(ColorRGBA.red);
        rep.setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));

        LabelRepresentation label = new LabelRepresentation(this, "label_force");
        //label.setOffset(0, DisplayConstants.getInstance().getForceLabelDistance());
        addRepresentation(label);
    }

    @Override
    public Force clone() {
        return new Force(this);
    }
}
