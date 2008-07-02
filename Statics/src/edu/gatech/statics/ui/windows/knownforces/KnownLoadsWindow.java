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
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.util.SolveListener;

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

        // first go through objects
        Exercise exercise = StaticsApplication.getApp().getExercise();
        for (SimulationObject obj : exercise.getSchematic().allObjects()) {

            // look at joints, specifically
            if (obj instanceof Connector) {
                Connector joint = (Connector) obj;
                if (joint.isSolved()) {
                    // iterate through reactions at joint
                    for (Vector force : joint.getReactions(joint.getBody1())) {

                        writeReaction(force, joint.getAnchor(), contents);
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

    private void writeReaction(Vector force, Point applicationPoint, StringBuffer contents) {
        writeReaction(force, applicationPoint, contents, force.getSymbolName());
    }
    
    private void writeReaction(Vector force, Point applicationPoint, StringBuffer contents, String name) {
        if (force.isSymbol() && !force.isKnown()) {
            return;
        }

        //String forceType = force instanceof Force ? "Force" : "Moment";
        String forceType = force.getUnit().name();
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
        contents.append(force.getQuantity().toStringDecimal()+force.getUnit().getSuffix());
        contents.append("</td></tr>");
    }

    public void onSolve() {
        updateView();
        pack(150,-1);
    }

    public void onLoadSolved(Load load) {
        onSolve();
    }

    public void onJointSolved(Connector joint) {
        onSolve();
    }
}
