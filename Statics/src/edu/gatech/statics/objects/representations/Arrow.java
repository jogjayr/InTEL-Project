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
package edu.gatech.statics.objects.representations;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Cylinder;

/**
 *
 * @author Calvin Ashmore
 */
public class Arrow extends Node {
    public static final double CULL_THRESHOLD = 0.05;

    private float tipWidth = .4f;
    private float tipHeight = 2.0f;
    private float tailWidth = .12f;
    private boolean hasTip = false;
    
    public void setSize(float tipWidth, float tipHeight, float tailWidth) {
        this.tipWidth = tipWidth;
        this.tipHeight = tipHeight;
        this.tailWidth = tailWidth;
    } 
    
    public void setHasTip(boolean hasTip) {
        
        if(this.hasTip == hasTip)
            return;
        
        this.hasTip = hasTip;
        if (hasTip) {
            /*tip = new Cylinder("", 6, 12, 1f, 1f, true);
            tip.setRadius1(0);*/
            tip = new Cylinder("", 6, 12, 0, 1f, 1f, true, false);
            attachChild(tip);
        } else {
            detachChild(tip);
            tip = null;
        }
    }
    
    private Cylinder tip;
    private Cylinder tail;

    private float length;

    public float getLength() {
        return length;
    }

    public Arrow() {
        this(true);
    }
    
    public Arrow(boolean hasTip) {
        tail = new Cylinder("", 6, 6, 1f, 1f, true);
        attachChild(tail);

        setHasTip(hasTip);
        
        setModelBound(new BoundingBox());
        updateModelBound();
        updateRenderState();
    }

    public void setLength(float length) {

        this.length = length;
        //magnitude /= StaticsApplication.getApp().getDrawScale();

        if (hasTip) {
            float tailMagnitude;
            float tipMagnitude;

            if (length < 2 * tipHeight) {
                tailMagnitude = length * .5f * tipHeight;
                tipMagnitude = length - tailMagnitude;
            } else {
                tipMagnitude = tipHeight;
                tailMagnitude = length - tipMagnitude;
            }

            if (length < CULL_THRESHOLD) {
                setCullHint(CullHint.Always);
            } else {
                setCullHint(CullHint.Never);
            }

            tail.setLocalTranslation(0, 0, tailMagnitude / 2);
            tail.setLocalScale(new Vector3f(tailWidth, tailWidth, tailMagnitude));

            tip.setLocalTranslation(0, 0, tailMagnitude + tipMagnitude / 2);
            tip.setLocalScale(new Vector3f(tipWidth, tipWidth, tipMagnitude));

        } else {
            tail.setLocalTranslation(0, 0, length / 2);
            tail.setLocalScale(new Vector3f(tailWidth, tailWidth, length));
        }
    }
}
