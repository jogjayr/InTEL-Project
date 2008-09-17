/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import edu.gatech.statics.exercise.Exercise;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lccstudent
 */
public class PostAssignment extends Poster {

    private static final String URL = "http://intel.gatech.edu/toolkit/auto_postAssignment.php";

    public PostAssignment() {
        super(URL, "assignment_id", "user_id", "exercise_status", "state_data", "verifier_key", "timestamp");
    }

    public void postState() {
        Map<String, String> postMap = getNewPostMap();

        String assignmentId = "" + Exercise.getExercise().getState().getAssignmentID();
        String userId = "" + Exercise.getExercise().getState().getUserID();
        String exerciseStatus = "" + Exercise.getExercise().getCompletionStatus();
        String stateData = "no state data yet";
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

    private String generateVerfierKey(String userId, String assignmentId, String exerciseStatus, String stateData) {

        String preHash = userId + ":" + assignmentId + ":" + exerciseStatus + ":" + stateData;

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // if this fails, we're in a bit of a snit.
            Logger.getLogger("Statics").log(Level.SEVERE, "cannot find an MD5 digest!", ex);
            return "ERROR";
        }

        byte[] digestBytes;
        try {
            digestBytes = md5.digest(preHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "cannot encode into UTF-8!", ex);
            return "ERROR";
        }

        String verifierKey = String.format("%02x%02x%02x%02x",
                digestBytes[0], digestBytes[1], digestBytes[2], digestBytes[3]);
        return verifierKey;
    }
}
