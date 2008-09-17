/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import edu.gatech.statics.exercise.Exercise;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author lccstudent
 */
public class DatabaseLogHandler extends Handler {

    private static boolean initialized = false;

    /**
     * This should be the default way of setting up logging.
     */
    public static void initialize() {
        if (!initialized) {
            Logger.getLogger("Statics").addHandler(new DatabaseLogHandler());
            initialized = true;
        }
    }
    private PostLogger poster = new PostLogger();

    @Override
    public void publish(LogRecord record) {
        Map<String, String> postMap = poster.getNewPostMap();
        postMap.put("problem_id", "" + Exercise.getExercise().getProblemID());
        postMap.put("session_id", "" + Exercise.getExercise().getSessionID());
        postMap.put("java_class", record.getSourceClassName());
        postMap.put("java_method", record.getSourceMethodName());
        postMap.put("message", record.getMessage());
        postMap.put("timestamp", "" + record.getMillis());

        poster.post(postMap);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
