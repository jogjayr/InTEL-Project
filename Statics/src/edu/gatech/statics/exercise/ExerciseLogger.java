/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseLogger {
    
    private static final String rootPage = "http://intel.gatech.edu/";

    private ByteArrayOutputStream bout;
    private StreamHandler streamHandler;
    private int instance; // used for uniqueness of logger data
    private ScheduledExecutorService loggerService;
    private String loggerAddress;

    public ExerciseLogger(String loggerAddress) {

        this.loggerAddress = loggerAddress;

        bout = new ByteArrayOutputStream();
        streamHandler = new StreamHandler(bout, new SimpleFormatter());
        Logger.getLogger("").addHandler(streamHandler);
        instance = new Random().nextInt();

        scheduleLogger();
    }

    public void scheduleLogger() {

        loggerService = Executors.newSingleThreadScheduledExecutor();

        Runnable loggerCall = new Runnable() {

            public void run() {
                sendData();
            }
        };

        loggerService.scheduleAtFixedRate(loggerCall, 10, 10, TimeUnit.SECONDS);
    }

    public void terminate() {
        loggerService.shutdown();
    }

    private void sendData() {

        /*URL documentBase = null;
        if (StaticsApplet.getInstance() == null) {
        try {
        documentBase = new URL(appletAddress);
        } catch (MalformedURLException ex) {
        // ???
        //Logger.getLogger(PurseExerciseGraded.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else {
        documentBase = StaticsApplet.getInstance().getDocumentBase();
        }*/

        // first, attempt to gather the logger data
        streamHandler.flush();
        byte[] loggerData = bout.toByteArray();
        String loggerString = new String(loggerData);

        System.out.println("*** Size: " + loggerData.length);

        String postData = null;
        try {
            //postData = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(studentName, "UTF-8");
            postData = "&" + URLEncoder.encode("instance", "UTF-8") + "=" + URLEncoder.encode("" + instance, "UTF-8");
            postData += "&" + URLEncoder.encode("loggerData", "UTF-8") + "=" + URLEncoder.encode(loggerString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }

        // ***
        // first send out our log messages
        try {
            String targetPage = loggerAddress;
            URL url = new URL(rootPage+targetPage);

            URLConnection connection = url.openConnection();
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
