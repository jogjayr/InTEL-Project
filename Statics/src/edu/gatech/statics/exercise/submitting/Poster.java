/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
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
import java.util.logging.Logger;

/**
 * This posts messages to a web form
 * @author lccstudent
 */
public class Poster {

    private String url;
    private List<String> fieldNames;
    private Executor executor;

    public Poster(String url, String... fieldNames) {
        this.url = url;
        this.fieldNames = Collections.unmodifiableList(Arrays.asList(fieldNames));

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
     * Actually, this schedules a process to post it.
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

    private void performPost(Map<String, String> data) {

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
            Logger.getLogger("Statics").log(Level.SEVERE, "Cannot use UTF encoding!?!", ex);
        }

        try {
            URL postURL = new URL(url);

            URLConnection connection = postURL.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(postData);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("response: " + line);
            }

            writer.close();
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
    }
}
