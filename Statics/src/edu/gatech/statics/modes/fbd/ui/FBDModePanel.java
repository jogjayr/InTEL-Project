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
package edu.gatech.statics.modes.fbd.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDModePanel extends ApplicationModePanel<FreeBodyDiagram> {

    @Override
    public DiagramType getDiagramType() {
        return FBDMode.instance.getDiagramType();
    }
    /**
     * FBD2DTools
     */
    protected FBDTools tools;
    /**
     * Contains checkButton and resetButton
     */
    protected BContainer checkContainer;
    protected BButton checkButton,  resetButton;

    /**
     * Constructor
     */
    public FBDModePanel() {
        super();

        setLayoutManager(new BorderLayout(5, 0));

        tools = makeTools();//new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        checkContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));

        CheckListener listener = new CheckListener();

        checkButton = new NextButton("Check", listener, "check");
        resetButton = new BButton("Reset", listener, "reset");
        //checkButton.setStyleClass("circle_button");
        //resetButton.setStyleClass("circle_button");
        checkContainer.add(checkButton);
        checkContainer.add(resetButton);

        add(tools, BorderLayout.CENTER);
        add(checkContainer, BorderLayout.EAST);
    }

    /**
     * By default we make FBDTools2D. Later this may need to be changed to
     * allow the tools to be dependent on the exercise.
     * @return
     */
    protected FBDTools makeTools() {
        return new FBDTools2D(this);
    }

//    @Override
//    protected ApplicationTab createTab() {
//        return new ApplicationTab("Add Loads");
//    }

    /**
     * Activates the mode panel after checking solved-ness of diagram
     */
    @Override
    public void activate() {
        super.activate();

        // need to have list of bodies here...
        FreeBodyDiagram diagram = (FreeBodyDiagram) getDiagram();
        
        if (diagram.getBodySubset().getSpecialName() != null) {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset().getSpecialName());
        } else {
            //getTitleLabel().setText("My Diagram: " + diagram.getBodySubset());
        }

        //Exercise.getExercise().enableTabs(diagram.getBodySubset());

        if (diagram.isSolved()) {
            checkButton.setEnabled(false);
            resetButton.setEnabled(false);
            tools.setEnabled(false);
        } else {
            checkButton.setEnabled(true);
            resetButton.setEnabled(true);
            tools.setEnabled(true);
        }
    }

    /**
     * This class handles what happens when the user presses the "check" or "reset" buttons.
     * if the check passes, the diagram is set as solved.
     */
    private class CheckListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            FreeBodyDiagram diagram = (FreeBodyDiagram) getDiagram();

            if (event.getAction().equals("reset")) {
                ResetPopup popup = new ResetPopup(diagram);
                popup.popup(0, 0, true);
                popup.center();
            } else if (event.getAction().equals("check")) {
                FBDChecker checker = diagram.getChecker();//new FBDChecker(diagram);
                if (checker.checkDiagram()) {
                    diagram.setSolved(true);

                    resetButton.setEnabled(false);
                    tools.setEnabled(false);

                    //System.out.println("woo!");

                    // let the diagram to know to advance past the FBD stage.
                    //diagram.postSolve();
                    diagram.completed();
                }
            }
        }
    }
}
