/*
 * World.java
 *
 * Created on June 4, 2007, 3:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import com.jme.input.InputHandler;
import edu.gatech.statics.objects.SimulationObject;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.RenderState;
import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Diagram {

    public static Schematic getSchematic() {
        return StaticsApplication.getApp().getExercise().getSchematic();
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();

    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }
    
    abstract public Mode getMode();

    public void add(SimulationObject obj) {
        if (!allObjects.contains(obj)) {
            allObjects.add(obj);
            invalidateNodes();
        }
    }

    public void addAll(Collection<SimulationObject> objs) {
        for (SimulationObject obj : objs) {
            add(obj);
        }
    }
    private static final SelectionFilter defaultFilter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return false;
        }
    };

    public InputHandler getInputHandler() {
        return null;
    }

    public SelectionFilter getSelectionFilter() {
        return defaultFilter;
    }

    public void remove(SimulationObject obj) {
        allObjects.remove(obj);
        invalidateNodes();
    }
    private Map<RepresentationLayer, Node> representationNodes = new HashMap<RepresentationLayer, Node>();

    public Node getNode(RepresentationLayer layer) {
        return representationNodes.get(layer);
    }

    public Node getNode(String layer) {
        return representationNodes.get(RepresentationLayer.getLayer(layer));
    }

    public List<Body> allBodies() {
        List<Body> bodies = new ArrayList<Body>();
        for (SimulationObject obj : allObjects) {
            if (obj instanceof Body) {
                bodies.add((Body) obj);
            }
        }
        return bodies;
    }
    // we cache the lists because these are refreshed constantly
    // if new ones are returned, lots of Object[] instances are left and hog memory
    private Map<RepresentationLayer, List<Representation>> representationCache = new HashMap<RepresentationLayer, List<Representation>>();

    public List<Representation> getRepresentations(RepresentationLayer layer) {
        List<Representation> r = representationCache.get(layer);
        if (r == null) {
            representationCache.put(layer, r = new ArrayList<Representation>());
        }

        r.clear();
        for (SimulationObject obj : allObjects()) {
            r.addAll(obj.getRepresentation(layer));
        }
        return r;
    }
    private List<LabelRepresentation> labels = new ArrayList<LabelRepresentation>();

    public List<LabelRepresentation> getLabels() {
        return labels;
    }

    /** Creates a new instance of World */
    public Diagram() {
    //setSelectableFilterDefault();
    }
    private boolean nodesUpdated = false;

    public void invalidateNodes() {
        nodesUpdated = false;
    }

    protected void updateNodes() {
        if (nodesUpdated) {
            return;
        }
        System.out.println("*** updating nodes");

        nodesUpdated = true;

        representationNodes.clear();
        labels.clear();

        List<RepresentationLayer> allLayers = RepresentationLayer.getLayers();

        for (RepresentationLayer layer : allLayers) {

            Node node = representationNodes.get(layer); //new Node();
            if (node == null) {
                node = new Node();
                representationNodes.put(layer, node);
            }
            node.detachAllChildren();

            boolean updateRenderState = false;

            for (Representation r : getRepresentations(layer)) {
                if (!r.isHidden()) {
                    node.attachChild(r);
                    addLabels(r);
                }
                if (!r.getRenderUpdated()) {
                    updateRenderState = true;
                    r.setRenderUpdated();
                }
            }

            if (updateRenderState) {
                for (RenderState renderState : layer.getRenderStates()) {
                    node.setRenderState(renderState);
                }
                node.updateRenderState();
            }

            node.updateWorldVectors();
            node.updateGeometricState(0f, true);
            node.updateModelBound();
            node.updateWorldBound();
        }
    }

    private void addLabels(Node node) {

        // search children 
        // this seems cheap, but otherwise seems to be the best way to collect
        // label representations...

        if (node instanceof Representation &&
                !((Representation) node).getLayer().isEnabled()) {
            return;
        }

        if (node.getChildren() != null) {
            for (Spatial child : node.getChildren()) {
                if (child instanceof Node) {
                    addLabels((Node) child);
                }
            }
        }

        if (node instanceof LabelRepresentation &&
                ((LabelRepresentation) node).getLayer().isEnabled()) {
            labels.add((LabelRepresentation) node);
        }
    }

    public void update() {
        updateNodes();
        for (SimulationObject obj : allObjects) {
            obj.update();
        }
    }

    public void onHover(SimulationObject obj) {
    }

    public void onClick(SimulationObject obj) {
    }

    public void activate() {
        //setSelectableFilterDefault();
        invalidateNodes();
        StaticsApplication.getApp().setCurrentTool(null);
    }

    public void deactivate() {
    }

    public void render(Renderer r) {

        for (RepresentationLayer layer : RepresentationLayer.getLayers()) {
            //r.clearBuffers();
            if (layer.isEnabled()) {
                r.draw(getNode(layer));
                r.renderQueue();
            //GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).execute();

            /*
            if(layer == RepresentationLayer.vectors)
            Debugger.setBoundsColor(ColorRGBA.pink);
            else Debugger.setBoundsColor(ColorRGBA.white);
            Debugger.drawBounds(getNode(layer), r, true);
             */
            }
            r.clearZBuffer();
        }

    }

    public List<Vector3f> getSensibleDirections(Point point) {
        List<Vector3f> directions = new ArrayList<Vector3f>();

        directions.add(new Vector3f(1, 0, 0));
        directions.add(new Vector3f(-1, 0, 0));
        directions.add(new Vector3f(0, 1, 0));
        directions.add(new Vector3f(0, -1, 0));

        for (Body body : getSchematic().allBodies()) {
            if (!body.getAttachedObjects().contains(point)) {
                continue;
            }

            // body has the point, now get its orientations...
            Matrix3f orientation = body.getRotation();

            directions.add(orientation.mult(new Vector3f(1, 0, 0)));
            directions.add(orientation.mult(new Vector3f(-1, 0, 0)));
            directions.add(orientation.mult(new Vector3f(0, 1, 0)));
            directions.add(orientation.mult(new Vector3f(0, -1, 0)));
        }

        return directions;
    }
}
