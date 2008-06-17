/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.demo;

import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import viewer.ViewerExercise;

/**
 *
 * @author Calvin Ashmore
 */
public class Test extends ViewerExercise {

    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        ic.setNavigationWindow(new Navigation3DWindow());
        return ic;
    }

    @Override
    public float getDrawScale() {
        return .2f;
    }

    @Override
    public void loadExercise() {

        ModelRepresentation rep = new ModelRepresentation(getSchematic().getBackground(),
                "viewer/demo/", "viewer/demo/bicycleNoLights.dae");
        getSchematic().getBackground().addRepresentation(rep);
        
        setModel(rep);
        
        Point origin = new Point(Vector3bd.ZERO);
        origin.setName("zero");
        getSchematic().add(origin);
        origin.createDefaultSchematicRepresentation();

        Point x1 = new Point(Vector3bd.UNIT_X);
        x1.setName("x");
        getSchematic().add(x1);
        x1.createDefaultSchematicRepresentation();

        Point y1 = new Point(Vector3bd.UNIT_Y);
        y1.setName("y");
        getSchematic().add(y1);
        y1.createDefaultSchematicRepresentation();

        Beam beam1 = new Beam(origin, x1);
        beam1.createDefaultSchematicRepresentation();
        getSchematic().add(beam1);

        Beam beam2 = new Beam(origin, y1);
        beam2.createDefaultSchematicRepresentation();
        getSchematic().add(beam2);
    }
}
