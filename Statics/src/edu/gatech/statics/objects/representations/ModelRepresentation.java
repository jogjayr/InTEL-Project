/*
 * ModelRepresentation.java
 *
 * Created on June 9, 2007, 3:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ModelRepresentation extends Representation {

    private Node modelNode;

    public void setModelOffset(Vector3f offset) {
        modelNode.setLocalTranslation(offset);
    }

    public void setModelScale(float scale) {
        modelNode.setLocalScale(scale);
    }

    public void setModelRotation(Matrix3f rotation) {
        modelNode.setLocalRotation(rotation);
    }

    ModelRepresentation(SimulationObject target, Node modelNode) {
        super(target);
        setLayer(RepresentationLayer.modelBodies);
        this.modelNode = modelNode;

        getRelativeNode().attachChild(modelNode);
        updateRenderState();
    }
}
