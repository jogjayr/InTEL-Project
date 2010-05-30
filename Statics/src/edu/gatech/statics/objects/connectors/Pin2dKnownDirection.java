/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.ConnectorRepresentation;

/**
 * This is a type of pin whose direction of action we know, so, for purposes of determining forces,
 * it behaves like a roller. To be consistent with the domain of Statics, this class should not
 * inherit from Roller2d, but it does make things easier for the time being.
 * 
 * @author Calvin Ashmore
 */
public class Pin2dKnownDirection extends Roller2d {

    private boolean negatable;

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Pin2dKnownDirection(String name) {
        super(name);
    }

    public Pin2dKnownDirection(Point point) {
        this(point, false);
    }
    
    public Pin2dKnownDirection(Point point, boolean negatable) {
        super(point);
        this.negatable = negatable;
    }
    
    /**
     * Depending on the type of pin, it cuold be negatable
     * @return
     */
    @Override
    public boolean isForceDirectionNegatable() {
        return negatable;
    }

    @Override
    public void createDefaultSchematicRepresentation() {

        ConnectorRepresentation rep = new ConnectorRepresentation(this, "rsrc/pin.png");
        rep.getQuad().setLocalScale(2);
        rep.getQuad().setLocalTranslation(0, -.5f, 0);
        addRepresentation(rep);
    }

    @Override
    public String connectorName() {
        return "connector";
    }
}
