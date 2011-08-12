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
