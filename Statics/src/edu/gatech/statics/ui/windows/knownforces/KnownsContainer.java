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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.knownforces;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.TableLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidMode;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.findpoints.FindPointsDiagram;
import edu.gatech.statics.modes.findpoints.FindPointsMode;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the UI section that contains the problem knowns
 * @author Calvin Ashmore
 */
public class KnownsContainer extends BContainer implements SolveListener {
    /**
     * Constructor. Has a table layout and listens for a solve event with SolveListener
     */
    public KnownsContainer() {
        // columns, row gap, column gap
        TableLayout layout = new TableLayout(2, 5, 5);
        setLayoutManager(layout);
        setStyleClass("sidebar_container");
        StaticsApplication.getApp().addSolveListener(this);
        updateView();
    }
    /**
     * checks if connector is Connector2ForceMember2d and adds it to handledConnectors if it doesn't belong already
     * Else, it calls writeReaction with the reactions of the connector
     * @param connector
     * @param handledConnectors
     */
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
    /**
     * 
     */
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
    /**
     * Sees what type of object obj is (Connector, Load, Body, ConstantObject,
     * CentroidBody, or Point, and calls the appropriate handler
     * @param obj 
     * @param handledConnectors 
     */
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

        if (obj instanceof CentroidBody) {
            CentroidBody cb = (CentroidBody) obj;
            writeCentroidBody(cb);
        }

        if (obj instanceof Point) {
            Point point = (Point) obj;
            FindPointsDiagram diagram = (FindPointsDiagram) Exercise.getExercise().getDiagram(null, FindPointsMode.instance.getDiagramType());
            if (diagram != null && diagram.getCurrentState().isLocked(point)) {
                writePoint(point, diagram);
            }
        }

//        if (obj instanceof CentroidPartObject) {
//            CentroidPartObject cpo = (CentroidPartObject) obj;
//            writeCentroidPartObject(cpo);
//        }
    }
    /**
     * Adds point name and position as label to container
     * @param point
     * @param findPointsDiagram 
     */
    protected void writePoint(Point point, FindPointsDiagram findPointsDiagram) {
        //******8
        BLabel label = new BLabel("@=b("+point.getName()+")");
        add(label);
        label = new BLabel(point.getPosition().toString());
        add(label);
    }
    /**
     * Writes the weight reaction of body to the container
     * @param body 
     */
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
    /**
     * Writes type of connector member (cable, bar), name of reactions, whether member is 
     * in tension or compression to knowns container
     * @param connector 
     */
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
    /**
     * Writes name, coords of load, if load is known
     */
    protected void writeReaction(Vector load, Point applicationPoint, Connector connector) {
        AnchoredVector load1 = Exercise.getExercise().getSymbolManager().getLoad(new AnchoredVector(applicationPoint, load), null);
        if (load1 != null) {
            load = load1.getVector();
        }
        writeReaction(load, applicationPoint, connector, load.getSymbolName());
    }
    /**
     * Writes name and quantity of constObj to knowns container
     * @param constObj 
     */
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
    /**
     * Adds name, surface area and centroid coordinates of cb to the container
     */
    protected void writeCentroidBody(CentroidBody cb) {
        CentroidDiagram cDiagram = (CentroidDiagram) Exercise.getExercise().getDiagram(cb, CentroidMode.instance.getDiagramType());
        BigDecimal surfaceArea = new BigDecimal("0.0");
        for (CentroidPartObject cpo : cb.getParts()) {
            surfaceArea = surfaceArea.add(cpo.getCentroidPart().getSurfaceArea());
        }
        if (cDiagram != null && cDiagram.getCurrentState() != null && cDiagram.isSolved()) {
            BLabel label1 = new BLabel(" @=b#ff0000(" + cb.getName() + ")");
            BLabel label2 = new BLabel("Surface area: @=b(" + surfaceArea + ")");
            BLabel label3 = new BLabel("Center X: @=b(" + cb.getCenterOfMass().getPosition().getX().toString() + ")");
            BLabel label4 = new BLabel("Center Y: @=b(" + cb.getCenterOfMass().getPosition().getY().toString() + ")");
            add(label1);
            add(label2);
            add(label3);
            add(label4);
        }
    }

//    protected void writeCentroidPartObject(CentroidPartObject cpo) {
//
//        // only write known constants
//        if (cpo.getState() == null || !cpo.getState().isLocked()) {
//            return;
//        }
//
//
////        "Weight of @=b(" + body.getName() + ") at @=b(" + body.getCenterOfMassPoint().getName() + "): ";
//
//
////        CentroidDiagram d = (CentroidDiagram) Exercise.getExercise().getDiagram(cpo.getCentroidPart(), DiagramType.getType(cpo.getCentroidPart().getPartName()));
////        if (d != null && d.getCurrentState().isLocked()) {
//            BLabel label1 = new BLabel(" @=b#ff0000(" + cpo.getName() + ")");
//            BLabel label2 = new BLabel("Surface area: @=b(" + cpo.getCentroidPart().getSurfaceArea() + ")");
//            BLabel label3 = new BLabel("Center X: @=b(" + cpo.getCentroidPart().getCentroid().getX().toString() + ")");
//            BLabel label4 = new BLabel("Center Y: @=b(" + cpo.getCentroidPart().getCentroid().getY().toString() + ")");
//            add(label1);
//            add(label2);
//            add(label3);
//            add(label4);
////        }
//    }
    /**
     * Adds load to list of knowns and updates labels with position, coordinates, if load is known
     * @param load
     * @param applicationPoint
     * @param connector
     * @param name 
     */
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
    /**
     * Returns true if load was given
     * @param load
     * @return 
     */
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
    /**
     * Calls updateView();
     */
    public void update() {
        updateView();
    }
    /**
     * Calls update() when load is solved
     */
    public void onLoadSolved(Load load) {
        update();
    }
    /**
     * Calls update() when joint is solved
     * @param joint 
     */
    public void onJointSolved(Connector joint) {
        update();
    }
}
