/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.bounding.BoundingBox;
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
import edu.gatech.statics.math.Unit;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * This class is responsible for drawing the colored regions that represent the
 * particular type of centroid part the object is. Currently we only support
 * rectangular shapes but it should be fairly easy to allow for other part
 * shapes based on the PartType that gets passed into the CentroidPartObject
 * upon creation.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidPartRepresentation extends Representation<CentroidPartObject> {

    private TriMesh surface;
    private ColorRGBA color;
    private CentroidUtil util;
    private CentroidPartObject target;

    public ColorRGBA getColor() {
        return color;
    }

    public CentroidPartRepresentation(CentroidPartObject target) {
        super(target);
        this.target = target;
        surface = createSurface();
        getRelativeNode().attachChild(surface);

        surface.setLocalScale(new Vector3f(
                .5f * target.getCentroidPart().getWidth().floatValue() * Unit.distance.getDisplayScale().floatValue(),
                .5f * target.getCentroidPart().getHeight().floatValue() * Unit.distance.getDisplayScale().floatValue(),
                1));

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

        surface.setModelBound(new BoundingBox());
        surface.updateModelBound();
        surface.updateRenderState();

        setDiffuse(ColorRGBA.red);
        setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
    }

    protected TriMesh createSurface() {

        int numberPoints = 4;
        int numberTriangles = 2;

        util = new CentroidUtil();

        FloatBuffer vertices = BufferUtils.createFloatBuffer(numberPoints * 3);
        IntBuffer indices = BufferUtils.createIntBuffer(numberTriangles * 3);

//        if (target != null) {
        if (target.getCentroidPart().getPart() == CentroidPart.PartType.RECTANGLE) {
            // y
            // ^13
            // |02
            // + -> x
            // vertices here range from -1 to 1
            vertices.put(-1).put(-1).put(0); // 0: lower left <-1, -1, 0>
            vertices.put(-1).put(1).put(0); // 1: upper left <-1, 1, 0>
            vertices.put(1).put(-1).put(0); // 2: lower right <1, -1, 0>
            vertices.put(1).put(1).put(0); // 3: upper right <1, 1, 0>

            indices.put(0).put(2).put(1); // first triangle: 0,2,1
            indices.put(1).put(3).put(2); // second triangle: 1,3,2
        } else if (target.getCentroidPart().getPart() == CentroidPart.PartType.CIRCLE) {
            throw new UnsupportedOperationException("Circle is an anticipated but not yet visually supported part type.");

        } else if (target.getCentroidPart().getPart() == CentroidPart.PartType.TRIANGLE) {
            throw new UnsupportedOperationException("Triangle is an anticipated but not yet visually supported part type.");
        } else {
            throw new UnsupportedOperationException(target.getCentroidPart().getPart().toString() + " is not a valid part type.");
        }
//        }

        color = util.generatePastelColor();

        TriMesh mesh = new TriMesh("", vertices, null, null, null, indices);
        mesh.setDefaultColor(color);
        return mesh;
    }

    @Override
    protected void updateMaterial() {
        super.updateMaterial();

        //if the CentroidPartObject associated with this Representation has been
        //solved set the colored region to blue
        if (target.getState() != null && target.getState().isLocked()) {
            color = ColorRGBA.blue;
        }

        //if the CentroidPartObject associated with this Representation has not
        //been solved and has been clicked on the color of the region is set to be
        //highlighted. If not clicked on it is the default color.
        if (isSelected() && (target.getState() == null || !target.getState().isLocked())) {
            surface.setDefaultColor(util.highlight(color));
            surface.setLocalTranslation(0, 0, 0.01f);
        } else {
            surface.setDefaultColor(color);
            surface.setLocalTranslation(0, 0, 0);
        }
    }
}
