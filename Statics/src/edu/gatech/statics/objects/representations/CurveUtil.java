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
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
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
    
    
}
