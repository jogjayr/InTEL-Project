/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.ui.DistributedInterfaceConfiguration;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class DistributedExercise extends OrdinaryExercise {

    //private Map<DistributedForce, DistributedDiagram> diagramMap = new HashMap();
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new DistributedInterfaceConfiguration();
    }

    public DistributedExercise() {
    }

    public DistributedExercise(Schematic schematic) {
        super(schematic);
    }

    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == DistributedMode.instance.getDiagramType()) {
            return createDistributedDiagram((DistributedForce) key);
        }

        return super.createNewDiagramImpl(key, type);
    }

    protected DistributedDiagram createDistributedDiagram(DistributedForce dl) {
        return new DistributedDiagram(dl);
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
