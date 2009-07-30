/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.maintabbar;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;

/**
 *
 * @author Calvin Ashmore
 */
public class MainTab extends BContainer {

    private final DiagramKey diagramKey;
    private DiagramType diagramType; // this is the type of the diagram when the tab has been created.
    // The type of the active diagram corresponding to this key may change over time, though.
    private BButton label;

    public MainTab(Diagram diagram) {
        super(new BorderLayout());
        this.diagramKey = diagram.getKey();
        this.diagramType = diagram.getType();

        label = new BButton("", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                onClicked();
            }
        }, "clickTab");

        add(label, BorderLayout.CENTER);

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

    private void onClicked() {
        //StaticsApplication.getApp().get
        //Diagram recentDiagram = Exercise.getExercise().getRecentDiagram(diagramKey);
        //StaticsApplication.getApp().setCurrentDiagram(recentDiagram);
        Diagram diagram = StaticsApplication.getApp().selectDiagram(diagramKey, diagramType);
        updateLabel(diagram);
    }

    void updateLabel(Diagram diagram) {

        label.setText(diagram.getName());
        setBackground(new TintedBackground(MainTabBar.getTabColor(diagram)));
        label.setColor(MainTabBar.getTextColor(diagram));
        //label.setText(diagram.toString());
    }

    DiagramKey getDiagramKey() {
        return diagramKey;
    }

    DiagramType getDiagramType() {
        return diagramType;
    }
}
