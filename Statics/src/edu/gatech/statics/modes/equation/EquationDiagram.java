/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * EquationWorld.java
 *
 * Created on July 19, 2007, 3:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.Mode;
import edu.gatech.statics.modes.equation.ui.EquationBar;
import edu.gatech.statics.modes.equation.worksheet.Worksheet;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.objects.VectorObject;
import edu.gatech.statics.objects.bodies.PointBody;
import edu.gatech.statics.objects.connectors.ContactPoint;
import edu.gatech.statics.objects.representations.CurveUtil;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.Pair;
import edu.gatech.statics.util.SelectionFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationDiagram extends SubDiagram<EquationState> {

    protected Worksheet worksheet;
    private FreeBodyDiagram fbd;

    public Worksheet getWorksheet() {
        return worksheet;
    }

    /**
     * Used for equation solving in ArbitraryEquationMath. Do not use!
     * @return
     * @deprecated
     */
    @Deprecated
    public FreeBodyDiagram getFBD() {
        return fbd;
    }

    /**
     * Sets the point about which the moment will be calculated.
     * This also notifies the panel to update its value for the moment point.
     * @param momentPoint
     */
    //public void setMomentPoint(Point momentPoint) {        //this.momentPoint = momentPoint;
    //EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
    //eqPanel.setMomentPoint(momentPoint);
    //}
    /**
     * Returns the point about which the moment is being calculated.
     * @return
     */
//    public Point getMomentPoint() {
//        EquationMathState ems;
//        for (Map.Entry<String, EquationMathState> entry : getCurrentState().getEquationStates().entrySet()) {
//            ems = entry.getValue();
//            if (ems instanceof TermEquationMathState) {
//                return ((TermEquationMathState) ems).getMomentPoint();
//            }
//        }
//        return null;
////        return getCurrentState().getMomentPoint();
//    }
    /**
     * This attempts to find the in-diagram Load object that corresponds to the
     * given AnchoredVector.
     * @param vector
     * @return
     */
    public Load getLoad(AnchoredVector vector) {
        if (vector == null) {
            return null;
        }
        for (Load load : fbd.getUserObjects()) {

            if (vector.equals(load.getAnchoredVector())) {
                return load;
            }
            if (vector.getAnchor() == load.getAnchor()
                    && vector.isSymbol() && load.isSymbol()
                    && vector.getVector().equalsSymbolic(load.getVector())
                    && vector.getSymbolName().equals(load.getSymbolName())) {
                return load;
            }
        }
        return null;
    }

    /**
     * Returns an AnchoredVector that matches the symbol name. This looks in the
     * list of loads from the underlying FBD.
     * @param symbolName
     * @return
     */
    public AnchoredVector getAnchoredVectorFromSymbol(String symbolName) {
        if (symbolName == null) {
            return null;
        }
        for (AnchoredVector vector : getDiagramLoads()) {
            if (symbolName.equals(vector.getSymbolName())) {
                return vector;//getLoad(vector);
            }
        }
        return null;
    }

    /**
     * 
     */
    @Override
    protected void stateChanged() {
        super.stateChanged();

        // check for an invalid state:
        // currently, these only occur with invalid states becoming persisted.
        // it will become necessary to find out what is wrong that is causing those errors to occur
        // in the first place, but this checks and solves for the particular case when no
        // data is present at all in the state, and the state is marked as solved.
        if (getCurrentState().isLocked()) {
            boolean anythingEntered = false;
            for (EquationMathState equationMathState : getCurrentState().getEquationStates().values()) {
                // if the state has any terms, set anythingEntered to true.
                if (equationMathState instanceof ArbitraryEquationMathState) {
                    //TODO write this in!
                } else if (equationMathState instanceof TermEquationMathState) {
                    anythingEntered |= !((TermEquationMathState) equationMathState).getTerms().isEmpty();
                } else if (equationMathState instanceof Moment3DEquationMathState) {
                    // ignore.
                } else {
                    throw new IllegalArgumentException("Unknown equation math state type! " + equationMathState);
                }


            }

            if (!anythingEntered) {
                // we have a problem.
                // there is nothing at all entered in the state itself, so we commit a new empty state.
                StaticsApplication.logger.log(Level.SEVERE, "Encountered push for invalid state in the equation state");
                pushState(createInitialState());
                clearStateStack();
            }
        }
    }

    /**
     * This method retrieves the diagram loads from the underlying FreeBodyDiagram
     * @return
     */
    public List<AnchoredVector> getDiagramLoads() {
        return fbd.getCurrentState().getAddedLoads();
    }

    /**
     * 
     * @return 
     */
    @Override
    protected List<SimulationObject> getBaseObjects() {
        //FreeBodyDiagram fbd = StaticsApplication.getApp().getExercise().getFreeBodyDiagram(getBodySubset());
        //Diagram fbd = Exercise.getExercise().getDiagram(getKey(), FBDMode.instance.getDiagramType());
        List<SimulationObject> objects = new ArrayList<SimulationObject>(fbd.allObjects());
        objects.removeAll(fbd.getAdjacentObjects());
        return objects;
    }

    /**
     * Creates a new instance of EquationWorld 
     * @param bodies 
     */
    public EquationDiagram(BodySubset bodies) {
        super(bodies);

        this.fbd = (FreeBodyDiagram) Exercise.getExercise().getDiagram(getKey(), FBDMode.instance.getDiagramType());
        // FIXME: This diagram automatically loads a 2D worksheet
        //worksheet = new Worksheet2D(this);
    }

    /**
     * This constructs the worksheet for this diagram. This method is called by
     * the diagram constructor, so subclasses should not
     * @return
     */
//    protected Initial2DEquationList createInitialEquationList() {
//        //************
//        // Should use some non-worksheet class here?
//        // this should return an object that contains the initial equation list.
//        // this will be used to create the initial state.
//        return new Initial2DEquationList(this);
//    }
    /**
     * This actually updates the vectors and joints with the result of the solution.
     * The update applies to objects within the EquationDiagram, but these are references
     * to the original copies defined in the schematic, which are updated correspondingly.
     * @param values
     */
    public void performSolve(Map<Quantity, Float> values) {
        StaticsApplication.logger.info("Performing the solve (updating other diagrams with the solution)");

        // first, get the builder
        // lock the diagram
        EquationState.Builder eqBuilder = new EquationState.Builder(getCurrentState());
        eqBuilder.setLocked(true);

        FBDState.Builder fbdBuilder = fbd.getCurrentState().getBuilder();

        // FIRST BATCH
        // this first batch of updates updates basic values, vectors and measurements
        // the second batch will depend on these values.

        // go through the vectors, and make sure everything is in order:
        // give the vectors the new solved values
        // also go through measurements
        for (SimulationObject obj : allObjects()) {
            if (obj instanceof Load) {
                Load vObj = (Load) obj;
                //Vector v = vObj.getVector();
                if (vObj.isSymbol() && !vObj.getVector().isKnown()) {
                    // v is a symbolic force, but is not yet solved.
                    if (values.get(vObj.getVector().getQuantity()) == null) {
                        continue;
                    }

                    float value = values.get(vObj.getVector().getQuantity());
                    //v.setValue(v.getValueNormalized().mult( value ));
                    //vObj.getAnchoredVector().setDiagramValue(new BigDecimal(value));
                    //vObj.getAnchoredVector().setKnown(true);

                    AnchoredVector oldLoad = vObj.getAnchoredVector();
                    // retrieve the load from the symbol manager
                    Pair<Connector, AnchoredVector> loadPair = Exercise.getExercise().getSymbolManager().getLoadPair(oldLoad.getSymbolName());
                    AnchoredVector newLoad = loadPair.getRight();

                    // make a copy of it
                    newLoad = new AnchoredVector(newLoad);

                    // the new load will be the load stored in the symbol manager.
                    // This is a load which was created in another diagram, but not solved for yet.
                    // We want to make sure that it has the correct direction for this diagram, though.
                    // This is because if *value* is negated, it is in reverse of the direction
                    // specified by the user in oldLoad.
                    // Thus, we check to see if they have the same direction, and reverse it otherwise.
                    if (newLoad.getVectorValue().dot(oldLoad.getVectorValue()).floatValue() < 0) {
                        // the dot product checks to see if they are pointing the same way.
                        newLoad.getVectorValue().negateLocal();
                    }

                    newLoad.setDiagramValue(new BigDecimal(value));
                    newLoad.setKnown(true);

                    // if the load from the symbol manager is due to the other end of a 2FM,
                    // then newLoad will be be located at the opposite point, and pointing in the wrong direction.
                    // so we perform a check and move its location and reverse it in this case.
                    if (!newLoad.getAnchor().equals(oldLoad.getAnchor())) {
                        // the only circumstance when the points will be different is if the stored load is due to a 2FM
                        newLoad.setAnchor(oldLoad.getAnchor());
                    }

                    // update the load in the symbol manager
                    Exercise.getExercise().getSymbolManager().addSymbol(newLoad, loadPair.getLeft());

                    // *****
                    // instead of trying to set the value on the load in the diagram, what we are doing
                    // instead is forcing a change in the FBD itself.
                    // We rewrite the FBD to use the new solved load...
                    fbdBuilder.removeLoad(vObj.getAnchoredVector());
                    fbdBuilder.addLoad(newLoad);

                    // and then force the coefficients to update
                    for (EquationMathState equationMathState : eqBuilder.getEquationStates().values()) {

                        if (equationMathState instanceof ArbitraryEquationMathState) {
                            //TODO write this in!
                        } else if (equationMathState instanceof TermEquationMathState) {
                            TermEquationMathState.Builder mathBuilder = ((TermEquationMathState) equationMathState).getBuilder();
                            Map<AnchoredVector, String> terms = mathBuilder.getTerms();
                            String coefficient = terms.remove(oldLoad);
                            if (coefficient == null) {
                                continue;
                            }
                            terms.put(newLoad, coefficient);
                            eqBuilder.getEquationStates().put(equationMathState.getName(), mathBuilder.build());
                        } else if (equationMathState instanceof Moment3DEquationMathState) {
                            // ignore.
                        } else {
                            throw new IllegalArgumentException("Unknown equation math state type! " + equationMathState);
                        }
                    }
                }
            }

            // this section will need to be updated
            // solved measures should be stored in the symbol manager.
            if (obj instanceof Measurement) {
                Measurement measure = (Measurement) obj;
                // ignore if the measurement is already known
                if (measure.isKnown()) {
                    continue;
                }
                // this is kind of a crummy test, but we'll try it anyway
                for (Map.Entry<Quantity, Float> entry : values.entrySet()) {
                    if (measure.getSymbolName().equals(entry.getKey().getSymbolName())) {
                        // okay, our measure actually is actually solved for in the solution
                        measure.updateQuantityValue(new BigDecimal(entry.getValue()));
                        measure.setKnown(true);
                    }
                }
            }

            // the
            if (obj instanceof ConstantObject) {
                ConstantObject constant = (ConstantObject) obj;
                if (constant.getQuantity().isKnown()) {
                    continue;
                }

                for (Map.Entry<Quantity, Float> entry : values.entrySet()) {
                    if (constant.getQuantity().getSymbolName().equals(entry.getKey().getSymbolName())) {
                        constant.getQuantity().setDiagramValue(new BigDecimal(entry.getValue()));
                        constant.getQuantity().setKnown(true);
                    }
                }
            }
        }

        // update the diagrams
        fbd.pushState(fbdBuilder.build());
        fbd.clearStateStack();

        pushState(eqBuilder.build());
        activate();
        InterfaceRoot.getInstance().getModePanel(getMode().getModeName()).activate();
        clearStateStack();

        // SECOND BATCH
        // this depends on the first batch to be updated before it will work

        // go through the joints, and mark the joints as solved,
        // assigning to them the solved values as having the updated vectors.
        // also go through unknown points...
        for (SimulationObject obj : allObjects()) {
            if (obj instanceof Connector) {
                solveConnector(obj, values);
            }

            if (obj instanceof UnknownPoint) {
                UnknownPoint point = (UnknownPoint) obj;

                // we do not test whether point is known, because by virtue of the measurement being solved just above
                // this would cause the point to always be known

                // this is kind of a crummy test, but we'll try it anyway
                for (Map.Entry<Quantity, Float> entry : values.entrySet()) {
                    if (point.getSymbol().equals(entry.getKey().getSymbolName())) {
                        // okay, our measure actually is actually solved for in the solution
                        point.setSolved();
                    }
                }
            }
        }
    }

    /**
     * 
     * @param obj 
     */
    @Override
    public void onClick(SimulationObject obj) {
        super.onClick(obj);

        if (StaticsApplication.getApp().getCurrentTool() != null
                && StaticsApplication.getApp().getCurrentTool().isActive()) {
            return;
        } // do not select vectors if we have a tool active

        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.onClick((Load) obj);
    }

    /**
     * 
     */
    @Override
    public void onHover(SimulationObject obj) {
        super.onHover(obj);

        Load load = (Load) obj;

        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.onHover(load);

        if (obj == null) {
            highlightVector(null);
        } else {
            highlightVector(load.getAnchoredVector());
        }
    }

    /**
     * find the number of unknowns for use in determining whether the user
     * has solved enough equations
     * @return
     */
    public int getNumberUnknowns() {
        List<String> symbols = new ArrayList<String>();
        for (SimulationObject obj : this.allObjects()) {
            Quantified q = null;

            if (obj instanceof Load) {
                q = ((Load) obj).getVector();
            } else if (obj instanceof Quantified) {
                q = (Quantified) obj;
            } else if (obj instanceof ContactPoint) {
                q = ((ContactPoint) obj).getFrictionCoefficient().getQuantity();
            }

            if (q != null && q.isSymbol() && !q.isKnown()) {
                String symbol = q.getSymbolName();
                if (!symbols.contains(symbol)) {
                    symbols.add(symbol);
                }
            }
        }
        return symbols.size();
    }

    /**
     * 
     */
    @Override
    public void activate() {
        // make sure the FBD is set before doing anything else
        // occasionally, when loaded from persistence, the FBD is assigned to null.
        this.fbd = (FreeBodyDiagram) Exercise.getExercise().getDiagram(getKey(), FBDMode.instance.getDiagramType());

        // okay, now actually perform the activation.
        super.activate();

        for (SimulationObject obj : allObjects()) {
            obj.setSelectable(true);
        }

        // make sure that the state is okay with the underlying diagram.
        uncheckSingularEquations(); // this marks singular equations as unchecked
        validateState(); // this checks to make sure no underlying stuff has changed

        // mark the state as changed so that the UI updates.
        stateChanged();

        if (!getCurrentState().isLocked()) {
            StaticsApplication.getApp().setDefaultUIFeedback(
                    java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_welcome"));
        } else {
            StaticsApplication.getApp().setDefaultUIFeedback("You have solved all the equations here!");
        }

        StaticsApplication.getApp().resetUIFeedback();

        solvabilityCheck();
    }

    /**
     * This checks to see if the diagram is solvable, and will show the TooManyUnknownsPopup if not.
     * This does not redirect or stop the user, though.
     */
    protected void solvabilityCheck() {

        // check to see if the diagram is solvable, and if not, post the TooManyUnknownsPopup
        int unknowns = 0;
        for (SimulationObject simulationObject : allObjects()) {

            // each unknown load is an unknown
            if (simulationObject instanceof Load) {
                Load load = (Load) simulationObject;
                if (!load.getAnchoredVector().isKnown()) {
                    unknowns++;
                }
            }

            // each unsolved contact point is an unknown
            if (simulationObject instanceof ContactPoint) {
                ContactPoint contact = (ContactPoint) simulationObject;
                if (!contact.getFrictionCoefficient().getQuantity().isKnown()) {
                    unknowns++;
                }
            }
        }
        int maxUnknowns = 3;

        if (fbd.getBodySubset().getBodies().size() == 1
                && fbd.getBodySubset().getBodies().toArray()[0] instanceof PointBody) {
            maxUnknowns = 2;
        }

        // contact points yield friction equations
        for (SimulationObject simulationObject : allObjects()) {
            if (simulationObject instanceof ContactPoint) {
                maxUnknowns++;
            }
        }

        if (unknowns > maxUnknowns) {
            TooManyUnknownsPopup popup = new TooManyUnknownsPopup();
            popup.popup(0, 0, true);
            popup.center();
        }
    }

    /**
     * This makes sure that singular equations are unchecked. 
     * Singular equations are ones that are of the form 0 = 0, or .001 = 0.
     */
    private void uncheckSingularEquations() {

        boolean changed = false;

        EquationState.Builder builder = new EquationState.Builder(getCurrentState());
        for (EquationMathState state : getCurrentState().getEquationStates().values()) {

            if (state instanceof ArbitraryEquationMathState) {
                // pass on this
            } else if (state instanceof TermEquationMathState) {
                TermEquationMathState termState = (TermEquationMathState) state;
                TermEquationMathState.Builder termBuilder = new TermEquationMathState.Builder(termState);

                boolean singular = true;
                for (AnchoredVector anchoredVector : termState.getTerms().keySet()) {
                    if (anchoredVector.isSymbol()) {
                        singular = false;
                    }
                }

                if (singular) {
                    termBuilder.setLocked(false);
                    builder.putEquationState(termBuilder.build());
                    changed = true;
                }
            }
        }

        if (changed) {
            // build the result.
            pushState(builder.build());

            // clear the states
            clearStateStack();
        }
    }

    /**
     * This method is called when the equation mode is loaded, and makes sure that
     * the loads described in the state are present in the underlying fbd. This is important
     * because when changes are made to the fbd externally, some forces might move, and others might disappear.
     */
    protected void validateState() {

        List<AnchoredVector> fbdLoads = fbd.getCurrentState().getAddedLoads();

        EquationState.Builder builder = new EquationState.Builder(getCurrentState());
        for (EquationMathState state : getCurrentState().getEquationStates().values()) {

            if (state instanceof ArbitraryEquationMathState) {
                //TODO write this in!
            } else if (state instanceof TermEquationMathState) {
                TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState) state);

                // get a list of everything present in the math
                List<AnchoredVector> toRemove = new ArrayList<AnchoredVector>(mathBuilder.getTerms().keySet());
                toRemove.removeAll(fbdLoads); // take out everything in the fbdLoads
                for (AnchoredVector load : toRemove) {
                    // what is left is stuff that does not belong.
                    mathBuilder.getTerms().remove(load);
                }

                // update the state in the builder.
                builder.putEquationState(mathBuilder.build());
            } else if (state instanceof Moment3DEquationMathState) {
                //TODO handle this case
                Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState) state);

                List<AnchoredVector> toRemove = new ArrayList<AnchoredVector>(mathBuilder.getTerms().keySet());
                toRemove.removeAll(fbdLoads); // take out everything in the fbdLoads
                for (AnchoredVector load : toRemove) {
                    // what is left is stuff that does not belong.
                    mathBuilder.getTerms().remove(load);
                }

            } else {
                throw new IllegalArgumentException("Unknown equation math state type! " + state);
            }
        }
        // build the result.
        pushState(builder.build());

        // clear the states
        clearStateStack();
    }
    private SelectionFilter selector = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load;
        }
    };

    /**
     * 
     * @return 
     */
    @Override
    public SelectionFilter getSelectionFilter() {
        return selector;
    }

    /**
     * This should be called by EquationModePanel when an equation is solved.
     */
    public void equationSolved() {
        clearStateStack();
    }

    /**
     * 
     */
    @Override
    public void render(Renderer r) {
        super.render(r);

        if (showingCurve) {
            CurveUtil.renderCurve(r, ColorRGBA.blue, curvePoints);
        }

        for (EquationMathState equationMathState : getCurrentState().getEquationStates().values()) {
            if (equationMathState instanceof TermEquationMathState) {
                TermEquationMathState termState = (TermEquationMathState) equationMathState;
                if (termState.getMomentPoint() != null) {
                    CurveUtil.renderCircle(r, ColorRGBA.blue, termState.getMomentPoint().getTranslation(),
                            DisplayConstants.getInstance().getMomentCircleRadius(), r.getCamera().getDirection());
                }
            } else if (equationMathState instanceof Moment3DEquationMathState) {
                Moment3DEquationMathState termState = (Moment3DEquationMathState) equationMathState;
                if (termState.getMomentPoint() != null) {
                    CurveUtil.renderCircle(r, ColorRGBA.blue, termState.getMomentPoint().getTranslation(),
                            DisplayConstants.getInstance().getMomentCircleRadius(), r.getCamera().getDirection());
                }

            }
        }
//        if (getMomentPoint() != null) {
//            CurveUtil.renderCircle(r, ColorRGBA.blue, getMomentPoint().getTranslation(),
//                    DisplayConstants.getInstance().getMomentCircleRadius(), r.getCamera().getDirection());
//        }
    }
    private SimulationObject currentHover;

    /**
     * 
     * @param v 
     */
    public void highlightVector(final AnchoredVector v) {

        // handle visual highlighting
        if (currentHover != null) {
            currentHover.setDisplayHighlight(false);
        }
        Load load;
        if (v == null) {
            load = null;
        } else {
            load = getLoad(v);
        }

        if (load != null) {
            load.setDisplayHighlight(true);


        }
        currentHover = load;

        //sumBar.highlightVector(obj);
        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        EquationBar activeEquation = eqPanel.getActiveEquation();
        if (activeEquation.getMath() instanceof EquationMathMoments) {
            //if (activeEquation.getMath().getTerm(v) != null || v == null) {
            //showMomentArm(load);
            //}
        }
        //showCurve(vectorMap.get(v), activeEquation.getLineAnchor(v));
    }
    private VectorObject momentArm;
    private Load momentArmTarget;

    /**
     * This method is called when the underlying diagram has changed.
     * The function of this method is to unlock all the underlying equations.
     */
    void resetSolve() {
        EquationState.Builder builder = new EquationState.Builder(getCurrentState());
        builder.setLocked(false);
        for (EquationMathState state : builder.getEquationStates().values()) {
            if (state instanceof ArbitraryEquationMathState) {
                //TODO write this in!
            } else if (state instanceof TermEquationMathState) {
                // make sure the sub-states are all unlocked.
                TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState) state);
                mathBuilder.setLocked(false);
                builder.putEquationState(mathBuilder.build());
            } else {
                throw new IllegalArgumentException("Unknown equation math state type! " + state);
            }
        }
        pushState(builder.build());

        // clear all of the states
        clearStateStack();
    }

    /**
     * 
     * @param target 
     */
    private void showMomentArm(Load target) {
//
//        if (target instanceof Moment) {
//            target = null;
//        }
//
//        // we may get hovers onto the momentarm itself...
//        if (momentArmTarget == target || target == momentArm) {
//            return;
//        }
//
//        if (momentArm != null) {
//            //removeUserObject(momentArm);
//            momentArm = null;
//            momentArmTarget = null;
//        }
//
//        if (target == null) {
//            return;
//        }
//
//        //if(!((EquationMathMoments) sumBar.getMath()).getObservationPointSet())
//        if (getMomentPoint() == null) {
//            return;
//        }
//
//        //Vector3f observationPoint = ((EquationMathMoments) sumBar.getMath()).getObservationPoint();
//        //Vector3f observationPointPos = this.momentPoint.getTranslation();
//        Point targetPoint = target.getAnchor();
//
//        // have a direction vector pointing from the observation point to the target point
//        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPointPos).mult(-1);
//        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPointPos).mult(-1/StaticsApplication.getApp().getDrawScale());
//        Vector3bd armDirection = targetPoint.getPosition().subtract(getMomentPoint().getPosition());
//        AnchoredVector momentArmVector = new AnchoredVector(targetPoint, new Vector(Unit.distance, armDirection, new BigDecimal(armDirection.length())));
//        momentArm = new VectorObject(momentArmVector);
//        momentArmTarget = target;
//
//        ArrowRepresentation rep = new ArrowRepresentation(momentArm, false);
//        rep.setAmbient(ColorRGBA.yellow);
//        rep.setDiffuse(ColorRGBA.yellow);
//        momentArm.addRepresentation(rep);
//        momentArm.setSelectable(false);
        //addUserObject(momentArm);
    }
    private boolean showingCurve = false;
    private Vector3f curvePoints[] = new Vector3f[3];

    /**
     * 
     */
    private void showCurve(Load obj, Vector2f pos) {
        if (obj == null || pos == null) {
            showingCurve = false;

            return;
        }

        // yet another hack in finding the vector center here....
        curvePoints[0] = obj.getDisplayCenter().add(obj.getTranslation()).mult(.5f);
        curvePoints[2] = StaticsApplication.getApp().getCamera().getWorldCoordinates(pos, .1f);
        curvePoints[1] = new Vector3f(curvePoints[2]);
        curvePoints[1].y += .5f;
        showingCurve = true;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Mode getMode() {
        return EquationMode.instance;
    }

    /**
     * 
     * @return 
     * 
     */
    @Override
    protected EquationState createInitialState() {
        Builder builder = new EquationState.Builder();
        builder.getEquationStates().put("F[x]", (new TermEquationMathState.Builder("F[x]", false, null, Vector3bd.UNIT_X).build()));
        builder.getEquationStates().put("F[y]", (new TermEquationMathState.Builder("F[y]", false, null, Vector3bd.UNIT_Y).build()));
        builder.getEquationStates().put("M[p]", (new TermEquationMathState.Builder("M[p]", true, null, Vector3bd.UNIT_Z).build()));

        checkWorksheet();
        return builder.build();
    }

    /**
     * Creates the worksheet for this equation diagram if it does not exist
     * This should be called in createInitialState.
     */
    protected void checkWorksheet() {

        if (worksheet == null) {
            worksheet = new Worksheet(this);
        }
    }

    @Override
    public void completed() {
    }

    /**
     * 
     * @param obj
     * @param values 
     */
    private void solveConnector(SimulationObject obj, Map<Quantity, Float> values) {
        Connector connector = (Connector) obj;

        // do not solve the connector if it is already solved.
        if (connector.isSolved()) {
            return;
        }

        // do not solve the connector if it is internal
        if (!(allBodies().contains(connector.getBody1()) ^ allBodies().contains(connector.getBody2()))) {
            return;
        }

        Point point = connector.getAnchor();
        List<Vector> reactions = new ArrayList<Vector>();

        // it's possible that the reactions are not meant for this particular connector
        // go through all of our solved quantities and see if we can get some matches.
        for (Vector reaction : connector.getReactions()) {
            for (Quantity q : values.keySet()) {
                AnchoredVector load = getAnchoredVectorFromSymbol(q.getSymbolName());
                //AnchoredVector load = Exercise.getExercise().getSymbolManager().getLoad
                if (load != null && load.getAnchor() == point) {
                    // now test if the direction is okay
                    if (load.getVectorValue().equals(reaction.getVectorValue()) || load.getVectorValue().equals(reaction.getVectorValue().negate())) {

                        // here we need to put a solved reaction into the reactions list
                        // first we need to make a clone of the vector, and then assign
                        // the solved value to it.
                        AnchoredVector solvedReaction = new AnchoredVector(load);
                        BigDecimal value = new BigDecimal(Math.abs(values.get(q)));
                        solvedReaction.getVector().setDiagramValue(value);
                        solvedReaction.setKnown(true);
                        reactions.add(solvedReaction.getVector());
                    }
                }
            }
        }

        if (reactions.isEmpty() || reactions.size() != connector.getReactions().size()) {
            return;
        }

        // hopefully this should be accurate...
        Body solveBody = null;

        for (Body body : allBodies()) {
            if (body.getAttachedObjects().contains(connector)) {
                solveBody = body;
            }
        }

        // we want to say that we have solved this joint from the perspective
        // of the current body in question.
        connector.solveReaction(solveBody, reactions);
    }

    @Override
    public String getDescriptionText() {
        return "Solve equilibrium equations: " + getKey();
    }
}
