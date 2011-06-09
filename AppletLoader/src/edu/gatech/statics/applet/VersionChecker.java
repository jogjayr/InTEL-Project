/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.applet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author Jayraj
 */
public class VersionChecker {

    private JSONObject newVersions, oldVersions;
    private JSONObject updatedVersions;

    public VersionChecker(String path, String parameterVersionString) {

        try {
            JSONTokener tokener = new JSONTokener(new FileInputStream(path + "version.json"));
            oldVersions = new JSONObject(tokener);
            updatedVersions = new JSONObject(oldVersions, JSONObject.getNames(oldVersions));
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("### local version file not found");
            oldVersions = new JSONObject();
            updatedVersions = new JSONObject();
        } catch (JSONException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("### error in reading local version file");
            oldVersions = new JSONObject();
            updatedVersions = new JSONObject();
        }

        try {
//            parameterVersionString = parameterVersionString.replaceAll("\'", "\"");
            JSONTokener tokener = new JSONTokener(parameterVersionString);
            newVersions = new JSONObject(tokener);
        } catch (JSONException ex) {
            // the remote versions are bad, this is a problem.
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
            newVersions = null;
        }
    }

    public boolean shouldDownload(String jarFilename) {

        try {
            String oldVersion = (String) oldVersions.get(jarFilename);
            String newVersion = (String) newVersions.get(jarFilename);

            // we should download if the values do NOT match
            return !oldVersion.equals(newVersion);

        } catch (JSONException ex) {
            // the key does not exist, so we should download
            return true;
        }
    }

    void markUpdate(String currentFile) {
        try {
            updatedVersions.put(currentFile, newVersions.get(currentFile));
        } catch (JSONException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
            // this shouldn't happen....
        }
    }

    void saveUpdatedVersions(String path) {
        try {
            FileWriter writer = new FileWriter(path + "version.json");
            writer.write(updatedVersions.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static ArrayList<String> getJarList(String versionString, String path, StringTokenizer jarListTokenizer) {
//        try {
//            ArrayList<String> retVal = new ArrayList<String>();
//            newVersion = new JSONObject(versionString);
//            oldVersion = new JSONObject(readVersionFile(path));
//            int jarListLength = jarListTokenizer.countTokens() + 1;
//            for (int i = 0; i < jarListLength; i++) {
//
//                if (!(newVersion.getString(jarListTokenizer.nextToken())).equalsIgnoreCase(oldVersion.getString(jarListTokenizer.nextToken()))) {
//                    retVal.add((newVersion.getString(jarListTokenizer.nextToken())));
//                }
//
//            }
//            writeVersionFile(path, versionString);
//        } catch (JSONException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        return null;
//    }
//
//    protected static JSONObject readVersionFile(String path) {
//        try {
//            JSONTokener tokener = new JSONTokener(new FileInputStream(path + "/version.json"));
//            return new JSONObject(tokener);
//
//
//            //        Scanner versionFileReader;
//            //        String retVal = "";
//            //        try {
//            //            versionFileReader = new Scanner(new File(path + "/version.json"));
//            //
//            //            while (versionFileReader.hasNextLine()) {
//            //                retVal = retVal + versionFileReader.nextLine();
//            //            }
//            //        } catch (FileNotFoundException ex) {
//            //            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
//            //        }
//            //        return retVal;
//            //        return retVal;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    static void writeVersionFile(String path, String versionString) {
//        FileWriter versionFileWriter;
//        try {
//            versionFileWriter = new FileWriter(new File(path + "/version.json"));
//            versionFileWriter.write(versionString);
//        } catch (IOException ex) {
//            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
