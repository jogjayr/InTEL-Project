/*
 * Point.java
 *
 * Created on June 7, 2007, 4:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.*;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Point extends SimulationObject {
    // location, etc, already built into SimulationObject
    private Vector3bd point;
    private TwoForceMember member;

    public Point(Point other) {
        this.point = new Vector3bd(other.point);
        updateTranslation();
    }

    public Point(Vector3bd point) {
        this.point = point;
        updateTranslation();
    }

    public Point(String x, String y, String z) {
        this.point = new Vector3bd(x, y, z);
        updateTranslation();
    }

    public Point(String formattedString) {
        this.point = new Vector3bd(formattedString);
        updateTranslation();
    }

    public TwoForceMember getMember() {
        return member;
    }
    
    public void setMember(TwoForceMember member) {
        this.member = member;
    }

    private void updateTranslation() {
        setTranslation(point.toVector3f());
    }

    protected void setPoint(Vector3bd point) {
        this.point = point;
    }

    public Vector3bd getPosition() {
        return point;
    }
    //public Point(Vector3f position) {
    //    setTranslation(position);
    //}
    public void createDefaultSchematicRepresentation() {
        Representation rep1 = new PointRepresentation(this);
        LabelRepresentation rep2 = new LabelRepresentation(this, "label_point");
        rep2.setOffset(15, 15);

        addRepresentation(rep1);
        addRepresentation(rep2);
    }

    @Override
    public String getDescription() {
        return "Point: " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.point != other.point && (this.point == null || !this.point.equals(other.point))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.point != null ? this.point.hashCode() : 0);
        return hash;
    }
}
