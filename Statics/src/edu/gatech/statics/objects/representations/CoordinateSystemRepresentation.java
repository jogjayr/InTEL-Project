/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.CoordinateSystem;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author gtg126z
 */
public class CoordinateSystemRepresentation extends Representation<CoordinateSystem> {

    private float axisSize;
    private float margin = 4f; // pixels
    private float arrowsize = 6f; // pixels
    private ColorRGBA color = ColorRGBA.black;

        private CoordinateLabel labelX;
        private CoordinateLabel labelY;
        private CoordinateLabel labelZ;

    private static class CoordinateLabel extends LabelRepresentation {

        private final Vector3f displayCenter;
        private final String label;

        public CoordinateLabel(SimulationObject target, Vector3f displayCenter, String label) {
            super(target, "label_measurement");
            this.displayCenter = displayCenter;
            this.label = label;
        }

        @Override
        protected Vector3f getDisplayCenter() {
            return displayCenter;
        }

        @Override
        protected String getLabelText() {
            return label;
        }
    }

    public CoordinateSystemRepresentation(CoordinateSystem target, float axisSize) {
        super(target);
        setLayer(RepresentationLayer.measurement);
        this.axisSize = axisSize;

        // create labels: x, y, z (if target is 3d)


        labelX = new CoordinateLabel(target, new Vector3f(axisSize, 0, 0), "X");
        getRelativeNode().attachChild(labelX);
        
        labelY = new CoordinateLabel(target, new Vector3f(0, axisSize, 0), "Y");
        getRelativeNode().attachChild(labelY);

        if (getTarget().is3D()) {
            labelZ = new CoordinateLabel(target, new Vector3f(0, 0, axisSize), "Z");
            getRelativeNode().attachChild(labelZ);
        }
    }

    @Override
    public void update() {
        super.update();
        labelX.update();
        labelY.update();
        if(labelZ != null)
            labelZ.update();
    }



    @Override
    public void draw(Renderer r) {
        super.draw(r);

        drawArrow(r, Vector3f.ZERO, new Vector3f(axisSize, 0, 0)); // X
        drawArrow(r, Vector3f.ZERO, new Vector3f(0, axisSize, 0)); // Y

        if (getTarget().is3D()) {
            drawArrow(r, Vector3f.ZERO, new Vector3f(0, 0, axisSize)); // Z
        }
    }
    // keeping these like this is rather unseemly, but
    // this class is was a leading cause of busywork for the GC in terms of
    // Vector3f objects that were churned through.
    // We keep these cached here so that they don't churn.
    private static Vector3f worldPointDifference, offsetDirection, scaledOffsetDirection;
    private static Vector3f p1, p2, pCenter, pDirection;
    private static Vector3f arrowOffset, measureExtent, measureExtentArrowHead;
    private static Vector3f arrow1, arrow2, arrowHead1, arrowHead2, arrowHead1a, arrowHead2a;

    static {
        worldPointDifference = new Vector3f();
        offsetDirection = new Vector3f();
        scaledOffsetDirection = new Vector3f();
        p1 = new Vector3f();
        p2 = new Vector3f();
        pCenter = new Vector3f();
        pDirection = new Vector3f();
        arrowOffset = new Vector3f();
        measureExtent = new Vector3f();
        measureExtentArrowHead = new Vector3f();
        arrow1 = new Vector3f();
        arrow2 = new Vector3f();
        arrowHead1 = new Vector3f();
        arrowHead2 = new Vector3f();
        arrowHead1a = new Vector3f();
        arrowHead2a = new Vector3f();
    }

    private void drawArrow(Renderer r, Vector3f start, Vector3f end) {
        worldPointDifference.set(end);
        worldPointDifference.subtractLocal(start);

        // draw main line.
        p1.set(start);
        p1.addLocal(scaledOffsetDirection);
        p2.set(end);
        p2.addLocal(scaledOffsetDirection);

        pCenter.set(p1);
        pCenter.addLocal(p2);
        pCenter.multLocal(.5f);

        pDirection.set(p1);
        pDirection.subtractLocal(p2);
        pDirection.normalizeLocal();
        float lineLength = p1.distance(p2);
        // this is a strange way of estimating the size, but it may be effective...

        // screen distance.
        float screenLength = r.getCamera().getScreenCoordinates(p1).distance(r.getCamera().getScreenCoordinates(p2));
        float worldRatio = lineLength / screenLength;

        // now that the textWorldLength is calculated, it may be good to do some tests
        // we may wish to display text differently if the text size is greater than the line length, etc.

        arrowOffset.set(offsetDirection);
        arrowOffset.multLocal(worldRatio * arrowsize);
        measureExtent.set(pDirection);
        measureExtent.multLocal(lineLength / 2 - worldRatio * margin);
        measureExtentArrowHead.set(pDirection);
        measureExtentArrowHead.multLocal(lineLength / 2 - worldRatio * (margin + arrowsize));

        arrow1.set(pCenter);
        arrow2.set(pCenter);
        arrow1.addLocal(measureExtent);
        arrow2.subtractLocal(measureExtent);

        arrowHead1.set(measureExtentArrowHead);
        arrowHead2.set(measureExtentArrowHead);
        arrowHead1.addLocal(arrowOffset);
        arrowHead2.subtractLocal(arrowOffset);

        // draw line:
        CurveUtil.renderLine(r, color, arrow1, arrow2);

        arrowHead1a.set(arrowHead1).multLocal(-1);
        arrowHead2a.set(arrowHead2).multLocal(-1);

        arrowHead1.addLocal(pCenter);
        arrowHead2.addLocal(pCenter);
        arrowHead1a.addLocal(pCenter);
        arrowHead2a.addLocal(pCenter);

        // draw arrowheads:
        CurveUtil.renderLine(r, color, arrow2, arrowHead1a);
        CurveUtil.renderLine(r, color, arrow2, arrowHead2a);
    }
}
