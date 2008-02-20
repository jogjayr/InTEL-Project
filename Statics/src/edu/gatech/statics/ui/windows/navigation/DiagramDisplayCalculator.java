/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.exercise.Diagram;

/**
 *
 * @author Calvin Ashmore
 */
public class DiagramDisplayCalculator {

    private float radiusMultiplier = 1.1f;
    private float radiusOffset = 1f;
    
    /**
     * Returns a view diagram state if calculation is successful,
     * will return null if there's a problem with bounding volume calculation, etc
     * @param diagram
     * @return
     */
    public ViewDiagramState calculate(Diagram diagram) {
        Node representationNode = diagram.getNode(RepresentationLayer.schematicBodies);
        //System.out.println("*** calculating "+representationNode);
        if(representationNode == null)
            return null;
        
        BoundingVolume volume = representationNode.getWorldBound();
        return calculate(volume);
    }

    protected ViewDiagramState calculate(BoundingVolume volume) {
        BoundingSphere boundingSphere = new BoundingSphere(.01f, volume.getCenter());
        boundingSphere.mergeLocal(volume);

        float radius = boundingSphere.getRadius();
        //System.out.println("**** radius: " + radius);

        Vector3f center = volume.getCenter();
        System.out.println(volume.getType() + " " + volume);

        ViewDiagramState state = new ViewDiagramState();
        state.setCameraFrame(center.add(Vector3f.UNIT_Z.mult(radiusMultiplier * radius + radiusOffset)), center);
        return state;
    }
}
