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
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.MimicRepresentation;
import edu.gatech.statics.objects.representations.ModelNode;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.math.BigDecimal;

/**
 *
 * @author Jimmy Truesdell
 */
public class SpaceStationExercise extends CentroidExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration interfaceConfiguration = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();
        interfaceConfiguration.setNavigationWindow(new Navigation3DWindow());
        ViewConstraints vc = new ViewConstraints();
        vc.setPositionConstraints(-5f, 5f, -4f, 6f);
        vc.setZoomConstraints(0.5f, 1.5f);
        vc.setRotationConstraints(-1, 1);
        interfaceConfiguration.setViewConstraints(vc);
        return interfaceConfiguration;
    }

    @Override
    public Description getDescription() {
        Description description = new Description();

        description.setTitle("International Space Station");

        description.setNarrative("Ninjas have stolen the ISS.");

        description.setProblemStatement("Are you a bad enough dude to save the space station?");

        description.setGoals("Find the space station's centroid.");

        return description;
    }

    @Override
    public void initExercise() {
        Unit.setSuffix(Unit.distance, " ft");
        Unit.setDisplayScale(Unit.distance, new BigDecimal(".01"));

        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setMeasurementBarSize(0.2f);
    }

    @Override
    public void loadExercise() {

        DisplaySystem.getDisplaySystem().getRenderer().setBackgroundColor(new ColorRGBA(.9f, .9f, .9f, 1.0f));

        Schematic schematic = getSchematic();

        Point A1,A2,A3,A4,B1,B2,B3,B4,B5,C1,C2,C3,C4,D1,D2,D3,D4;
        A1 = new Point("A1","-0.05","1.25","0.0"); //topleft
        A2 = new Point("A2","1.82","1.25","0.0"); //topright
        A3 = new Point("A3","-0.05","0.95","0.0"); //bottomleft
        A4 = new Point("A4","1.7","1.10","0.0"); //centerright

        B1 = new Point("B1","-0.05","0.75","0.0"); //left
        B2 = new Point("B2","1.2","0.75","0.0"); //2nd left
        B3 = new Point("B3","1.85","0.75","0.0"); //3rd left
        B4 = new Point("B4","2.55","0.75","0.0"); //4th left
        B5 = new Point("B5", "1.9","1.10","0.0"); //topwidth

        C1 = new Point("C1","2.6","1.75","0.0"); //top
        C2 = new Point("C2","2.6","0.95","0.0"); //2nd top
        C3 = new Point("C3","2.6","0.6","0.0"); //3rd top
        C4 = new Point("C4","2.6","0.0","0.0"); //bottom

        D1 = new Point("A1","2.55","1.85","0.0"); //topleft
        D2 = new Point("A2","2.85","1.85","0.0");  //topright
        D3 = new Point("A3","2.55","0","0.0");  //bottom left
        D4 = new Point("A4","2.85","0","0.0");  //bottom right
//        A1.createDefaultSchematicRepresentation();
//        A2.createDefaultSchematicRepresentation();
//        A3.createDefaultSchematicRepresentation();
//        A4.createDefaultSchematicRepresentation();
        CentroidPartObject A = new CentroidPartObject(new CentroidPart("0.0", "0.0", "0.0", "Part A"));
        A.setName("Part A");
        CentroidPartObject B = new CentroidPartObject(new CentroidPart("0.0", "0.0", "0.0", "Part B"));
        B.setName("Part B");
        CentroidPartObject C = new CentroidPartObject(new CentroidPart("0.0", "0.0", "0.0", "Part C"));
        C.setName("Part C");
        CentroidPartObject D = new CentroidPartObject(new CentroidPart("0.0", "0.0", "0.0", "Part D"));
        D.setName("Part D");
        Point p = new Point("Centroid", new Vector3bd("451", "381", "0"));
        p.setName("Centroid");

        CentroidBody station = new CentroidBody("Space Station", p);
        station.setName("Space Station");
        station.addObject(A);
        station.addObject(B);
        station.addObject(C);
        station.addObject(D);


        DistanceMeasurement distance1 = new DistanceMeasurement(A1,A2);
        distance1.setName("Measure A1-A2");
        distance1.createDefaultSchematicRepresentation(0.5f);
        distance1.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance1);

        DistanceMeasurement distance2 = new DistanceMeasurement(A1,A3);
        distance2.setName("Measure A1-A3");
        distance2.createDefaultSchematicRepresentation(0.5f);
        distance2.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance2);

        DistanceMeasurement distance3 = new DistanceMeasurement(A3,A4);
        distance3.setName("Measure A3-A4");
        distance3.createDefaultSchematicRepresentation(0.5f);
        distance3.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance3);
        
        DistanceMeasurement distance4 = new DistanceMeasurement(B1,B2);
        distance4.setName("Measure B1-B2");
        distance4.createDefaultSchematicRepresentation(0.5f);
        distance4.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance4);

        DistanceMeasurement distance5 = new DistanceMeasurement(B2,B3);
        distance5.setName("Measure B2-B3");
        distance5.createDefaultSchematicRepresentation(0.5f);
        distance5.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance5);

        DistanceMeasurement distance6 = new DistanceMeasurement(B3,B4);
        distance6.setName("Measure B3-B4");
        distance6.createDefaultSchematicRepresentation(0.5f);
        distance6.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance6);

        DistanceMeasurement distance7 = new DistanceMeasurement(A2,B5);
        distance7.setName("Measure A2-B5");
        distance7.createDefaultSchematicRepresentation(0.5f);
        distance7.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance7);

        DistanceMeasurement distance8 = new DistanceMeasurement(C1,C2);
        distance8.setName("Measure C1-C2");
        distance8.createDefaultSchematicRepresentation(0.5f);
        distance8.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance8);

        DistanceMeasurement distance9 = new DistanceMeasurement(C2,C3);
        distance9.setName("Measure C2-C3");
        distance9.createDefaultSchematicRepresentation(0.5f);
        distance9.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance9);

        DistanceMeasurement distance10 = new DistanceMeasurement(C3,C4);
        distance10.setName("Measure C3-C4");
        distance10.createDefaultSchematicRepresentation(0.5f);
        distance10.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance10);

        DistanceMeasurement distance11 = new DistanceMeasurement(D1,D2);
        distance11.setName("Measure D1-D2");
        distance11.createDefaultSchematicRepresentation(0f);
        distance11.forceHorizontal();
        //distance1.addPoint(A);
        schematic.add(distance11);

        DistanceMeasurement distance12 = new DistanceMeasurement(D2,D3);
        distance12.setName("Measure D2-D3");
        distance12.createDefaultSchematicRepresentation(-0.5f);
        distance12.forceVertical();
        //distance1.addPoint(A);
        schematic.add(distance12);

