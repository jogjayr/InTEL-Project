/*
 * DistanceMeasurement.java
 *
 * Created on July 17, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.CoordinateAxisRepresentation;
import java.math.BigDecimal;

/**
 * Draws a straight line with a circle at the origin and an arrow at the other
 * end. To make this display as a coordinate system you have to add two of them
 * (one in each direction). A future change should maybe be to make it so that
 * one class creates both lines.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CoordinateAxis extends Measurement {

    private Point v1;
    private Point v2;
    private Direction d;


    public void setDistance(BigDecimal distance) {
        if (d.equals(Direction.horizontal)) {
            BigDecimal tempX = v1.getPosition().getX();
            tempX = tempX.add(distance);
            this.v2 = new Point("zoomj", new Vector3bd(tempX, v2.getPosition().getY(), v2.getPosition().getZ()));
        } else {
            BigDecimal tempY = v1.getPosition().getY();
            tempY = tempY.add(distance);
            this.v2 = new Point("zoomj", new Vector3bd(v2.getPosition().getX(), tempY, v2.getPosition().getZ()));
        }
    }

    public Point getPoint1() {
        return v1;
    }

    public Point getPoint2() {
        return v2;
    }

    public Direction getDirection() {
        return d;
    }

    public enum Direction {

        horizontal,
        vertical;
    }

    /** Creates a new instance of DistanceMeasurement */
    public CoordinateAxis(Point v1, Direction d) {
        setName("Axis" + v1.getName());

        this.d = d;

        if (d.equals(Direction.horizontal)) {
            BigDecimal tempX = v1.getPosition().getX();
            tempX = tempX.add(BigDecimal.ONE);
            this.v1 = v1;
            this.v2 = new Point("zoomj", new Vector3bd(tempX, v1.getPosition().getY(), v1.getPosition().getZ()));
            //updateQuantityValue(new BigDecimal(v1.getPosition().distance(v2.getPosition())));
            updateQuantityValue(BigDecimal.ZERO);
        } else {
            BigDecimal tempY = v1.getPosition().getY();
            tempY = tempY.add(BigDecimal.ONE);
            this.v1 = v1;
            this.v2 = new Point("zoomj", new Vector3bd(v1.getPosition().getX(), tempY, v1.getPosition().getZ()));
            //updateQuantityValue(new BigDecimal(v1.getPosition().distance(v2.getPosition())));
            updateQuantityValue(BigDecimal.ZERO);
        }
    }

    @Override
    public void update() {
        super.update();
        //updateQuantityValue(new BigDecimal(v1.getPosition().distance(v2.getPosition())));
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new CoordinateAxisRepresentation(this);
        addRepresentation(rep);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        // if the system is without a display, do not attempt to create the representation
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            CoordinateAxisRepresentation rep = new CoordinateAxisRepresentation(this);
            rep.setOffset(offset);
            addRepresentation(rep);
        }
    }

    public Unit getUnit() {
        return Unit.distance;
    }
}