/*
 * DistanceMeasurement.java
 *
 * Created on July 17, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.DistanceRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class DistanceMeasurement extends Measurement {

    private Point v1,  v2;
    private boolean verticalLine = false,  horizontalLine = false;

    public void forceVertical() {
        horizontalLine = false;
        verticalLine = true;
    }

    public void forceHorizontal() {
        verticalLine = false;
        horizontalLine = true;
    }

    public boolean isVertical() {
        return verticalLine;
    }

    public boolean isHorizontal() {
        return horizontalLine;
    }

    public Point getPoint1() {
        return v1;
    }

    public Point getPoint2() {
        return v2;
    }

    /** Creates a new instance of DistanceMeasurement */
    public DistanceMeasurement(Point v1, Point v2) {
        super(v1, v2);
        setName("Distance: " + v1.getName() + ", " + v2.getName());

        this.v1 = v1;
        this.v2 = v2;
        if (isVertical() == true) {
            Point p1 = new Point("default", new Vector3bd(Float.toString(v2.getTranslation().getX()), Float.toString(v1.getTranslation().getY()), Float.toString(v1.getTranslation().getZ())));
            updateQuantityValue(new BigDecimal(p1.getPosition().distance(v2.getPosition())));
        } else if (isHorizontal() == true) {
            Point p1 = new Point("default", new Vector3bd(Float.toString(v1.getTranslation().getX()), Float.toString(v2.getTranslation().getY()), Float.toString(v1.getTranslation().getZ())));
            updateQuantityValue(new BigDecimal(p1.getPosition().distance(v2.getPosition())));
        } else {
            updateQuantityValue(new BigDecimal(v1.getPosition().distance(v2.getPosition())));
        }
    }

    @Override
    public void update() {
        super.update();
        if (isVertical() == true) {
            Point p1 = new Point("default", new Vector3bd(Float.toString(v2.getTranslation().getX()), Float.toString(v1.getTranslation().getY()), Float.toString(v1.getTranslation().getZ())));
            updateQuantityValue(new BigDecimal(p1.getPosition().distance(v2.getPosition())));
        } else if (isHorizontal() == true) {
            Point p1 = new Point("default", new Vector3bd(Float.toString(v1.getTranslation().getX()), Float.toString(v2.getTranslation().getY()), Float.toString(v1.getTranslation().getZ())));
            updateQuantityValue(new BigDecimal(p1.getPosition().distance(v2.getPosition())));
        } else {
            updateQuantityValue(new BigDecimal(v1.getPosition().distance(v2.getPosition())));
        }
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new DistanceRepresentation(this);
        addRepresentation(rep);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        DistanceRepresentation rep = new DistanceRepresentation(this);
        rep.setOffset(offset);
        addRepresentation(rep);
    }

    @Override
    public Vector3f getDisplayCenter() {
        return v1.getTranslation().add(v2.getTranslation()).mult(.5f);
    }

    public Unit getUnit() {
        return Unit.distance;
    }
}
