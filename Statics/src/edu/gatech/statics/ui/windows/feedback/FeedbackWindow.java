/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.feedback;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.DiagramListener;

/**
 *
 * @author Calvin Ashmore
 */
public class FeedbackWindow extends AppWindow {

    public static final int WIDTH = 200;
    private HTMLView view;

    public FeedbackWindow() {
        super(new BorderLayout());

        BContainer header = new BContainer(new BorderLayout());
        header.setStyleClass("draggable_title");

        BLabel title = new BLabel("Feedback");
        header.add(title, BorderLayout.CENTER);

        BButton closeButton = new BButton("x", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                closePressed();
            }
        }, "close");
        header.add(closeButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        BContainer body = new BContainer(new BorderLayout());
        body.setStyleClass("application_bar");

        view = new HTMLView();
        //add(view, BorderLayout.CENTER);
        body.add(view, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
        view.setStyleClass("advice_box");

        //setStyleClass("advice_box");
        //setVisible(false);

        // close when the diagram changes
        StaticsApplication.getApp().addDiagramListener(new DiagramListener() {

            public void onDiagramCreated(Diagram diagram) {
            }

            public void onDiagramChanged(Diagram newDiagram) {
                closePressed();
            }
        });
    }

    private void closePressed() {
        // close
        if (InterfaceRoot.getInstance().getBuiNode().getWindows().contains(this)) {
            InterfaceRoot.getInstance().getBuiNode().removeWindow(this);
        }
    }

    public void setFeedback(String feedback) {

        if (!InterfaceRoot.getInstance().getBuiNode().getWindows().contains(this)) {
            InterfaceRoot.getInstance().getBuiNode().addWindow(this);
        }

        view.setContents(feedback);
        //setVisible(true);
        //Dimension preferredSize1 = view.getPreferredSize(WIDTH, -1);
        //Dimension preferredSize2 = view.getPreferredSize(-1, -1);
        //view.setPreferredSize(WIDTH, preferredSize2.height);
        //System.out.println("HEIGHT: "+preferredSize.height);

        setPreferredSize(WIDTH, -1);
        //int height = InterfaceRoot.getInstance().getApplicationBar().getHeight();
        //setPreferredSize(WIDTH, height);
        pack();
    }
}
