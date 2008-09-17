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
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseUtilities {

    private ExerciseUtilities() {
    }

    /*public static String showNamePopup() {
    
    final ModalPopupWindow popup = new ModalPopupWindow(new BorderLayout());
    popup.setStyleClass("application_popup");
    popup.setModal(true);
    
    popup.setPreferredSize(300, -1);
    
    BContainer title = new BContainer(new BorderLayout());
    BLabel titleLabel = new BLabel("Before we start...", "title_container");
    title.add(titleLabel, BorderLayout.CENTER);
    popup.add(title, BorderLayout.NORTH);
    
    GroupLayout contentLayout = GroupLayout.makeVert(GroupLayout.TOP);
    contentLayout.setOffAxisJustification(GroupLayout.LEFT);
    
    BContainer content = new BContainer(contentLayout);
    content.setStyleClass("content_container");
    popup.add(content, BorderLayout.CENTER);
    
    content.add(new BLabel("Please give your full name so you can be given credit for this exercise."));
    
    BContainer nameRow = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
    content.add(nameRow);
    nameRow.add(new BLabel("Name: "));
    final BTextField nameField = new BTextField();
    nameField.setPreferredWidth(200);
    nameRow.add(nameField);
    
    class MyActionListener implements ActionListener {
    
    String studentName;
    
    public void actionPerformed(ActionEvent event) {
    String name = nameField.getText();
    if (name.trim().equals("")) {
    return;
    }
    studentName = name;
    popup.dismiss();
    }
    }
    
    MyActionListener actionListener = new MyActionListener();
    
    BButton okButton = new BButton("OK", actionListener, "ok");
    content.add(okButton);
    nameField.addListener(actionListener);
    nameField.requestFocus();
    
    popup.popup(0, 0, true);
    
    return actionListener.studentName;
    }*/
    /**
     * Returns true if the user wishes to finish and navigate away
     * @return
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
            textLabel = new BLabel("CONGRATULATIONS! You have solved this exercise. " +
                    "Your work has automatically been submitted.");
        } else {
            textLabel = new BLabel("You have not finished the exercise." +
                    "Your progress has already been submitted.");
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
    /*
    public static void navigateAway(String destinationPage) {
    
    // this only works if we have an applet...
    if (StaticsApplet.getInstance() == null) {
    return;
    }
    
    try {
    URL url = new URL(StaticsApplet.getInstance().getDocumentBase(), destinationPage);
    StaticsApplet.getInstance().getAppletContext().showDocument(url);
    } catch (MalformedURLException ex) {
    }
    }
    
    public static void navigateToSubmission(String studentName, String postPage) {
    
    // this only works if we have an applet...
    if (StaticsApplet.getInstance() ==
    null) {
    return;
    }
    
    MessageDigest md5;
    try {
    md5 = MessageDigest.getInstance(
    "MD5");
    } catch (NoSuchAlgorithmException ex) {
    // if this fails, we're in a bit of a snit.
    // should popup a dialog with some message on it??
    return;
    }
    
    String exerciseName = Exercise.getExercise().getName();
    if (Exercise.getExercise().isExerciseFinished()) {
    // if exercise is solved, give full credit
    exerciseName += " [Solved]";
    }
    String finalString = studentName +
    "@" + exerciseName;
    byte[] resultBytes = md5.digest(finalString.getBytes());
    
    String finalCode = String.format("%02x%02x%02x", resultBytes[0], resultBytes[1], resultBytes[2]);
    
    // first send out our logger data
    //sendLoggerData();
    
    // next send out the actual submission
    try {
    // format resulting URL:
    
    String formattedName = URLEncoder.encode(studentName, "UTF-8");
    String formattedExercise = URLEncoder.encode(exerciseName, "UTF-8");
    
    String targetPage = postPage + "?name=" + formattedName + "&exercise=" + formattedExercise + "&code=" + finalCode;
    
    // navigate away if we are in an applet now.
    URL url = new URL(StaticsApplet.getInstance().getDocumentBase(), targetPage);
    StaticsApplet.getInstance().getAppletContext().showDocument(url);
    
    } catch (MalformedURLException ex) {
    // shouldn't wind up here, but...
    } catch (UnsupportedEncodingException ex) {
    // we're also in trouble if we wind up here.
    // should do our popup again.
    }
    }*/
}
