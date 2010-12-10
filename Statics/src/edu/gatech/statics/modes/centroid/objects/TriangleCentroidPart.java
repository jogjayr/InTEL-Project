/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Calvin
 */
public class TriangleCentroidPart extends CentroidPart {

    final private Point p1, p2, p3;
    final private Vector3bd centroid;
    private final BigDecimal surfaceArea;

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
