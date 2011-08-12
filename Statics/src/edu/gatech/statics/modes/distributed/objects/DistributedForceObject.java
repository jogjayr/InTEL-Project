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
package edu.gatech.statics.modes.distributed.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
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
public class DistributedForceObject extends SimulationObject implements ResolvableByName {

    private Force resultantForce;
    private Point resultantAnchor;
    private DistanceMeasurement measure;
    private final DistributedForce dl;
    private String suffix;

    /**
     * The suffix represents the suffix at the end of the resultant and centroid.
     * In diagrams with multiple distributed forces, it is useful to have the suffixes be "1", "2", and so on
     * @param dl
     * @param suffix
     */
    public DistributedForceObject(DistributedForce dl, String suffix) {
        setName("distributed force: " + dl.getName());
        this.dl = dl;
        this.suffix = suffix;

        if (dl.getSurface() == null) {
            // if this is the case, then we are persisting or loading a DFO
            // do not try to initialize any important data this way.
            return;
        }

        // create the force and anchor
        //String anchorName = dl.getName() + " anchor";
        String anchorName = "C" + suffix;
        String loadName = "R" + suffix;
        resultantAnchor = new Point(anchorName, dl.getResultantPosition());
        resultantForce = new Force(resultantAnchor, dl.getPeak().getVectorValue(), loadName);
        resultantForce.setName(loadName);
        //resultantForce.setName(dl.getName() + " resultant");

        // this can occur during persistence...
        if (dl.getSurface().getEnd1() != null) {
            measure = new DistanceMeasurement(
                    //new Point(dl.getName() + " end1", dl.getSurface().getEndpoint1()), resultantAnchor);
                    dl.getSurface().getEnd1(), resultantAnchor);
            measure.setKnown(false);
            measure.setSymbol("pos");
        }
    }

    /**
     * 
     * @return suffix at the end of the resultant and centroid.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 
     * @return get DistanceMeasurement between start and end points of distributed force
     */
    public DistanceMeasurement getMeasure() {
        return measure;
    }

    /**
     * Is the force solved?
     * @return
     */
    public boolean isSolved() {
        Diagram diagram = Exercise.getExercise().getDiagram(dl, DistributedMode.instance.getDiagramType());
        if (diagram == null) {
            return false;
        }
        return diagram.isLocked();
    }

    /**
     *
     * @return DistributedForce associated with this object
     */
    public DistributedForce getDistributedForce() {
        return dl;
    }

    /**
     * 
     * @return Anchor of resultant force of distributed forces
     */
    public Point getResultantAnchor() {
        return resultantAnchor;
    }

    /**
     * 
     * @return Resultant force of distributed force
     */
    public Force getResultantForce() {
        return resultantForce;
    }

    /**
     * 
     * @return
     */
    @Override
    public Vector3f getTranslation() {

        // this will occur during persistence
        if (dl.getStartPoint() == null || dl.getEndPoint() == null) {
            return new Vector3f();
        }

        return dl.getStartPoint().getTranslation().add(dl.getEndPoint().getTranslation()).mult(.5f);
    }

    /**
     * 
     * @return End point of distributed force
     */
    public Point getEndPoint() {
        return dl.getEndPoint();
    }

    /**
     * 
     * @return Start point of distributed force
     */
    public Point getStartPoint() {
        return dl.getStartPoint();
    }

    /**
     * 
     * @return Beam on hwich force acts
     */
    public Beam getSurface() {
        return dl.getSurface();
    }

    /**
     * 
     * @return Rotation of surface on which force acts
     */
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

    /**
     * 
     * @return Logical center of the DistributedForceObject
     */
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
        return endpoint.add(peakValue.mult(DisplayConstants.getInstance().getDistributedLabelMultiplier()));
    }

    /**
     * 
     * @return Label text of DistributedForceObject
     */
    @Override
    public String getLabelText() {
        return dl.getPeak().getPrettyName();
    }

    /**
     * 
     */
    @Override
    public void createDefaultSchematicRepresentation() {
        // only one sample is necessary here.
        createDefaultSchematicRepresentation(5, 10, 2f);
    }

    /**
     * 
     * @param displayScale Display scale of the representation
     * @param arrows number of arrows to represent force with 
     * @param measureDistance DistanceMeasurement between start and end of the distributed force
     */
    public void createDefaultSchematicRepresentation(float displayScale, int arrows, float measureDistance) {
        addRepresentation(new DistributedForceRepresentation(this, 30, displayScale, arrows));

        LabelRepresentation label = new LabelRepresentation(this, "label_force");
        addRepresentation(label);

        resultantForce.createDefaultSchematicRepresentation();
        resultantAnchor.createDefaultSchematicRepresentation();
        measure.createDefaultSchematicRepresentation(measureDistance);
    }
}
