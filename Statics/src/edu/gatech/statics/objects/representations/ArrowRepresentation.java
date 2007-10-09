/*
 * ArrowRepresentation.java
 *
 * Created on June 30, 2007, 1:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Cylinder;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.Vector;

/**
 *
 * @author Calvin Ashmore
 */
public class ArrowRepresentation extends Representation<Vector> {
    
    protected float tipWidth = .4f;
    protected float tipHeight = 2.0f;
    protected float tailWidth = .12f;
    
    protected boolean hasHead;
    
    protected Cylinder tip;
    protected Cylinder tail;
    
    protected float axisOffset = 0;
    public void setAxisOffset(float axisOffset) {this.axisOffset = axisOffset;}
    public float getAxisOffset() {return axisOffset;}
    
    protected float magnitude;
    public float getLength() {return magnitude;}
    
    public ArrowRepresentation(Vector target) {
        this(target, true);
    }
    
    /** Creates a new instance of ArrowRepresentation */
    public ArrowRepresentation(Vector target, boolean hasHead) {
        super(target);
        this.hasHead = hasHead;
        setLayer(RepresentationLayer.vectors);
        
        buildGeometry();
        
        //setModelBound(new OrientedBoundingBox());
        setModelBound(new BoundingBox());
        update();
        updateModelBound();
    }

    public void update() {
        
        super.update();
        
        Vector3f axis = getTarget().getValue();
        setMagnitude(axis.length());
    }

    protected void buildGeometry() {
        
        tail = new Cylinder("", 6, 6, 1f, 1f, true);
        attachChild(tail);
        
        if(hasHead) {
            tip = new Cylinder("", 6, 12, 1f, 1f, true);
            tip.setRadius1(0);
            attachChild(tip);
        }
    }

    protected void setMagnitude(float magnitude) {
        
        this.magnitude = magnitude;
        //magnitude /= StaticsApplication.getApp().getDrawScale();
        
        if(hasHead) {
            float tailMagnitude;
            float tipMagnitude;

            if(magnitude < 2*tipHeight) {
                tailMagnitude = magnitude*.5f*tipHeight;
                tipMagnitude = magnitude-tailMagnitude;
            } else {
                tipMagnitude = tipHeight;
                tailMagnitude = magnitude-tipMagnitude;
            }

            if(magnitude < .05) {
                setCullMode(CULL_ALWAYS);
            } else {
                setCullMode(CULL_NEVER);
            }

            tail.setLocalTranslation(0,0,tailMagnitude/2 + axisOffset);
            tail.setLocalScale(new Vector3f( tailWidth, tailWidth, tailMagnitude ));
            //tail.setHeight(tailMagnitude);
            //tail.setDefaultColor(color);

            tip.setLocalTranslation(0,0,tailMagnitude+tipMagnitude/2 + axisOffset);
            tip.setLocalScale(new Vector3f( tipWidth, tipWidth, tipMagnitude ));
            //tip.setHeight(tipMagnitude);
            //tip.setDefaultColor(color);
            
        } else {
            
            tail.setLocalTranslation(0,0,magnitude/2 + axisOffset);
            tail.setLocalScale(new Vector3f( tailWidth, tailWidth, magnitude ));
            
        }
    }
    
}
