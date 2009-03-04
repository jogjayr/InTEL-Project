/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletruss;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class SimpleTruss1 extends FrameExercise {

    @Override
    public void initExercise() {
        setName("Simple Truss 1");

        setDescription(
                "Solve for the tension and compression in each member.");

        Unit.setSuffix(Unit.distance, " m");
        Unit.setSuffix(Unit.force, " kN");
        Unit.setSuffix(Unit.moment, " kN*m");
        //Unit.setDisplayScale(Unit.distance, new BigDecimal("10"));
        getDisplayConstants().setMomentSize(0.5f);
        getDisplayConstants().setForceSize(0.5f);
        getDisplayConstants().setPointSize(0.5f);
        getDisplayConstants().setCylinderRadius(0.5f);
        getDisplayConstants().setForceLabelDistance(1f);
        getDisplayConstants().setMomentLabelDistance(0f);
        getDisplayConstants().setMeasurementBarSize(0.1f);
    }

    @Override
    public void loadExercise() {

        // create points
        Point A, B, C, D, E, F, G, H;
        A = new Point("A", "[0,0,0]");
        B = new Point("B", "[4,3,0]");
        C = new Point("C", "[4,0,0]");
        D = new Point("D", "[8,4.2,0]");
        E = new Point("E", "[8,0,0]");
        F = new Point("F", "[12,3,0]");
        G = new Point("G", "[12,0,0]");
        H = new Point("H", "[16,0,0]");

        List<Point> points = Arrays.asList(
                new Point[]{A, B, C, D, E, F, G, H});

        // create point bodies
        PointBody Ab, Bb, Cb, Db, Eb, Fb, Gb, Hb;
        Ab = new PointBody("Joint A", A);
        Bb = new PointBody("Joint B", B);
        Cb = new PointBody("Joint C", C);
        Db = new PointBody("Joint D", D);
        Eb = new PointBody("Joint E", E);
        Fb = new PointBody("Joint F", F);
        Gb = new PointBody("Joint G", G);
        Hb = new PointBody("Joint H", H);

        List<PointBody> pointBodies = Arrays.asList(
                new PointBody[]{Ab, Bb, Cb, Db, Eb, Fb, Gb, Hb});
        Map<Point, PointBody> pointBodyMap = new HashMap();
        pointBodyMap.put(A, Ab);
        pointBodyMap.put(B, Bb);
        pointBodyMap.put(C, Cb);
        pointBodyMap.put(D, Db);
        pointBodyMap.put(E, Eb);
        pointBodyMap.put(F, Fb);
        pointBodyMap.put(G, Gb);
        pointBodyMap.put(H, Hb);

        // create members
        Bar AB, AC, BC, BD, BE, CE, DE, DF, EF, EG, FG, FH, GH;
        AB = new Bar("AB", A, B);
        AC = new Bar("AC", A, C);
        BC = new Bar("BC", B, C);
        BD = new Bar("BD", B, D);
        BE = new Bar("BE", B, E);
        CE = new Bar("CE", C, E);
        DE = new Bar("DE", D, E);
        DF = new Bar("DF", D, F);
        EF = new Bar("EF", E, F);
        EG = new Bar("EG", E, G);
        FG = new Bar("FG", F, G);
        FH = new Bar("FH", F, H);
        GH = new Bar("GH", G, H);

        List<Bar> bars = Arrays.asList(
                new Bar[]{AB, AC, BC, BD, BE, CE, DE, DF, EF, EG, FG, FH, GH});

        // connect the bars of the truss to the joints.
        for (Bar bar : bars) {
            Point p1 = bar.getEnd1();
            Point p2 = bar.getEnd2();
            Connector2ForceMember2d c1 = new Connector2ForceMember2d(p1, bar);
            Connector2ForceMember2d c2 = new Connector2ForceMember2d(p2, bar);
            c1.attach(bar, pointBodyMap.get(p1));
            c2.attach(bar, pointBodyMap.get(p2));
        }

        // add the given forces
        Force forceA = new Force(A, Vector3bd.UNIT_Y.negate(), new BigDecimal(3));
        Force forceB = new Force(B, Vector3bd.UNIT_Y.negate(), new BigDecimal(6));
        Force forceD = new Force(D, Vector3bd.UNIT_Y.negate(), new BigDecimal(6));
        Force forceF = new Force(F, Vector3bd.UNIT_Y.negate(), new BigDecimal(6));
        Force forceH = new Force(H, Vector3bd.UNIT_Y.negate(), new BigDecimal(3));
        forceA.setName("loading A");
        forceB.setName("loading B");
        forceD.setName("loading D");
        forceF.setName("loading F");
        forceH.setName("loading H");
        Ab.addObject(forceA);
        Bb.addObject(forceB);
        Db.addObject(forceD);
        Fb.addObject(forceF);
        Hb.addObject(forceH);
        forceA.createDefaultSchematicRepresentation();
        forceB.createDefaultSchematicRepresentation();
        forceD.createDefaultSchematicRepresentation();
        forceF.createDefaultSchematicRepresentation();
        forceH.createDefaultSchematicRepresentation();

        // connect the truss to the world
        Roller2d rollerA = new Roller2d(A);
        rollerA.setName("roller A");
        rollerA.setDirection(Vector3bd.UNIT_Y);
        rollerA.attachToWorld(Ab);
        rollerA.createDefaultSchematicRepresentation();

        Pin2d pinH = new Pin2d(H);
        pinH.setName("pin H");
        pinH.attachToWorld(Hb);
        pinH.createDefaultSchematicRepresentation();

        for (Bar bar : bars) {
            bar.createDefaultSchematicRepresentation();
            getSchematic().add(bar);
        }

        for (PointBody pointBody : pointBodies) {
            pointBody.createDefaultSchematicRepresentation();
            getSchematic().add(pointBody);
        }

        for (Point point : points) {
            point.createDefaultSchematicRepresentation();
        }
    }
}
