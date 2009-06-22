/*
 * Pin.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class ContactPoint extends Connector {

    private Vector3bd frictionDirection = Vector3bd.UNIT_X;
    private Vector3bd normalDirection = Vector3bd.UNIT_Y;

    public Vector3bd getFrictionDirection() {
        return frictionDirection;
    }

    public void setFrictionDirection(Vector3bd direction) {
        this.frictionDirection = direction.normalize();
    }

    public Vector3bd getNormalDirection() {
        return normalDirection;
    }

    public void setNormalDirection(Vector3bd direction) {
        this.normalDirection = direction.normalize();
    }

    /** Creates a new instance of ContactPoint */
    public ContactPoint(Point point) {
        super(point);
    }

    //change it so that there is some kind of setter for the direction
    //or plane that the thing operates on...direction i suppose, which way is up maybe.
    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, frictionDirection, ""),
                new Vector(Unit.force, normalDirection, ""));
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        System.out.println("There is currently no representation for Friction.");
    }

    @Override
    public String connectorName() {
        return "contact point";
    }
}