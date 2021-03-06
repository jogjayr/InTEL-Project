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
package edu.gatech.statics.exercise;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseUtilities {

    private ExerciseUtilities() {
    }

    /**
     * Shows a popup when the user has completed the exercise
     */
    public static void showCompletionPopup() {

        final ModalPopupWindow popup = new ModalPopupWindow(
                new BorderLayout(5, 5));
        popup.setStyleClass("application_popup");
        popup.setModal(true);

        popup.setPreferredSize(300, -1);

        BContainer title = new BContainer(new BorderLayout());
        BLabel titleLabel = new BLabel("Submit Assignment", "title_container");
        title.add(titleLabel, BorderLayout.CENTER);
        popup.add(title, BorderLayout.NORTH);

        BLabel textLabel;

        if (Exercise.getExercise().isExerciseFinished()) {
            if (StaticsApplication.getApp().isGraded()) {
                textLabel = new BLabel("GOOD JOB! You have solved this exercise. " +
                        "Your work has automatically been submitted.");

            } else {
                textLabel = new BLabel("GOOD JOB! You have solved this exercise.");
            }
        } else {
            textLabel = new BLabel("");
        }

        BContainer textContainer = new BContainer(new BorderLayout());
        textContainer.setStyleClass("padded_container");

        textContainer.add(textLabel, BorderLayout.CENTER);

        popup.add(textContainer, BorderLayout.CENTER);

        class MyActionListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                popup.dismiss();
            }
        }
        MyActionListener actionListener = new MyActionListener();

        BContainer buttonContainer = new BContainer(new BorderLayout());

        BButton submitButton = new BButton("OK", actionListener, "ok");
        buttonContainer.add(submitButton, BorderLayout.CENTER);


        popup.add(buttonContainer, BorderLayout.SOUTH);

        Dimension dim = new Dimension(300, 0);
        popup.popup((DisplaySystem.getDisplaySystem().getWidth() - dim.width) / 2, (DisplaySystem.getDisplaySystem().getHeight() - dim.height) / 2, true);
    }
}
