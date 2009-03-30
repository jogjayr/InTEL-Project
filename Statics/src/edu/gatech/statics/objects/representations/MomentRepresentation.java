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
import com.jme.scene.Node;
import com.jme.scene.shape.Cylinder;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.objects.Moment;

/**
 *
 * @author Calvin Ashmore
 */
public class MomentRepresentation extends Representation<Moment> {

    protected float torusInnerRadius = .12f;
    protected float torusOuterRadius = 1.5f;
    protected float tipWidth = .4f;
    protected float tipHeight = 1.0f;

    protected float torusSweep = 1.33f * (float) Math.PI;

    /** Creates a new instance of MomentRepresentation */
    public MomentRepresentation(Moment target) {
        super(target);
        setLayer(RepresentationLayer.vectors);
        Node contents = new Node("");

        PartialTorus torus = new PartialTorus("", 16, 6, torusInnerRadius, torusOuterRadius, torusSweep);
        //Matrix3f mat = new Matrix3f();
        //mat.fromStartEndVectors(Vector3f.UNIT_Z, new Vector3f(0, 0, -1));
        //torus.setLocalRotation(mat);
        contents.attachChild(torus);

        Cylinder cylinder = new Cylinder("", 6, 12, tipWidth, tipHeight, true);
        cylinder.setRadius1(0);
        cylinder.setLocalTranslation(torusOuterRadius, -tipHeight/2, 0);
        Matrix3f mat = new Matrix3f();
        mat.fromStartEndVectors(Vector3f.UNIT_Z, new Vector3f(0, -1, 0));
        cylinder.setLocalRotation(mat);

        contents.attachChild(cylinder);
        mat = new Matrix3f();
        mat.fromStartEndVectors(Vector3f.UNIT_Z, new Vector3f(0, 0, -1));
        contents.setLocalRotation(mat);
        
        getRelativeNode().attachChild(contents);
        
        setModelBound(new BoundingBox());
        update();
        updateModelBound();
        updateRenderState();
    }
    @Override
    public void update() {
        super.update();
        if(useWorldScale()){
            setLocalScale(DisplayConstants.getInstance().getMomentSize());
        }
    }
}
