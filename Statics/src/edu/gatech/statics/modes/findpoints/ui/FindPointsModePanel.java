/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints.ui;

import com.jmex.bui.BContainer;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.findpoints.FindPointsDiagram;
import edu.gatech.statics.modes.findpoints.FindPointsMode;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin
 */
public class FindPointsModePanel extends ApplicationModePanel<FindPointsDiagram> {

    @Override
    public DiagramType getDiagramType() {
        return FindPointsMode.instance.getDiagramType();
    }
    private BContainer mainContainer;
    private BScrollPane mainScrollPane;
    private Map<Point, PointBar> pointBars = new HashMap<Point, PointBar>();

    public FindPointsModePanel() {

        GroupLayout equationLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        equationLayout.setOffAxisJustification(GroupLayout.LEFT);
        mainContainer = new BContainer(equationLayout);

        mainScrollPane = new BScrollPane(mainContainer, true, true);
        mainScrollPane.setShowScrollbarAlways(false);
        add(mainScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void activate() {
        super.activate();

        // empty contents
        mainContainer.removeAll();
        pointBars.clear();

        // refresh
        for (SimulationObject simulationObject : Diagram.getSchematic().allObjects()) {
            if (simulationObject instanceof Point) {
                Point point = (Point) simulationObject;
                PointBar pointBar = new PointBar(this, point);
                mainContainer.add(pointBar);
                pointBars.put(point, pointBar);

                if (getDiagram().getCurrentState().isLocked(point)) {
                    pointBar.lockBar();
                }
            }
        }
    }
}
