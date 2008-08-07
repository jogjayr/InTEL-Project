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
import edu.gatech.statics.exercise.state.DiagramState;
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
import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.exercise.state.StateStack;
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
public abstract class Diagram<StateType extends DiagramState> {

    /**
     * Returns the type of this diagram. Together, the type and the key should
     * make a unique identifier for this diagram.
     * @return
     */
    public final DiagramType getType() {
        return getMode().getDiagramType();
    }

    /**
     * Returns the diagram key for this particular diagram.
     * @return
     */
    abstract public DiagramKey getKey();
    /**
     * The state stack representing the current state and history of this diagram.
     */
    private StateStack<StateType> states;

    /**
     * creates the initial state for the diagram. Subclasses must override this method
     * and supply a starting state.
     */
    abstract protected StateType createInitialState();

    /**
     * This is called when the diagram has been completed, and should move on to the next
     * diagram, or move back into a different mode or do something to that effect.
     * This behavior is being moved into Diagram (instead of the UI) so that subclasses
     * can more easily control how the the completion behavior works.
     */
    abstract public void completed();
    
    /**
     * Undoes the last action done to this diagram
     */
    public void undo() {
        states.undo();
        updateDiagram();
    }

    /**
     * Redoes the last undone action
     */
    public void redo() {
        states.redo();
        updateDiagram();
    }

    /**
     * returns the current state of the diagram.
     * @return
     */
    public StateType getCurrentState() {
        return states.getCurrent();
    }

    /**
     * returns true if it is possible to undo from the current state
     * @return
     */
    public boolean canUndo() {
        return states.canUndo();
    }

    /**
     * returns true if it is possible to redo from the current state
     * @return
     */
    public boolean canRedo() {
        return states.canRedo();
    }

    /**
     * performs the given action if possible.
     * @param action
     */
    public void performAction(DiagramAction<StateType> action) {
        if (isLocked() || !states.canPush()) {
            return;
        }
        StateType newState = action.performAction(getCurrentState());
        states.push(newState);

        // update diagram
        updateDiagram();
    }

    /**
     * Pushes the current state into the state stack.
     * Does nothing if the state cannot be pushed.
     * @param state
     */
    protected void pushState(StateType state) {
        states.push(state);
    }

    /**
     * Clears the entries in the current state, leaving only the current state
     * This should only be used when the undo history needs to be cleared,
     * or if the diagram is just activated.
     */
    protected void clearStateStack() {
        states.clear();
    }

    /**
     * returns true if the current diagram is locked and cannot be modified.
     * Diagrams are locked when the user has successfully solved them, but it is
     * possible for diagrams to get unlocked if external changes are made.
     * @return
     */
    public boolean isLocked() {
        return getCurrentState().isLocked();
    }

    public static Schematic getSchematic() {
        return StaticsApplication.getApp().getExercise().getSchematic();
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();
    //private List<SimulationObject> userObjects = new ArrayList<SimulationObject>();
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

    /**
     * Returns a list of all the representations present in the diagram that reside
     * on the given representation layer.
     * @param layer
     * @return
     */
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

    /**
     * Returns a list of all the label representations present in this diagram.
     * @return
     */
    public List<LabelRepresentation> getLabels() {
        return labels;
    }

    /** Creates a new instance of World */
    public Diagram() {
        states = new StateStack<StateType>(createInitialState());
        //setSelectableFilterDefault();
    }
    private boolean nodesUpdated = false;

    /**
     * This is the publicly accepted way to get the diagram to refresh its nodes.
     */
    public void invalidateNodes() {
        nodesUpdated = false;
    }

    /**
     * This method updates all the nodes in the scene graph, making sure that
     * they correctly represent the objects that are present in the diagram.
     * This is automatically called during the display process, and should 
     * not usually be overridden.
     */
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

    /**
     * This updates the state of the diagram internally. Most of this does not 
     * involve many state changes, but might involve refreshing of UI elements and
     * object representations.
     */
    public void update() {
        updateNodes();
        for (SimulationObject obj : allObjects) {
            obj.update();
        }
    }

    /**
     * called when the user is hovering over the given object.
     * @param obj
     */
    public void onHover(SimulationObject obj) {
    }

    /**
     * called when the given object has been clicked by the user
     * @param obj
     */
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
        //allObjects.addAll(userObjects);
        allObjects.addAll(getCurrentState().getStateObjects());
        invalidateNodes();
    }

    /**
     * called when the diagram is deactivated. This can be used for clearing away any
     * temporary data made by the diagram that needs to be disposed.
     */
    public void deactivate() {
    }

    /**
     * Renders the current diagram scene graph.
     * This works by going through all the representation layers and then rendering
     * each on top of the other.
     * @param r
     */
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
