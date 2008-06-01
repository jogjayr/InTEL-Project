/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.FBDExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.ui.DistributedInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */

public class DistributedExercise extends FBDExercise {

    private Map<DistributedForce, DistributedDiagram> diagramMap = new HashMap();
    
    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        return new DistributedInterfaceConfiguration();
    }

    public DistributedExercise(Schematic schematic) {
        super(schematic);
    }

    public DistributedDiagram getDiagram(DistributedForce dl) {
        DistributedDiagram diagram = diagramMap.get(dl);
        if(diagram == null) {
            diagram = new DistributedDiagram(dl);
            diagramMap.put(dl, diagram);
        }
        return diagram;
    }
}
