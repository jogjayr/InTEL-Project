/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.versioning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jayraj
 */
public class Hasher {
    
    HashMap<String, byte[]> fileHashes = new HashMap<String, byte[]>();
    static File jarDirectory;
    MessageDigest md;
    public Hasher() {
        jarDirectory = getJarPath();
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Returns a path to the intel/applet folder from the current working directory
    public static File getJarPath() {
        return new File(new File(System.getProperty("user.dir")).getParent() + "/applet");
    }
    protected void hashFiles() {
        File [] jars = jarDirectory.listFiles();
        for(File jarDirectoryFile : jars) {
            if(jarDirectoryFile.getName().endsWith(".jar")) {
                try {
                    FileReader jarReader = new FileReader(jarDirectoryFile);
                    md.update(jarReader.toString().getBytes());
                    String jarFilePathKey = jarDirectoryFile.getName();
                    jarFilePathKey = jarFilePathKey.substring(0, jarFilePathKey.indexOf(".jar"));
                    fileHashes.put(jarFilePathKey, md.digest());
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public HashMap getFileHashes() {
        hashFiles();
        return fileHashes;
    }
}
