/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.objects.Body;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ashmore
 */
public class BodySubset implements DiagramKey {

    private Set<Body> bodies;

    public BodySubset(Body... bodies) {
        this(Arrays.asList(bodies));
    }

    public BodySubset(Collection<Body> bodies) {
        this.bodies = new HashSet<Body>(bodies);
        if (bodies.size() == 0) {
            throw new UnsupportedOperationException("There must be at least one body in a body subset");
        }
    }

    public Set<Body> getBodies() {
        return Collections.unmodifiableSet(bodies);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BodySubset other = (BodySubset) obj;
        if (this.bodies != other.bodies && (this.bodies == null || !this.bodies.equals(other.bodies))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.bodies != null ? this.bodies.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return bodies.toString();
    }

    /**
     * This method returns a nicely formatted version of the bodies within the subset.
     * It will return a listing that is more legible in plain English. The format
     * depends on the number of bodies in the subset.
     * For one body, it will return the body name,
     * For two bodies, it will return "A and B" where A and B are the names of the bodies.
     * For three or more, it will return something like, "A, B, C, and D".
     * @return
     */
    public String toStringPretty() {

        String bodyString = "";

        if (bodies.size() == 0) {
            throw new AssertionError();
        } else if (bodies.size() == 1) {
            // get the first body in the set
            bodyString = bodies.iterator().next().getName();
        } else if (bodies.size() == 2) {
            // two bodies, no comma, joined by "and"
            Iterator<Body> bodyIterator = bodies.iterator();
            bodyString = bodyIterator.next().getName();
            bodyString += " and ";
            bodyString += bodyIterator.next().getName();
        } else {
            // a bunch of bodies, "A, B, and C"
            List<Body> bodyList = new ArrayList(bodies);

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
        return bodyString;
    }
}
