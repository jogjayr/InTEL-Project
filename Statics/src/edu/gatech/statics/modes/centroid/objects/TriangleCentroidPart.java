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

import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Class for triangular parts in centroid problems
 * @author Calvin
 */
public class TriangleCentroidPart extends CentroidPart {

    /**
     * Three points of the triangle
     */
    final private Point p1, p2, p3;
    /**
     * Centroid of the triangle
     */
    final private Vector3bd centroid;
    /**
     * Surface area of the triangle
     */
    private final BigDecimal surfaceArea;

    /**
     * Constructor
     * @param partName
     * @param p1
     * @param p2
     * @param p3
     */
    public TriangleCentroidPart(String partName, Point p1, Point p2, Point p3) {
        super(partName);

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        centroid = p1.getPosition().add(p2.getPosition()).add(p3.getPosition()).divide(new BigDecimal("3"));

        Vector3bd p1p2 = p1.getPosition().subtract(p2.getPosition());
        double p1p2magnitude = p1p2.length();
        Vector3bd p1p2hat = p1p2.normalize();

        // this follows the area of a triangle rule, where the area is 1/2 times the perpindicular times the base length.
        // this calculates the length of the perpindicular from p3.
        double perpindicular = p3.getPosition().subtract( p1p2.mult(p1p2hat.dot(p3.getPosition()))).length();
        surfaceArea = new BigDecimal(.5 * p1p2magnitude * perpindicular);
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
     * Creates the representation of the TriangleCentroidPart
     * @param obj
     * @return
     */
    @Override
    public Representation<CentroidPartObject> createRepresentation(CentroidPartObject obj) {
                return new CentroidPartRepresentation(obj, new CentroidPartRepresentation.SurfaceBuilder() {

            public TriMesh createMesh() {
                int numberPoints = 3;
                int numberTriangles = 1;

                FloatBuffer vertices = BufferUtils.createFloatBuffer(numberPoints * 3);
                IntBuffer indices = BufferUtils.createIntBuffer(numberTriangles * 3);

                // a little kludgy here, but should work.
                vertices.put(p1.getPosition().getX().floatValue()).put(p1.getPosition().getY().floatValue()).put(p1.getPosition().getZ().floatValue());
                vertices.put(p2.getPosition().getX().floatValue()).put(p2.getPosition().getY().floatValue()).put(p2.getPosition().getZ().floatValue());
                vertices.put(p3.getPosition().getX().floatValue()).put(p3.getPosition().getY().floatValue()).put(p3.getPosition().getZ().floatValue());

                indices.put(0).put(2).put(1); // first triangle: 0,2,1

                TriMesh mesh = new TriMesh("", vertices, null, null, null, indices);

//                float displayScale = Unit.distance.getDisplayScale().floatValue();
//                mesh.setLocalScale(new Vector3f(displayScale, displayScale, displayScale));

                return mesh;
            }
        });

    }
}
