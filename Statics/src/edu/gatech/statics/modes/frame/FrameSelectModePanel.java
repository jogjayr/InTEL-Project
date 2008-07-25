package edu.gatech.statics.modes.frame;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.bodies.Background;

/**
 * The UI class for the buttons for selection mode.
 * @author Calvin Ashmore
 */
public class FrameSelectModePanel extends SelectModePanel {

    public FrameSelectModePanel() {
        super();

        add(makeTools(), BorderLayout.CENTER);
//            // THIS IMPLEMENTATION IS UGLY, IT SHOULD BE FIXED!!!
//            BComponent nextButton = getComponent(2);
//            remove(2); // remove the next button
//
//            BContainer buttonContainer = new BContainer(new BorderLayout());
//            buttonContainer.add(nextButton, BorderLayout.CENTER);
//
//            BButton selectAllButton = new BButton("Frame", new ActionListener() {
//
//                public void actionPerformed(ActionEvent event) {
//                    performSelectAll();
//                }
//            }, "selectall");
//            selectAllButton.setStyleClass("circle_button");
//            buttonContainer.add(selectAllButton, BorderLayout.SOUTH);
//
//            add(buttonContainer, BorderLayout.EAST);
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
            getDiagram().onClick(body);
        }
    }
}