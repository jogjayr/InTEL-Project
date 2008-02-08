/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

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
    
}
