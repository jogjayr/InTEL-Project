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
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelSelector /*extends SelectionTool*/ {

    private boolean isCreating;
    private String hintText = "F";
    private String advice = "Please give a name or a value";
    private String units = "";
    private LabelListener labelListener;
    private Vector3f displayPoint;

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    /** Creates a new instance of LabelSelector */
    public LabelSelector(LabelListener listener, Vector3f displayPoint) {
        this.labelListener = listener;
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
        popup.setStyleClass("info_window");
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

                if (labelListener.onLabel(textfield.getText())) {
                    popup.dismiss();
                    StaticsApplication.getApp().resetAdvice();
                //finish();
                } else {
                //StaticsApplication.getApp().setAdvice("I did not seem to recognize that input. Try again?");
                //System.out.println("some problem?");
                }
            }
        };
        textfield.addListener(listener);


        BButton button = new BButton("OK", listener, "ok");
        popup.add(button, BorderLayout.SOUTH);

        Vector3f screenCoords = StaticsApplication.getApp().getCamera().getScreenCoordinates(displayPoint);

        popup.popup((int) screenCoords.x, (int) screenCoords.y, true);

        BBackground background = new TintedBackground(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        for (int state = 0; state < 3; state++) {
            popup.setBackground(state, background);
        }
    }
}
