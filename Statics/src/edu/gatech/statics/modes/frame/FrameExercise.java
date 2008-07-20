/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.frame;

import com.jmex.bui.BButton;
import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.bodies.Background;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;

/**
 * A frame exercise is like an ordinary exercise, but it provides some UI elements to simplify the 
 * process working with frames.
 * @author Calvin Ashmore
 */
public class FrameExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();

        // attempt to remove the regular select panel from the mode panels.
        // ideally in the future, this sort of operation could be handled by a hashmap
        // or even better, a lookup.
        List<ApplicationModePanel> modePanels = ic.getModePanels();
        ApplicationModePanel selectModePanel = null;
        for (ApplicationModePanel panel : modePanels) {
            if (panel instanceof SelectModePanel) {
                selectModePanel = panel;
            }
        }
        modePanels.remove(selectModePanel);
        modePanels.add(new FrameSelectModePanel());

        return ic;
    }

    private class FrameSelectModePanel extends SelectModePanel {

        public FrameSelectModePanel() {
            // THIS IMPLEMENTATION IS UGLY, IT SHOULD BE FIXED!!!
            BComponent nextButton = getComponent(2);
            remove(2); // remove the next button

            BContainer buttonContainer = new BContainer(new BorderLayout());
            buttonContainer.add(nextButton, BorderLayout.CENTER);

            BButton selectAllButton = new BButton("Frame", new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    performSelectAll();
                }
            }, "selectall");
            selectAllButton.setStyleClass("circle_button");
            buttonContainer.add(selectAllButton, BorderLayout.SOUTH);

            add(buttonContainer, BorderLayout.EAST);
        }

        private void performSelectAll() {
            getDiagram().onClick(null);

            for (Body body : getDiagram().allBodies()) {
                if (body instanceof Background) {
                    continue;
                }
                getDiagram().onClick(body);
            }
        }
    }
}
