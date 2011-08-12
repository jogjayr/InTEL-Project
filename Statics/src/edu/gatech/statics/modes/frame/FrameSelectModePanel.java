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
package edu.gatech.statics.modes.frame;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Background;
import java.util.ArrayList;
import java.util.List;

/**
 * The UI class for the buttons for selection mode.
 * @author Calvin Ashmore
 */
public class FrameSelectModePanel extends SelectModePanel {

    public FrameSelectModePanel() {
        super();

        add(makeTools(), BorderLayout.CENTER);
    }

    /**
     * By default we make FBDTools2D. Later this may need to be changed to
     * allow the tools to be dependent on the exercise.
     * @return
     */
    protected FrameTools makeTools() {
        return new FrameTools2D(this);
    }

    public void performSelectAll() {
        getDiagram().onClick(null);

        for (Body body : getDiagram().allBodies()) {
            if (body instanceof Background) {
                continue;
            }
            getDiagram().selectAll();
            //getDiagram().onClick(body);
        }
    }

    @Override
    protected String getContents(List<SimulationObject> selection) {
        // return a special bit of text if the selection represents the whole frame
        List<Body> selectionBodies = new ArrayList<Body>();
        for (SimulationObject obj : selection) {
            selectionBodies.add((Body) obj);
        }

        if (FrameUtil.isWholeDiagram(selectionBodies)) {
            String contents = "<font size=\"5\" color=\"white\">";
            contents += FrameUtil.whatToCallTheWholeDiagram + "</font>";
            return contents;
        }

        return super.getContents(selection);
    }
}
