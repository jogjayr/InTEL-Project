/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

/**
 *
 * @author lccstudent
 */
public class PostLogger extends Poster {

//    private static final String defaultURL = "http://intel.gatech.edu/toolkit/auto_postLogger.php";
    private static final String destination = "auto_postLogger.php";

//    private static String getTargetPage() {
//        if (StaticsApplet.getInstance() != null) {
//            URL documentBase = StaticsApplet.getInstance().getDocumentBase();
//            try {
//                return new java.net.URL(documentBase, destination).toString();
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(PostAssignment.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return defaultURL;
//    }
    public PostLogger(String urlBase) {
        super(urlBase + destination, "problem_id", "user_id", "session_id", "java_class", "java_method", "level", "message", "timestamp");
        System.out.println("Initializing PostLogger with URL "+urlBase);
    }
}
