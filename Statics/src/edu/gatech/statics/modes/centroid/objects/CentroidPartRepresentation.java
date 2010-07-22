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
import edu.gatech.statics.modes.centroid.objects.CentroidUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Jimmy Truesdell
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
        surface = createSurface();
        getRelativeNode().attachChild(surface);
        this.target = target;
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
        // *** The .5f is necessary because the surface object has a size of 2.
        surface.setLocalScale(new Vector3f(
                .5f*target.getCentroidPart().getWidth().floatValue() * Unit.distance.getDisplayScale().floatValue(),
                .5f*target.getCentroidPart().getHeight().floatValue() * Unit.distance.getDisplayScale().floatValue(),
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
        color = util.generatePastelColor();//= new ColorRGBA(1, 0, 0, .85f);

        TriMesh mesh = new TriMesh("", vertices, null, null, null, indices);
        mesh.setDefaultColor(color);
        return mesh;
    }

    @Override
    protected void updateMaterial() {
        super.updateMaterial();


        if(target.getState() != null && target.getState().isLocked()){
            color = ColorRGBA.blue;
        }

        if(isSelected() && (target.getState() == null || !target.getState().isLocked())) {
            surface.setDefaultColor(util.highlight(color));
        } else {
            surface.setDefaultColor(color);
        }
    }


}
