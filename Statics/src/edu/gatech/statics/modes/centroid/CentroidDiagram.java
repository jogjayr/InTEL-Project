/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.centroid.CentroidState.Builder;
import edu.gatech.statics.modes.centroid.actions.*;
import edu.gatech.statics.modes.centroid.ui.CentroidModePanel;
import edu.gatech.statics.objects.CentroidBodyMarker;
import edu.gatech.statics.objects.CentroidPartMarker;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.SelectionFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sets up the main Diagram class for centroids. Also handles graying and displaying of the floating white boxes.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidDiagram extends Diagram<CentroidState> {

    private static final float TOLERANCE = .01f;
//    private CentroidPartObject cpObj;
    private CentroidBody body;
    private CentroidPartObject currentlySelected;

    public CentroidDiagram(CentroidBody body) {
        super(body);
        this.body = body;

        // pick out the actual centroid part object from the schematic
//        for (SimulationObject obj : Exercise.getExercise().getSchematic().allObjects()) {
//            if (obj instanceof CentroidPartObject) {
//                CentroidPartObject cpObjTest = (CentroidPartObject) obj;
//                if (cpObjTest.getCentroidPart().equals(body)) {
//                    cpObj = cpObjTest;
//                }
//            }
//        }
    }

    @Override
    protected void stateChanged() {
        super.stateChanged();

        //if we remove this it leads to major headaches
        //CentroidPartOBject.getState() is used by the knowns window AND the floating boxes
        //so that they know what information to display.
        //Honestly I don't know how I feel about what should be a UI class containing anything
        //but a reference to it's state but there it is.
        //Actually, this is very bad since this is a mutable location. Ask Calvin about it.
        for (CentroidPartObject cpo : body.getParts()) {
            cpo.setState(getCurrentState().getMyPartState(cpo.getCentroidPart().getPartName()));
        }
    }

    public CentroidBody getBody() {
        return body;
    }
    private SelectionFilter selector = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof CentroidPartObject;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        //can select CentroidPartObjects. not the body
        return selector;
    }

    @Override
    public void onHover(SimulationObject obj) {
        super.onHover(obj);

        for (SimulationObject simulationObject : allObjects()) {
            if (simulationObject instanceof CentroidPartObject) {
                simulationObject.setDisplayHighlight(false);
            }
        }

        if (obj != null) {
            obj.setDisplayHighlight(true);
        }
    }

    @Override
    public void onClick(SimulationObject obj) {
        //somethind like that. also needs to be selectable = true
        //CentroidModePanel modePanel = (CentroidModePanel) InterfaceRoot.getInstance().getModePanel(CentroiMode.instance.getModeName());
        //modePanel.onSelect((CentroidPartObject) obj);
        //System.out.println("Oi!!! Clicked on: "+obj);

        int totalSolved = 0;

        // no effect if we click on the same thing twice
        if (currentlySelected == obj) {
            return;
        }

        // if we are here, we know that currentlySelected != obj
        // de-highlight the last selection
        if (currentlySelected != null) {
            currentlySelected.setDisplaySelected(false);
            this.activate();
        }

        // switch the current selection
        currentlySelected = (CentroidPartObject) obj;

        if (currentlySelected != null) {
            currentlySelected.setDisplaySelected(true);
        }

        //if the student has clicked on the body and all the parts are solved then display the interface to solve the centroid body
        if (((CentroidState) getCurrentState()).allPartsSolved(this)) {
            CentroidModePanel modePanel = (CentroidModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
            //this.setLocked();
            modePanel.displayBodySolver();
        } else {
            // update the UI
            CentroidModePanel modePanel = (CentroidModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
            modePanel.updateSelection(currentlySelected);
        }
    }

    /**
     * Checks to see if the currently selected part's desired values match the ones entered by the student.
     * @param areaValue
     * @param xValue
     * @param yValue
     * @return
     */
    public boolean checkPart(String areaValue, String xValue, String yValue) {
        BigDecimal userArea = Parser.evaluate(areaValue);
        BigDecimal userXPosition = Parser.evaluate(xValue);
        BigDecimal userYPosition = Parser.evaluate(yValue);

        if (userArea == null || userXPosition == null || userYPosition == null) {
            return false;
        }

        CentroidPart part = getCentroidPart();
        BigDecimal desiredArea = part.getSurfaceArea();
        BigDecimal desiredX = part.getCentroid().getX();
        BigDecimal desiredY = part.getCentroid().getY();

        // debugging messages
        System.out.println("user area: \"" + areaValue + "\" " + userArea);
        System.out.println("user xpos: \"" + xValue + "\" " + userXPosition);
        System.out.println("user ypos: \"" + yValue + "\" " + userYPosition);
        System.out.println("area: " + desiredArea);
        System.out.println("xpos: " + desiredX);
        System.out.println("ypos: " + desiredY);

        boolean success = true;

        float areaTolerance = TOLERANCE * Math.max(1, Math.abs(desiredArea.floatValue()));
        float xPositionTolerance = TOLERANCE * Math.max(1, Math.abs(desiredX.floatValue()));
        float yPositionTolerance = TOLERANCE * Math.max(1, Math.abs(desiredY.floatValue()));

        success &= Math.abs(desiredArea.subtract(userArea).floatValue()) < areaTolerance;
        success &= Math.abs(desiredX.subtract(userXPosition).floatValue()) < xPositionTolerance;
        success &= Math.abs(desiredY.subtract(userYPosition).floatValue()) < yPositionTolerance;

        System.out.println("success: " + success);

        return success;
    }

    /**
     * Checks to see if the centroid body matches the values entered by the user.
     * @param areaValue
     * @param xValue
     * @param yValue
     * @return
     */
    public boolean checkBody(String areaValue, String xValue, String yValue) {
        BigDecimal userArea = Parser.evaluate(areaValue);
        BigDecimal userXPosition = Parser.evaluate(xValue);
        BigDecimal userYPosition = Parser.evaluate(yValue);

        if (userArea == null || userXPosition == null || userYPosition == null) {
            return false;
        }

        BigDecimal desiredArea = new BigDecimal("0.0");
        BigDecimal desiredX = new BigDecimal(body.getCenterOfMass().getPosition().getX() + "");
        BigDecimal desiredY = new BigDecimal(body.getCenterOfMass().getPosition().getY() + "");

        for (CentroidPartObject cpo : body.getParts()) {
            desiredArea = desiredArea.add(cpo.getCentroidPart().getSurfaceArea());
        }

        // debugging messages
        System.out.println("user area: \"" + areaValue + "\" " + userArea);
        System.out.println("user xpos: \"" + xValue + "\" " + userXPosition);
        System.out.println("user ypos: \"" + yValue + "\" " + userYPosition);
        System.out.println("area: " + desiredArea);
        System.out.println("xpos: " + desiredX);
        System.out.println("ypos: " + desiredY);

        boolean success = true;

        float areaTolerance = TOLERANCE * Math.max(1, Math.abs(desiredArea.floatValue()));
        float xPositionTolerance = TOLERANCE * Math.max(1, Math.abs(desiredX.floatValue()));
        float yPositionTolerance = TOLERANCE * Math.max(1, Math.abs(desiredY.floatValue()));

        success &= Math.abs(desiredArea.subtract(userArea).floatValue()) < areaTolerance;
        success &= Math.abs(desiredX.subtract(userXPosition).floatValue()) < xPositionTolerance;
        success &= Math.abs(desiredY.subtract(userYPosition).floatValue()) < yPositionTolerance;

        System.out.println("success: " + success);

        return success;
    }

    public void setArea(String text, boolean allSolved) {
        SetAreaValue action = new SetAreaValue(text, currentlySelected, allSolved);
        performAction(action);
    }

    public void setXPosition(String text, boolean allSolved) {
        SetXPositionValue action = new SetXPositionValue(text, currentlySelected, allSolved);
        performAction(action);
    }

    public void setYPosition(String text, boolean allSolved) {
        SetYPositionValue action = new SetYPositionValue(text, currentlySelected, allSolved);
        performAction(action);
    }

    @Override
    public void activate() {
        super.activate();
        updateCentroid();

        if (isSolved()) {
            StaticsApplication.getApp().setUIFeedback("You have solved for the values for the centroid.");
        } else {
            StaticsApplication.getApp().setUIFeedback("Select a part of the body to determine its centroid.");
        }
        //StaticsApplication.getApp().resetUIFeedback();

        //gray out the model when in the centroid diagram
        for (Representation r : body.getRepresentation(RepresentationLayer.modelBodies)) {
            r.setGrayColors(new ColorRGBA(.2f, .2f, .2f, 1f), new ColorRGBA(.65f, .65f, .65f, 1f));
        }
        body.setDisplayGrayed(true);
//        for (Representation rep : body.allRepresentations()) {
//            if (!(rep instanceof ModelRepresentation)) {
//                continue;
//            }
//            ModelRepresentation mRep = (ModelRepresentation) rep;
//            mRep.setDisplayGrayed(true);
//        }


        // activate the mimic representations
//        for (SimulationObject obj : getBaseObjects()) {
//            if (obj instanceof CentroidPartObject) {
//                setCentroidPartActive((CentroidPartObject) obj, true);
//            }
//        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        //TODO: ADD THE SPECIFIC CODE THAT DECONSTRUCTS THE DIAGRAM

        // deactivate the mimic representations
//        for (SimulationObject obj : getBaseObjects()) {
//            if (obj instanceof CentroidPartObject) {
//                setCentroidPartActive((CentroidPartObject) obj, false);
//            }
//        }
        body.setDisplayGrayed(false);
//        for (Representation rep : body.allRepresentations()) {
//            if (!(rep instanceof ModelRepresentation)) {
//                continue;
//            }
//            ModelRepresentation mRep = (ModelRepresentation) rep;
//            mRep.setDisplayGrayed(false);
//        }

    }

    public void setSolved() {
        //updateResultant();
        Builder builder = getCurrentState().getBuilder();
        builder.setLocked(true);
        pushState(builder.build());
        clearStateStack();
        //needed to make the overall centroid position appear
        //still might not be enough, need to look into this
        Point pt = new Point(body.getName() + "Center",
                Unit.distance.getDisplayScale().multiply(body.getCenterOfMass().getPosition().getX()).toString(),
                Unit.distance.getDisplayScale().multiply(body.getCenterOfMass().getPosition().getY()).toString(),
                Unit.distance.getDisplayScale().multiply(body.getCenterOfMass().getPosition().getZ()).toString());
        CentroidBodyMarker cbm = new CentroidBodyMarker(body.getName() + " Marker", pt, body);
        cbm.createDefaultSchematicRepresentation();
        body.addObject(cbm);
        getSchematic().add(cbm);

    }

    public boolean isSolved() {
        return getCurrentState().isLocked();
    }

    protected CentroidPart getCentroidPart() {
        return currentlySelected.getCentroidPart();//getKey();
    }

    @Override
    public String getDescriptionText() {
        return "Determine the centroid for the part: " + getKey();
    }

    @Override
    protected CentroidState createInitialState() {
        CentroidState.Builder builder = new CentroidState.Builder();
        return builder.build();
    }

    @Override
    public void completed() {
        //nothing apparently
    }

    @Override
    public Mode getMode() {
        return CentroidMode.instance;
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        //objects.add(cpObj);
        objects.add(body);
        objects.addAll(body.getParts());

        for (CentroidPartObject part : body.getParts()) {

            for (SimulationObject obj : this.allObjects()) {
                if (obj instanceof CentroidPartMarker && ((CentroidPartMarker) obj).getName().equals(part.getName() + "Marker")) {
                    break;
                }
            }

            //displays those floating white boxes that has the name, area, x, and y values of each part
            BigDecimal displayScale = Unit.distance.getDisplayScale();
            //if (part.getState() != null && part.getState().isLocked() == true) {

            Vector3f markerOffset = part.getMarkerOffset();
            Point pt = new Point(part.getName() + "Center",
                    displayScale.multiply(part.getCentroidPart().getCentroid().getX()).add(new BigDecimal(markerOffset.x)).toString(),
                    displayScale.multiply(part.getCentroidPart().getCentroid().getY()).add(new BigDecimal(markerOffset.y)).toString(),
                    displayScale.multiply(part.getCentroidPart().getCentroid().getZ()).add(new BigDecimal(markerOffset.z)).toString());
            CentroidPartMarker cpm = new CentroidPartMarker(part.getName() + "Marker", pt, part);
            cpm.createDefaultSchematicRepresentation();
            objects.add(cpm);
            //}
        }

//        for (SimulationObject measurement : Exercise.getExercise().getSchematic().allObjects()) {
        for (CentroidPartObject cpo : body.getParts()) {
            for (Measurement m : cpo.getMeasurements()) {
                objects.add(m);
            }
        }

        // add the centroid body marker, so the dot shows up in the centroid diagram.
        for (SimulationObject obj : body.getAttachedObjects()) {
            if (obj instanceof CentroidBodyMarker) {
                objects.add(obj);
            }
        }

        return objects;
    }

    public void updateCentroid() {
//        for (SimulationObject cpo : getBaseObjects()) {
//            if (cpo instanceof CentroidPartObject) {
//                //System.out.println("#############"+((CentroidPartObject)cpo).getName());
//                ((CentroidPartObject)cpo).setSelectable(true);
//            }
//        }
        //TODO FILL THIS OUT
        //Needs to update so that the centroid is displayed properly once the user solves,
        //but this might not be needed and we can do it like friction problems for mu
    }
//    private void setCentroidPartActive(CentroidPartObject part, boolean active) {
//        for (Representation representation : part.allRepresentations()) {
//            if (representation instanceof MimicRepresentation) {
//                MimicRepresentation mimic = (MimicRepresentation) representation;
//                if (active) {
//                    mimic.activate();
//                } else {
//                    mimic.deactivate();
//                }
//            }
//        }
//    }
}
