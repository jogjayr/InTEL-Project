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
import com.jme.scene.Line;
import com.jme.scene.state.AlphaState;
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
    
    private ColorRGBA color = ColorRGBA.white;
    public void setColor(ColorRGBA color) {this.color = color;}
    
    //public void drawLabel(boolean drawLabel) {this.drawLabel = drawLabel;}
    
    /** Creates a new instance of DistanceRepresentation */
    public DistanceRepresentation(DistanceMeasurement target) {
        super(target);
        setLayer(RepresentationLayer.measurement);
        
        offset = 1f; // world coordinates for now...
        
        setSynchronizeRotation(false);
        setSynchronizeTranslation(false);
        
        label = new LabelRepresentation(target) {
            protected Vector3f getDisplayCenter() {
                return labelCenter;
            }
        };
        attachChild(label);
        
        setUseWorldScale(false);
        
        update();
    }
    
    public void update() {
        super.update();
        label.update();
        label.setAmbient(color);
        label.setDiffuse(color);
    }

    public void draw(Renderer r) {
        super.draw(r);
        
        Vector3f offsetDirection = r.getCamera().getDirection().cross(
                getTarget().getPoint2().subtract(getTarget().getPoint1()));
        offsetDirection.normalizeLocal();
        
        // draw main line.
        Vector3f p1 = getTarget().getPoint1().add( offsetDirection.mult(offset) );
        Vector3f p2 = getTarget().getPoint2().add( offsetDirection.mult(offset) );
        Vector3f pCenter = p1.add(p2).mult(.5f);
        Vector3f pDirection = p1.subtract(p2).normalize();
        float horizontalRatio = Math.abs(pDirection.dot( r.getCamera().getLeft() ));
        float lineLength = p1.distance(p2);
        // this is a strange way of estimating the size, but it may be effective...
        float textScreenSize = label.getText().getWidth() * horizontalRatio + label.getText().getHeight() * (1 - horizontalRatio);
        
        // screen distance.
        float screenLength = r.getCamera().getScreenCoordinates(p1).distance( r.getCamera().getScreenCoordinates(p2) );
        float worldRatio = lineLength / screenLength;
        float textWorldLength = textScreenSize * worldRatio;
        
        // now that the textWorldLength is calculated, it may be good to do some tests
        // we may wish to display text differently if the text size is greater than the line length, etc.
        
        if(textWorldLength + 8*margin*worldRatio > lineLength) {
            // do not display text along line, display 10px under (?)
            textWorldLength = 0;
            labelCenter = pCenter.add( offsetDirection.mult(12*worldRatio) );
        
            // draw line:
            CurveUtil.renderLine(r, color,
                    pCenter.add(pDirection.mult(lineLength/2 - worldRatio*margin)),
                    pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*margin)));
            
        } else {
            // display text normally...
            labelCenter = pCenter;
        
            // draw lines:
            CurveUtil.renderLine(r, color,
                    pCenter.add(pDirection.mult(lineLength/2 - worldRatio*margin)),
                    pCenter.add(pDirection.mult(textWorldLength/2 + worldRatio*margin)));
            CurveUtil.renderLine(r, color,
                    pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*margin)),
                    pCenter.subtract(pDirection.mult(textWorldLength/2 + worldRatio*margin)));
        }
        
        // draw arrowheads:
        CurveUtil.renderLine(r, color,
                pCenter.add(pDirection.mult(lineLength/2 - worldRatio*margin)),
                pCenter.add(pDirection.mult(lineLength/2 - worldRatio*(margin+arrowsize)).add(offsetDirection.mult(worldRatio*arrowsize))));
        CurveUtil.renderLine(r, color,
                pCenter.add(pDirection.mult(lineLength/2 - worldRatio*margin)),
                pCenter.add(pDirection.mult(lineLength/2 - worldRatio*(margin+arrowsize)).subtract(offsetDirection.mult(worldRatio*arrowsize))));
        CurveUtil.renderLine(r, color,
                pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*margin)),
                pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*(margin+arrowsize)).add(offsetDirection.mult(worldRatio*arrowsize))));
        CurveUtil.renderLine(r, color,
                pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*margin)),
                pCenter.subtract(pDirection.mult(lineLength/2 - worldRatio*(margin+arrowsize)).subtract(offsetDirection.mult(worldRatio*arrowsize))));
        
        //CurveUtil.renderLine(r, ColorRGBA.black, p1, p2);
        
        if(drawLeftBar) {
            Vector3f p1a = getTarget().getPoint1().add( offsetDirection.mult(offset + edgeWidth/2) );
            Vector3f p1b = getTarget().getPoint1().add( offsetDirection.mult(offset - edgeWidth/2) );
            CurveUtil.renderLine(r, color, p1a, p1b);
        }
        
        if(drawRightBar) {
            Vector3f p2a = getTarget().getPoint2().add( offsetDirection.mult(offset + edgeWidth/2) );
            Vector3f p2b = getTarget().getPoint2().add( offsetDirection.mult(offset - edgeWidth/2) );
            CurveUtil.renderLine(r, color, p2a, p2b);
        }
        
    }
    
}
