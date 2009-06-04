/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;

/**
 * This is a representation that mimics another representation. It actually uses the
 * node of the other representation, but can be set to have its target point to a
 * different SimulationObject. This way an object can share the representation of another.
 * The application must DEACTIVATE the MimicRepresentation when the original representation
 * needs to be displayed. This is necessary because JME Nodes can not have a node with multiple parents.
 * @author Calvin Ashmore
 */
public class MimicRepresentation extends Representation {

    private final Representation base;

    public MimicRepresentation(SimulationObject target, Representation base) {
        super(target);
        this.base = base;

        attachChild(base.getRelativeNode());
    }

    /**
     * This enables the mimic representation if it has been disabled.
     */
    public void activate() {
        if (!getChildren().contains(base.getRelativeNode())) {
            attachChild(base.getRelativeNode());
        }
    }

    /**
     * This disables the mimic representation, allowing the representation to work normally
     */
    public void deactivate() {
        if (getChildren().contains(base.getRelativeNode())) {
            base.attachChild(base.getRelativeNode());
            base.setRenderStateChanged(true);
        }
    }

    public Vector3f getWorldTranslation() {
        return base.getWorldTranslation();
    }

    public Vector3f getWorldScale() {
        return base.getWorldScale();
    }

    public Quaternion getWorldRotation() {
        return base.getWorldRotation();
    }

    public Vector3f getLocalTranslation() {
        return base.getLocalTranslation();
    }

    public Vector3f getLocalScale() {
        return base.getLocalScale();
    }

    public Quaternion getLocalRotation() {
        return base.getLocalRotation();
    }

    public void update() {
        base.update();
    }

    public void setUseWorldScale(boolean useScale) {
        base.setUseWorldScale(useScale);
    }

    public void setSynchronizeTranslation(boolean synch) {
        base.setSynchronizeTranslation(synch);
    }

    public void setSynchronizeRotation(boolean synch) {
        base.setSynchronizeRotation(synch);
    }

    public void setRenderStateChanged(boolean changed) {
        base.setRenderStateChanged(changed);
    }

    public void setLayer(RepresentationLayer layer) {
        base.setLayer(layer);
    }

//    public boolean getRenderStateChanged() {
//        return base.getRenderStateChanged();
//    }
    public Node getRelativeNode() {
        return base.getRelativeNode();
    }

    public RepresentationLayer getLayer() {
        return base.getLayer();
    }
}
