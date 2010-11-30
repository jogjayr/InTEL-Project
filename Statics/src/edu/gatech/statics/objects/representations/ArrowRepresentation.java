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
