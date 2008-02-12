/*
 * DistanceRepresentation.java
 *
 * Created on July 17, 2007, 12:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.DistanceMeasurement;

/**
 *
 * @author Calvin Ashmore
 */
public class DistanceRepresentation extends Representation<DistanceMeasurement> {
    
    private float edgeWidth = .5f; // world units- should fix
    private float margin = 4f; // pixels
    private float arrowsize = 6f; // pixels
    
    private float offset;
    public float getOffset() {return offset;}
    public void setOffset(float offset) {this.offset = offset;}
    
    private LabelRepresentation label;
    protected Vector3f labelCenter = new Vector3f();
    
    boolean drawLeftBar = true, drawRightBar = true;
    //boolean drawLabel = true;
    
    public void drawBars(boolean left, boolean right) {
        drawLeftBar = left;
        drawRightBar = right;
    }
    
    private ColorRGBA color = ColorRGBA.black;
    public void setColor(ColorRGBA color) {this.color = color;}
    
    //public void drawLabel(boolean drawLabel) {this.drawLabel = drawLabel;}
    
    /** Creates a new instance of DistanceRepresentation */
    public DistanceRepresentation(DistanceMeasurement target) {
        super(target);
        setLayer(RepresentationLayer.measurement);
        
        
        worldPointDifference = new Vector3f();
        offsetDirection = new Vector3f();
        scaledOffsetDirection = new Vector3f();
        p1 = new Vector3f();
        p2 = new Vector3f();
        pCenter = new Vector3f();
        pDirection = new Vector3f();
        arrowOffset = new Vector3f();
        measureExtent = new Vector3f();
        measureExtentArrowHead = new Vector3f();
        arrow1 = new Vector3f();
        arrow2 = new Vector3f();
        arrowHead1 = new Vector3f();
        arrowHead2 = new Vector3f();
        arrowHead1a = new Vector3f();
        arrowHead2a = new Vector3f();
        barOffset1 = new Vector3f();
        barOffset2 = new Vector3f();
        bar1 = new Vector3f();
        bar2 = new Vector3f();
        midVector = new Vector3f();
        midVector1 = new Vector3f();
        midVector2 = new Vector3f();
        
        offset = 1f; // world coordinates for now...
        
        setSynchronizeRotation(false);
        setSynchronizeTranslation(false);
        
        label = new LabelRepresentation(target, "label_measurement") {
            @Override
            protected Vector3f getDisplayCenter() {
                return labelCenter;
            }
        };
        attachChild(label);
        
        //setUseWorldScale(false);
        
        update();
        updateRenderState();
    }
    
    @Override
    public void update() {
        super.update();
        label.update();
        label.setAmbient(color);
        label.setDiffuse(color);
    }


    // keeping these like this is rather unseemly, but
    // this class is was a leading cause of busywork for the GC in terms of 
    // Vector3f objects that were churned through.
    // We keep these cached here so that they don't churn.
    Vector3f worldPointDifference, offsetDirection, scaledOffsetDirection;
    Vector3f p1, p2, pCenter, pDirection;
    Vector3f arrowOffset, measureExtent, measureExtentArrowHead;
    Vector3f arrow1, arrow2, arrowHead1, arrowHead2, arrowHead1a, arrowHead2a;
    Vector3f barOffset1, barOffset2, bar1, bar2;
    Vector3f midVector, midVector1, midVector2;
    
