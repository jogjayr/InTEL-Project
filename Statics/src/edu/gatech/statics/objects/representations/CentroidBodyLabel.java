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
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.CentroidSelectDiagram;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.CentroidBodyMarker;
import edu.gatech.statics.objects.CentroidPartMarker;

/**
 * This class is what actually fills out and draws the white label that
 * represents the name of the CentroidBody that has been solved.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidBodyLabel extends LabelRepresentation {

    private boolean enabled;
    private CentroidBody body;

    public CentroidBodyLabel(CentroidBodyMarker target) {
        super(target, "label_hovering_centroid_part");

        this.body = target.getBody();

        setOffset(queueDistance, queueDistance);

        getLabel().addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                onClick();
            }
        });
    }

    protected void onClick() {
        if (isHidden()) {
            setHidden(false);
        } else {
            setHidden(true);
        }
    }

    @Override
    protected String getLabelText() {

        if (body == null) {
            return "Not ready yet.";
        } else {
            return "Centroid position of " + body.getName();
        }
    }

    @Override
    public void update() {

        // return if the the application is not loaded.
        if (StaticsApplication.getApp() == null) {
            return;
        }

        // we should enable the representation if the diagram is a select diagram
        boolean shouldEnable = StaticsApplication.getApp().getCurrentDiagram() instanceof CentroidSelectDiagram;

        enabled = shouldEnable;

//        if (cpo != null && cpo.getState() != null) {
        setHidden(false);
//        } else {
//            setHidden(true);
//        }
        super.update();
    }
}
