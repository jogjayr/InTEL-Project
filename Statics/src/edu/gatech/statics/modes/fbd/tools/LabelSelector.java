/*
 * LabelSelector.java
 *
 * Created on July 23, 2007, 1:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jmex.bui.BButton;
import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BTextField;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.event.MouseMotionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.fbd.actions.RemoveLoad;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.ui.components.ChromaButton;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 * This class creates the popup for changing the text for the label of a force or moment.
 * The typical usage is to create the LabelSelector, set various properties, and 
 * then call createPopup().
 * @author Calvin Ashmore
 */
public class LabelSelector /*extends SelectionTool*/ {

    // the how many pixels away the popup window will appear from the anchor of the load being labeled.
    private static final int POPUP_OFFSET = 20;
    private boolean isCreating;
    private String hintText = "F";
    private String advice = "Please give a name or a value";
    //private String units = "";
    private LabelListener labelListener;
    private Vector3f displayPoint;
    private Load load;
    private FreeBodyDiagram diagram;
    private BPopupWindow popup;

    /**
     * 
     * @param advice
     */
    public void setAdvice(String advice) {
        this.advice = advice;
    }

    /**
     * Creates a new instance of LabelSelector
     * @param fbd
     * @param force
     * @param displayPoint
     */
    public LabelSelector(FreeBodyDiagram fbd, Load force, Vector3f displayPoint) {
        diagram = fbd;
        this.load = force;
        this.labelListener = new LoadLabelListener(force);
        this.displayPoint = displayPoint;
        //super(world, VectorObject.class);
    }

    /**
     * Set text as hint for LabelSelector
     * @param text
     */
    public void setHintText(String text) {
        hintText = text;
    }

//    public void setUnits(String units) {
//        this.units = units;
//    }
    
    /**
     * 
     * @param isCreating
     */
    public void setIsCreating(boolean isCreating) {
        this.isCreating = isCreating;
    }

    /**
     * Dismiss label popup
     */
    public void dismiss() {
        popup.dismiss();
        StaticsApplication.getApp().resetUIFeedback();
    }

    /**
     * Create modal text field window for labeling the force or moment
     */
    public void popup() {
        StaticsApplication.getApp().setUIFeedback(advice);


        popup = new ModalPopupWindow(new BorderLayout(5, 5));
        popup.setStyleClass("application_popup");
        popup.setModal(true);

        String forceOrMoment = load.getUnit() == Unit.force ? "Force" : "Moment";

        BLabel label = new BLabel("Label " + forceOrMoment + ":\n(give a name or a number)");
        popup.add(label, BorderLayout.NORTH);

        BContainer centerContainer = new BContainer(new BorderLayout(4, 4));
        final BTextField textfield = new BTextField();

        textfield.setText(hintText);

        centerContainer.add(textfield, BorderLayout.CENTER);
        BLabel unitsLabel = new BLabel(load.getUnit().getSuffix());
        centerContainer.add(unitsLabel, BorderLayout.EAST);
        popup.add(centerContainer, BorderLayout.CENTER);

        textfield.requestFocus();

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //System.out.println("selected OK??");

                if (event.getAction().equals("cancel")) {
                    if (isCreating) {
                        // remove the load
                        RemoveLoad removeLoadAction = new RemoveLoad(load.getAnchoredVector());
                        diagram.performAction(removeLoadAction);
                    }
                    dismiss();
                } else if (event.getAction().equals("delete")) {
                    RemoveLoad removeLoadAction = new RemoveLoad(load.getAnchoredVector());
                    diagram.performAction(removeLoadAction);
                    dismiss();
                } else {
                    // try to set the label to be the new value
                    boolean success = labelListener.onLabel(textfield.getText());
                    if (success) {
                        dismiss();
                    }
                }
            }
        };
        textfield.addListener(listener);

        BContainer buttonContainer = new BContainer(new BorderLayout());

        BButton okButton = new BButton("OK", listener, "ok");
        buttonContainer.add(okButton, BorderLayout.CENTER);
        BButton cancelButton = new BButton("Cancel", listener, "cancel");
        buttonContainer.add(cancelButton, BorderLayout.WEST);
        if (!isCreating) {
            BButton deleteButton = new ChromaButton("rsrc/interfaceTextures/button",ColorRGBA.red,"Delete", listener, "delete");
            buttonContainer.add(deleteButton, BorderLayout.EAST);
        }

        popup.add(buttonContainer, BorderLayout.SOUTH);

        Vector3f screenCoords = StaticsApplication.getApp().getCamera().getScreenCoordinates(displayPoint);

        popup.popup((int) screenCoords.x + POPUP_OFFSET, (int) screenCoords.y + POPUP_OFFSET, true);

        DragController dragController = new DragController(popup);
        popup.addListener(dragController);
        label.addListener(dragController);

        BBackground background = new TintedBackground(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        for (int state = 0; state < 3; state++) {
            popup.setBackground(state, background);
        }
    }

    // copied from DraggablePopupWindow
    private class DragController implements MouseListener, MouseMotionListener {

        private static final int WINDOW_TOLERANCE = 10;

        public DragController(BComponent component) {
            this.component = component;
        }
        private final BComponent component;
        private boolean dragging;
        private int mouseX;
        private int mouseY;

        /**
         * Handles mouse press event
         * @param event
         */
        public void mousePressed(MouseEvent event) {
            dragging = true;
            mouseX = event.getX();
            mouseY = event.getY();
        }

        /**
         * Handles mouse release event
         * @param event
         */
        public void mouseReleased(MouseEvent event) {
            dragging = false;
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
            if (!MouseInput.get().isButtonDown(0)) {
                dragging = false;
            }
        }

        /**
         * Handles mouse drag event, makes sure label window can't be dragged off screen
         * @param event
         */
        public void mouseDragged(MouseEvent event) {

            if (!dragging) {
                return;
            }

            int dx = event.getX() - mouseX;
            int dy = event.getY() - mouseY;

            // check the boundary so that we can't accidentally move the window off the screen.
            int newX = dx + component.getX();
            int newY = dy + component.getY();

            if (newX < WINDOW_TOLERANCE) {
                newX = WINDOW_TOLERANCE;
            }
            if (newY < WINDOW_TOLERANCE) {
                newY = WINDOW_TOLERANCE;
            }
            if (newX > DisplaySystem.getDisplaySystem().getWidth() - component.getWidth() - WINDOW_TOLERANCE) {
                newX = DisplaySystem.getDisplaySystem().getWidth() - component.getWidth() - WINDOW_TOLERANCE;
            }
            if (newY > DisplaySystem.getDisplaySystem().getHeight() - component.getHeight() - WINDOW_TOLERANCE) {
                newY = DisplaySystem.getDisplaySystem().getHeight() - component.getHeight() - WINDOW_TOLERANCE;
            }
            component.setLocation(newX, newY);
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }
}
