/*
 * FBDWorld.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import com.jme.input.InputHandler;
import com.jme.renderer.Renderer;
import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.fbd.tools.LabelSelector;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FreeBodyDiagram extends SubDiagram<FBDState> {

    private FBDInput fbdInput;
    /**
     * This is a list of Loads that are currently maintained by the diagram.
     * These loads are not the state of the diagram, but will be updated to reflect
     * the state. We do not use a Map&lt;AnchoredVector,Load&gt; because that would
     * require there to be distinct AnchoredVectors in the state. This is not necessarily
     * the case, becuase users may add two forces that are equivalent. 
     */
    private List<Load> loadObjects = new ArrayList<Load>();

    /**
     * The user objects for this diagram are merely the loads that are present.
     * @return
     */
    @Override
    public List<? extends SimulationObject> getUserObjects() {
        return loadObjects;
    }

    /**
     * This method can be used for making sure that the loads which are displayed
     * are reflective of the state. Returns null if none is found.
     * This will search to find if a load corresponds to the vector provided. If
     * there are multiple vectors present in the state that are equivalent, this will return the first one.
     * @param vector
     * @return
     */
    protected Load getLoad(AnchoredVector vector) {
        // this uses the brute force technique.

        for (Load load : loadObjects) {
            if (load.getAnchoredVector().equals(vector)) {
                return load;
            }
        }
        return null;
    }

    /**
     * This is called whenever the state changes, and causes the loads in this
     * diagram to be synchronized with what is present in the state.
     */
    @Override
    protected void stateChanged() {

        // check if there are unnecessary loads now
        List<Load> missingLoads = new ArrayList<Load>();
        for (Load load : loadObjects) {
            AnchoredVector vector = load.getAnchoredVector();
            if (!getCurrentState().getAddedLoads().contains(vector)) {
                missingLoads.add(load);
            }
        }

        // remove them
        for (Load load : missingLoads) {
            missingLoads.remove(load);
        }

        // check for newly added loads
        // this check does something special in that it uses an equivalence test,
        // but also needs to make sure that even duplicates get corresponding loads
        // to back them.
        List<AnchoredVector> newVectors = new ArrayList<AnchoredVector>();
        List<Load> accountedFor = new ArrayList<Load>(loadObjects);
        for (AnchoredVector vector : getCurrentState().getAddedLoads()) {
            // try to retrieve from the accounted list.
            Load load = null;
            for (Load candidate : accountedFor) {
                if (candidate.getAnchoredVector().equals(vector)) {
                    load = candidate;                //if()
                }
            }
            // if we have not found it 
            if (load == null) {
                newVectors.add(vector);
            }
        }

        for (AnchoredVector vector : newVectors) {
            Load load = createLoad(vector);
            loadObjects.add(load);
        }
    }

    protected Load createLoad(AnchoredVector vector) {
        if (vector.getUnit() == Unit.force) {
            return new Force(vector);
        } else if (vector.getUnit() == Unit.moment) {
            return new Moment(vector);
        } else {
            throw new IllegalArgumentException(
                    "Have some sort of invalid type of load: " + vector +
                    " the unit is a " + vector.getUnit() + ". It should be either a force or moment.");
        }
    }

    /**
     * Override this method to supply diagram checkers with additional functionality.
     * @return
     */
    public FBDChecker getChecker() {
        return new FBDChecker(this);
    }

    /**
     * Reset simply instantiates the initial state.
     */
    public void reset() {
        FBDState initialState = createInitialState();
        pushState(initialState);

        currentHighlight = null;
        currentSelection = null;
    }

    /**
     * This marks the diagram as solved (or unmarks it when passing false),
     * and locks or unlocks the diagram correspondingly.
     * @param solved
     */
    public void setSolved(boolean solved) {

        // current state already reflects the change, OK
        if (getCurrentState().isSolved() == solved) {
            return;
        }

        // otherwise...
        // push the changed state with the solve
        FBDState.Builder builder = new FBDState.Builder(getCurrentState());
        builder.setSolved(solved);
        pushState(builder.build());

        // make sure that the state history is purged
        clearStateStack();

        if (solved) {
            for (SimulationObject obj : allObjects()) {
                if (!(obj instanceof Load)) {
                    continue;
                }
                if (!(((Load) obj).isSymbol())) {
                    continue;
                }
                StaticsApplication.getApp().getExercise().getSymbolManager().addSymbol((Load) obj);
            }

            String advice = java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_feedback_check_success");
            StaticsApplication.getApp().setAdvice(advice);
            StaticsApplication.getApp().setDefaultAdvice(advice);
            StaticsApplication.getApp().resetAdvice();
        } else {
        }
    }

    /**
     * This handles what happens when the diagram is solved.
     * By default, we load the equation mode. Later on, other exercise types may 
     * choose to pass this step.
     */
    @Override
    public void completed() {
        EquationMode.instance.load(getBodySubset());
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        for (Body body : getBodySubset().getBodies()) {
            objects.add(body);
            for (SimulationObject obj : body.getAttachedObjects()) {
                if (!(obj instanceof Load)) {
                    objects.add(obj);
                }
            }
        }

        for (Measurement measurement : getSchematic().getMeasurements(getBodySubset())) {
            objects.add(measurement);
        }
        return objects;
    }

    /** Creates a new instance of FBDWorld */
    public FreeBodyDiagram(BodySubset bodies) {
        super(bodies);


        fbdInput = new FBDInput(this);
    }

    /*@Override
    public void addUserObject(SimulationObject obj) {
    super.addUserObject(obj);
    if (obj instanceof Load) {
    addedLoads.add((Load) obj);
    new LabelManipulator((Load) obj);
    }
    }*/
    @Override
    public InputHandler getInputHandler() {
        return fbdInput;
    }
    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }
    private Load currentHighlight;
    private Load currentSelection;

    @Override
    public void onHover(SimulationObject obj) {

        if (currentHighlight == obj) {
            return;
        }

        // we are changing our hover, so clear the current
        if (currentHighlight != null) {
            currentHighlight.setDisplayHighlight(false);
        }

        if (obj == null) {
            currentHighlight = null;
            return;
        }

        if (obj != currentSelection) {
            currentHighlight = (Load) obj;
            currentHighlight.setDisplayHighlight(true);
        }
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null) {
            if (currentSelection == null) {
                return;
            }
            currentSelection.setDisplaySelected(false);
            currentSelection = null;
            return;
        }

        if (currentSelection == obj) {
            currentSelection.setDisplaySelected(false);
            currentSelection = null;
        } else {
            if (currentSelection != null) {
                currentSelection.setDisplaySelected(false);
            }
            currentSelection = (Load) obj;
            currentSelection.setDisplaySelected(true);

            fbdInput.onSelect(currentSelection);
        }
    }

    public void onLabel(Load load) {
        if (getCurrentState().isLocked()) {
            return;
        }

        LabelSelector labelTool = new LabelSelector(this, load, load.getAnchor().getTranslation());
        labelTool.setAdvice("Please give a name or a value for your load");
        labelTool.setUnits(load.getUnit().getSuffix());
        //labelTool.setHintText("");
        if (load.isSymbol()) {
            labelTool.setHintText(load.getSymbolName());
        } else {
            labelTool.setHintText(load.toStringDecimal());
        }
        labelTool.setIsCreating(false);
        labelTool.renamePopup();
    }

    @Override
    public void render(Renderer r) {
        super.render(r);
    //renderIcon();
    }

    @Override
    public void activate() {
        super.activate();

        StaticsApplication.getApp().setDefaultAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("fbd_feedback_welcome"));
        StaticsApplication.getApp().resetAdvice();

        if (!getCurrentState().isLocked()) {
            setSolved(true);
        }
    }

    Load getSelection() {
        return currentSelection;
    }

    @Override
    public Mode getMode() {
        return FBDMode.instance;
    }

    @Override
    protected FBDState createInitialState() {
        return new FBDState.Builder().build();
    }
}
