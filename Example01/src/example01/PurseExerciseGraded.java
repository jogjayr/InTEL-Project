/*
 * PurseExerciseGraded.java
 *
 * Created on Oct 23, 2007, 4:00:47 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example01;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplet;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.ui.ModalPopupWindow;
import edu.gatech.statics.objects.Joint;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExerciseGraded extends PurseExercise {

    private String studentName;

    public PurseExerciseGraded() {

        Random rand = new Random();
        
        handPoint = -17 + (float)rand.nextInt(20)/10 - 1;
        tendonAnchorB = 13 + (float)rand.nextInt(20)/10;
        tendonAnchorD = 13 + (float)rand.nextInt(20)/10 - 1;
        shoulderHeight = 16 + -(float)rand.nextInt(10)/10;
        forearmWeight = 9 + (float)rand.nextInt(20)/10 - 1;
        purseWeight = 19.6f + (float)rand.nextInt(20)/10 - 1;
        
    }
    
    public void initExercise() {
        super.initExercise();
        
        setDescription(
                "<html><body>" +
                "<center><font size=\"6\">"+getName()+"</font></center>"+
                "Here is a simplified version of the human arm. " +
                "Please solve for the reactions at each of the points: B, C, and E." +
                "The weight of the forearm is "+forearmWeight+" N, and the weight of the purse is "+purseWeight+" N." +
                "</body></html>"
                );
    }

    public void postLoadExercise() {

        final ModalPopupWindow popup = new ModalPopupWindow(StaticsApplication.getApp().getCurrentInterface().getToolbar(), new BorderLayout());
        popup.setStyleClass("info_window_opaque");
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

        content.add(new BLabel("Please give your name so you can be given credit for this exercise."));

        BContainer nameRow = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        content.add(nameRow);
        nameRow.add(new BLabel("Name: "));
        final BTextField nameField = new BTextField();
        nameField.setPreferredWidth(200);
        nameRow.add(nameField);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText();
                if(name.trim().equals(""))
                    return;
                studentName = name;
                popup.dismiss();
            }
        };
        
        BButton okButton = new BButton("OK", actionListener, "ok");
        content.add(okButton);
        nameField.addListener(actionListener);
        nameField.requestFocus();
        
        //Dimension dim = popup.getPreferredSize(0, 0);
        Dimension dim = new Dimension(300, 0);
        popup.popup((DisplaySystem.getDisplaySystem().getWidth() - dim.width) / 2, (DisplaySystem.getDisplaySystem().getHeight() - dim.height) / 2, true);
    }

    public boolean isExerciseSolved() {
        for (SimulationObject obj : getWorld().allObjects())
            if (obj instanceof Joint && !((Joint) obj).isSolved() && !((Joint) obj).getPoint().getName().equals("D"))
                return false;
        return true;
    }

    public void finishExercise() {
        super.finishExercise();

        ModalPopupWindow popup = new ModalPopupWindow(StaticsApplication.getApp().getCurrentInterface().getToolbar(), new BorderLayout());
        popup.setStyleClass("info_window_opaque");
        popup.setModal(true);

        popup.setPreferredSize(300, -1);

        BContainer title = new BContainer(new BorderLayout());
        BLabel titleLabel = new BLabel("Congratulations!", "title_container");
        title.add(titleLabel, BorderLayout.CENTER);
        popup.add(title, BorderLayout.NORTH);
        
        BLabel textLabel = new BLabel("You have solved for all of the unknown joints in this exercise." +
                "Please click the button below to submit your work.");
        popup.add(textLabel, BorderLayout.CENTER);
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                navigateAway();
            }
        };
        
        BButton submitButton = new BButton("Submit",actionListener,"submit");
        popup.add(submitButton, BorderLayout.SOUTH);
        
        Dimension dim = new Dimension(300, 0);
        popup.popup((DisplaySystem.getDisplaySystem().getWidth() - dim.width) / 2, (DisplaySystem.getDisplaySystem().getHeight() - dim.height) / 2, true);
    }
    
    private void navigateAway() {
        
        // this only works if we have an applet...
        if(StaticsApplet.getInstance() == null)
            return;
        
        MessageDigest md5;
        try {
             md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // if this fails, we're in a bit of a snit.
            // should popup a dialog with some message on it??
            return;
        }
        
        String finalString = studentName + "@" + getName();
        byte[] resultBytes = md5.digest(finalString.getBytes());
        
        String finalCode = String.format("%x%x%x", resultBytes[0], resultBytes[1], resultBytes[2]);
        
        try {
            // format resulting URL:
            String postPage = "problemPost.php";

            String formattedName = URLEncoder.encode(studentName, "UTF-8");
            String formattedExercise = URLEncoder.encode(getName(), "UTF-8");

            String targetPage = postPage+"?name="+formattedName+"&exercise="+formattedExercise+"&code="+finalCode;
            
            // navigate away if we are in an applet now.
            URL url = new URL(StaticsApplet.getInstance().getDocumentBase(), targetPage);
            StaticsApplet.getInstance().getAppletContext().showDocument(url);
            
        } catch (MalformedURLException ex) {
            // shouldn't wind up here, but...
        } catch (UnsupportedEncodingException ex) {
            // we're also in trouble if we wind up here.
            // should do our popup again.
        }
    }
}