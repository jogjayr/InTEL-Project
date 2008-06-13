/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import viewer.ui.ViewerModePanel;
import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerExercise extends Exercise {

    private ViewerDiagram diagram;
    private ModelRepresentation model;

    public ModelRepresentation getModel() {
        return model;
    }

    protected void setModel(ModelRepresentation model) {
        this.model = model;
    }

    public ViewerDiagram getDiagram() {
        if (diagram == null) {
            diagram = new ViewerDiagram();
        }
        return diagram;
    }

    @Override
    public Mode loadStartingMode() {
        ViewerMode.instance.load();
        return ViewerMode.instance;
    }

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {

        AbstractInterfaceConfiguration ic = new DefaultInterfaceConfiguration() {

            @Override
            public void createModePanels() {
                getModePanels().add(new ViewerModePanel());
            }

            @Override
            public void createPopupWindows() {
                // pass, do not create any popups.
            }
        };
        return ic;
    }
}
