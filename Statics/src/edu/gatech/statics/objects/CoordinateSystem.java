/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.CoordinateSystemRepresentation;

/**
 *
 * @author gtg126z
 */
public class CoordinateSystem extends Measurement {

    private boolean is3D;

    public CoordinateSystem(boolean is3D) {
        this.is3D = is3D;
        setName("CoordinateSystem");
    }

    public boolean is3D() {
        return is3D;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CoordinateSystemRepresentation(this, 1.0f);
        addRepresentation(rep);
    }

    public Unit getUnit() {
        return Unit.none;
    }
}
