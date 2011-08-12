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
