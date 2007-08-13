/*
 * CylinderRepresentation.java
 *
 * Created on June 9, 2007, 3:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies.representations;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Body;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Cylinder;

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
    
    public Cylinder getCylinder() {return cylinder;}
    
    public float getRadius() {return radius;}
    public void setRadius(float radius) {this.radius = radius;}
    public boolean synchRadius() {return synchRadius;}
    public void synchRadius(boolean synchRadius) {this.synchRadius = synchRadius;}
    
    public float getHeight() {return height;}
    public void setHeight(float height) {this.height = height;}
    public boolean synchHeight() {return synchHeight;}
    public void synchHeight(boolean synchHeight) {this.synchHeight = synchHeight;}
    
    /** Creates a new instance of CylinderRepresentation */
    public CylinderRepresentation(Body target) {
        super(target);
        
        radius = .25f;
        height = 1;
        
        cylinder = new Cylinder("", 10, 10, 1, 1, true);
        attachChild(cylinder);
        
        Matrix3f rotation = new Matrix3f();
        rotation.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
        cylinder.setLocalRotation(rotation);
        
        setModelBound(new OrientedBoundingBox());
        setUseWorldScale(false);
        
        update();
        updateModelBound();
    }
    
    public void update() {
        super.update();
        
        if(synchHeight)
            height = getTarget().getHeight();
        
        if(synchRadius)
            radius = getTarget().getWidth();
        
        float effectiveRadius = radius;
        //if(useWorldScale())
            effectiveRadius *= StaticsApplication.getApp().getDrawScale();
        
        cylinder.setLocalScale(new Vector3f(effectiveRadius, effectiveRadius, height));
    }
}
