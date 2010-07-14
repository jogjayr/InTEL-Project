/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.TriMesh;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.math.Vector3bd;
import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartRepresentation extends Representation<CentroidPartObject> {

    private TriMesh surface;

    public CentroidPartRepresentation(CentroidPartObject target, int samples, float displayScale) {
        super(target);
        surface = createSurface(samples);
        getRelativeNode().attachChild(surface);
//        temp = temp.subtract(new BigDecimal(target.getCentroidPart().width).divide(new BigDecimal("2.0")));

//        Vector3bd startPoint = target.getCentroidPart().getCentroid();
//        startPoint.setX(startPoint.getX().multiply(displayScale));
//        startPoint.setY(startPoint.getY().multiply(displayScale));
//        startPoint.setZ(startPoint.getZ().multiply(displayScale));
//
//        startPoint.setX(startPoint.getX().subtract(new BigDecimal(target.getCentroidPart().getWidth()).multiply(displayScale).divide(new BigDecimal("2.0")))); //= new Vector3bd(startPoint.getX().subtract(new BigDecimal(target.getCentroidPart().getWidth()).divide(new BigDecimal("2.0"))), startPoint.getY(), startPoint.getZ());//Vector3bd.ZERO;//new Vector3bd(getTarget().getCentroidPart().getxPosition(), getTarget().getCentroidPart().getyPosition(), getTarget().getCentroidPart().getzPosition());//getTarget().getStartPoint().getPosition();
//        Vector3bd endPoint = startPoint;
//        endPoint.setX(startPoint.getX().add(new BigDecimal(target.getCentroidPart().getWidth()).multiply(displayScale)));
//        float distance = (float) startPoint.distance(endPoint);

        //Vector3bd center = startPoint.add(endPoint).divide(new BigDecimal("2"));

        //make width and height bigdecimals
        surface.setLocalScale(new Vector3f(target.getCentroidPart().getWidth().multiply(new BigDecimal(displayScale)).intValue(), target.getCentroidPart().getHeight().multiply(new BigDecimal(displayScale)).intValue(), 1));

        Renderer renderer = DisplaySystem.getDisplaySystem().getRenderer();
        BlendState as = renderer.createBlendState();
        as.setEnabled(true);
        as.setBlendEnabled(true);
        as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        as.setTestEnabled(true);
        as.setTestFunction(BlendState.TestFunction.Always);
        surface.setRenderState(as);

        CullState cullState = renderer.createCullState();
        cullState.setCullFace(CullState.Face.None);
        surface.setRenderState(cullState);

        surface.setLightCombineMode(LightCombineMode.Off);

        setLayer(RepresentationLayer.vectors);

        surface.updateRenderState();

        setDiffuse(ColorRGBA.red);
        setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
    }

    protected TriMesh createSurface(int samples) {

        int numberPoints = (1 + samples) * 2;
        int numberTriangles = samples * 2;

        FloatBuffer vertices = BufferUtils.createFloatBuffer(numberPoints * 3);
        IntBuffer indices = BufferUtils.createIntBuffer(numberTriangles * 3);

        for (int i = 0; i <= samples; i++) {

            float x = (float) i / samples;

            // first point, the base
            vertices.put(2 * x - 1).put(0).put(0);

            // second point, the elevated value
            vertices.put(2 * x - 1).put(1).put(0);

            // y
            // ^13
            // |02
            // + -> x

            if (i > 0) {
                // the triangles rotate counter-clockwise
                // but the material should turn off culling later.
                // first triangle (021)
                indices.put(2 * i - 2).put(2 * i).put(2 * i - 1);
                // the second triangle (132)
                indices.put(2 * i - 1).put(2 * i + 1).put(2 * i);
            }
        }

        ColorRGBA color = new ColorRGBA(1, 0, 0, .5f);

        TriMesh mesh = new TriMesh("", vertices, null, null, null, indices);
        mesh.setDefaultColor(color);
        return mesh;
    }
}
