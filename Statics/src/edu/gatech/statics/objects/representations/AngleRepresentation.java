/*
 * AngleRepresentation.java
 *
 * Created on July 23, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.objects.AngleMeasurement;

/**
 *
 * @author Calvin Ashmore
 */
public class AngleRepresentation extends Representation<AngleMeasurement> {

    //private float edgeWidth = .5f; // world units- should fix
    private float margin = 4f; // pixels
    //private float arrowsize = 6f; // pixels
    private float offset; //world units

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }
    private LabelRepresentation label;
    protected Vector3f labelCenter = new Vector3f();
    boolean drawLeftBar = true, drawRightBar = true;
    //boolean drawLabel = true;

    public void drawBars(boolean left, boolean right) {
        drawLeftBar = left;
        drawRightBar = right;
    }
    private ColorRGBA color = ColorRGBA.black;

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    /** Creates a new instance of AngleRepresentation */
    public AngleRepresentation(AngleMeasurement target) {
        super(target);

        setLayer(RepresentationLayer.measurement);

        offset = 1f; // world coordinates for now...

        setSynchronizeRotation(false);
        setSynchronizeTranslation(false);

        label = new LabelRepresentation(target, "label_measurement") {

            @Override
            protected Vector3f getDisplayCenter() {
                return labelCenter;
            }
        };
        getRelativeNode().attachChild(label);

        setUseWorldScale(false);

        update();
        updateRenderState();
    }

    @Override
    public void update() {
        super.update();
        label.update();
        label.setAmbient(color);
        label.setDiffuse(color);
    }
    // keeping these like this is rather unseemly, but
    // this class is was a leading cause of busywork for the GC in terms of 
    // Vector3f objects that were churned through.
    // We keep these cached here so that they don't churn.
    //private static Vector3f worldPointDifference, offsetDirection, scaledOffsetDirection;
    private static Vector3f p1, p2, pCenter, pDirection;
    //private static Vector3f arrowOffset, measureExtent, measureExtentArrowHead;
    //private static Vector3f arrow1, arrow2, arrowHead1, arrowHead2, arrowHead1a, arrowHead2a;
    private static Vector3f barOffset1, barOffset2, bar1, bar2;
    private static Vector3f midVector, midVector1, midVector2;
    //private static Vector3f perpindicular, xUnit, yUnit;

    static {
        //worldPointDifference = new Vector3f();
        //offsetDirection = new Vector3f();
        //scaledOffsetDirection = new Vector3f();
        p1 = new Vector3f();
        p2 = new Vector3f();
        pCenter = new Vector3f();
        pDirection = new Vector3f();
        //arrowOffset = new Vector3f();
        //measureExtent = new Vector3f();
        //measureExtentArrowHead = new Vector3f();
        //arrow1 = new Vector3f();
        //arrow2 = new Vector3f();
        //arrowHead1 = new Vector3f();
        //arrowHead2 = new Vector3f();
        //arrowHead1a = new Vector3f();
        //arrowHead2a = new Vector3f();
        barOffset1 = new Vector3f();
        barOffset2 = new Vector3f();
        bar1 = new Vector3f();
        bar2 = new Vector3f();
        midVector = new Vector3f();
        midVector1 = new Vector3f();
        midVector2 = new Vector3f();
        //perpindicular = new Vector3f();
        //xUnit = new Vector3f();
        //yUnit = new Vector3f();
    }

    @Override
    public void draw(Renderer r) {
        super.draw(r);

        if (!StaticsApplication.getApp().getDrawLabels()) {
            return;
        }

        float edgeWidth = DisplayConstants.getInstance().getMeasurementBarSize();

        final Vector3f axis1 = getTarget().getAxis1();
        final Vector3f axis2 = getTarget().getAxis2();
        final Vector3f anchor = getTarget().getAnchor().getTranslation();
        //final float angle = getTarget().getValue();

        //perpindicular.set(axis1);
        //perpindicular.crossLocal(axis2);
        //perpindicular.normalizeLocal();

        // p1 and p2 are the endpoint centers
        p1.set(axis1);
        p1.multLocal(offset);
        p1.addLocal(anchor);
        p2.set(axis2);
        p2.multLocal(offset);
        p2.addLocal(anchor);

        barOffset1.set(axis1);
        barOffset2.set(axis1);
        barOffset1.multLocal(+edgeWidth / 2);
        barOffset2.multLocal(-edgeWidth / 2);

        // draw bars first
        if (drawLeftBar) {
            bar1.set(p1);
            bar2.set(p1);
            bar1.addLocal(barOffset1);
            bar2.addLocal(barOffset2);
            CurveUtil.renderLine(r, color, bar1, bar2);
        }

        barOffset1.set(axis2);
        barOffset2.set(axis2);
        barOffset1.multLocal(+edgeWidth / 2);
        barOffset2.multLocal(-edgeWidth / 2);

        if (drawRightBar) {
            bar1.set(p2);
            bar2.set(p2);
            bar1.addLocal(barOffset1);
            bar2.addLocal(barOffset2);
            CurveUtil.renderLine(r, color, bar1, bar2);
        }

        // pDirection points towards the midpoint of the two axes.
        pDirection.set(axis1);
        pDirection.addLocal(axis2);
        pDirection.multLocal(.5f);
        pDirection.normalizeLocal();

        // pCenter is the mid point of the arc
        pCenter.set(pDirection);
        pCenter.multLocal(offset);
        pCenter.addLocal(anchor);

        // calculate screen distance of the text, so we know how to fit it in here

        float horizontalRatio = Math.abs(pDirection.dot(r.getCamera().getLeft()));
        float lineLength = p1.distance(p2);
        // this is a strange way of estimating the size, but it may be effective...
        float textScreenSize = label.getWidth() * horizontalRatio + label.getHeight() * (1 - horizontalRatio);

        // screen distance.
        float screenLength = r.getCamera().getScreenCoordinates(p1).distance(r.getCamera().getScreenCoordinates(p2));
        float worldRatio = lineLength / screenLength;
        float textWorldLength = textScreenSize * worldRatio;

        if (textWorldLength + 8 * margin * worldRatio > lineLength) {
            //if(false) {
            //float totalOffset = textWorldLength;

            //labelCenter.set(pCenter);

            labelCenter.set(pDirection);
            float extra = DisplayConstants.getInstance().getAngleLabelExtra();
            labelCenter.multLocal(offset + extra);
            labelCenter.addLocal(anchor);
            //offsetDirection.set(pDirection);
            //offsetDirection.multLocal(totalOffset);
            //labelCenter.addLocal( offsetDirection );

            // draw line:
            CurveUtil.renderArc(r, color, anchor, offset, axis1, pDirection);
            CurveUtil.renderArc(r, color, anchor, offset, pDirection, axis2);

            //CurveUtil.renderLine(r, color, arrow1, arrow2);

        } else {
            // display text normally...
            //labelCenter = pCenter;
            labelCenter.set(pCenter);
            midVector.set(p1);
            midVector.subtractLocal(p2);
            midVector.normalizeLocal();
            //midVector.crossLocal(pDifference);
            midVector.multLocal(textWorldLength / 2 + worldRatio * margin);
            midVector1.set(pDirection);
            midVector2.set(pDirection);
            midVector1.addLocal(midVector);
            midVector2.subtractLocal(midVector);
            midVector1.normalizeLocal();
            midVector2.normalizeLocal();

            // draw lines:
            CurveUtil.renderArc(r, color, anchor, offset, axis1, midVector1);
            CurveUtil.renderArc(r, color, anchor, offset, midVector2, axis2);

            //CurveUtil.renderLine(r, color, arrow1, midVector1);
            //CurveUtil.renderLine(r, color, arrow2, midVector2);
        }


    }
}
