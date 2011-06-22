/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.description;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.AbsoluteLayout;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.util.Point;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.ui.maintabbar.MainTabBar;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionUI extends AppWindow {

    //private HTMLView descriptionView;
    private BButton button;
    private boolean addedComponents = false;

    /**
     * Constructor
     */
    public DescriptionUI() {
        //super(new AbsoluteLayout());
        super(null);

        int width = getDisplay().getWidth();
        int height = getDisplay().getHeight() - MainTabBar.MAIN_TAB_BAR_HEIGHT;
        setPreferredSize(width, height);
        //setBackground(new TintedBackground(new ColorRGBA(1, 1, 1, .75f)));
        setBackground(new TintedBackground(new ColorRGBA(1, 1, 1, 1f)));

//        descriptionView = new HTMLView();
//        descriptionView.setContents(Exercise.getExercise().getFullDescription());
//        descriptionView.getStyleSheet().addRule("body {font-size: 115%}");

        button = new BButton("Model and Solve", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                nextPressed();
            }
        }, "next");

        // add view to take up entire window.
        //add(descriptionView, new Rectangle(100, 100, 700, 500));
        //add(button, new Point(600, 200));
        add(button);
        //descriptionView.setLocation(0, 0);
    }

    /**
     * Accessible so that layouts can move the button around.
     * @return the "Model and Solve" button
     */
    public BButton getButton() {
        return button;
    }

    /**
     * Handles next button click event
     */
    private void nextPressed() {
        Exercise.getExercise().loadStartingMode();
    }

    /**
     * Sets window to preferred sized and moves to top left
     */
    void updatePlacement() {
        pack();
        //setLocation(0, MainTabBar.MAIN_TAB_BAR_HEIGHT);
        setLocation(0, 0);
    }

    /**
     * 
     * @param description to be added to UI
     */
    void layout(Description description) {
        if (!addedComponents) {
            description.getLayout().addComponents(this);
            addedComponents = true;
        }

        description.getLayout().layout(this, description);
    }
}
