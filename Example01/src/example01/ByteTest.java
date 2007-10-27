/*
 * ByteTest.java
 * 
 * Created on Oct 27, 2007, 12:40:48 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example01;

import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Calvin Ashmore
 */
public class ByteTest {
    public static void main(String args[]) throws Exception {
        MessageDigest md5;
        try {
             md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // if this fails, we're in a bit of a snit.
            // should popup a dialog with some message on it??
            return;
        }
        
        String name = "Calvin Ashmore";
        String exercise = "Purse Exercise";
        
        BigInteger nameCode = new BigInteger(md5.digest(name.getBytes()));
        BigInteger exerciseCode = new BigInteger(md5.digest(exercise.getBytes()));
        
        BigInteger result = nameCode.add(exerciseCode);
        byte[] resultBytes = result.toByteArray();
        
        String r = String.format("%x%x%x", resultBytes[0], resultBytes[1], resultBytes[2]);
        System.out.println(r);
        
        String url = "problemPost.php?name="+URLEncoder.encode(name,"UTF-8")+"&exercise="+URLEncoder.encode(exercise,"UTF-8")+"&code="+r;
        //URL url = new URL(URLEncoder.encode("problemPost.php?name="+name+"&exercise="+exercise+"&code="+r,"UTF-8"));
        //URI uri = url.toURI();
        //url = uri.toURL();
        //System.out.println(uri);
        System.out.println(url);
    }
}
