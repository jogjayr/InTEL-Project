/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.applet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 * This class is responsible for interfacing with the applet version file
 * (applet/version.json). It checks the version numbers of JAR files in the local
 * stored copy of version.json and the copy on the server and tells AppletLoader whether
 * to re-download a JAR
 * @author Jayraj
 */
public class VersionChecker {

    private JSONObject newVersions, oldVersions;
    private JSONObject updatedVersions;
    /**
     * Constructor
     * @param path Path to version.json
     * @param parameterVersionString JSON string containing version numbers of JARs on the server
     */
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
    /**
     * Checks the local version number and version number on server of a JAR and
     * decides whether it needs to be downloaded again
     * @param jarFilename
     * @return
     */
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
    /**
     * Puts currentFile into a list of files whose local version numbers need to be
     * updated
     * @param currentFile
     */
    void markUpdate(String currentFile) {
        try {
            updatedVersions.put(currentFile, newVersions.get(currentFile));
        } catch (JSONException ex) {
            Logger.getLogger(VersionChecker.class.getName()).log(Level.SEVERE, null, ex);
            // this shouldn't happen....
        }
    }
    /**
     * Writes the updated version numbers to the local file
     * @param path
     */
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
