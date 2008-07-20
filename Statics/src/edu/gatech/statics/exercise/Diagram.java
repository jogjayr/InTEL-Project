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
import edu.gatech.statics.objects.AngleMeasurement;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Diagram {

    public static Schematic getSchematic() {
        return StaticsApplication.getApp().getExercise().getSchematic();
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();
    private List<SimulationObject> userObjects = new ArrayList<SimulationObject>();

    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }

    abstract public Mode getMode();

    /**
     * Get base objects that belong in the diagram.
     * This will get all of the objects that belong in the diagram from its particular
     * context. It does not contain anything that the user might have added.
     * @return
     */
    abstract protected List<SimulationObject> getBaseObjects();

    /**
     * Adds an object to the list of objects that users have added to the diagram.
     */
    public void addUserObject(SimulationObject obj) {
        userObjects.add(obj);
        allObjects.add(obj);
        invalidateNodes();
    }

    public void removeUserObject(SimulationObject obj) {
        userObjects.remove(obj);
        allObjects.remove(obj);
        invalidateNodes();
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
        //Logger.getLogger("Statics").info("*** updating nodes");

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

    /**
     * This method is called when a diagram is activated and the user switches to it
     */
    public void activate() {
        //setSelectableFilterDefault();
        invalidateNodes();
        StaticsApplication.getApp().setCurrentTool(null);
        updateDiagram();
    }

    /**
     * Check to see if there are any objects that have changed outside of this diagram
     * and that need to get updated or added.
     */
    protected void updateDiagram() {
        allObjects.clear();
        allObjects.addAll(getBaseObjects());
        allObjects.addAll(userObjects);
        invalidateNodes();
    }

    public void deactivate() {
    }

    public void render(Renderer r) {

        for (RepresentationLayer layer : RepresentationLayer.getLayers()) {
            //r.clearBuffers();
            if (layer.isEnabled()) {
                r.draw(getNode(layer));
                r.renderQueue();

            // This is a little bit of code that may be uncommented to 
            // view the bounding volumes
                /*if (layer == RepresentationLayer.vectors) {
            Debugger.setBoundsColor(ColorRGBA.pink);
            } else if (layer == RepresentationLayer.schematicBodies) {
            Debugger.setBoundsColor(ColorRGBA.white);
            } else {
            Debugger.setBoundsColor(ColorRGBA.green);
            }
            Debugger.drawBounds(getNode(layer), r, true);*/
            }
            r.clearZBuffer();
        }
    }

    /**
     * Returns a list of the connectors in the diagram present at the given point.
     * Generally there will only be one connector, but in some cases, especially with two force members,
     * multiple connectors may be present at the point.
     * @param point
     * @return
     */
    public List<Connector> getConnectorsAtPoint(Point point) {
        List<Connector> connectors = new ArrayList<Connector>();
        for (SimulationObject obj : allObjects) {
            if (obj instanceof Connector) {
                Connector connector = (Connector) obj;
                if (connector.getAnchor().equals(point)) {
                    connectors.add(connector);
                }
            }
        }
        return connectors;
    }

    /**
     * A convenience method to get all of the loads at a given point. This goes through
     * all of the loads in the current diagram and checks against them.
     * @param point
     * @return
     */
    public List<Load> getLoadsAtPoint(Point point) {
        List<Load> loads = new ArrayList<Load>();
        for (SimulationObject obj : allObjects) {
            if (obj instanceof Load) {
                Load load = (Load) obj;
                if (load.getAnchor().equals(point)) {
                    loads.add(load);
                }
            }
        }
        return loads;
    }

    /**
     * Return a list of directions that make sense as snapping options for a
     * force being positioned around a point. 
     * @param point
     * @return
     */
    public List<Vector3f> getSensibleDirections(Point point) {
        //List<Vector3f> directions = new ArrayList<Vector3f>();
        Set<Vector3f> directions = new HashSet<Vector3f>();

        // add cardinal directions
        addCardinalDirectionsAroundMatrix(new Matrix3f(), directions);

        // first try going through bodies to add their orientations
        for (Body body : getSchematic().allBodies()) {
            if (!body.getAttachedObjects().contains(point)) {
                continue;
            }

            // body has the point, now get its orientations...
            Matrix3f orientation = body.getRotation();
            addCardinalDirectionsAroundMatrix(orientation, directions);
        }

        // now check angle measurements
        for (SimulationObject object : getSchematic().allObjects()) {
            if (object instanceof AngleMeasurement) {
                AngleMeasurement measure = (AngleMeasurement) object;

                // our angle is anchored at this point
                if (measure.getAnchor() == point) {
                    Matrix3f matrix = new Matrix3f();

                    matrix.fromStartEndVectors(Vector3f.UNIT_X, measure.getAxis1());
                    addCardinalDirectionsAroundMatrix(matrix, directions);

                    matrix.fromStartEndVectors(Vector3f.UNIT_X, measure.getAxis2());
                    addCardinalDirectionsAroundMatrix(matrix, directions);
                }
            }
        }

        return new ArrayList<Vector3f>(directions);
    }

    private void addCardinalDirectionsAroundMatrix(Matrix3f matrix, Set<Vector3f> directions) {
        directions.add(matrix.mult(new Vector3f(1, 0, 0)));
        directions.add(matrix.mult(new Vector3f(-1, 0, 0)));
        directions.add(matrix.mult(new Vector3f(0, 1, 0)));
        directions.add(matrix.mult(new Vector3f(0, -1, 0)));
        directions.add(matrix.mult(new Vector3f(0, 0, 1)));
        directions.add(matrix.mult(new Vector3f(0, 0, -1)));
    }
}
