/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.persistence.StateIO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author lccstudent
 */
public class PostAssignment extends Poster {

//    private static final String defaultURL = "http://intel.gatech.edu/toolkit/auto_postAssignment.php";
    private static final String destination = "auto_postAssignment.php";

//    private static String getTargetPage() {
//        if (StaticsApplet.getInstance() != null) {
//            URL documentBase = StaticsApplet.getInstance().getDocumentBase();
//            try {
//                return new java.net.URL(documentBase, destination).toString();
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(PostAssignment.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return defaultURL;
//    }
    /**
     * 
     * @param urlBase
     */
    public PostAssignment(String urlBase) {
        super(urlBase + destination, "assignment_id", "user_id", "exercise_status", "state_data", "verifier_key", "timestamp");
    }

    /**
     * Creates a state containing the assignmentId, userId, exerciseStatus, stateData, timestamp
     * and verifierKey and posts it to server
     */
    public void postState() {
        Map<String, String> postMap = getNewPostMap();

        String assignmentId = "" + Exercise.getExercise().getState().getAssignmentID();
        String userId = "" + Exercise.getExercise().getState().getUserID();
        String exerciseStatus = "" + Exercise.getExercise().getCompletionStatus();
        String stateData = StateIO.saveState();
        String timestamp = "" + System.currentTimeMillis();
        String verifierKey = generateVerfierKey(userId, assignmentId, exerciseStatus, stateData);

        postMap.put("assignment_id", assignmentId);
        postMap.put("user_id", userId);
        postMap.put("state_data", stateData);
        postMap.put("exercise_status", exerciseStatus);
        postMap.put("verifier_key", verifierKey);
        postMap.put("timestamp", timestamp);

        post(postMap);
    }

    /**
     * Creates the verified key using userId, assignmentId, exerciseStatus and stateData,
     * for verification when posting to server
     * @param userId
     * @param assignmentId
     * @param exerciseStatus
     * @param stateData
     * @return
     */
    private String generateVerfierKey(String userId, String assignmentId, String exerciseStatus, String stateData) {

        String preHash = userId + ":" + assignmentId + ":" + exerciseStatus + ":" + stateData;

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // if this fails, we're in a bit of a snit.
            StaticsApplication.logger.log(Level.SEVERE, "cannot find an MD5 digest!", ex);
            return "ERROR";
        }

        byte[] digestBytes;
        try {
            digestBytes = md5.digest(preHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            StaticsApplication.logger.log(Level.SEVERE, "cannot encode into UTF-8!", ex);
            return "ERROR";
        }

        String verifierKey = String.format("%02x%02x%02x%02x",
                digestBytes[0], digestBytes[1], digestBytes[2], digestBytes[3]);
        return verifierKey;
    }

    /**
     * Handles failed post event. Creates a SubmittingErrorPopup
     * @param ex
     */
    @Override
    protected void failedPost(Exception ex) {
        StaticsApplication.logger.log(Level.SEVERE, "Cannot submit assignment progress!", ex);

        SubmittingErrorPopup popup = new SubmittingErrorPopup();
        popup.popup(0, 0, true);
        popup.center();
    }
}
