/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.versioning;


/**
 *
 * @author jayraj
 */
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
public class ManifestReader {

        ArrayList<String> jarFiles = new ArrayList<String>();
        JarFile jarFile;
        Manifest manifestFile;
        public ManifestReader () {
            jarFiles.add("BUI.jar");
            jarFiles.add("Statics.jar");
            jarFiles.add("AppletLoader.jar");
        } 
}
