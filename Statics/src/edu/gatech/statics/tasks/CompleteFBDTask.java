/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Body;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class CompleteFBDTask implements Task {

    private BodySubset bodies;

    public CompleteFBDTask(BodySubset bodies) {
        this.bodies = bodies;
    }

    public boolean isSatisfied() {
        FreeBodyDiagram fbd = Exercise.getExercise().getFreeBodyDiagram(bodies);
        return fbd.isSolved();
    }

    public String getDescription() {
        String bodyString = "???";

        if (bodies.getBodies().size() == 0) {
            throw new AssertionError("Should not have zero bodies in a body subset");
        } else if (bodies.getBodies().size() == 1) {
            // get the first body in the set
            bodyString = bodies.getBodies().iterator().next().getName();
        } else if (bodies.getBodies().size() == 2) {
            // two bodies, no comma, joined by "and"
            Iterator<Body> bodyIterator = bodies.getBodies().iterator();
            bodyString = bodyIterator.next().getName();
            bodyString += " and ";
            bodyString += bodyIterator.next().getName();
        } else {
            // a bunch of bodies, "A, B, and C"
            List<Body> bodyList = new ArrayList(bodies.getBodies());

            bodyString = "";
            for (int i = 0; i < bodyList.size(); i++) {
                bodyString += bodyList.get(i).getName();
                if (i < bodyList.size() - 1) {
                    bodyString += ", ";
                }
                if (i == bodyList.size() - 2) {
                    bodyString += "and ";
                }
            }
        }

        return "Build a FBD out of " + bodyString;
    }
}
