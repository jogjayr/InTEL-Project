/*
 * Roller.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.joints;

import edu.gatech.statics.objects.Joint;
import com.jme.math.Vector3f;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.PointRepresentation;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Roller2d extends Joint {

    private Vector3f direction;

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction.normalize();
    }

    /** Creates a new instance of Roller */
    public Roller2d(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, direction));
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        
        PointRepresentation rep = new PointRepresentation(this, "rsrc/roller.png");
        rep.getQuad().setLocalScale(2);
        rep.getQuad().setLocalTranslation(0,-.5f,0);
        addRepresentation(rep);
    }
    
}
