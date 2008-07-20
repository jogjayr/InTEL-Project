/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.ui.DistributedInterfaceConfiguration;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedExercise extends OrdinaryExercise {

    private Map<DistributedForce, DistributedDiagram> diagramMap = new HashMap();

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new DistributedInterfaceConfiguration();
    }

    public DistributedExercise() {
    }

    public DistributedExercise(Schematic schematic) {
        super(schematic);
    }

    public DistributedDiagram getDiagram(DistributedForce dl) {
        DistributedDiagram diagram = diagramMap.get(dl);
        if (diagram == null) {
            diagram = new DistributedDiagram(dl);
            diagramMap.put(dl, diagram);
        }
        return diagram;
    }

    @Override
    protected SelectDiagram createSelectDiagram() {
        return new DistributedSelectDiagram();
    }

    @Override
    protected EquationDiagram createEquationDiagram(BodySubset bodySubset) {
        return new DistributedEquationDiagram(bodySubset);
    }

    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodySubset) {
        return new DistributedFreeBodyDiagram(bodySubset);
    }
}
