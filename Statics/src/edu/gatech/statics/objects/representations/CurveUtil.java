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
import java.nio.FloatBuffer;

/**
 *
 * @author Calvin Ashmore
 */
public class CurveUtil {
    
    public static void renderLine(Renderer r, ColorRGBA color, Vector3f point1, Vector3f point2) {
        Vector3f va1[] = new Vector3f[2];
        va1[0] = point1;
        va1[1] = point2;
        
        ColorRGBA va2[] = new ColorRGBA[2];
        va2[0] = color;
        va2[1] = color;
        
        Line line = new Line("", va1, null, va2, null);
        
        AlphaState as = r.createAlphaState();
        as.setEnabled(true);
        as.setBlendEnabled( true );
        as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        as.setTestEnabled( true );
        as.setTestFunction( AlphaState.TF_ALWAYS );
        line.setRenderState(as);
        
        line.setAntialiased(true);
        
        line.updateRenderState();
        r.draw(line);
    }
    
    public static void renderCurve(Renderer r, ColorRGBA color, Vector3f ... points) {

        Curve line = new BezierCurve("", points);
        line.setSteps(10 * points.length);
        
        AlphaState as = r.createAlphaState();
        as.setEnabled(true);
        as.setBlendEnabled( true );
        as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        as.setTestEnabled( true );
        as.setTestFunction( AlphaState.TF_ALWAYS );
        line.setRenderState(as);
        
        WireframeState ws = r.createWireframeState();
        ws.setEnabled(true);
        ws.setAntialiased(true);
        line.setRenderState(ws);
        
        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        line.setColorBuffer(0,fb);
        
        line.updateRenderState();
        r.draw(line);
    }
    
    public static void renderCircle(Renderer r, ColorRGBA color, final Vector3f center, final float radius, Vector3f perp) {

        //int size = 40;
        //Vector3f[] points = new Vector3f[size+1];
        
        final Vector3f xunit;
        if(Vector3f.UNIT_Y.cross(perp).equals(Vector3f.ZERO))
            xunit = Vector3f.UNIT_X.cross(perp);
        else xunit = Vector3f.UNIT_Y.cross(perp);
        xunit.normalizeLocal();
        
        final Vector3f yunit = xunit.cross(perp);
        yunit.normalizeLocal();
        
        /*
        for(int i=0; i<=size; i++) {
            float theta = (float)(2*i*Math.PI)/size;
            
            points[i] = center.
                    add( xunit.mult(radius*(float)Math.cos(theta)) ).
                    add( yunit.mult(radius*(float)Math.sin(theta)) );
        }*/
        
        Curve line = new Curve("") {
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
        };
        //new BezierCurve("", points);
        //line.setSteps(points.length);
        line.setSteps(40);
        
        AlphaState as = r.createAlphaState();
        as.setEnabled(true);
        as.setBlendEnabled( true );
        as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        as.setTestEnabled( true );
        as.setTestFunction( AlphaState.TF_ALWAYS );
        line.setRenderState(as);
        
        WireframeState ws = r.createWireframeState();
        ws.setEnabled(true);
        ws.setAntialiased(true);
        line.setRenderState(ws);
        
        FloatBuffer fb = FloatBuffer.allocate(4);
        fb.put(color.r);
        fb.put(color.g);
        fb.put(color.b);
        fb.put(color.a);
        line.setColorBuffer(0,fb);
        
        line.updateRenderState();
        r.draw(line);
    }
    
    
}
