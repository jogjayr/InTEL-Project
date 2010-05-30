/*
 * Roller.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.ConnectorRepresentation;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Roller2d extends Connector {

    private Vector3bd direction;

    public Vector3bd getDirection() {
        return direction;
    }

    public void setDirection(Vector3bd direction) {
        this.direction = direction.normalize();
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Roller2d(String name) {
        super(name);
    }

    /** Creates a new instance of Roller */
    public Roller2d(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, direction, ""));
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        
        ConnectorRepresentation rep = new ConnectorRepresentation(this, "rsrc/roller.png");
        rep.getQuad().setLocalScale(2);
        rep.getQuad().setLocalTranslation(0,-.5f,0);
        addRepresentation(rep);
    }

    @Override
    public String connectorName() {
        return "roller";
    }
    
}
