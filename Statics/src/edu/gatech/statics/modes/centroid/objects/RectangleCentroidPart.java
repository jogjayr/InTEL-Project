/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Calvin
 */
public class RectangleCentroidPart extends CentroidPart {

    private final double width;
    private final double height;
    private final double rotation;
    private final Vector3bd centroid;
    private final BigDecimal surfaceArea;

    public RectangleCentroidPart(String partName, Vector3bd centroid, double width, double height) {
        this(partName, centroid, width, height, 0);
    }

    public RectangleCentroidPart(String partName, Vector3bd centroid, double width, double height, double rotation) {
        super(partName);
        this.centroid = centroid;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.surfaceArea = new BigDecimal(width * height);
    }

    @Override
    public Vector3bd getCentroid() {
        return centroid;
    }

    @Override
    public BigDecimal getSurfaceArea() {
        return surfaceArea;
    }

    @Override
    public Representation<CentroidPartObject> createRepresentation(CentroidPartObject obj) {
        return new CentroidPartRepresentation(obj, new CentroidPartRepresentation.SurfaceBuilder() {

            public TriMesh createMesh() {
                int numberPoints = 4;
                int numberTriangles = 2;

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

                TriMesh mesh = new TriMesh("", vertices, null, null, null, indices);

                float displayScale = Unit.distance.getDisplayScale().floatValue();
                mesh.setLocalScale(new Vector3f(.5f * (float) width * displayScale, .5f * (float) height * displayScale, 1));
                Matrix3f matrix = new Matrix3f();
                matrix.fromAngleAxis((float) rotation, Vector3f.UNIT_Z);
                mesh.setLocalRotation(matrix);

                return mesh;
            }
        });

    }
}
