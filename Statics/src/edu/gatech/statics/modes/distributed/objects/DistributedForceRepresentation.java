/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.Arrow;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedForceRepresentation extends Representation<DistributedForceObject> {

    private TriMesh surface;
    private Line border;

    /**
     * Constructor
     * @param target Object for which representation is created
     * @param samples number of sample points to use in creating mesh for representation. Should be 1
     * @param displayScale local scale of this node
     * @param arrows
     */
    public DistributedForceRepresentation(DistributedForceObject target, int samples, float displayScale, int arrows) {
        super(target);

        surface = createSurface(samples);
        border = createBorder(samples);

        getRelativeNode().attachChild(surface);
        getRelativeNode().attachChild(border);

        Vector3bd startPoint = getTarget().getStartPoint().getPosition();
        Vector3bd endPoint = getTarget().getEndPoint().getPosition();
        float distance = (float) startPoint.distance(endPoint);

        //Vector3bd center = startPoint.add(endPoint).divide(new BigDecimal("2"));

        surface.setLocalScale(new Vector3f(distance / 2, displayScale, 1));
        border.setLocalScale(new Vector3f(distance / 2, displayScale, 1));

        Renderer renderer = DisplaySystem.getDisplaySystem().getRenderer();
        BlendState as = renderer.createBlendState();
        as.setEnabled(true);
        as.setBlendEnabled(true);
        as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        as.setTestEnabled(true);
        as.setTestFunction(BlendState.TestFunction.Always);
        surface.setRenderState(as);
        border.setRenderState(as);

        CullState cullState = renderer.createCullState();
        cullState.setCullFace(CullState.Face.None);
        surface.setRenderState(cullState);

        WireframeState ws = renderer.createWireframeState();
        ws.setEnabled(true);
        ws.setAntialiased(true);
        border.setRenderState(ws);
        border.setAntialiased(true);

        surface.setLightCombineMode(LightCombineMode.Off);
        border.setLightCombineMode(lightCombineMode.Off);

        setLayer(RepresentationLayer.vectors);

        surface.updateRenderState();
        border.updateRenderState();

        Node arrowNode = new Node();

        for (int i = 0; i < arrows; i++) {
            Arrow arrow = createArrow((i + .5f) / arrows, distance, displayScale);
            arrowNode.attachChild(arrow);
        }

        getRelativeNode().attachChild(arrowNode);
        arrowNode.updateRenderState();

        setDiffuse(ColorRGBA.red);
        setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
    }

    /**
     * 
     * @param grayed Flag to set display grayed. True if yes, false if no
     */
    @Override
    public void setDisplayGrayed(boolean grayed) {
        super.setDisplayGrayed(grayed);
        if (grayed) {
            surface.setDefaultColor(new ColorRGBA(.7f, .7f, .7f, .5f));
            border.setDefaultColor(new ColorRGBA(.7f, .7f, .7f, 1));
        } else {
            surface.setDefaultColor(new ColorRGBA(1f, 0f, 0f, .5f));
            border.setDefaultColor(new ColorRGBA(1f, 0f, 0f, 1f));
        }
    }

    /**
     * This method should create the arrow in the correct position and scale.
     * @param pos a number ranging from 0 to 1, denoting the percentage along the span to create the arrow. 
     */
    protected Arrow createArrow(float pos, float spanSize, float displayScale) {

        Arrow arrow = new Arrow();
        //attachChild(arrow);

        Matrix3f matrix = new Matrix3f();
        matrix.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_Y.negate());
        arrow.setLocalRotation(matrix);
        float arrowScale = DisplayConstants.getInstance().getDistributedArrowSize();
        arrow.setSize(.2f * arrowScale, 0.5f, .075f * arrowScale);
        //arrow.setSize(.1f, 0.5f, .05f);

        float height = displayScale * getTarget().getDistributedForce().getCurveValue(pos);
        arrow.setLength(height);

        arrow.setLocalTranslation(spanSize * (pos - .5f), height, 0);

        return arrow;
    }

    /**
     * This method constructs the TriMesh making up the surface that describes 
     * the distributed load. The actual trimesh should live starting at the origin,
     * with the peak value scaled to 1 in the y direction. The mesh should also have length 2,
     * going from -1 to 1 into the +x direction.
     * @param samples the number of sample points to use in constructing the mesh. Should be 1 for a single triangle/quad.
     * @return TriMesh making up surface describing distributed load
     */
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
            vertices.put(2 * x - 1).put(getTarget().getDistributedForce().getCurveValue(x)).put(0);

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

    /**
     * This method constructs the Line that is used to represent the border of the 
     * distributed load.
     * @param samples the number of sample points to use in constructing the mesh. Should be 1 for a single triangle/quad.
     * @return
     */
    protected Line createBorder(int samples) {

        FloatBuffer points = BufferUtils.createFloatBuffer(((samples + 1) * 3 - 1) * 2);
        for (int i = 0; i <= samples; i++) {

            float x = (float) i / samples;
            float y = getTarget().getDistributedForce().getCurveValue(x);

            points.put(2 * x - 1).put(y).put(0);
            // add a point again if the point is in the body of the line
            if (i > 0 && i < samples) {
                points.put(2 * x - 1).put(y).put(0);
            }
        }

        ColorRGBA color = new ColorRGBA(1, 0, 0, 1f);
        Line line = new Line("", points, null, null, null);
        line.setDefaultColor(color);
        line.setLineWidth(2);
        return line;
    }
}
