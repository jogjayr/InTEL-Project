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
 * CylinderRepresentation.java
 *
 * Created on June 9, 2007, 3:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies.representations;

import com.jme.bounding.OrientedBoundingBox;
import edu.gatech.statics.objects.Body;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Cylinder;
import edu.gatech.statics.exercise.DisplayConstants;

/**
 *
 * @author Calvin Ashmore
 */
public class CylinderRepresentation extends BodyRepresentation {

    private Cylinder cylinder;
    private float radius;
    private float height;
    private boolean synchHeight = true;
    private boolean synchRadius = false;

    public Cylinder getCylinder() {
        return cylinder;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean synchRadius() {
        return synchRadius;
    }

    public void synchRadius(boolean synchRadius) {
        this.synchRadius = synchRadius;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean synchHeight() {
        return synchHeight;
    }

    public void synchHeight(boolean synchHeight) {
        this.synchHeight = synchHeight;
    }

    /** Creates a new instance of CylinderRepresentation */
    public CylinderRepresentation(Body target) {
        super(target);

        radius = .25f;
        height = 1;

        cylinder = new Cylinder("", 10, 10, 1, 1, true);
        getRelativeNode().attachChild(cylinder);

        //Matrix3f rotation = new Matrix3f();
        //rotation.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
        //cylinder.setLocalRotation(rotation);

        setModelBound(new OrientedBoundingBox());
        //setModelBound(new BoundingBox());
        setUseWorldScale(false);

        update();
        updateModelBound();
        updateRenderState();
    }

    @Override
    public void update() {
        super.update();

        if (synchHeight) {
            height = getTarget().getHeight();
        }

        if (synchRadius) {
            radius = getTarget().getWidth();
        }

        float effectiveRadius = radius;
        //if(useWorldScale())
        
        effectiveRadius *= DisplayConstants.getInstance().getCylinderRadius();

        cylinder.setLocalScale(new Vector3f(effectiveRadius, effectiveRadius, height));
    }
}
