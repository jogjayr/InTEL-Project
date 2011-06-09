/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.versioning;

/**
 *
 * @author jayraj
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersionFileMaker {

    File versionFilePath;

    public VersionFileMaker() {
        versionFilePath = Hasher.getJarPath();

    }

    public static String byteArrayToString(byte[] ba) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < ba.length; i++) {
            hexString.append(Integer.toHexString(0xFF & ba[i]));
        }
        return hexString.toString();
    }

    public void createVersionFile() {
        FileWriter w = null;
        try {
            w = new FileWriter(new File(versionFilePath.getPath() + "/version.json"));
            w.write("{");
            Hasher h = new Hasher();
            HashMap fileHashes = h.getFileHashes();
            Iterator it = fileHashes.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry) it.next();
//                String fileEntry = "{ jar: '" + (String)pair.getKey() + "', hash : '" + ((byte[])pair.getValue()).toString() + "'},";
                byte[] baHash = (byte[]) pair.getValue();
                String hash = byteArrayToString(baHash);

                String fileEntry = "'" + pair.getKey() + ".jar' : '" + hash + "', ";
                System.out.println(fileEntry);
                w.write(fileEntry);
            }
            w.write("}");

        } catch (IOException ex) {
            Logger.getLogger(VersionFileMaker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                w.close();
            } catch (IOException ex) {
                Logger.getLogger(VersionFileMaker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    }

    public static void main(String args[]) {
        VersionFileMaker v = new VersionFileMaker();
        v.createVersionFile();
    }
}
