/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.demo;

import viewer.ui.heirarchy.OpeningPanel;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import viewer.ViewerExercise;
import viewer.ViewerMode;

/**
 *
 * @author Calvin Ashmore
 */
public class Test extends ViewerExercise {

    public Test() {
        // set native look and feel, since we use Swing dialogs.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpeningPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(OpeningPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OpeningPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(OpeningPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    //OpeningFrame opener = new OpeningFrame();
    //opener.setVisible(true);
    }

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        ic.setNavigationWindow(new Navigation3DWindow());
        return ic;
    }

    @Override
    public void loadExercise() {
        
        System.out.println(ViewerMode.instance);
        
        //ModelRepresentation rep = new ModelRepresentation(getSchematic().getBackground(),
        //"viewer/demo/", "viewer/demo/bicycleNoLights.dae");
        //        "viewer/demo/", "viewer/demo/keyboard3.dae");
        //"viewer/demo/", "viewer/demo/bicycle5.dae");
        //"viewer/demo/", "viewer/demo/toast.dae");
        //getSchematic().getBackground().addRepresentation(rep);
        //getDisplayConstants().setDrawScale(2f);
        //setModel(rep);

        Point origin = new Point("origin", Vector3bd.ZERO);
        origin.setName("zero");
        getSchematic().add(origin);
        origin.createDefaultSchematicRepresentation();

        Point x1 = new Point("x1", Vector3bd.UNIT_X);
        x1.setName("x");
        getSchematic().add(x1);
        x1.createDefaultSchematicRepresentation();

        Point y1 = new Point("y1", Vector3bd.UNIT_Y);
        y1.setName("y");
        getSchematic().add(y1);
        y1.createDefaultSchematicRepresentation();

        Beam beam1 = new Beam("beam1", origin, x1);
        beam1.createDefaultSchematicRepresentation();
        getSchematic().add(beam1);

        Beam beam2 = new Beam("beam2", origin, y1);
        beam2.createDefaultSchematicRepresentation();
        getSchematic().add(beam2);
    }
}
