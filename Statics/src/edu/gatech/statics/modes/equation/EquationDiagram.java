/*
 * EquationWorld.java
 *
 * Created on July 19, 2007, 3:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

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
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationDiagram extends SubDiagram {

    private Worksheet worksheet;
    public Worksheet getWorksheet() {return worksheet;}
    
    private Point momentPoint;

    public void setMomentPoint(Point momentPoint) {
        this.momentPoint = momentPoint;
    }

    public Point getMomentPoint() {
        return momentPoint;
    }
    private Map<Vector, Load> vectorMap = new HashMap<Vector, Load>();

    public Load getLoad(Vector vector) {
        return vectorMap.get(vector);
    }

    /** Creates a new instance of EquationWorld */
    public EquationDiagram(BodySubset bodies) {
        super(bodies);

        FreeBodyDiagram fbd = StaticsApplication.getApp().getExercise().getFreeBodyDiagram(bodies);
        for (SimulationObject obj : fbd.allObjects()) {
            add(obj);
            if (obj instanceof Load) {
                Load load = (Load) obj;
                vectorMap.put(load.getVector(), load);
            }
        }
        
        // FIXME: This diagram automatically loads a 2D worksheet
        worksheet = new Worksheet2D(this);
    }

    void performSolve(Map<Load, Float> values) {

        // go through the vectors, and make sure everything is in order:
        // give the vectors the new solved values
        for (SimulationObject obj : allObjects()) {
            if (obj instanceof Load) {
                Load vObj = (Load) obj;
                Vector v = vObj.getVector();
                if (v.isSymbol() && !v.isKnown()) {
                    // v is a symbolic force, but is not yet solved.
                    float value = values.get(vObj);
                    //v.setValue(v.getValueNormalized().mult( value ));
                    v.setValue(value);
                    v.setKnown(true);
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
                for (Load v : values.keySet()) {
                    if (v.getAnchor() == point) {
                        reactions.add(v.getVector());
                    }
                }

                // hopefully this should be accurate...
                Body solveBody = null;
                for (Body body : allBodies()) {
                    if (body.getAttachedObjects().contains(point)) {
                        solveBody = body;
                    }
                }

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
        eqPanel.onClick( (Load) obj);
    }

    @Override
    public void onHover(SimulationObject obj) {
        super.onHover(obj);
        
        EquationModePanel eqPanel = (EquationModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        eqPanel.onHover( (Load) obj);
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

    /*if(     sumBar != null &&
    sumBar.getMath() == sumMp &&
    sumMp.getObservationPointSet()) {
    CurveUtil.renderCircle(r, ColorRGBA.blue, sumMp.getObservationPoint(), 2, r.getCamera().getDirection());
    }*/
    }

    public void highlightVector(final Vector v) {
    /*sumBar.highlightVector(obj);
    if(sumBar.getMath() instanceof EquationMathMoments) {
    if(sumBar.getMath().getTerm(obj) != null || obj == null)
    showMomentArm(obj);
    }
    showCurve(obj, sumBar.getLineAnchor(obj));*/
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
        Vector3f observationPointPos = this.momentPoint.getTranslation();
        Point targetPoint = target.getAnchor();

        // have a direction vector pointing from the observation point to the target point
        Vector3f armDirection = targetPoint.getTranslation().subtract(observationPointPos).mult(-1);
        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPoint).mult(-1/StaticsApplication.getApp().getWorldScale());
        momentArm = new VectorObject(targetPoint, new Vector(Unit.distance, armDirection));
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
}
