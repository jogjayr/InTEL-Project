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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jayraj
 */
public class Hasher {
    ArrayList<String> jarFilePaths = new ArrayList<String>();
    HashMap<String, byte[]> fileHashes = new HashMap<String, byte[]>();
    MessageDigest md;
    public Hasher() {
        fileHashes.put("BUI.jar", null);
        fileHashes.put("Statics.jar", null);
        jarFilePaths.add("/home/jayraj/intel/InTEL/applet/BUI.jar");
        jarFilePaths.add("/home/jayraj/intel/InTEL/applet/Statics.jar");
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void hashFiles() {

        for(String jarFilePath : jarFilePaths) {
            try {
                FileReader jarReader = new FileReader(new File(jarFilePath));
                md.update(jarReader.toString().getBytes());
                String jarFilePathKey = jarFilePath.substring(jarFilePath.lastIndexOf("/") + 1);
                fileHashes.put(jarFilePathKey, md.digest());

//                File x = new File(jarFilePath);
//                System.out.println(x.exists());
                //System.out.println(md.digest());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public HashMap getFileHashes() {
        hashFiles();
        return fileHashes;
    }
//    public static void main(String args[]) {
//        Hasher h = new Hasher();
//        h.hashFiles();
//    }

}
