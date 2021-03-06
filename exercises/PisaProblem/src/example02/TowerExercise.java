/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * TowerExercise.java
 *
 * Created on August 11, 2007, 10:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package example02;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class TowerExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = new DefaultInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        return interfaceConfiguration;
    }

    /** Creates a new instance of TowerExercise */
    public TowerExercise() {
        super(new Schematic());
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("Tower of Pisa");
        description.setProblemStatement("This is a model of the leaning tower of Pisa.");
        description.setGoals("Solve for the reaction forces at the base of the tower. " +
                "The tower's weight is 14700 tons.");

        return description;
    }

    @Override
    public void initExercise() {
//        setName("Tower of Pisa");
//
//        setDescription(
//                "This is a model of the tower of Pisa, solve for the reaction forces at its base. " +
//                "The tower's weight is 14700 tons.");

        Unit.setSuffix(Unit.distance, " ft");
        Unit.setSuffix(Unit.force, " ton");
        Unit.setSuffix(Unit.moment, " ton*ft");

        //getDisplayConstants().setDrawScale(3.5f);
        getDisplayConstants().setPointSize(3.5f);
        getDisplayConstants().setForceSize(3.5f);
        getDisplayConstants().setMomentSize(3.5f);
        getDisplayConstants().setCylinderRadius(3.5f);
        getDisplayConstants().setMeasurementBarSize(3.5f);
    }

    @Override
    public void loadExercise() {
        Schematic world = getSchematic();

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.0f, .5f, 1.0f, 1.0f));

        StaticsApplication.getApp().getCamera().setLocation(new Vector3f(0.0f, 24.0f, 100.0f));

        Point A = new Point("A", "0", "0", "0");
        Point B = new Point("B", "2.5", "55", "0");
        Point G = new Point("G", A.getPosition().add(B.getPosition()).mult(new BigDecimal(".5")));

        Point underG = new Point("underG", "" + G.getPosition().getX(), "0", "0");

        DistanceMeasurement horizontalDistance = new DistanceMeasurement(A, underG);
        horizontalDistance.createDefaultSchematicRepresentation();
        world.add(horizontalDistance);

        Fix2d jointA = new Fix2d(A);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        G.createDefaultSchematicRepresentation();

        Body tower = new Beam("Tower", A, B);
        tower.setCenterOfMassPoint(G);
        tower.createDefaultSchematicRepresentation();
        tower.getWeight().setDiagramValue(new BigDecimal("14700"));
        world.add(tower);

        jointA.attachToWorld(tower);

        float scale = 4f;

        ModelNode modelNode = ModelNode.load("example02/assets/", "example02/assets/pisaTower.dae");

        modelNode.extractLights();

        ModelRepresentation rep = modelNode.extractElement(tower, "VisualSceneNode/group27");
        //rep.setModelOffset(new Vector3f(0, 0, -22.0f / scale));

        Matrix3f rotation = new Matrix3f();
        rotation.fromAngleAxis(-.24f, Vector3f.UNIT_Z);
        rep.setModelRotation(rotation);
        rep.setLocalScale(scale * 1.5f);
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);

        tower.addRepresentation(rep);
        rep = modelNode.getRemainder(world.getBackground());
        rep.setLocalScale(scale);

        world.getBackground().addRepresentation(rep);

    }
}
