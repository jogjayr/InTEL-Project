/*
 * EquationWorld.java
 *
 * Created on July 19, 2007, 3:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.FBDWorld;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.Vector;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import edu.gatech.statics.objects.representations.CurveUtil;
import edu.gatech.statics.util.SelectableFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationWorld extends World {
    
    // 2d logic for time being
    EquationMath sumFx;
    EquationMath sumFy;
    EquationMathMoments sumMp;
    
    private SumBar sumBar;
    void setSumBar(SumBar sumBar) {this.sumBar = sumBar;}
    
    private World parentWorld;
    public World getParentWorld() {return parentWorld;}
    
    /** Creates a new instance of EquationWorld */
    public EquationWorld(FBDWorld world) {
        this.parentWorld = world;
        
        sumFx = new EquationMath(this);
        sumFy = new EquationMath(this);
        sumMp = new EquationMathMoments(this);
        
        sumFx.setObservationDirection(Vector3f.UNIT_X);
        sumFy.setObservationDirection(Vector3f.UNIT_Y);
        sumMp.setObservationDirection(Vector3f.UNIT_Z);
        
        enableSelectMultipleDefault(false);
        enableManipulatorsOnSelectDefault(false);
        
        /*for(Body body : parentWorld.allBodies())
            if(!body.isDisplayGrayed()) {
                add(body);
                for(SimulationObject obj : body.getAttachedObjects())
                    add(obj);
            }
        
        for(SimulationObject obj : parentWorld.allObjects()) {
            if(obj instanceof Measurement)
                add(obj);
        }*/
        
        for(SimulationObject obj : parentWorld.allObjects()) {
            if(    !obj.isDisplayGrayed() ||
                    obj instanceof Measurement) {
                add(obj);
            }
        }
    }
    
    void performSolve(Map<Vector, Float> values) {
        
        // go through the vectors, and make sure everything is in order:
        // give the vectors the new solved values
        for(SimulationObject obj : allObjects()) {
            if(obj instanceof Vector) {
                Vector v = (Vector)obj;
                if(v.isSymbol() && !v.isSolved()) {
                    // v is a symbolic force, but is not yet solved.
                    v.setSolved(true);
                    float value = values.get(v);
                    v.setValue(v.getValue().mult( value ));
                }
            }
        }
        
        // go through the joints, and mark the joints as solved,
        // assigning to them the solved values as having the updated vectors.
        for(SimulationObject obj : allObjects()) {
            if(obj instanceof Joint) {
                Joint joint = (Joint)obj;
                if(joint.isSolved())
                    continue;
                
                Point point = joint.getPoint();
                List<Vector> reactions = new ArrayList<Vector>();
                for(Vector v : values.keySet())
                    if(v.getAnchor() == point)
                        reactions.add(v);
                
                // hopefully this should be accurate...
                Body solveBody = null;
                for(Body body : allBodies())
                    if(body.getAttachedObjects().contains(point))
                        solveBody = body;
                
                if(reactions.size() > 0) {
                    joint.solveReaction(solveBody, reactions);
                }
            }
        }
    }

    void setMomentPoint(Point point) {
        sumMp.setObservationPoint(point.getTranslation());
        sumBar.setMomentCenter(point);
    }

    public void click(SimulationObject obj) {
        super.click(obj);
        
        if(     StaticsApplication.getApp().getCurrentTool() != null &&
                StaticsApplication.getApp().getCurrentTool().isActive())
            return; // do not select points if we have a tool active
        
        if(obj != null && obj instanceof Vector) {
            if(sumBar == null)
                return;
            
            Vector target = (Vector) obj;
            sumBar.addTerm(target);
        }
    }    

    public void hover(SimulationObject obj) {
        super.hover(obj);
        
        if(sumBar != null && (obj instanceof Vector || obj == null))
            highlightVector((Vector)obj);
        // draw line from vector to target?
    }
    
    public void activate() {
        for(SimulationObject obj : allObjects()) {
            obj.setSelectable(true);
        }
        
        StaticsApplication.getApp().setDefaultAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_welcome"));
        StaticsApplication.getApp().resetAdvice();
    }

    public void setSelectableFilterDefault() {
        setSelectableFilter(new SelectableFilter() {
            public boolean canSelect(SimulationObject obj) {
                return  obj instanceof Force ||
                        obj instanceof Moment;
            }
        });
    }

    public void render(Renderer r) {
        super.render(r);
        
        if(showingCurve) {
            CurveUtil.renderCurve(r, ColorRGBA.blue, curvePoints);
        }
        
        if(     sumBar != null &&
                sumBar.getMath() == sumMp &&
                sumMp.getObservationPointSet()) {
            CurveUtil.renderCircle(r, ColorRGBA.blue, sumMp.getObservationPoint(), 2, r.getCamera().getDirection());
        }
    }
    
    void highlightVector(Vector obj) {
        sumBar.highlightVector(obj);
        
        if(sumBar.getMath() instanceof EquationMathMoments) {
            if(sumBar.getMath().getTerm(obj) != null || obj == null)
                showMomentArm(obj);
        }
        
        showCurve(obj, sumBar.getLineAnchor(obj));
    }

    private Vector momentArm;
    private Vector momentArmTarget;
    private void showMomentArm(Vector target) {
        
        if(target instanceof Moment)
            target = null;
        
        // we may get hovers onto the momentarm itself...
        if(momentArmTarget == target || target == momentArm)
            return;
        
        if(momentArm != null) {
            remove(momentArm);
            momentArm = null;
            momentArmTarget = null;
        }
        
        if(target == null)
            return;
        
        if(!((EquationMathMoments) sumBar.getMath()).getObservationPointSet())
            return;
        
        Vector3f observationPoint = ((EquationMathMoments) sumBar.getMath()).getObservationPoint();
        Point targetPoint = target.getAnchor();
        
        // have a direction vector pointing from the observation point to the target point
        Vector3f armDirection = targetPoint.getTranslation().subtract(observationPoint).mult(-1/StaticsApplication.getApp().getDrawScale());
        momentArm = new Vector(targetPoint, armDirection);
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
    private void showCurve(Vector obj, Vector2f pos) {
        if(obj == null || pos == null) {
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
