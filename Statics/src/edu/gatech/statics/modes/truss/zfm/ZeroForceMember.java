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
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.Representation;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class ZeroForceMember extends TwoForceMember {

    /**
     *
     * @param name
     */
    public ZeroForceMember(String name) {
        super(name);
    }

    /**
     * Constructor
     * @param name Member name
     * @param end1 An endpoint
     * @param end2 Other endpoint
     */
    public ZeroForceMember(String name, Point end1, Point end2) {
        super(name, end1, end2);
    }

    /**
     * Is member displayed as grayed
     * @return
     */
    @Override
    public boolean isDisplayGrayed() {
        // let the parent class know that this is grayed.
        super.setDisplayGrayed(true);
        return true;
    }

    /**
     * Set member display to grayed
     * @param grayed
     */
    @Override
    public void setDisplayGrayed(boolean grayed) {
        // do nothing
        super.setDisplayGrayed(true);
    }

    /**
     * Handles the addObject event for ZeroForceMembers (by not allowing it)
     * @param obj
     */
    @Override
    public void addObject(SimulationObject obj) {
        if (obj instanceof Connector) {
            throw new IllegalArgumentException("Can not attach a connector to a ZFM!");
        }
        super.addObject(obj);
    }

    /**
     * getter
     * @return
     */
    @Override
    public boolean canCompress() {
        return false;
    }

    /**
     * Creates a schematic representation for a ZFM
     */
    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        rep.setDisplayGrayed(true);
        addRepresentation(rep);
    }
}
