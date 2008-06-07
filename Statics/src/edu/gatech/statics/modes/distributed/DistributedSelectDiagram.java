/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;

/**
 * This is a variant on SelectDiagram, which makes it possible to select a distributed
 * load and create a resultant from it.
 * @author Calvin Ashmore
 */
public class DistributedSelectDiagram extends SelectDiagram {

    private SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Body || obj instanceof DistributedForce;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj instanceof DistributedForce) {
            System.out.println("ping!");
            DistributedMode.instance.load((DistributedForce)obj);
            return;
        }

        super.onClick(obj);
    }
}
