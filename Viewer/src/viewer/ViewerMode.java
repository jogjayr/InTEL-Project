/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerMode extends Mode {

    public static final ViewerMode instance = new ViewerMode();

    @Override
    protected Diagram getDiagram(DiagramKey key) {

        return ((ViewerExercise) getExercise()).getDiagram();
    }

    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("viewer", 1);
    }
}
