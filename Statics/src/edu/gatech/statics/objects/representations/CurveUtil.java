/*
 * CurveUtil.java
 *
 * Created on July 23, 2007, 10:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.curve.BezierCurve;
import com.jme.curve.Curve;
import com.jme.intersection.CollisionResults;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.WireframeState;
import com.jme.util.geom.BufferUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author Calvin Ashmore
 */
public class CurveUtil {

    private static Line curveUtil_line;
    private static FloatBuffer curveUtil_line_points;
    private static FloatBuffer curveUtil_line_colors;
    private static Curve curveUtil_curve;
    private static Circle curveUtil_circle;
    private static Arc curveUtil_arc;

    public static void renderLine(Renderer r, ColorRGBA color, Vector3f point1, Vector3f point2) {
        renderLine(r, color, point1, point2, 1f);
    }

    public static void renderLine(Renderer r, ColorRGBA color, Vector3f point1, Vector3f point2, float width) {

        if (curveUtil_line == null) {

            curveUtil_line_colors = BufferUtils.createFloatBuffer(2 * 4);
            curveUtil_line_points = BufferUtils.createFloatBuffer(2 * 3);

            curveUtil_line = new Line();

            BlendState as = r.createBlendState();
            as.setEnabled(true);
            as.setBlendEnabled(true);
            as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
            as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
            as.setTestEnabled(true);
            as.setTestFunction(BlendState.TestFunction.Always);
            curveUtil_line.setRenderState(as);

            curveUtil_line.setAntialiased(true);

            curveUtil_line.updateRenderState();

            curveUtil_line.setVertexBuffer(curveUtil_line_points);
            curveUtil_line.setColorBuffer(curveUtil_line_colors);
            curveUtil_line.generateIndices();
        }

        curveUtil_line_colors.rewind();
        curveUtil_line_colors.put(color.r).put(color.g).put(color.b).put(color.a);
        curveUtil_line_colors.put(color.r).put(color.g).put(color.b).put(color.a);

        curveUtil_line_points.rewind();
        curveUtil_line_points.put(point1.x).put(point1.y).put(point1.z);
        curveUtil_line_points.put(point2.x).put(point2.y).put(point2.z);

        curveUtil_line.setLineWidth(width);

        r.draw(curveUtil_line);
    }

    public static void renderCurve(Renderer r, ColorRGBA color, Vector3f... points) {

        if (curveUtil_curve == null) {
            curveUtil_curve = new BezierCurve("");

            BlendState as = r.createBlendState();
            as.setEnabled(true);
            as.setBlendEnabled(true);
            as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
            as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
            as.setTestEnabled(true);
            as.setTestFunction(BlendState.TestFunction.Always);
            curveUtil_curve.setRenderState(as);

            WireframeState ws = r.createWireframeState();
            ws.setEnabled(true);
            ws.setAntialiased(true);
            curveUtil_curve.setRenderState(ws);

            curveUtil_curve.updateRenderState();
        }

        //Curve line = new BezierCurve("", points);
        curveUtil_curve.setVertexBuffer(BufferUtils.createFloatBuffer(points));
        curveUtil_curve.setSteps(10 * points.length);


        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        curveUtil_curve.setColorBuffer(fb);

        r.draw(curveUtil_curve);
    }

    private static class Circle extends Curve {

        Circle() {
            super("");
        }
        private Vector3f center, xunit, yunit;
        private float radius;

        public void setCoords(Vector3f center, Vector3f xunit, Vector3f yunit, float radius) {
            this.center = center;
            this.xunit = xunit;
            this.yunit = yunit;
            this.radius = radius;
        }

        public Vector3f getPoint(float time) {
            float theta = (float) (2 * Math.PI) * time;
            return center.add(xunit.mult(radius * (float) Math.cos(theta))).
                    add(yunit.mult(radius * (float) Math.sin(theta)));
        }

        public Vector3f getPoint(float time, Vector3f store) {
            return store.set(getPoint(time));
        }

        public Matrix3f getOrientation(float time, float precision) {
            return new Matrix3f();
        }

        public Matrix3f getOrientation(float time, float precision, Vector3f up) {
            return new Matrix3f();
        }

        public void findCollisions(Spatial scene, CollisionResults results, int requiredOnBits) {
            return;
        }

        public boolean hasCollision(Spatial scene, boolean checkTriangles, int requiredOnBits) {
            return false;
        }
    }

