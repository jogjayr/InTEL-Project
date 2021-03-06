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
package edu.gatech.statics.ui.maintabbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.ui.components.ChromaButton;

/**
 *
 * @author Calvin Ashmore
 */
public class MainTab extends BContainer {

    private final DiagramKey diagramKey;
    private DiagramType diagramType; // this is the type of the diagram when the tab has been created.
    // The type of the active diagram corresponding to this key may change over time, though.
    private ChromaButton button;

    /**
     * Constructor
     * @param diagram 
     */
    public MainTab(Diagram diagram) {
        super(new BorderLayout());
        this.diagramKey = diagram.getKey();
        this.diagramType = diagram.getType();

        button = new ChromaButton(
                "rsrc/interfaceTextures/tab",
                null, "",
                new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        onClicked();
                    }
                },
                "clickTab");

        add(button, BorderLayout.CENTER);

        updateLabel(diagram);
    }

    void onActivated() {
        // disable button,
        // change style (color?)
    }

    void onDeactivated() {
        // enable button
        // change style
    }

    /**
     * Handles click event on the main tab. 
     */
    private void onClicked() {
        //StaticsApplication.getApp().get
        //Diagram recentDiagram = Exercise.getExercise().getRecentDiagram(diagramKey);
        //StaticsApplication.getApp().setCurrentDiagram(recentDiagram);
        Diagram diagram = StaticsApplication.getApp().selectDiagram(diagramKey, diagramType);
        updateLabel(diagram);
    }

    /**
     * Sets button text to name of diagram
     * @param diagram 
     */
    void updateLabel(Diagram diagram) {

        button.setText(diagram.getName());
        //setBackground(new TintedBackground(MainTabBar.getTabColor(diagram)));
        button.setChroma(MainTabBar.getTabColor(diagram));
        button.setColor(MainTabBar.getTextColor(diagram));
        //label.setText(diagram.toString());
    }

    /**
     * Getter
     * @return 
     */
    DiagramKey getDiagramKey() {
        return diagramKey;
    }

    /**
     * Getter
     * @return 
     */
    DiagramType getDiagramType() {
        return diagramType;
    }
}
