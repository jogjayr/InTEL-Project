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
    /**
     * 
     * @return
     */
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new DistributedInterfaceConfiguration();
    }

    public DistributedExercise() {
    }

    public DistributedExercise(Schematic schematic) {
        super(schematic);
    }

    /**
     * Create a DistributedDiagram for the Distributed exercise
     * @param key DiagramKey corresponding to Distibuted Diagram
     * @param type DiagramType corresponding to Distibuted Diagram
     * @return A diagram for the Distributed exercise
     */
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == DistributedMode.instance.getDiagramType()) {
            return createDistributedDiagram((DistributedForce) key);
        }

        return super.createNewDiagramImpl(key, type);
    }

    /**
     * Creates a distributed digram fiven a distributed force
     * @param dl Distributed force
     * @return DistributedDiagram
     */
    protected DistributedDiagram createDistributedDiagram(DistributedForce dl) {
        return new DistributedDiagram(dl);
    }

    /**
     * Creates a select diagram for the exercise
     * @return A new select diagram for the exercise
     */
    @Override
    protected SelectDiagram createSelectDiagram() {
        return new DistributedSelectDiagram();
    }

    /**
     * Creates an equation diagram for the given body subset
     * @param bodySubset Body subset for which to create an EquationDiagram
     * @return EquationDiagram created with bodySubset
     */
    @Override
    protected EquationDiagram createEquationDiagram(BodySubset bodySubset) {
        return new DistributedEquationDiagram(bodySubset);
    }

    /**
     * Creates a free body diagram for the given body subset
     * @param bodySubset Body subset for which to create an FreeBodyDiagram
     * @return FreeBodyDiagram created with bodySubset
     */
    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodySubset) {
        return new DistributedFreeBodyDiagram(bodySubset);
    }
}
