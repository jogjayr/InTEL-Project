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

    public Pin2dKnownDirection(Point point) {
        super(point);
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
        return "pin";
    }
}
