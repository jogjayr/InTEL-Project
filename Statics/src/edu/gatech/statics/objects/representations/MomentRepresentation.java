/*
 * MomentRepresentation.java
 *
 * Created on July 1, 2007, 1:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.bounding.BoundingBox;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Torus;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.Moment;

/**
 *
 * @author Calvin Ashmore
 */
public class MomentRepresentation extends Representation<Moment> {
    
    protected float torusInnerRadius = .025f;
    protected float torusOuterRadius = .75f;
    
    /** Creates a new instance of MomentRepresentation */
    public MomentRepresentation(Moment target) {
        super(target);
        setLayer(RepresentationLayer.vectors);
        
        //buildGeometry();
        Torus torus = new Torus("", 16, 6, torusInnerRadius, torusOuterRadius);
        attachChild(torus);
        
        for(int i=0; i<6; i++) {
            Cylinder cylinder = new Cylinder("", 6, 12, .15f, .25f);
            cylinder.setRadius1(0);
            cylinder.setLocalTranslation(
                    torusOuterRadius*(float)Math.cos(2*Math.PI*i/6.0f),
                    torusOuterRadius*(float)Math.sin(2*Math.PI*i/6.0f),0);
            //cylinder.setLocalR
            
            Matrix3f mat = new Matrix3f();
            mat.fromStartEndVectors( Vector3f.UNIT_Z, new Vector3f(
                    -(float)Math.sin(2*Math.PI*i/6.0f),
                    (float)Math.cos(2*Math.PI*i/6.0f),0
                    ));
            cylinder.setLocalRotation(mat);
            
            attachChild(cylinder);
        }
        
        //setModelBound(new OrientedBoundingBox());
        setModelBound(new BoundingBox());
        update();
        updateModelBound();
    }
    
}
