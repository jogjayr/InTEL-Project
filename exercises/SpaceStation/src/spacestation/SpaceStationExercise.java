/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacestation;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.CentroidExercise;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.centroid.objects.RectangleCentroidPart;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.CoordinateAxis;
import edu.gatech.statics.objects.CoordinateAxis.Direction;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.tasks.SolveCentroidBodyTask;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
//import edu.gatech.statics.modes.centroid.objects.RectangleCentroidPart;
import java.math.BigDecimal;

/**
 *
 * @author Jimmy Truesdell
 */
public class SpaceStationExercise extends CentroidExercise {

    private CentroidBody station;
    private CentroidPartObject A, B, C, D;

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-5f, 5f, -4f, 6f);
        vc.setZoomConstraints(0.5f, 2f);
        vc.setRotationConstraints(-1, 1, -1, 1);
        interfaceConfiguration.setViewConstraints(vc);

        DiagramDisplayCalculator displayCalc = interfaceConfiguration.getDisplayCalculator();
        displayCalc.setRadiusMultiplier(1.8f);

//        DisplayConstants dc = new DisplayConstants();
//        dc.setPointSize(0.5);
//        getDisplayConstants().setPointSize(.15f);
        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("International Space Station");

        description.setNarrative("Kayla Neal is an Aerospace Engineering junior "
                + "who just found out in her Dynamics class that the position of "
                + "an object’s center of gravity is critically important to "
                + "characterize its motion. This summer, she’s taking a break "
                + "from GT to be an intern at NASA’s Jet Propulsion Lab in "
                + "Pasadena, CA. She’s very excited! Her supervisor asked her to "
                + "calculate the position of the center of gravity of the "
                + "International Space Station. Ultimately, she will have to "
                + "write a FORTRAN program to determine the path of the ISS over "
                + "time, based on the addition or removal of various modules "
                + "to its structure.");

        description.setProblemStatement("As a first step, she decides to "
                + "calculate its centroid, which means she assumes each module "
                + "has the same weight density. She also assumes the ISS is "
                + "entirely 2D (two-dimensional), and that the geometry of each "
                + "module can be simplified as shown below: this represents the "
                + "configuration of the ISS she will be working with, to start. "
                + "Can you help her calculate the position of the centroid of "
                + "the ISS?");

        description.setGoals("Find the space station's centroid.");

        description.addImage("spacestation/assets/spacestation0.jpg");
        description.addImage("spacestation/assets/spacestation1.jpg");
        description.addImage("spacestation/assets/spacestation2.jpg");
        description.addImage("spacestation/assets/spacestation3.jpg");

        return description;
    }

    @Override
    public void initExercise() {
//        Unit.setSuffix(Unit.distance, " ft");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".1"));

        getDisplayConstants().setPointSize(0.25f);
        getDisplayConstants().setMeasurementBarSize(0.2f);
  
    }

    @Override
    public void loadExercise() {

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.8f, .8f, .8f, 1.0f));


        Schematic schematic = getSchematic();

//        A1.createDefaultSchematicRepresentation();
//        A2.createDefaultSchematicRepresentation();
//        A3.createDefaultSchematicRepresentation();
//        A4.createDefaultSchematicRepresentation();
        //A = new CentroidPartObject(new CentroidPart("93.5", "110.0", "0.0", "SolarPanelA", "5610.0"));
        A = new CentroidPartObject(new RectangleCentroidPart("Solar Panel A", new Vector3bd("35.0", "50.75", "0.0"), 70.0, 11.5));
        A.setName("Solar Panel A");
        //B = new CentroidPartObject(new CentroidPart("276.0", "92.5", "0.0", "SolarPanelB", "5550.0"));
        B = new CentroidPartObject(new RectangleCentroidPart("Solar Panel B", new Vector3bd("125.75", "35.0", "0.0"), 11.5, 70.0));
        B.setName("Solar Panel B");
        C = new CentroidPartObject(new RectangleCentroidPart("Living Quarters", new Vector3bd("72.3", "42.5", "0.0"), 4.6, 55.0));
        C.setName("Living Quarters");
        D = new CentroidPartObject(new RectangleCentroidPart("Support Truss", new Vector3bd("80.0", "35.5", "0.0"), 80.0, 3.0));
        D.setName("Support Truss");
        Point p = new Point("Centroid", new Vector3bd("79.3246553", "41.9882311", "0.0"));
        p.setName("Centroid");

        C.setMarkerOffset(new Vector3f(0, 1, 0));

        station = new CentroidBody("Space Station", p);
        station.setName("Space Station");
        station.addObject(A);
        station.addObject(B);
        station.addObject(C);
        station.addObject(D);

        A.createDefaultSchematicRepresentation();
        B.createDefaultSchematicRepresentation();
        C.createDefaultSchematicRepresentation();
        D.createDefaultSchematicRepresentation();
        
        
        
        
        Point A1, A2, A3, B1, B2, B3, B4, B5, B6, C1, C2, C3, C4, C5, D1, D2, D3, origin;
        origin = new Point("Origin", "0.0", "0.0", "0.0");

