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
import com.jme.scene.state.AlphaState;
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

    public static void renderLine(Renderer r, ColorRGBA color, Vector3f point1, Vector3f point2) {
        
        if(curveUtil_line == null) {
            
            curveUtil_line_colors = BufferUtils.createFloatBuffer(2*4);
            curveUtil_line_points = BufferUtils.createFloatBuffer(2*3);
            
            curveUtil_line = new Line();

            AlphaState as = r.createAlphaState();
            as.setEnabled(true);
            as.setBlendEnabled( true );
            as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
            as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
            as.setTestEnabled( true );
            as.setTestFunction( AlphaState.TF_ALWAYS );
            curveUtil_line.setRenderState(as);

            curveUtil_line.setAntialiased(true);

            curveUtil_line.updateRenderState();
            
            curveUtil_line.setVertexBuffer(0, curveUtil_line_points);
            curveUtil_line.setColorBuffer(0, curveUtil_line_colors);
            curveUtil_line.generateIndices(0);
        }
        
        curveUtil_line_colors.rewind();
        curveUtil_line_colors.put(color.r).put(color.g).put(color.b).put(color.a);
        curveUtil_line_colors.put(color.r).put(color.g).put(color.b).put(color.a);
        
        curveUtil_line_points.rewind();
        curveUtil_line_points.put(point1.x).put(point1.y).put(point1.z);
        curveUtil_line_points.put(point2.x).put(point2.y).put(point2.z);
        
        r.draw(curveUtil_line);
    }
    
    public static void renderCurve(Renderer r, ColorRGBA color, Vector3f ... points) {

        if(curveUtil_curve == null) {
            curveUtil_curve = new BezierCurve("");
            
            AlphaState as = r.createAlphaState();
            as.setEnabled(true);
            as.setBlendEnabled( true );
            as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
            as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
            as.setTestEnabled( true );
            as.setTestFunction( AlphaState.TF_ALWAYS );
            curveUtil_curve.setRenderState(as);

            WireframeState ws = r.createWireframeState();
            ws.setEnabled(true);
            ws.setAntialiased(true);
            curveUtil_curve.setRenderState(ws);

            curveUtil_curve.updateRenderState();
        }

        //Curve line = new BezierCurve("", points);
        curveUtil_curve.setVertexBuffer(0, BufferUtils.createFloatBuffer(points));
        curveUtil_curve.setSteps(10 * points.length);
        
        
        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        curveUtil_curve.setColorBuffer(0,fb);
        
        r.draw(curveUtil_curve);
    }

    private static class Circle extends Curve {

        Circle() {super("");}

        private Vector3f center, xunit, yunit;
        private float radius;
        public void setCoords(Vector3f center, Vector3f xunit, Vector3f yunit, float radius) {
            this.center = center;
            this.xunit = xunit;
            this.yunit = yunit;
            this.radius = radius;
        }

        public Vector3f getPoint(float time) {
            float theta = (float)(2*Math.PI)*time;
            return center.
                add( xunit.mult(radius*(float)Math.cos(theta)) ).
                add( yunit.mult(radius*(float)Math.sin(theta)) );
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

        public void findCollisions(Spatial scene, CollisionResults results) {
            return;
        }

        public boolean hasCollision(Spatial scene, boolean checkTriangles) {
            return false;
        }
    }
    
    public static void renderCircle(Renderer r, ColorRGBA color, final Vector3f center, final float radius, Vector3f perp) {

        if(curveUtil_circle == null) {
            curveUtil_circle = new Circle();

            AlphaState as = r.createAlphaState();
            as.setEnabled(true);
            as.setBlendEnabled( true );
            as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
            as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
            as.setTestEnabled( true );
            as.setTestFunction( AlphaState.TF_ALWAYS );
            curveUtil_circle.setRenderState(as);

            WireframeState ws = r.createWireframeState();
            ws.setEnabled(true);
            ws.setAntialiased(true);
            curveUtil_circle.setRenderState(ws);

            curveUtil_circle.updateRenderState();
        }

        final Vector3f xunit;
        if(Vector3f.UNIT_Y.cross(perp).equals(Vector3f.ZERO))
            xunit = Vector3f.UNIT_X.cross(perp);
        else xunit = Vector3f.UNIT_Y.cross(perp);
        xunit.normalizeLocal();
        
        final Vector3f yunit = xunit.cross(perp);
        yunit.normalizeLocal();

        curveUtil_circle.setCoords(center, xunit, yunit, radius);
        curveUtil_circle.setSteps(40);
        
        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        curveUtil_circle.setColorBuffer(0,fb);
        
        r.draw(curveUtil_circle);
    }
    
    
}