    @Override
    public void draw(Renderer r) {
        super.draw(r);
    
        worldPointDifference.set(getTarget().getPoint2().getTranslation());
        worldPointDifference.subtractLocal(getTarget().getPoint1().getTranslation());
        offsetDirection.set(r.getCamera().getDirection());
        offsetDirection.crossLocal(worldPointDifference);
        offsetDirection.normalizeLocal();
        
        scaledOffsetDirection.set(offsetDirection);
        scaledOffsetDirection.multLocal(offset);
        
        // draw main line.
        p1.set(getTarget().getPoint1().getTranslation());
        p1.addLocal( scaledOffsetDirection );
        p2.set(getTarget().getPoint2().getTranslation());
        p2.addLocal( scaledOffsetDirection );
        
        pCenter.set(p1);
        pCenter.addLocal(p2);
        pCenter.multLocal(.5f);
        
        pDirection.set(p1);
        pDirection.subtractLocal(p2);
        pDirection.normalizeLocal();
        float horizontalRatio = Math.abs(pDirection.dot( r.getCamera().getLeft() ));
        float lineLength = p1.distance(p2);
        // this is a strange way of estimating the size, but it may be effective...
        float textScreenSize = label.getWidth() * horizontalRatio + label.getHeight() * (1 - horizontalRatio);
        
        // screen distance.
        float screenLength = r.getCamera().getScreenCoordinates(p1).distance( r.getCamera().getScreenCoordinates(p2) );
        float worldRatio = lineLength / screenLength;
        float textWorldLength = textScreenSize * worldRatio;
        
        // now that the textWorldLength is calculated, it may be good to do some tests
        // we may wish to display text differently if the text size is greater than the line length, etc.
        
        arrowOffset.set(offsetDirection);
        arrowOffset.multLocal(worldRatio*arrowsize);
        measureExtent.set(pDirection);
        measureExtent.multLocal(lineLength/2 - worldRatio*margin);
        measureExtentArrowHead.set(pDirection);
        measureExtentArrowHead.multLocal(lineLength/2 - worldRatio*(margin+arrowsize));
        
        arrow1.set(pCenter);
        arrow2.set(pCenter);
        arrow1.addLocal(measureExtent);
        arrow2.subtractLocal(measureExtent);
        
        arrowHead1.set(measureExtentArrowHead);
        arrowHead2.set(measureExtentArrowHead);
        arrowHead1.addLocal(arrowOffset);
        arrowHead2.subtractLocal(arrowOffset);

        barOffset1.set(offsetDirection);
        barOffset2.set(offsetDirection);
        barOffset1.multLocal(offset + edgeWidth/2);
        barOffset2.multLocal(offset - edgeWidth/2);
        
        if(drawLeftBar) {
            bar1.set(getTarget().getPoint1().getTranslation());
            bar2.set(getTarget().getPoint1().getTranslation());
            bar1.addLocal( barOffset1 );
            bar2.addLocal( barOffset2 );
            CurveUtil.renderLine(r, color, bar1, bar2);
        }
        
        if(drawRightBar) {
            bar1.set(getTarget().getPoint2().getTranslation());
            bar2.set(getTarget().getPoint2().getTranslation());
            bar1.addLocal( barOffset1 );
            bar2.addLocal( barOffset2 );
            CurveUtil.renderLine(r, color, bar1, bar2);
        }
        
        if(textWorldLength + 8*margin*worldRatio > lineLength) {
            // do not display text along line, display 10px under (?)
            //textWorldLength = 0;
            
            // THIS IS A HACK HACK HACK!!!
            float yComposition = Math.abs(offsetDirection.y);
            float otherComposition = 1 - yComposition*yComposition;
            float totalOffset = (yComposition)*12*worldRatio + 3*otherComposition*textWorldLength;
            
            labelCenter.set(pCenter);
            labelCenter.addLocal( offsetDirection.multLocal(totalOffset) );
            // the multLocal here is cheating, but offsetDirection is not reused, so it should be okay...
        
            // draw line:
            CurveUtil.renderLine(r, color, arrow1, arrow2);
            
        } else {
            // display text normally...
            labelCenter = pCenter;
            midVector.set(pDirection);
            midVector.multLocal(textWorldLength/2 + worldRatio*margin);
            midVector1.set(pCenter);
            midVector2.set(pCenter);
            midVector1.addLocal(midVector);
            midVector2.subtractLocal(midVector);
        
            // draw lines:
            CurveUtil.renderLine(r, color, arrow1, midVector1);
            CurveUtil.renderLine(r, color, arrow2, midVector2);
        }
        
        arrowHead1a.set(arrowHead1).multLocal(-1);
        arrowHead2a.set(arrowHead2).multLocal(-1);
        
        arrowHead1.addLocal(pCenter);
        arrowHead2.addLocal(pCenter);
        arrowHead1a.addLocal(pCenter);
        arrowHead2a.addLocal(pCenter);
        
        // draw arrowheads:
        CurveUtil.renderLine(r, color, arrow1, arrowHead1);
        CurveUtil.renderLine(r, color, arrow1, arrowHead2);
        CurveUtil.renderLine(r, color, arrow2, arrowHead1a);
        CurveUtil.renderLine(r, color, arrow2, arrowHead2a);
    }
    
}
