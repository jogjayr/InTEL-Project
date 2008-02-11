/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.exercise;

import edu.gatech.statics.objects.Body;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ashmore
 */
public class BodySubset {

    private Set<Body> bodies;
    
    public BodySubset(Collection<Body> bodies) {
        this.bodies = new HashSet<Body>(bodies);
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
    
}
