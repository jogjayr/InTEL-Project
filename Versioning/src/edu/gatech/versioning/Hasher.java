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
    //ArrayList<String> jarFilePaths = new ArrayList<String>();
    HashMap<String, byte[]> fileHashes = new HashMap<String, byte[]>();
    static File currentWorkingDirectory, jarDirectory;
    MessageDigest md;
    public Hasher() {
        currentWorkingDirectory = new File(System.getProperty("user.dir"));
        jarDirectory = getJarPath();//new File(currentWorkingDirectory.getParent() + "/applet/");
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
    public static void main(String args[]) {
        Hasher h = new Hasher();
        System.out.println(h.getFileHashes());
    }

}
