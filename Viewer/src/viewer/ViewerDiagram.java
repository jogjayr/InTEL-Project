/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerDiagram extends Diagram {

    @Override
    public Mode getMode() {
        return ViewerMode.instance;
    }

    public ModelRepresentation getModel() {
        return ((ViewerExercise) Exercise.getExercise()).getModel();
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        return getSchematic().allObjects();
    }

    public ViewerDiagram() {
    }
}
