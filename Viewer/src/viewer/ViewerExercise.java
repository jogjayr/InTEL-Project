/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.ui.sidebar.Sidebar;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import viewer.ui.ViewerModePanel;
import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.io.File;
import java.net.URL;

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

    public void loadModel(String modelFileName, String modelDirectory) {

        if (model != null) {
            getSchematic().getBackground().removeRepresentation(model);
            model = null;
        }

        File directoryFile = new File(modelDirectory);
        File modelFile = new File(modelDirectory, modelFileName);

        URL directoryUrl = null;
        URL modelUrl = null;
        try {
            modelUrl = modelFile.toURI().toURL();
            directoryUrl = directoryFile.toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ViewerExercise.class.getName()).log(Level.SEVERE, null, ex);
        }

        ModelNode modelNode = ModelNode.load(directoryUrl, modelUrl);

        modelNode.extractLights();

        model = modelNode.getRemainder(getSchematic().getBackground());
        //model = modelNode.extractElement(getSchematic().getBackground(),
        //        "VisualSceneNode/stand/leg2");

        getSchematic().getBackground().addRepresentation(model);

        ViewerModePanel modePanel = (ViewerModePanel) InterfaceRoot.getInstance().getModePanel("viewer");
        modePanel.setModel(model);

        StaticsApplication.getApp().getCurrentDiagram().invalidateNodes();
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
            public Sidebar createSidebar() {
                Sidebar sidebar = new Sidebar();
                return sidebar;
            }

            @Override
            public void createModePanels() {
                getModePanels().add(new ViewerModePanel());
            }

            @Override
            public ViewConstraints createViewConstraints() {
                //return super.createViewConstraints();
                ViewConstraints constraints = new ViewConstraints();

                constraints.setPositionConstraints(-30, 30, -30, 30);
                constraints.setZoomConstraints(.5f, 50);
                constraints.setRotationConstraints(-1.5f, 1.5f);

                return constraints;
            }

            @Override
            public NavigationWindow createNavigationWindow() {
                return new Navigation3DWindow();
            }
//            @Override
//            public void createPopupWindows() {
//                // pass, do not create any popups.
//            }
        };
        return ic;
    }

    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        return new ViewerDiagram();
    }

    @Override
    public boolean supportsType(DiagramType type) {
        return type == DiagramType.getType("viewer");
    }

    @Override
    public Description getDescription() {
        Description desc = new Description();
        return desc;
    }

    @Override
    public void loadDescriptionMode() {
        // load the starting mode instead of the description.
        loadStartingMode();
    }
}