//        CentroidPartMarker solarPanelA, solarPanelB, livingQuarters, panelExtender;

        A1 = new Point("A1", "0.0", "5.65", "0.0"); //topleft
        A2 = new Point("A2", "7.0", "5.65", "0.0"); //topright
        A3 = new Point("A3", "0.0", "4.5", "0.0"); //bottomleft

        ////////////////////
        //LEFT SOLAR PANEL//
        ////////////////////
        
        //BOTTOM HORIZONTAL
        DistanceMeasurement distance1 = new DistanceMeasurement(A1, A2);
        distance1.setName("Measure A1-A2");
        distance1.createDefaultSchematicRepresentation(-0.3f);
        distance1.forceHorizontal();
        A.addMeasurement(distance1);
        //distance1.addPoint(A);
//        schematic.add(distance1);

        //LEFT VERTICAL
        DistanceMeasurement distance2 = new DistanceMeasurement(A1, A3);
        distance2.setName("Measure A1-A3");
        distance2.createDefaultSchematicRepresentation(0.3f);
        distance2.forceVertical();
        //distance1.addPoint(A);
//        schematic.add(distance2);
        A.addMeasurement(distance2);


        DistanceMeasurement distance1a = new DistanceMeasurement(origin, A3);
        distance1a.setName("Measure origin-A3");
        distance1a.createDefaultSchematicRepresentation(-0.3f);
        distance1a.forceVertical();
        A.addMeasurement(distance1a);

        ////////////////////
        //HORIZONTAL STICK//
        ////////////////////

        B1 = new Point("B1", "4.0", "3.4", "0.0");
        Point B1a = new Point("B1a", "4.0", "3.7", "0.0");

        B2 = new Point("B2", "4.0", "0", "0.0"); //2nd left
        B3 = new Point("B3", "7.0", "0", "0.0"); //3rd left
        B4 = new Point("B4", "12.0", "0", "0.0"); //4th left
        
        //BOTTOM LEFT HORIZONTAL
        DistanceMeasurement distance4 = new DistanceMeasurement(origin, B2);
        distance4.setName("Measure origin-B2");
        distance4.createDefaultSchematicRepresentation(0.75f);
        distance4.forceHorizontal();
        D.addMeasurement(distance4);
        //distance1.addPoint(A);
//        schematic.add(distance4);


        DistanceMeasurement distance4a = new DistanceMeasurement(B1a, B1);
        distance4a.setName("Measure B1-B1a");
        distance4a.createDefaultSchematicRepresentation(0.75f);
        distance4a.forceVertical();
        D.addMeasurement(distance4a);

        DistanceMeasurement distance4b = new DistanceMeasurement(B1, B4);
        distance4b.setName("Measure B1-B1a");
        distance4b.createDefaultSchematicRepresentation(0.75f);
        distance4b.forceHorizontal();
        D.addMeasurement(distance4b);

        //BOTTOM MIDDLE HORIZONTAL
        DistanceMeasurement distance5 = new DistanceMeasurement(B2, B3);
        distance5.setName("Measure B2-B3");
        distance5.createDefaultSchematicRepresentation(0.75f);
        distance5.forceHorizontal();
        D.addMeasurement(distance5);
        //distance1.addPoint(A);
