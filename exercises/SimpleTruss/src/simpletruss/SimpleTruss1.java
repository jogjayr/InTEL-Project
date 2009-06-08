/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletruss;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.truss.TrussExercise;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.DistanceMeasurement;
import edu.gatech.statics.objects.FixedAngleMeasurement;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.PointAngleMeasurement;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.bodies.TwoForceMember;
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
public class SimpleTruss1 extends TrussExercise {

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
                //new PointBody[]{Ab, Bb, Db, Eb, Fb, Hb});
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
        TwoForceMember AB, AC, BC, BD, BE, CE, DE, DF, EF, EG, FG, FH, GH;
        //TwoForceMember AE, EH;
        AB = new Bar("AB", A, B);
        BC = new ZeroForceMember("BC", B, C);
        BD = new Bar("BD", B, D);
        BE = new Bar("BE", B, E);
        DE = new Bar("DE", D, E);
        DF = new Bar("DF", D, F);
        EF = new Bar("EF", E, F);
        FG = new ZeroForceMember("FG", F, G);
        FH = new Bar("FH", F, H);
        //AE = new Bar("AE", A, E);
        //EH = new Bar("EH", E, H);

        // these ones are redundant, they are covered by AE and EH,
        // but their representations are used by thep potential ZFMs.
        // these are not added to the bars list.
        AC = new Bar("AC", A, C);
        CE = new Bar("CE", C, E);
        EG = new Bar("EG", E, G);
        GH = new Bar("GH", G, H);

        List<TwoForceMember> bars = Arrays.asList(
                new TwoForceMember[]{AB, BC, BD, BE, DE, DF, EF, FG, FH, AC, CE, EG, GH});

        // connect the bars of the truss to the joints.
        for (TwoForceMember bar : bars) {

            if (bar instanceof ZeroForceMember) {
                continue;
            }

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

        for (TwoForceMember bar : bars) {
            bar.createDefaultSchematicRepresentation();
            getSchematic().add(bar);
        }

        AngleMeasurement angleBAC, angleDBX, angleXBE, angleFHG, angleDFX, angleXFE;
        angleBAC = new PointAngleMeasurement(A, B, C);
        angleDBX = new FixedAngleMeasurement(B, D, Vector3f.UNIT_X);
        angleXBE = new FixedAngleMeasurement(B, E, Vector3f.UNIT_X);
        angleFHG = new PointAngleMeasurement(H, F, G);
        angleDFX = new FixedAngleMeasurement(F, D, Vector3f.UNIT_X.negate());
        angleXFE = new FixedAngleMeasurement(F, E, Vector3f.UNIT_X.negate());
        angleBAC.setName("angle bac");
        angleDBX.setName("angle dbx");
        angleXBE.setName("angle xbe");
        angleFHG.setName("angle fhg");
        angleDFX.setName("angle dfx");
        angleXFE.setName("angle xfe");

        DistanceMeasurement distanceAC, distanceCE, distanceEG, distanceGH;
        distanceAC = new DistanceMeasurement(A, C);
        distanceCE = new DistanceMeasurement(C, E);
        distanceEG = new DistanceMeasurement(E, G);
        distanceGH = new DistanceMeasurement(G, H);
        distanceAC.setName("distance ac");
        distanceCE.setName("distance ce");
        distanceEG.setName("distance eg");
        distanceGH.setName("distance gh");

        //AE.addObject(distanceAC);
        //AE.addObject(distanceCE);
        //EH.addObject(distanceEG);
        //EH.addObject(distanceGH);

        List<Measurement> measures = Arrays.asList(new Measurement[]{
                    angleBAC, angleDBX, angleXBE, angleFHG, angleDFX, angleXFE,
                    distanceAC, distanceCE, distanceEG, distanceGH});
        for (Measurement measurement : measures) {
            measurement.createDefaultSchematicRepresentation();
            getSchematic().add(measurement);
        }

        PotentialZFM pAB, pAC, pBC, pBD, pBE, pCE, pDE, pDF, pEF, pEG, pFG, pFH, pGH;
        pAB = new PotentialZFM(false);
        pAC = new PotentialZFM(false);
        pBC = new PotentialZFM(true);
        pBD = new PotentialZFM(false);
        pBE = new PotentialZFM(false);
        pCE = new PotentialZFM(false);
        pDE = new PotentialZFM(false);
        pDF = new PotentialZFM(false);
        pEF = new PotentialZFM(false);
        pEG = new PotentialZFM(false);
        pFG = new PotentialZFM(true);
        pFH = new PotentialZFM(false);
        pGH = new PotentialZFM(false);

        pAB.setBaseName("AB");
        pAC.setBaseName("AC");
        pBC.setBaseName("BC");
        pBD.setBaseName("BD");
        pBE.setBaseName("BE");
        pCE.setBaseName("CE");
        pDE.setBaseName("DE");
        pDF.setBaseName("DF");
        pEF.setBaseName("EF");
        pEG.setBaseName("EG");
        pFG.setBaseName("FG");
        pFH.setBaseName("FH");
        pGH.setBaseName("GH");

        // create default schematic representations for the redundant bars.
        AC.createDefaultSchematicRepresentation();
        CE.createDefaultSchematicRepresentation();
        EG.createDefaultSchematicRepresentation();
        GH.createDefaultSchematicRepresentation();
        pAB.addRepresentations(AB);
        pAC.addRepresentations(AC);
        pBC.addRepresentations(BC);
        pBD.addRepresentations(BD);
        pBE.addRepresentations(BE);
        pCE.addRepresentations(CE);
        pDE.addRepresentations(DE);
        pDF.addRepresentations(DF);
        pEF.addRepresentations(EF);
        pEG.addRepresentations(EG);
        pFG.addRepresentations(FG);
        pFH.addRepresentations(FH);
        pGH.addRepresentations(GH);

        List<PotentialZFM> potentialBars = Arrays.asList(
                new PotentialZFM[]{pAB, pBC, pBD, pBE, pDE, pDF, pEF, pFG, pFH, pAC, pCE, pEG, pGH});
        for (PotentialZFM potentialZFM : potentialBars) {
            // just use defaults, since there are no models in the simple exercises anyway.
            getSchematic().add(potentialZFM);
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
