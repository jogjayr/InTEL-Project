/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * Class to represent rectangular parts in centroid problems
 * @author Calvin
 */
public class RectangleCentroidPart extends CentroidPart {

    /**
     * Rectangle width
     */
    private final double width;
    /**
     * Rectangle height
     */
    private final double height;
    /**
     * Rectangle rotation
     */
    private final double rotation;
    /**
     * Centroid position
     */
    private final Vector3bd centroid;
    /**
     * Rectangle surface area
     */
    private final BigDecimal surfaceArea;

    /**
     * Constructor
     * @param partName
     * @param centroid
     * @param width
     * @param height
     */
    public RectangleCentroidPart(String partName, Vector3bd centroid, double width, double height) {
        this(partName, centroid, width, height, 0);
    }

    /**
     * Constructor
     * @param partName
     * @param centroid
     * @param width
     * @param height
     * @param rotation
     */
    public RectangleCentroidPart(String partName, Vector3bd centroid, double width, double height, double rotation) {
        super(partName);
        this.centroid = centroid;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.surfaceArea = new BigDecimal(width * height);
    }

    /**
     * Getter
     * @return
     */
    @Override
    public Vector3bd getCentroid() {
        return centroid;
    }

    /**
     * Getter
     * @return
     */
    @Override
    public BigDecimal getSurfaceArea() {
        return surfaceArea;
    }

    /**
     * Create representation for obj
     * @param obj
     * @return
     */
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