//        schematic.add(distance5);

        //BOTTOM RIGHT HORIZONTAL
        DistanceMeasurement distance6 = new DistanceMeasurement(B3, B4);
        distance6.setName("Measure B3-B4");
        distance6.createDefaultSchematicRepresentation(0.75f);
        distance6.forceHorizontal();
        D.addMeasurement(distance6);
        //distance1.addPoint(A);
//        schematic.add(distance6);

        DistanceMeasurement distance6a = new DistanceMeasurement(B2, B1);
        distance6a.setName("Measure B2-B1");
        distance6a.createDefaultSchematicRepresentation(0.75f);
        distance6a.forceVertical();
        D.addMeasurement(distance6a);



        ///////////////////
        //LIVING QUARTERS//
        ///////////////////

        
        B5 = new Point("B5", "7.0", "7.0", "0.0"); //topwidth
        B6 = new Point("B6", "7.46", "7.0", "0.0");

//        B5.createDefaultSchematicRepresentation();
//        B6.createDefaultSchematicRepresentation();
//        schematic.add(B5);
//        schematic.add(B6);


        //HORZONTAL
        DistanceMeasurement distance7 = new DistanceMeasurement(B6, B5);
        distance7.setName("Measure A2-B5");
        distance7.createDefaultSchematicRepresentation(0.7f);
        distance7.forceHorizontal();
        C.addMeasurement(distance7);
        //distance1.addPoint(A);
//        schematic.add(distance7);
        
        C1 = new Point("C1", "7.0", "7.0", "0.0"); //top
        C2 = new Point("C2", "7.0", "3.4", "0.0"); //2nd top
        C3 = new Point("C3", "7.0", "1.5", "0.0"); //3rd top
        C4 = new Point("C4", "7.0", "0.0", "0.0"); //bottom
        C5 = new Point("C5", "7.0", "3.7", "0.0");

//        C1.createDefaultSchematicRepresentation();
//        C2.createDefaultSchematicRepresentation();
//        C3.createDefaultSchematicRepresentation();
//        C4.createDefaultSchematicRepresentation();
//        C5.createDefaultSchematicRepresentation();
//        schematic.add(C1);
//        schematic.add(C2);
//        schematic.add(C3);
//        schematic.add(C4);
//        schematic.add(C5);

//        //TOP VERTICAL
//        DistanceMeasurement distance8 = new DistanceMeasurement(C1, C2);
//        distance8.setName("Measure C1-C2");
//        distance8.createDefaultSchematicRepresentation(0.33f);
//        distance8.forceVertical();
//        C.addMeasurement(distance8);
//        //distance1.addPoint(A);
//        schematic.add(distance8);

//        //TOP MIDDLE VERTICAL
//        DistanceMeasurement distance9 = new DistanceMeasurement(C2, C3);
//        distance9.setName("Measure C2-C3");
//        distance9.createDefaultSchematicRepresentation(0.33f);
//        distance9.forceVertical();
//        C.addMeasurement(distance9);
//        //distance1.addPoint(A);
////        schematic.add(distance9);
//
//        //BOTTOM MIDDLE VERTICAL
//        DistanceMeasurement distance13 = new DistanceMeasurement(C5, C2);
//        distance13.setName("Measure C2-C5");
//        distance13.createDefaultSchematicRepresentation(0.25f);
//        distance13.forceVertical();
//        C.addMeasurement(distance13);
//        schematic.add(distance13);

        //BOTTOM VERTICAL
        DistanceMeasurement distance10 = new DistanceMeasurement(C3, C4);
        distance10.setName("Measure C3-C4");
        distance10.createDefaultSchematicRepresentation(0.33f);
        distance10.forceVertical();
        //distance1.addPoint(A);
        C.addMeasurement(distance10);
//        schematic.add(distance10);

        DistanceMeasurement distance10a = new DistanceMeasurement(C1, C3);
        distance10a.setName("Measure C1-C3");
        distance10a.createDefaultSchematicRepresentation(0.33f);
        distance10a.forceVertical();
        //distance1.addPoint(A);
        C.addMeasurement(distance10a);
