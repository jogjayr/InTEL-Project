/*
 * AppletLauncher.java
 *
 * Created on August 11, 2007, 10:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.application;

import edu.gatech.statics.exercise.Exercise;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class AppletLauncher extends StaticsApplet {

    /** Creates a new instance of AppletLauncher */
    public AppletLauncher() {
    }

    @Override
    public void init() {

        try {
            String exerciseName = getExercise();
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();

            configureAppletData(exercise);

            Logger.getLogger("Statics").info("Exercise loaded!");

            super.init();
            getApplication().setExercise(exercise);

        } catch (NullPointerException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch (ClassCastException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch (InstantiationException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        }
    }

    private void configureAppletData(Exercise exercise) {

        String userIDString = getParameter("userID");
        String exerciseIDString = getParameter("exerciseID");
        String problemIDString = getParameter("problemID");
        String problemName = getParameter("problemName");

        if (userIDString == null || exerciseIDString == null) {
            Logger.getLogger("Statics").info("Applet loaded, but user ID not recorded. Continuing anonymously.");
            return;
        }
        Logger.getLogger("Statics").info("Applet loaded, user ID recorded. Continuing for credit.");

        if (!checkVerifierKey()) {
            // this should also post an error message of some sort as well.
            Logger.getLogger("Statics").info("Verifier key does not check!! This is a problem. Continuing anonymously.");
        }

        int exerciseID = Integer.valueOf(exerciseIDString);
        int userID = Integer.valueOf(userIDString);
        int problemID = Integer.valueOf(problemIDString);

        exercise.setAppletExerciseName(problemName);
        exercise.setProblemID(problemID);
        exercise.getState().setExerciseID(exerciseID);
        exercise.getState().setUserID(userID);
    }

    private boolean checkVerifierKey() {
        String userIDString = getParameter("userID");
        String exerciseIDString = getParameter("exerciseID");
        String problemIDString = getParameter("problemID");
        String problemName = getParameter("problemName");
        String givenVerifierKey = getParameter("verifierKey");

        String preHash = userIDString + ":" + problemIDString + ":" + exerciseIDString + ":" + problemName;

        // use MD5 to generate our key
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // if this fails, we're in a bit of a snit.
            Logger.getLogger("Statics").log(Level.SEVERE, "cannot find an MD5 digest!", ex);
            return false;
        }

        byte[] digestBytes;
        try {
            digestBytes = md5.digest(preHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "cannot encode into UTF-8!", ex);
            return false;
        }

        String verifierKey = String.format("%02x%02x%02x%02x",
                digestBytes[0], digestBytes[1], digestBytes[2], digestBytes[3]);

        return verifierKey.equals(givenVerifierKey);
    }
}
