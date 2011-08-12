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
 * ArrowRepresentation.java
 *
 * Created on June 30, 2007, 1:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.bounding.BoundingBox;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.objects.VectorObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ArrowRepresentation extends Representation<VectorObject> {

    //private DoubleArrow arrow;
    private Arrow arrow;
    protected float axisOffset = 0;

    public void setAxisOffset(float axisOffset) {
        this.axisOffset = axisOffset;
    }

    public float getAxisOffset() {
        return axisOffset;
    }
    public float getLength() {
        return arrow.getLength();
    }

    public ArrowRepresentation(VectorObject target) {
        this(target, true);
    }

    /** Creates a new instance of ArrowRepresentation */
    public ArrowRepresentation(VectorObject target, boolean hasTip) {
        super(target);
        arrow = new Arrow(hasTip);
        //arrow = new DoubleArrow(hasTip);
        setLayer(RepresentationLayer.vectors);
        getRelativeNode().attachChild(arrow);

        //setModelBound(new OrientedBoundingBox());
        setModelBound(new BoundingBox());
        update();
        updateModelBound();
        updateRenderState();
    }

    @Override
    public final void update() {

        super.update();
        arrow.setLocalTranslation(0, 0, axisOffset);
        setMagnitude((float) getTarget().getAnchoredVector().doubleValue());
        setLocalScale(DisplayConstants.getInstance().getForceSize());

        super.updateRepresentationBounds();
        //updateGeometricState(0, true);
    }

    protected void setMagnitude(float magnitude) {
        arrow.setLength(magnitude);
    }
}
