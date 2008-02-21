/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jme.bounding.BoundingBox;
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
    private float verticalViewOffsetProportion = 0.13f;
    private float viewAspectRatio = 1.5f;
    
    private ViewDiagramState defaultState = new ViewDiagramState();
    
    /**
     * Returns a view diagram state if calculation is successful,
     * will return null if there's a problem with bounding volume calculation, etc
     * @param diagram
     * @return
     */
    public ViewDiagramState calculate(Diagram diagram) {
        Node representationNode = diagram.getNode(RepresentationLayer.schematicBodies);
        if(representationNode == null)
            return null;
        
        BoundingVolume volume = representationNode.getWorldBound();
        return calculate(volume);
    }
    
    protected ViewDiagramState calculate(BoundingVolume volume) {
        Vector3f look = defaultState.getCameraLookAtCenter().subtract(defaultState.getCameraCenter());
        look.normalizeLocal();
        
        Vector3f right = look.cross(Vector3f.UNIT_Y);
        right.normalizeLocal();
        
        Vector3f up = right.cross(look);
        up.normalizeLocal();
        
        /*
         * What is going on here is that we are trying to find the view extent of the
         * diagram, and to do that, we need to do some tests on the bounding box.
         * We are using an axis-aligned bounding box for this because they are usually
         * easy to use. 
         */
        
        //OrientedBoundingBox obb = (OrientedBoundingBox)volume;
        BoundingBox aabb = new BoundingBox(volume.getCenter(),0,0,0);
        aabb.mergeLocal(volume);
        
        Vector3f extent = new Vector3f(aabb.getExtent(null));
        
        float rightRadius = extent.dot(right);
        float upRadius = extent.dot(up);
        
        /*System.out.println("look:   "+look);
        System.out.println("right:  "+right);
        System.out.println("up:     "+up);
        System.out.println("extent: "+extent);
        System.out.println("center: "+volume.getCenter());
        System.out.println("upRadius:    "+upRadius);
        System.out.println("rightRadius: "+rightRadius);*/
        
        float radius = Math.max(
                rightRadius,
                upRadius * viewAspectRatio
                );
        
        //System.out.println("radius:      "+radius);
        
        Vector3f center = new Vector3f(volume.getCenter());
        center.addLocal(Vector3f.UNIT_Y.mult(-verticalViewOffsetProportion*radius));
        Vector3f cameraPos = center.subtract(look.mult(radiusMultiplier * radius + radiusOffset));

        ViewDiagramState state = new ViewDiagramState();
        state.setCameraFrame(cameraPos, center);
        return state;
    }
}
