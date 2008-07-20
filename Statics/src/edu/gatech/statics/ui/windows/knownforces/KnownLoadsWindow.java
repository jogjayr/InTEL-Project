/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.knownforces;

import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Bar;
import edu.gatech.statics.objects.bodies.Cable;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.util.SolveListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownLoadsWindow extends TitledDraggablePopupWindow implements SolveListener {

    public static final String windowName = "known loads";

    @Override
    public String getName() {
        return windowName;
    }
    private HTMLView view;

    public KnownLoadsWindow() {
        super(new BorderLayout(), "Known Loads");


        view = new HTMLView() {

            @Override
            public Dimension getPreferredSize(int whint, int hhint) {
                Dimension dim = super.getPreferredSize(whint, hhint);
                dim.height /= 2;
                dim.height += 10;
                return dim;
            }
        };
        add(view, BorderLayout.CENTER);

        StaticsApplication.getApp().addSolveListener(this);
        updateView();
    }

    private void updateView() {
        StringBuffer contents = new StringBuffer();
        //contents.append("<html><body>");
        contents.append("<table cellspacing=\"2\" cellpadding=\"2\">");

        // handled connectors for sake of convenience with 2fms
        List<Connector2ForceMember2d> handledConnectors = new ArrayList<Connector2ForceMember2d>();

        // first go through objects
        Exercise exercise = StaticsApplication.getApp().getExercise();
        for (SimulationObject obj : exercise.getSchematic().allObjects()) {

            // look at joints, specifically
            if (obj instanceof Connector) {
                Connector connector = (Connector) obj;
                if (connector.isSolved()) {
                    // iterate through reactions at joint
                    if (connector instanceof Connector2ForceMember2d) {
                        Connector2ForceMember2d connector2fm = (Connector2ForceMember2d) connector;
                        if (!handledConnectors.contains(connector2fm.getOpposite())) {
                            writeReaction2FM(connector2fm, contents);
                            handledConnectors.add(connector2fm);
                        }
                    } else {
                        for (Vector force : connector.getReactions(connector.getBody1())) {
                            writeReaction(force, connector.getAnchor(), contents);
                        }
                    }
                }
            }

            // look at free vectors
            if (obj instanceof Load) {
                Load vObj = (Load) obj;
                writeReaction(vObj.getVector(), vObj.getAnchor(), contents, vObj.getName());
            }

            if (obj instanceof Body) {
                Body body = (Body) obj;
                writeWeightReaction(body, contents);
            }
        }

        contents.append("</table>");
        //contents.append("</body></html>");

        view.setContents(contents.toString());
    }

    private void writeWeightReaction(Body body, StringBuffer contents) {
        if (body.getWeight().doubleValue() == 0) {
            return;
        }

        // we will probably want to have some facility for letting
        // weights be unknown later on...

        contents.append("<tr><td>");
        contents.append("Weight of <b>" + body.getName() + "</b> at [" + body.getCenterOfMassPoint().getName() + "]: ");
        contents.append("</td><td>");
        contents.append(body.getWeight().toString());
        contents.append("</td></tr>");
    }

    private void writeReaction2FM(Connector2ForceMember2d connector, StringBuffer contents) {

        contents.append("<tr><td>");

        TwoForceMember member = connector.getMember();

        if (member instanceof Cable) {
            contents.append("cable ");
        } else if (member instanceof Bar) {
            contents.append("bar ");
        } else {
            contents.append("??? ");
        }

        contents.append(member.getConnector1().getAnchor().getName());
        contents.append(member.getConnector2().getAnchor().getName());

        // this is the vector value for the 2fm
        Vector reaction = connector.getReactions(member).get(0);
        contents.append(" <font color=\"#ff0000\"><b>" + reaction.getSymbolName() + "</b></font>");

        contents.append("</td><td>");

        String tensionOrCompression;
        if (connector.getDirection().equals(reaction.getVectorValue())) {
            tensionOrCompression = "tension";
        } else {
            tensionOrCompression = "compression";
        }
        contents.append(reaction.getQuantity().toStringDecimal() + reaction.getUnit().getSuffix() + " " + tensionOrCompression);
        contents.append("</td></tr>");
    }

    private void writeReaction(Vector load, Point applicationPoint, StringBuffer contents) {
        writeReaction(load, applicationPoint, contents, load.getSymbolName());
    }

    private void writeReaction(Vector load, Point applicationPoint, StringBuffer contents, String name) {
        if (load.isSymbol() && !load.isKnown()) {
            return;
        }

        //String forceType = force instanceof Force ? "Force" : "Moment";
        String forceType = load.getUnit().name();
        contents.append("<tr><td>");
        contents.append(forceType + " ");
        //if(Diagram.getSchematic().allObjects())
        //    contents.append("<b>"+force.getName()+"</b>");
        //else
        contents.append("<font color=\"#ff0000\"><b>" + name + "</b></font>");
        if (applicationPoint != null) {
            contents.append(" at [" + applicationPoint.getName() + "]: ");
        }

        contents.append("</td><td>");
        contents.append(load.getQuantity().toStringDecimal() + load.getUnit().getSuffix());
        contents.append("</td></tr>");
    }

    public void onSolve() {
        updateView();
        pack(150, -1);
    }

    public void onLoadSolved(Load load) {
        onSolve();
    }

    public void onJointSolved(Connector joint) {
        onSolve();
    }
}
