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
package edu.gatech.statics.objects.representations;

import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.ui.LoadSelector;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.manipulators.Tool;

/**
 *
 * @author Calvin Ashmore
 */
public class FrictionCoefficientLabel extends LabelRepresentation {

    private boolean enabled;

    public FrictionCoefficientLabel(ContactPoint target) {
        super(target, "label_hovering_coefficient");

        setOffset(queueDistance, queueDistance);

        getLabel().addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                onClick();
            }
        });
    }

    protected void onClick() {
        // sneaky trick to make sure that when this is clicked while the load selector tool is active,
        // then the tool registers the click of this object.
        Tool currentTool = StaticsApplication.getApp().getCurrentTool();
        if(currentTool != null && currentTool instanceof LoadSelector)  {
            ConstantObject frictionCoefficient = ((ContactPoint) getTarget()).getFrictionCoefficient();
            currentTool.onClick(frictionCoefficient);
        }
    }

    @Override
    protected String getLabelText() {
        ConstantObject coefficient = ((ContactPoint) getTarget()).getFrictionCoefficient();
        return coefficient.getLabelText();
    }

    @Override
    public void update() {

        // return if the the application is not loaded.
        if (StaticsApplication.getApp() == null) {
            return;
        }

        // we should enable the representation if the diagram is a select diagram
        boolean shouldEnable = StaticsApplication.getApp().getCurrentDiagram() instanceof EquationDiagram;

        enabled = shouldEnable;

        if (enabled) {
            setHidden(false);
        } else {
            setHidden(true);
        }
        super.update();
    }
}
