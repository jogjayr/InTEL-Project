/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.applet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author Jayraj
 */
public class VersionChecker {

    private static JSONObject newVersion, oldVersion;
    
    public static ArrayList<String> getJarList(String versionString, String path, StringTokenizer jarListTokenizer) {
        try {
            ArrayList<String> retVal = new ArrayList<String>();
            newVersion = new JSONObject(versionString);
            oldVersion = new JSONObject(readVersionFile(path));
            int jarListLength = jarListTokenizer.countTokens() + 1;
            for(int i = 0; i < jarListLength; i++) {
                
                if(!(newVersion.getString(jarListTokenizer.nextToken())).equalsIgnoreCase(oldVersion.getString(jarListTokenizer.nextToken()))) {
                    retVal.add((newVersion.getString(jarListTokenizer.nextToken())));
                }
                
            }
            writeVersionFile(path, versionString);
        } catch (JSONException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
        }


        return null;
    }

    protected static String readVersionFile(String path) {

        Scanner versionFileReader;
        String retVal = "";
        try {
            versionFileReader = new Scanner(new File(path + "/version.json"));

            while (versionFileReader.hasNextLine()) {
                retVal = retVal + versionFileReader.nextLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }
    
    protected static void writeVersionFile(String path, String versionString) {
        FileWriter versionFileWriter;
        try {
            versionFileWriter = new FileWriter(new File(path + "/version.json"));
            versionFileWriter.write(versionString);
        } catch (IOException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