//        A.createDefaultSchematicRepresentation();
//        B.createDefaultSchematicRepresentation();
//        C.createDefaultSchematicRepresentation();
//        D.createDefaultSchematicRepresentation();

        //bookshelf.createDefaultSchematicRepresentation();

//        DistanceMeasurement measureFull = new DistanceMeasurement(A, B);
//        measureFull.createDefaultSchematicRepresentation(3);
//        schematic.add(measureFull);
//
//        DistanceMeasurement measure1 = new DistanceMeasurement(A, mid1);
//        measure1.createDefaultSchematicRepresentation();
//        schematic.add(measure1);
//
//        DistanceMeasurement measure2 = new DistanceMeasurement(mid1, mid2);
//        measure2.createDefaultSchematicRepresentation();
//        schematic.add(measure2);
//
//        DistanceMeasurement measure3 = new DistanceMeasurement(mid3, B);
//        measure3.createDefaultSchematicRepresentation();
//        schematic.add(measure3);

        schematic.add(station);

        ModelNode modelNode = ModelNode.load("spacestation/assets/", "spacestation/assets/SpaceStation.dae");
        modelNode.extractLights();

        Vector3f modelTranslation = new Vector3f(0, 0, 0);
        float modelScale = .4f;

        ModelRepresentation rep = modelNode.extractElement(station, "RootNode/completespacestation/solarpanel1");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);

        MimicRepresentation mimic = new MimicRepresentation(A, rep);
        A.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "RootNode/completespacestation/solarpanel2");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);

        mimic = new MimicRepresentation(B, rep);
        B.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "RootNode/completespacestation/mainBody");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);

        mimic = new MimicRepresentation(C, rep);
        C.addRepresentation(mimic);

        rep = modelNode.extractElement(station, "RootNode/completespacestation/truss");
        rep.setSynchronizeRotation(false);
        rep.setSynchronizeTranslation(false);
        rep.setModelScale(modelScale);
        rep.setModelOffset(modelTranslation);
        station.addRepresentation(rep);

        mimic = new MimicRepresentation(D, rep);
        D.addRepresentation(mimic);

        rep = modelNode.getRemainder(schematic.getBackground());
        rep.setModelOffset(modelTranslation);
        rep.setModelScale(modelScale);
        schematic.getBackground().addRepresentation(rep);

//        addTask(new SolveConnectorTask("Solve A", end1));
//        addTask(new SolveConnectorTask("Solve B", end2));
    }
}
