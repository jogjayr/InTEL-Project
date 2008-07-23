/*
 * LabelSelector.java
 *
 * Created on July 23, 2007, 1:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BTextField;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 * This class creates the popup for changing the text for the label of a force or moment.
 * The typical usage is to create the LabelSelector, set various properties, and 
 * then call createPopup().
 * @author Calvin Ashmore
 */
public class LabelSelector /*extends SelectionTool*/ {

    private boolean isCreating;
    private String hintText = "F";
    private String advice = "Please give a name or a value";
    private String units = "";
    private LabelListener labelListener;
    private Vector3f displayPoint;
    private Load force;
    private Diagram diagram;

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    /** Creates a new instance of LabelSelector */
    public LabelSelector(Diagram d, Load force, Vector3f displayPoint) {
        diagram = d;
        this.force = force;
        this.labelListener = new LoadLabelListener(force);
        this.displayPoint = displayPoint;
    //super(world, VectorObject.class);
    }

    public void setHintText(String text) {
        hintText = text;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setIsCreating(boolean isCreating) {
        this.isCreating = isCreating;
    }

    public void createPopup() {
        StaticsApplication.getApp().setAdvice(advice);

        final BPopupWindow popup = new ModalPopupWindow(new BorderLayout(5, 5));
        popup.setStyleClass("application_popup");
        popup.setModal(true);

        BLabel label = new BLabel("Label Force:\n(give a name or a number)");
        popup.add(label, BorderLayout.NORTH);

        BContainer centerContainer = new BContainer(new BorderLayout(4, 4));
        final BTextField textfield = new BTextField();

        textfield.setText(hintText);

        centerContainer.add(textfield, BorderLayout.CENTER);
        BLabel unitsLabel = new BLabel(units);
        centerContainer.add(unitsLabel, BorderLayout.EAST);
        popup.add(centerContainer, BorderLayout.CENTER);

        textfield.requestFocus();

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //System.out.println("selected OK??");
                if (event.getAction().equals("cancel")) {
                    popup.dismiss();
                    StaticsApplication.getApp().resetAdvice();
                    diagram.removeUserObject(force);
                    diagram.onClick(null);
                //StaticsApplication.getApp().setAdvice("I did not seem to recognize that input. Try again?");
                //System.out.println("some problem?");
                } else if (labelListener.onLabel(textfield.getText())) {
                    popup.dismiss();
                    StaticsApplication.getApp().resetAdvice();
                //finish();
                }
            }
        };
        textfield.addListener(listener);

        BContainer buttonContainer = new BContainer(new BorderLayout());

        BButton okButton = new BButton("OK", listener, "ok");
        buttonContainer.add(okButton, BorderLayout.CENTER);

        BButton cancelButton = new BButton("Cancel", listener, "cancel");
        buttonContainer.add(cancelButton, BorderLayout.WEST);

        popup.add(buttonContainer, BorderLayout.SOUTH);

        Vector3f screenCoords = StaticsApplication.getApp().getCamera().getScreenCoordinates(displayPoint);

        popup.popup((int) screenCoords.x, (int) screenCoords.y, true);

        BBackground background = new TintedBackground(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        for (int state = 0; state < 3; state++) {
            popup.setBackground(state, background);
        }
    }
    public void renamePopup() {
        StaticsApplication.getApp().setAdvice(advice);

        final BPopupWindow popup = new ModalPopupWindow(new BorderLayout(5, 5));
        popup.setStyleClass("application_popup");
        popup.setModal(true);

        BLabel label = new BLabel("Label Force:\n(give a name or a number)");
        popup.add(label, BorderLayout.NORTH);

        BContainer centerContainer = new BContainer(new BorderLayout(4, 4));
        final BTextField textfield = new BTextField();

        textfield.setText(hintText);

        centerContainer.add(textfield, BorderLayout.CENTER);
        BLabel unitsLabel = new BLabel(units);
        centerContainer.add(unitsLabel, BorderLayout.EAST);
        popup.add(centerContainer, BorderLayout.CENTER);

        textfield.requestFocus();

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //System.out.println("selected OK??");
                if (event.getAction().equals("cancel")) {
                    popup.dismiss();
                    StaticsApplication.getApp().resetAdvice();
                //StaticsApplication.getApp().setAdvice("I did not seem to recognize that input. Try again?");
                //System.out.println("some problem?");
                } else if (labelListener.onLabel(textfield.getText())) {
                    popup.dismiss();
                    StaticsApplication.getApp().resetAdvice();
                //finish();
                }
            }
        };
        textfield.addListener(listener);

        BContainer buttonContainer = new BContainer(new BorderLayout());

        BButton okButton = new BButton("OK", listener, "ok");
        buttonContainer.add(okButton, BorderLayout.CENTER);

        BButton cancelButton = new BButton("Cancel", listener, "cancel");
        buttonContainer.add(cancelButton, BorderLayout.WEST);

        popup.add(buttonContainer, BorderLayout.SOUTH);

        Vector3f screenCoords = StaticsApplication.getApp().getCamera().getScreenCoordinates(displayPoint);

        popup.popup((int) screenCoords.x, (int) screenCoords.y, true);

        BBackground background = new TintedBackground(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        for (int state = 0; state < 3; state++) {
            popup.setBackground(state, background);
        }
    }
}
