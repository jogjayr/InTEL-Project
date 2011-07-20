/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import edu.gatech.statics.application.StaticsApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * This posts messages to a web form
 * @author lccstudent
 */
public class Poster {

    private String url;
    private List<String> fieldNames;
    private Executor executor;

    /**
     * 
     * @param url
     * @param fieldNames
     */
    public Poster(String url, String... fieldNames) {
        this.url = url;
        this.fieldNames = Collections.unmodifiableList(Arrays.asList(fieldNames));

        //System.out.println("(poster) I am destroyed: " + Thread.currentThread().getThreadGroup().isDestroyed());
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Returns a new map that is already fit with the post data. It is up to the user to
     * put all the relevant values into the map and then pass it back to the post method.
     * @return
     */
    public Map<String, String> getNewPostMap() {
        Map<String, String> postMap = new HashMap<String, String>();
        for (String fieldName : fieldNames) {
            postMap.put(fieldName, "");
        }
        return postMap;
    }

    /**
     * This posts the data to the web form.
     * Actually, this schedules a thread to post it.
     * @param data
     */
    public void post(Map<String, String> data) {

        executor.execute(new PosterWorker(data));
    }

    private class PosterWorker implements Runnable {

        private Map<String, String> data;

        public PosterWorker(Map<String, String> data) {
            this.data = data;
        }

        public void run() {
            performPost(data);
        }
    }

    /**
     * Converts data to an HTTP-style key-value string
     * ("?key1=value1&key2=value" etc) and then makes a call to
     * url
     * @param data
     */
    private void performPost(Map<String, String> data) {

//        System.out.println("Poster: performing post");
        String postData = "";
        try {
            boolean first = true;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                String line = "";
                if (!first) {
                    line += "&";
                }
                line += URLEncoder.encode(key, "UTF-8");
                line += "=";
                line += URLEncoder.encode(value, "UTF-8");
                first = false;

                postData += line;
            }
        } catch (UnsupportedEncodingException ex) {
            StaticsApplication.logger.log(Level.SEVERE, "Cannot use UTF encoding!?!", ex);
        }

        try {
            URL postURL = new URL(url);

//            System.out.println("Poster: opening connection and sending data");
            URLConnection connection = postURL.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(postData);
            writer.flush();
//            System.out.println("Poster: sent, getting response");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("response: " + line);
            }

            writer.close();
//            System.out.println("Poster: done");
        } catch (IOException ex) {
            System.out.println("post failed!");
            failedPost(ex);
        }
    }

    protected void failedPost(Exception ex) {
        // by default do nothing
    }
}
