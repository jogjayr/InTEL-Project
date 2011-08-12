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
    /**
     * 
     * @param urlBase
     */
    /**
     * Cconstructor
     * @param urlBase
     */
    public PostLogger(String urlBase) {
        super(urlBase + destination, "problem_id", "user_id", "session_id", "java_class", "java_method", "level", "message", "timestamp");
        System.out.println("Initializing PostLogger with URL "+urlBase);
    }
}
