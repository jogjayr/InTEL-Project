/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.knownforces;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.TableLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.util.SolveListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownsContainer extends BContainer implements SolveListener {

    public KnownsContainer() {

        // columns, row gap, column gap
        TableLayout layout = new TableLayout(2, 5, 5);
        setLayoutManager(layout);

        setStyleClass("sidebar_container");

        StaticsApplication.getApp().addSolveListener(this);
        updateView();
    }

    protected void handleConnector(Connector connector, List<Connector2ForceMember2d> handledConnectors) {
        // iterate through reactions at joint
        if (connector instanceof Connector2ForceMember2d) {
            Connector2ForceMember2d connector2fm = (Connector2ForceMember2d) connector;
            if (!handledConnectors.contains(connector2fm.getOpposite())) {
                writeReaction2FM(connector2fm);
                handledConnectors.add(connector2fm);
            }
        } else {
            for (Vector force : connector.getReactions(connector.getBody1())) {
                writeReaction(force, connector.getAnchor(), connector);
            }
        }
    }

    private void updateView() {

        removeAll();

        // handled connectors for sake of convenience with 2fms
        List<Connector2ForceMember2d> handledConnectors = new ArrayList<Connector2ForceMember2d>();

        // first go through objects
        Exercise exercise = StaticsApplication.getApp().getExercise();

        for (SimulationObject obj : exercise.getSchematic().allObjects()) {
            checkObject(obj, handledConnectors);
        }
    }

    protected void checkObject(SimulationObject obj, List<Connector2ForceMember2d> handledConnectors) {

        // look at joints, specifically
        if (obj instanceof Connector) {
            Connector connector = (Connector) obj;
            if (connector.isSolved()) {
                handleConnector(connector, handledConnectors);
            }
        }

        // look at free vectors
        if (obj instanceof Load) {
            Load vObj = (Load) obj;
            writeReaction(vObj.getVector(), vObj.getAnchor(), null, vObj.getName());
        }

        if (obj instanceof Body) {
            Body body = (Body) obj;
            writeWeightReaction(body);
        }

        if (obj instanceof ConstantObject) {
            ConstantObject constObj = (ConstantObject) obj;
            writeConstantObject(constObj);
        }

        if (obj instanceof CentroidPartObject) {
            CentroidPartObject cpo = (CentroidPartObject) obj;
            writeCentroidPartObject(cpo);
        }
    }

    protected void writeWeightReaction(Body body) {
        if (body.getWeight().doubleValue() == 0) {
            return;
        }

        // we will probably want to have some facility for letting
        // weights be unknown later on...

        String text = "Weight of @=b(" + body.getName() + ") at @=b(" + body.getCenterOfMassPoint().getName() + "): ";

        BLabel label = new BLabel(text);
        add(label);

        label = new BLabel(body.getWeight().toString());
        add(label);
    }

    protected void writeReaction2FM(Connector2ForceMember2d connector) {

        TwoForceMember member = connector.getMember();

        String text1;

        if (member instanceof Cable) {
            text1 = "cable ";
        } else if (member instanceof Bar) {
            text1 = "bar ";
        } else {
            text1 = "??? ";
        }

        text1 += member.getConnector1().getAnchor().getName()
                + member.getConnector2().getAnchor().getName();

        // this is the vector value for the 2fm
        Vector reaction = connector.getReactions(member).get(0);

        AnchoredVector reaction1 = Exercise.getExercise().getSymbolManager().getLoad(new AnchoredVector(connector.getAnchor(), reaction), connector);
        if (reaction1 != null) {
            reaction = reaction1.getVector();
        }

        text1 += " @=b#ff0000(" + reaction.getSymbolName() + ")";

        String tensionOrCompression;
        if (connector.inTension()) {
            tensionOrCompression = "tension";
        } else {
            tensionOrCompression = "compression";
        }

        String value = reaction.getQuantity().toStringDecimal() + reaction.getUnit().getSuffix();

        String text2 = value + " " + tensionOrCompression;

        BLabel label1 = new BLabel(text1);
        BLabel label2 = new BLabel(text2);

        add(label1);
        add(label2);
    }

    protected void writeReaction(Vector load, Point applicationPoint, Connector connector) {

        AnchoredVector load1 = Exercise.getExercise().getSymbolManager().getLoad(new AnchoredVector(applicationPoint, load), null);
        if (load1 != null) {
            load = load1.getVector();
        }

        writeReaction(load, applicationPoint, connector, load.getSymbolName());
    }

    protected void writeConstantObject(ConstantObject constObj) {

        // only write known constants
        if (!constObj.getQuantity().isKnown()) {
            return;
        }

        BLabel label1 = new BLabel(constObj.getName());
        BLabel label2 = new BLabel(constObj.getQuantity().toString());

        add(label1);
        add(label2);
    }

    protected void writeCentroidPartObject(CentroidPartObject cpo) {

        // only write known constants
//        if (!cpo) {
//            return;
//        }

//        "Weight of @=b(" + body.getName() + ") at @=b(" + body.getCenterOfMassPoint().getName() + "): ";


//        CentroidDiagram d = (CentroidDiagram) Exercise.getExercise().getDiagram(cpo.getCentroidPart(), DiagramType.getType(cpo.getCentroidPart().getPartName()));
//        if (d != null && d.getCurrentState().isLocked()) {
            BLabel label1 = new BLabel(" @=b#ff0000(" + cpo.getName() + ")");
            BLabel label2 = new BLabel("Surface area: @=b(" + cpo.getCentroidPart().getSurfaceArea() + ")");
            BLabel label3 = new BLabel("Center X: @=b(" + cpo.getCentroidPart().getxPosition() + ")");
            BLabel label4 = new BLabel("Center Y: @=b(" + cpo.getCentroidPart().getyPosition() + ")");
            add(label1);
            add(label2);
            add(label3);
            add(label4);
//        }
    }

    protected void writeReaction(Vector load, Point applicationPoint, Connector connector, String name) {
        if (!isGivenLoad(load) || load.isSymbol()) {
            AnchoredVector load1 = Exercise.getExercise().getSymbolManager().getLoad(new AnchoredVector(applicationPoint, load), connector);
            if (load1 != null) {
                load = load1.getVector();
            }
        }

        if (load.isSymbol() && !load.isKnown()) {
            return;
        }

        String text1 = load.getUnit().name() + " ";
        text1 += "@=b#ff0000(" + name + ")";

        if (applicationPoint != null) {
            text1 += " at @=b(" + applicationPoint.getName() + "): ";
        }

        String text2 = load.getQuantity().toStringDecimal() + load.getUnit().getSuffix();

        BLabel label1 = new BLabel(text1);
        BLabel label2 = new BLabel(text2);

        add(label1);
        add(label2);
    }

    protected boolean isGivenLoad(Vector load) {
        for (Body body : Exercise.getExercise().getSchematic().allBodies()) {
            for (SimulationObject obj : body.getAttachedObjects()) {
                if (obj instanceof Load && ((Load) obj).getVector().equals(load)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        updateView();
    }

    public void onLoadSolved(Load load) {
        update();
    }

    public void onJointSolved(Connector joint) {
        update();
    }
}
