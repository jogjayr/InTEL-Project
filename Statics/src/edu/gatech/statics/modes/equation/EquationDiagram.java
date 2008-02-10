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
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.VectorObject;
import edu.gatech.statics.objects.representations.CurveUtil;
//import edu.gatech.statics.util.SelectableFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationDiagram extends SubDiagram {
    
    // 2d logic for time being
    EquationMath sumFx;
    EquationMath sumFy;
    EquationMathMoments sumMp;
    
    //private SumBar sumBar;
    //void setSumBar(SumBar sumBar) {this.sumBar = sumBar;}
    
    /** Creates a new instance of EquationWorld */
    public EquationDiagram(BodySubset bodies) {
        super(bodies);
        
        sumFx = new EquationMath(this);
        sumFy = new EquationMath(this);
        sumMp = new EquationMathMoments(this);
        
        sumFx.setObservationDirection(Vector3f.UNIT_X);
        sumFy.setObservationDirection(Vector3f.UNIT_Y);
        sumMp.setObservationDirection(Vector3f.UNIT_Z);
        
        //enableSelectMultipleDefault(false);
        //enableManipulatorsOnSelectDefault(false);
        
        /*for(SimulationObject obj : parentWorld.allObjects()) {
            if(    !obj.isDisplayGrayed() ||
                    obj instanceof Measurement) {
                add(obj);
            }
        }*/
    }
    
    void performSolve(Map<VectorObject, Float> values) {
        
        // go through the vectors, and make sure everything is in order:
        // give the vectors the new solved values
        for(SimulationObject obj : allObjects()) {
            if(obj instanceof VectorObject) {
                VectorObject vObj = (VectorObject)obj;
                Vector v = vObj.getVector();
                if(v.isSymbol() && !v.isKnown()) {
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
        for(SimulationObject obj : allObjects()) {
            if(obj instanceof Joint) {
                Joint joint = (Joint)obj;
                if(joint.isSolved())
                    continue;
                
                Point point = joint.getPoint();
                List<Vector> reactions = new ArrayList<Vector>();
                for(VectorObject v : values.keySet())
                    if(v.getAnchor() == point)
                        reactions.add(v.getVector());
                
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
        //sumBar.setMomentCenter(point);
    }

    @Override
    public void onClick(SimulationObject obj) {
        super.onClick(obj);
        
        if(     StaticsApplication.getApp().getCurrentTool() != null &&
                StaticsApplication.getApp().getCurrentTool().isActive())
            return; // do not select points if we have a tool active
        
        /*if(obj != null && obj instanceof VectorObject) {
            if(sumBar == null)
                return;
            
            VectorObject target = (VectorObject) obj;
            sumBar.addTerm(target);
        }*/
    }    

    @Override
    public void onHover(SimulationObject obj) {
        super.onHover(obj);
        
        //if(sumBar != null && (obj instanceof VectorObject || obj == null))
        //    highlightVector((VectorObject)obj);
        // draw line from vector to target?
    }
    
    @Override
    public void activate() {
        super.activate();
        for(SimulationObject obj : allObjects()) {
            obj.setSelectable(true);
        }
        
        StaticsApplication.getApp().setDefaultAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("equation_welcome"));
        StaticsApplication.getApp().resetAdvice();
    }

    /*
    @Override
    public void setSelectableFilterDefault() {
        setSelectableFilter(new SelectableFilter() {
            public boolean canSelect(SimulationObject obj) {
                return  obj instanceof Force ||
                        obj instanceof Moment;
            }
        });
    }*/

    @Override
    public void render(Renderer r) {
        super.render(r);
        
        if(showingCurve) {
            CurveUtil.renderCurve(r, ColorRGBA.blue, curvePoints);
        }
        
        /*if(     sumBar != null &&
                sumBar.getMath() == sumMp &&
                sumMp.getObservationPointSet()) {
            CurveUtil.renderCircle(r, ColorRGBA.blue, sumMp.getObservationPoint(), 2, r.getCamera().getDirection());
        }*/
    }
    
    void highlightVector(final VectorObject obj) {
        /*sumBar.highlightVector(obj);
        
        if(sumBar.getMath() instanceof EquationMathMoments) {
            if(sumBar.getMath().getTerm(obj) != null || obj == null)
                showMomentArm(obj);
        }
        
        showCurve(obj, sumBar.getLineAnchor(obj));*/
    }

    private VectorObject momentArm;
    private VectorObject momentArmTarget;
    private void showMomentArm(VectorObject target) {
        
        /*
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
        Vector3f armDirection = targetPoint.getTranslation().subtract(observationPoint).mult(-1);
        //Vector3f armDirection = targetPoint.getTranslation().subtract(observationPoint).mult(-1/StaticsApplication.getApp().getWorldScale());
        momentArm = new VectorObject(targetPoint, new Vector(Unit.distance, armDirection));
        momentArmTarget = target;
        
        ArrowRepresentation rep = new ArrowRepresentation(momentArm, false);
        rep.setAmbient(ColorRGBA.yellow);
        rep.setDiffuse(ColorRGBA.yellow);
        momentArm.addRepresentation(rep);
        momentArm.setSelectable(false);
        add(momentArm);*/
    }

    private boolean showingCurve = false;
    private Vector3f curvePoints[] = new Vector3f[3];
    private void showCurve(VectorObject obj, Vector2f pos) {
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
