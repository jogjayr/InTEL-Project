/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.distributed.DistributedMode;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.LabelRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedForceObject extends SimulationObject {

    private Force resultantForce;
    private Point resultantAnchor;
    private DistanceMeasurement measure;
    private DistributedForce dl;

    /**
     * The suffix represents the suffix at the end of the resultant and centroid.
     * In diagrams with multiple distributed forces, it is useful to have the suffixes be "1", "2", and so on
     * @param dl
     * @param suffix
     */
    public DistributedForceObject(DistributedForce dl, String suffix) {
        setName(dl.getName() + " object");
        this.dl = dl;

        // create the force and anchor
        //String anchorName = dl.getName() + " anchor";
        String anchorName = "C" + suffix;
        String loadName = "R" + suffix;
        resultantAnchor = new Point(anchorName, dl.getResultantPosition());
        resultantForce = new Force(resultantAnchor, dl.getPeak().getVectorValue(), loadName);
        resultantForce.setName(loadName);
        //resultantForce.setName(dl.getName() + " resultant");


        measure = new DistanceMeasurement(
                new Point(dl.getName() + " end1", dl.getSurface().getEndpoint1()), resultantAnchor);
        measure.setKnown(false);
        measure.setSymbol("pos");
    }

    public DistanceMeasurement getMeasure() {
        return measure;
    }

    public boolean isSolved() {
        Diagram diagram = Exercise.getExercise().getDiagram(dl, DistributedMode.instance.getDiagramType());
        if (diagram == null) {
            return false;
        }
        return diagram.isLocked();
    }

    public DistributedForce getDistributedForce() {
        return dl;
    }

    public Point getResultantAnchor() {
        return resultantAnchor;
    }

    public Force getResultantForce() {
        return resultantForce;
    }

    @Override
    public Vector3f getTranslation() {

        // this will occur during persistence
        if (dl.getStartPoint() == null || dl.getEndPoint() == null) {
            return new Vector3f();
        }

        return dl.getStartPoint().getTranslation().add(dl.getEndPoint().getTranslation()).mult(.5f);
    }

    public Point getEndPoint() {
        return dl.getEndPoint();
    }

    public Point getStartPoint() {
        return dl.getStartPoint();
    }

    public Beam getSurface() {
        return dl.getSurface();
    }

    @Override
    public Matrix3f getRotation() {
        //return surface.getRotation();

        // this will occur during persistence
        if (dl.getStartPoint() == null || dl.getEndPoint() == null) {
            return new Matrix3f();
        }

        Vector3f direction;
        direction = dl.getEndPoint().getTranslation().subtract(dl.getStartPoint().getTranslation());
        direction.normalizeLocal();

        Vector3f unitPeak = dl.getPeak().getVectorValue().toVector3f().normalize();
        unitPeak.negateLocal();
        Vector3f myZ = direction.cross(unitPeak);

        Matrix3f mat = new Matrix3f();
        mat.setColumn(0, direction);
        mat.setColumn(1, unitPeak);
        mat.setColumn(2, myZ);

        return mat;
    }

    @Override
    public Vector3f getDisplayCenter() {
        Vector3f endpoint;

        if (dl instanceof ConstantDistributedForce) {
            endpoint = dl.getStartPoint().getDisplayCenter().add(dl.getEndPoint().getDisplayCenter()).mult(.5f);
        } else {
            endpoint = dl.getStartPoint().getDisplayCenter();
        }

        // the vectors point into the body as opposed to from the body,
        // so the offset generated by the peak will be negative.
        Vector3f peakValue = dl.getPeak().getVectorValue().toVector3f().negate();

        // **** FIXME THIS USES A FIXED VALUE
        return endpoint.add(peakValue.mult(5));
    }

    @Override
    public String getLabelText() {
        return dl.getPeak().getPrettyName();
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        // only one sample is necessary here.
        createDefaultSchematicRepresentation(5, 10, 2f);
    }

    public void createDefaultSchematicRepresentation(float displayScale, int arrows, float measureDistance) {
        addRepresentation(new DistributedForceRepresentation(this, 30, displayScale, arrows));

        LabelRepresentation label = new LabelRepresentation(this, "label_force");
        addRepresentation(label);

        resultantForce.createDefaultSchematicRepresentation();
        resultantAnchor.createDefaultSchematicRepresentation();
        measure.createDefaultSchematicRepresentation(measureDistance);
    }
}
