/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedDiagram extends Diagram {

    private DistributedForce dl;
    
    public DistributedDiagram(DistributedForce dl) {
        this.dl = dl;
        
        add(dl);
        add(dl.getSurface());
        add(dl.getEndPoint());
        add(dl.getStartPoint());
    }

    @Override
    public Mode getMode() {
        return DistributedMode.instance;
    }

}
