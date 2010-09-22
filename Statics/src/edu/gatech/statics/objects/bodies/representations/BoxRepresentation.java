/*
 * BoxRepresentation.java
 *
 * Created on June 9, 2007, 3:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies.representations;

import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import edu.gatech.statics.objects.Body;

/**
 *
 * @author Calvin Ashmore
 */
public class BoxRepresentation extends BodyRepresentation {

    private Box box;

    /** Creates a new instance of BoxRepresentation */
    public BoxRepresentation(Body target) {
        this(target, 1,1,1);
    }

    public BoxRepresentation(Body target, float length, float height, float depth) {
        super(target);

        box = new Box(name, Vector3f.ZERO, length, height, depth);
        getRelativeNode().attachChild(box);

        setModelBound(new OrientedBoundingBox());

        setUseWorldScale(false);

        update();
        updateModelBound();
        updateRenderState();
    }
}
