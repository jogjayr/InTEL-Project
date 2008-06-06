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
            tip = new Cylinder("", 6, 12, 1f, 1f, true);
            tip.setRadius1(0);
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
                setCullMode(CULL_ALWAYS);
            } else {
                setCullMode(CULL_NEVER);
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