    public static void renderCircle(Renderer r, ColorRGBA color, final Vector3f center, final float radius, Vector3f perp) {

        if (curveUtil_circle == null) {
            curveUtil_circle = new Circle();

            BlendState as = r.createBlendState();
            as.setEnabled(true);
            as.setBlendEnabled(true);
            as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
            as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
            as.setTestEnabled(true);
            as.setTestFunction(BlendState.TestFunction.Always);
            curveUtil_circle.setRenderState(as);

            WireframeState ws = r.createWireframeState();
            ws.setEnabled(true);
            ws.setAntialiased(true);
            curveUtil_circle.setRenderState(ws);

            curveUtil_circle.updateRenderState();
        }

        final Vector3f xunit = getXUnit(perp);
        final Vector3f yunit = xunit.cross(perp);
        yunit.normalizeLocal();

        curveUtil_circle.setCoords(center, xunit, yunit, radius);
        curveUtil_circle.setSteps(40);

        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        curveUtil_circle.setColorBuffer(fb);

        r.draw(curveUtil_circle);
    }

    private static Vector3f getXUnit(Vector3f perp) {
        final Vector3f xunit;
        if (Vector3f.UNIT_Y.cross(perp).equals(Vector3f.ZERO)) {
            xunit = Vector3f.UNIT_X.cross(perp);
        } else {
            xunit = Vector3f.UNIT_Y.cross(perp);
        }
        xunit.normalizeLocal();
        return xunit;
    }

    private static class Arc extends Curve {

        Arc() {
            super("");
        }
        private Vector3f center, axis1, axis2;
        private float radius;

        public void setCoords(Vector3f center, Vector3f axis1, Vector3f axis2, float radius) {
            this.center = new Vector3f(center);
            this.radius = radius; // this fixed number is a hack. We'll need to fix it later.

            this.axis1 = axis1;
            this.axis2 = axis2;
        }

        public Vector3f getPoint(float time) {
            //float theta = (float)(Math.PI/2)*time;

            Vector3f r = new Vector3f();
            r.addLocal(axis1.mult(1 - time));
            r.addLocal(axis2.mult(time));
            r.normalizeLocal();
            r.multLocal(radius);
            r.addLocal(center);

            //r.addLocal((center.add(axis1)).mult((float)Math.pow(1-time,3)));
            //r.addLocal((center.add(axis1).add(perp1)).mult(3*time*(float)Math.pow(1-time,2)));
            //r.addLocal((center.add(axis2).add(perp2)).mult(3*time*time*(1-time)));
            //r.addLocal((center.add(axis2)).mult((float)Math.pow(time,3)));
            return r;

        }

        public Vector3f getPoint(float time, Vector3f store) {
            return store.set(getPoint(time));
        }

        public Matrix3f getOrientation(float time, float precision) {
            return new Matrix3f();
        }

        public Matrix3f getOrientation(float time, float precision, Vector3f up) {
            return new Matrix3f();
        }

        public void findCollisions(Spatial scene, CollisionResults results, int requiredOnBits) {
            return;
        }

        public boolean hasCollision(Spatial scene, boolean checkTriangles, int requiredOnBits) {
            return false;
        }
    }

    public static void renderArc(Renderer r, ColorRGBA color,
            final Vector3f center, final float radius, Vector3f axis1, Vector3f axis2) {

        if (curveUtil_arc == null) {
            curveUtil_arc = new Arc();

            BlendState as = r.createBlendState();
            as.setEnabled(true);
            as.setBlendEnabled(true);
            as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
            as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
            as.setTestEnabled(true);
            as.setTestFunction(BlendState.TestFunction.Always);
            curveUtil_arc.setRenderState(as);

            WireframeState ws = r.createWireframeState();
            ws.setEnabled(true);
            ws.setAntialiased(true);
            curveUtil_arc.setRenderState(ws);

            curveUtil_arc.updateRenderState();
        }

        curveUtil_arc.setCoords(center, axis1, axis2, radius);

        float distance = axis1.distance(axis2);
        if (Float.isNaN(distance) || Float.isInfinite(distance)) {
            distance = 0;
        }
        int steps = (int) Math.min(2 + distance * radius * 6, 20);

        curveUtil_arc.setSteps(steps);

        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        curveUtil_arc.setColorBuffer(fb);

        r.draw(curveUtil_arc);
    }
}
