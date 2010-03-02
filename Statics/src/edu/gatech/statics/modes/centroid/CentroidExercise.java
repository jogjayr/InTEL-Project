/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.modes.select.SelectDiagram;

/**
 *
 * @author Jimmy Truesdell
 */
abstract public class CentroidExercise extends OrdinaryExercise {

    public CentroidExercise() {
    }

    public CentroidExercise(Schematic schematic) {
        super(schematic);
    }
    
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == CentroidMode.instance.getDiagramType()) {
            return createCentroidDiagram((CentroidBody) key);
        }

        return super.createNewDiagramImpl(key, type);
    }
    
    protected CentroidDiagram createCentroidDiagram(CentroidBody body) {
        return new CentroidDiagram(body);
    }
    
    @Override
    protected SelectDiagram createSelectDiagram() {
        return new CentroidSelectDiagram();
    }
}
