/*
 * LabelSelector.java
 *
 * Created on July 23, 2007, 1:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BTextField;
import com.jmex.bui.BWindow;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.ModalPopupWindow;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.VectorObject;
import edu.gatech.statics.objects.manipulators.SelectionTool;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelSelector extends SelectionTool {

    private BWindow popupRoot;
    private String hintText = null;
    private boolean isCreating;

    /** Creates a new instance of LabelSelector */
    public LabelSelector(World world, BWindow popupRoot) {
        super(world, VectorObject.class);
        this.popupRoot = popupRoot;
    }

    @Override
    protected void onActivate() {
        super.onActivate();

        getWorld().enableManipulatorsOnSelect(false);
        getWorld().enableSelectMultiple(false);

        StaticsApplication.getApp().setAdvice("Select a force or moment to apply a label.");
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null || !(obj instanceof VectorObject)) {
            return;
        }

        if (obj.isGiven()) {
            System.out.println("can't select given object for labelling");
            return;
        }

        System.out.println("Selected... " + obj);

        createPopup((VectorObject) obj);
        // clear selection
        getWorld().clearSelection();
    }

    void setHintText(String text) {
        hintText = text;
    }

    void setIsCreating(boolean isCreating) {
        this.isCreating = isCreating;
    }

    private void createPopup(final VectorObject obj) {

        StaticsApplication.getApp().setAdvice("Please input a numerical value or symbolic name or a symbol for the " +
                ((obj instanceof Moment) ? "moment" : "force") + ".");

        final BPopupWindow popup = new ModalPopupWindow(popupRoot, new BorderLayout(5, 5));
        popup.setStyleClass("info_window");
        popup.setModal(true);

        BLabel label = new BLabel("Label Force:\n(give a name or a number)");
        popup.add(label, BorderLayout.NORTH);

        BContainer centerContainer = new BContainer(new BorderLayout(4, 4));
        final BTextField textfield = new BTextField();

        if (hintText == null) {
            if (obj.isSymbol()) {
                textfield.setText(obj.getSymbolName());
            } else {
                textfield.setText(obj.toStringDecimal());
            }
        } else {
            textfield.setText(hintText);
        }

        centerContainer.add(textfield, BorderLayout.CENTER);
        BLabel unitsLabel = new BLabel(obj.getUnit().getSuffix());
        centerContainer.add(unitsLabel, BorderLayout.EAST);
        popup.add(centerContainer, BorderLayout.CENTER);

        textfield.requestFocus();

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //System.out.println("selected OK??");

                if (updateVector(obj, textfield.getText())) {
                    popup.dismiss();
                    finish();
                } else {
                    StaticsApplication.getApp().setAdvice("I did not seem to recognize that input. Try again?");
                    System.out.println("some problem?");
                }
            }
        };
        textfield.addListener(listener);

        /*KeyListener keyListener = new KeyListener() {
        public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() != KeyInput.KEY_ESCAPE)
        return;
        if(isCreating) {
        // destroy force
        StaticsApplication.getApp().getCurrentWorld().clearSelection();
        StaticsApplication.getApp().getCurrentWorld().remove(obj);
        obj.destroy();                    
        }
        // dispose popup and tool
        popup.dismiss();
        finish();
        }
        public void keyReleased(KeyEvent event) {}
        };
        textfield.addListener(keyListener);
        popup.addListener(keyListener);*/

        BButton button = new BButton("OK", listener, "ok");
        popup.add(button, BorderLayout.SOUTH);

        Vector3f screenCoords = StaticsApplication.getApp().getCamera().getScreenCoordinates(obj.getTranslation());

        popup.popup((int) screenCoords.x, (int) screenCoords.y, true);

        BBackground background = new TintedBackground(new ColorRGBA(.8f, .8f, .8f, 1.0f));
        for (int state = 0; state < 3; state++) {
            popup.setBackground(state, background);
        }
    }

    private boolean updateVector(VectorObject obj, String text) {

        if (text.length() == 0) {
            return false;
        }

        if (Character.isDigit(text.charAt(0)) ||
                text.charAt(0) == '.' ||
                text.charAt(0) == '-') {
            // numerical, try to lop off tail end
            String[] split = text.split(" ");
            try {
                float value = Float.parseFloat(split[0]);

                // we do not want null values.
                if (value == 0) {
                    return false;
                }

                obj.setValue(value);
                obj.setSymbol(null);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {

            //obj.setName(text);
            obj.setSymbol(text);
        }
        return true;
    }
}
