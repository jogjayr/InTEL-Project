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

    public ZeroForceMember(String name) {
        super(name);
    }

    public ZeroForceMember(String name, Point end1, Point end2) {
        super(name, end1, end2);
    }

    @Override
    public boolean isDisplayGrayed() {
        return true;
    }

    @Override
    public void setDisplayGrayed(boolean grayed) {
        // do nothing
        //super.setDisplayGrayed(grayed);
    }

    @Override
    public void addObject(SimulationObject obj) {
        if (obj instanceof Connector) {
            throw new IllegalArgumentException("Can not attach a connector to a ZFM!");
        }
        super.addObject(obj);
    }

    @Override
    public boolean canCompress() {
        return false;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        rep.setDisplayGrayed(true);
        addRepresentation(rep);
    }
}
