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
 * As the name implies, a body subset is a subset of the bodies in a problem. 
 * BodySubset is used in the equation mode. TODO More explanation
 * @author ashmore
 */
public class BodySubset implements DiagramKey {

    private Set<Body> bodies;
    private String specialName;

    /**
     * Constructor
     * @param bodies
     */
    public BodySubset(Body... bodies) {
        this(Arrays.asList(bodies));
    }

    /**
     * Constructor
     * @param bodies
     */
    public BodySubset(Collection<Body> bodies) {
        this.bodies = new HashSet<Body>(bodies);
        if (bodies.size() == 0) {
            throw new UnsupportedOperationException("There must be at least one body in a body subset");
        }
    }

    /**
     * 
     * @return
     */
    public String getSpecialName() {
        return specialName;
    }

    /**
     * 
     * @param specialName
     */
    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    /**
     * 
     * @return
     */
    public Set<Body> getBodies() {
        return Collections.unmodifiableSet(bodies);
    }

    /**
     * Checks for equality with obj. Compares bodies of both this and obj
     * @param obj
     * @return Is equal to obj?
     */
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
        return toStringPretty();
        //return bodies.toString();
    }

    /**
     * This method returns a nicely formatted version of the bodies within the subset.
     * It will return a listing that is more legible in plain English. The format
     * depends on the number of bodies in the subset.
     * @return For one body, it will return the body name,
     * For two bodies, it will return "A and B" where A and B are the names of the bodies.
     * For three or more, it will return something like, "A, B, C, and D".
     */
    public String toStringPretty() {

        String bodyString = "";

        if (specialName != null) {
            return specialName;
        }
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
