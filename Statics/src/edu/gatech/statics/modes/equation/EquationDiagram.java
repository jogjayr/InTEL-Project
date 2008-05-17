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
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.Worksheet2D;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.VectorObject;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import edu.gatech.statics.objects.representations.CurveUtil;
//import edu.gatech.statics.util.SelectableFilter;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.SelectionFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationDiagram extends SubDiagram {

    private Worksheet worksheet;

    public Worksheet getWorksheet() {
        return worksheet;
    }
    private Point momentPoint;

    public void setMomentPoint(Point momentPoint) {
        this.momentPoint = momentPoint;
        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.setMomentPoint(momentPoint);
    }

    public Point getMomentPoint() {
        return momentPoint;
    }
    
    // IMPORTANT NOTE HERE
    // if the vector is symbolic and has been reversed,
    // then it will no longer be *equal* to the value stored as a key
    // in vectorMap. So, we need to use another approach to pull out the vector.
    // This is why we do not use a hashmap
    public Load getLoad(Vector vector) {
        //return vectorMap.get(vector);
        if(vector == null)
            return null;
        for(SimulationObject obj : allObjects())
            if (obj instanceof Load) {
                Load load = (Load) obj;
                if(vector.equals(load.getVector()))
                    return load;
                
                if(     vector.isSymbol() && load.isSymbol() &&
                        vector.equalsSymbolic(load.getVector()) && 
                        vector.getSymbolName().equals(load.getSymbolName()))
                    return load;
                //vectorMap.put(load.getVector(), load);
            }
        return null;
    }

    /** Creates a new instance of EquationWorld */
    public EquationDiagram(BodySubset bodies) {
        super(bodies);

        FreeBodyDiagram fbd = StaticsApplication.getApp().getExercise().getFreeBodyDiagram(bodies);
        addAll(fbd.allObjects());
        
        // FIXME: This diagram automatically loads a 2D worksheet
        worksheet = new Worksheet2D(this);
    }
    
    public void performSolve(Map<Vector, Float> values) {
        
        // go through the vectors, and make sure everything is in order:
        // give the vectors the new solved values
        for (SimulationObject obj : allObjects()) {
            if (obj instanceof Load) {
                Load vObj = (Load) obj;
                Vector v = vObj.getVector();
                if (v.isSymbol() && !v.isKnown()) {
                    // v is a symbolic force, but is not yet solved.
                    float value = values.get(v);
                    //v.setValue(v.getValueNormalized().mult( value ));
                    vObj.setDiagramValue(new BigDecimal(value));
                    vObj.setKnown(true);
                }
            }
        }

        // go through the joints, and mark the joints as solved,
        // assigning to them the solved values as having the updated vectors.
        for (SimulationObject obj : allObjects()) {
            if (obj instanceof Joint) {
                Joint joint = (Joint) obj;
                if (joint.isSolved()) {
                    continue;
                }

                Point point = joint.getPoint();
                List<Vector> reactions = new ArrayList<Vector>();
                for (Vector v : values.keySet()) {
                    if (getLoad(v).getAnchor() == point) {
                        reactions.add(v);
                    }
                }

                // hopefully this should be accurate...
                Body solveBody = null;
                for (Body body : allBodies()) {
                    if (body.getAttachedObjects().contains(point)) {
                        solveBody = body;
                    }
                }

                // we want to say that we have solved this joint from the perspective
                // of the current body in question. 
                if (reactions.size() > 0) {
                    joint.solveReaction(solveBody, reactions);
                }
            }
        }
    }

    @Override
    public void onClick(SimulationObject obj) {
        super.onClick(obj);

        if (StaticsApplication.getApp().getCurrentTool() != null &&
                StaticsApplication.getApp().getCurrentTool().isActive()) {
            return;
        } // do not select vectors if we have a tool active

        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.onClick((Load) obj);
    }

    @Override
    public void onHover(SimulationObject obj) {
        super.onHover(obj);
        
        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.onHover((Load) obj);

        if(obj == null)
            highlightVector(null);
        else
            highlightVector(((Load) obj).getVector());
    }

    @Override
    public void activate() {
        super.activate();
        for (SimulationObject obj : allObjects()) {
            obj.setSelectable(true);
        }

        StaticsApplication.getApp().setDefaultAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_welcome"));
        StaticsApplication.getApp().resetAdvice();
    }
    private SelectionFilter selector = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Load;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return selector;
    }

    @Override
    public void render(Renderer r) {
        super.render(r);

        if (showingCurve) {
            CurveUtil.renderCurve(r, ColorRGBA.blue, curvePoints);
        }

        if (momentPoint != null) {
            CurveUtil.renderCircle(r, ColorRGBA.blue, momentPoint.getTranslation(), 2, r.getCamera().getDirection());
        }
    }
    
    private SimulationObject currentHover;

    public void highlightVector(final Vector v) {
        
        // handle visual highlighting
        if(currentHover != null)
            currentHover.setDisplayHighlight(false);
        
        Load load = getLoad(v);
        if(load != null)
            load.setDisplayHighlight(true);
        currentHover = load;
        
        //sumBar.highlightVector(obj);
        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        EquationBar activeEquation = eqPanel.getActiveEquation();
        if (activeEquation.getMath() instanceof EquationMathMoments) {
            if (activeEquation.getMath().getTerm(v) != null || v == null) {
                //showMomentArm(load);
            }
        }
        //showCurve(vectorMap.get(v), activeEquation.getLineAnchor(v));
    }
    private VectorObject momentArm;
    private Load momentArmTarget;

    private void showMomentArm(Load target) {

        if (target instanceof Moment) {
            target = null;
        }

        // we may get hovers onto the momentarm itself...
        if (momentArmTarget == target || target == momentArm) {
            return;
        }

        if (momentArm != null) {
            remove(momentArm);
            momentArm = null;
            momentArmTarget = null;
        }

        if (target == null) {
            return;
        }

        //if(!((EquationMathMoments) sumBar.getMath()).getObservationPointSet())
        if (momentPoint == null) {
            return;
        }

        //Vector3f observationPoint = ((EquationMathMoments) sumBar.getMath()).getObservationPoint();
        //Vector3f observationPointPos = this.momentPoint.getTranslation();
        Point targetPoint = target.getAnchor();

        // have a direction vector pointing from the observation point to the target point
        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPointPos).mult(-1);
        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPointPos).mult(-1/StaticsApplication.getApp().getDrawScale());
        Vector3bd armDirection = targetPoint.getPosition().subtract(this.momentPoint.getPosition());
        momentArm = new VectorObject(targetPoint, new Vector(Unit.distance, armDirection, new BigDecimal(armDirection.length())));
        momentArmTarget = target;

        ArrowRepresentation rep = new ArrowRepresentation(momentArm, false);
        rep.setAmbient(ColorRGBA.yellow);
        rep.setDiffuse(ColorRGBA.yellow);
        momentArm.addRepresentation(rep);
        momentArm.setSelectable(false);
        add(momentArm);
    }
    private boolean showingCurve = false;
    private Vector3f curvePoints[] = new Vector3f[3];

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

    @Override
    public Mode getMode() {
        return EquationMode.instance;
    }
}
