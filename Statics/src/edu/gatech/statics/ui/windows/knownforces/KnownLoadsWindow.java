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
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.VectorObject;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownLoadsWindow extends TitledDraggablePopupWindow implements TaskStatusListener {

    public static final String windowName = "known loads";

    @Override
    public String getName() {
        return windowName;
    }
    private HTMLView view;

    public KnownLoadsWindow() {
        super(new BorderLayout(), "Known Loads");


        view = new HTMLView();
        add(view, BorderLayout.CENTER);

        updateView();
    }

    private void updateView() {
        StringBuffer contents = new StringBuffer();
        contents.append("<html><head>");
        contents.append("<table cellspacing=\"2\" cellpadding=\"2\">");

        // first go through objects
        Exercise exercise = StaticsApplication.getApp().getExercise();
        for (SimulationObject obj : exercise.getSchematic().allObjects()) {

            // look at joints, specifically
            if (obj instanceof Joint) {
                Joint joint = (Joint) obj;
                if (joint.isSolved()) {
                    // iterate through reactions at joint
                    for (Vector force : joint.getReactions(joint.getBody1())) {

                        writeReaction(force, joint.getPoint(), contents);
                    }
                }
            }

            // look at free vectors
            if (obj instanceof VectorObject) {
                VectorObject vObj = (VectorObject) obj;
                writeReaction(vObj.getVector(), vObj.getAnchor(), contents);
            }

            if (obj instanceof Body) {
                Body body = (Body) obj;
                writeWeightReaction(body, contents);
            }
        }

        contents.append("</table>");
        contents.append("</html></head>");

        view.setContents(contents.toString());

    /*try {
    System.out.println("View update on KnownSheetPopup:");
    System.out.println("View preferred size: "+view.getPreferredSize(-1, -1));
    System.out.println("View actual size: "+view.getWidth()+","+view.getHeight());
    System.out.println("Window preferred size: "+getPreferredSize(-1, -1));
    System.out.println("Window actual size: "+getWidth()+","+getHeight());
    } catch(NullPointerException e) {
    System.out.println("Null Pointer Exception");
    }*/
    }

    private void writeWeightReaction(Body body, StringBuffer contents) {
        if (body.getWeight().getValue() == 0) {
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
        contents.append("<font color=\"#ff0000\"><b>" + force.getSymbolName() + "</b></font>");
        if (applicationPoint != null) {
            contents.append(" at [" + applicationPoint.getName() + "]: ");
        }
        contents.append("</td><td>");
        contents.append(force.getQuantity().toStringDecimal());
        contents.append("</td></tr>");
    }

    @Override
    public void popup(int x, int y, boolean above) {
        super.popup(x, y, above);
        StaticsApplication.getApp().getExercise().addTaskListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        StaticsApplication.getApp().getExercise().removeTaskListener(this);
    }

    public void taskSatisfied(Task task) {
        //view.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
        updateView();

        int height = getHeight();
        //pack(150,150);

        Dimension preferredSize = getPreferredSize(150, -1);
        setSize(preferredSize.width, 2 * preferredSize.height / 3);
        int newHeight = getHeight();

        setLocation(getX(), getY() - (newHeight - height));
    }
}