//        schematic.add(distance10);

        /////////////////////
        //RIGHT SOLAR PANEL//
        /////////////////////
        
        
        D1 = new Point("D1", "12.0", "7.0", "0.0"); //topleft
        D2 = new Point("D2", "13.15", "7.0", "0.0");  //topright
        D3 = new Point("D3", "13.15", "0", "0.0");  //bottom left
        
        //HORIZONTAL
        DistanceMeasurement distance11 = new DistanceMeasurement(D1, D2);
        distance11.setName("Measure D1-D2");
        distance11.createDefaultSchematicRepresentation(-0.2f);
        distance11.forceHorizontal();
        B.addMeasurement(distance11);
        //distance1.addPoint(A);
//        schematic.add(distance11);

        //VERTICAL
        DistanceMeasurement distance12 = new DistanceMeasurement(D2, D3);
        distance12.setName("Measure D2-D3");
        distance12.createDefaultSchematicRepresentation(-0.9f);
        distance12.forceVertical();
        B.addMeasurement(distance12);
        //distance1.addPoint(A);
//        schematic.add(distance12);
        
        CoordinateAxis xAxis = new CoordinateAxis(origin, Direction.horizontal);//new Point("xAxis","0.0","0.0","0.0"));
        xAxis.setName("xAxis");
        xAxis.setDistance(new BigDecimal("1.0"));
        xAxis.createDefaultSchematicRepresentation(0f);
        LabelRepresentation label = new LabelRepresentation(xAxis, "label_measurement");
        label.setDisplayOffset(new Vector3f(1.1f, 0, 0));
        label.setLabelText("x");
        xAxis.addRepresentation(label);
        label = new LabelRepresentation(xAxis, "label_measurement");
        label.setDisplayOffset(new Vector3f(0, 0, 0));
        label.setOffset(10, 10);
        label.setLabelText("O");
        xAxis.addRepresentation(label);

        B.addMeasurement(xAxis);
//        schematic.add(xAxis);

        CoordinateAxis yAxis = new CoordinateAxis(origin, Direction.vertical);//new Point("xAxis","0.0","0.0","0.0"));
        yAxis.setName("yAxis");
        yAxis.setDistance(new BigDecimal("1"));
        yAxis.createDefaultSchematicRepresentation(0f);
        label = new LabelRepresentation(yAxis, "label_measurement");
        label.setDisplayOffset(new Vector3f(0, 1.1f, 0));
        label.setLabelText("y");
        yAxis.addRepresentation(label);
        B.addMeasurement(yAxis);
//        schematic.add(yAxis);

        schematic.add(station);

        ModelNode modelNode = ModelNode.load("spacestation/assets/", "spacestation/assets/SpaceStation.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0, 0, 0);
        float modelScale = 1f;

        ModelRepresentation rep = modelNode.extractElement(station, "VisualSceneNode/completespacestation/solarpanel1");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);
//        A.addRepresentation(rep);
//        MimicRepresentation mimic = new MimicRepresentation(A, rep);
//        A.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "VisualSceneNode/completespacestation/solarpanel2");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);
//        B.addRepresentation(rep);
//        mimic = new MimicRepresentation(B, rep);
//        B.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "VisualSceneNode/completespacestation/mainBody");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);
//        C.addRepresentation(rep);
//        mimic = new MimicRepresentation(C, rep);
//        C.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "VisualSceneNode/completespacestation/truss");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);
//        D.addRepresentation(rep);
//        mimic = new MimicRepresentation(D, rep);
//        D.addRepresentation(mimic);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelScale(modelScale);
        schematic.getBackground().addRepresentation(rep);

//        addTask(new SolveCentroidPartTask("Solve for solar panel A's centroid", A));
//        addTask(new SolveCentroidPartTask("Solve for solar panel B's centroid", B));
//        addTask(new SolveCentroidPartTask("Solve for the living quarter's centroid", C));
//        addTask(new SolveCentroidPartTask("Solve for the panel extender's centroid", D));
        addTask(new SolveCentroidBodyTask("Solve the ISS' centroid", station));
    }
}
