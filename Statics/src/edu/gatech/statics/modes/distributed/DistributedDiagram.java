/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.objects.Force;

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
        
        Force resultant = dl.getResultant();
        resultant.createDefaultSchematicRepresentation();
        resultant.getAnchor().createDefaultSchematicRepresentation();
        
        add(resultant);
        add(resultant.getAnchor());
    }

    @Override
    public Mode getMode() {
        return DistributedMode.instance;
    }

}
