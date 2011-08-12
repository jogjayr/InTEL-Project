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
 * LabelRepresentation.java
 *
 * Created on June 30, 2007, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelRepresentation extends Representation {

    private BWindow bWindow;
    private BLabel label;
    private Vector3f offset = new Vector3f();
    private Vector3f displayOffset = new Vector3f();
    private String overrideLabelText;

    //public Text getText() {return text;}
    public float getWidth() {
        return bWindow.getWidth();
    }

    public float getHeight() {
        return bWindow.getHeight();
    }

    public void setLabelText(String labelText) {
        this.overrideLabelText = labelText;
    }

    public void setDisplayOffset(Vector3f displayOffset) {
        this.displayOffset = displayOffset;
    }

    public void setOffset(float xOffset, float yOffset) {
        offset = new Vector3f(xOffset, yOffset, 0);
    }

    public BLabel getLabel() {
        return label;
    }

    public void addToInterface() {
        StaticsApplication.getApp().getLabelNode().addWindow(bWindow);
        bWindow.pack();
    }

    public void removeFromInterface() {
        StaticsApplication.getApp().getLabelNode().removeWindow(bWindow);
    }

    /** Creates a new instance of LabelRepresentation */
    public LabelRepresentation(SimulationObject target, String style) {
        super(target);
        setLayer(RepresentationLayer.labels);

        // the interface root may be null if the application is running without
        // a display. Simply do not initialize the representation in this case.
        if (InterfaceRoot.getInstance() == null) {
            return;
        }

        bWindow = new BWindow(
                InterfaceRoot.getInstance().getStyle(),
                //StaticsApplication.getApp().getBuiStyle(),
                new BorderLayout());

        label = new BLabel("");
        label.setText(target.getLabelText());
        //label.configureStyle(StaticsApplication.getApp().getBuiStyle());
        label.configureStyle(InterfaceRoot.getInstance().getStyle());

        bWindow.setStyleClass(style);
        label.setStyleClass(style);

        //label.addListener(new LabelClickListener());

        bWindow.add(label, BorderLayout.CENTER);

        setUseWorldScale(false);

        setSynchronizeRotation(false);
        setSynchronizeTranslation(false);

        updateRenderState();
//        update();
    }

    protected Vector3f getDisplayCenter() {
        return getTarget().getDisplayCenter();
    }
    private Vector3f pos2d;
    private String labelText = null;

    protected String getLabelText() {
        if (overrideLabelText != null) {
            return overrideLabelText;
        } else {
            return getTarget().getLabelText();
        }
    }

    @Override
    public void update() {

        String newLabelText = getLabelText();

        if (!newLabelText.equals(labelText)) {

            labelText = newLabelText;
            label.setText(labelText);
            bWindow.pack();
        }

        StaticsApplication app = StaticsApplication.getApp();

        // sometimes occurs if camera is not yet set up.
        if (app == null || app.getCamera() == null) {
            return;
        }

        pos2d = app.getCamera().getScreenCoordinates(getDisplayCenter().add(displayOffset));
        pos2d.addLocal(-getWidth() / 2, -getHeight() / 2, 0);
        pos2d.addLocal(offset);
        pos2d.z = 0;

        bWindow.setLocation((int) pos2d.x, (int) pos2d.y);
    }
}
