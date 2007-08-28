/*
 * AppInterface.java
 *
 * Created on June 13, 2007, 11:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import edu.gatech.statics.SimulationObject;
import com.jme.system.DisplaySystem;
import com.jmex.bui.BComponent;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import edu.gatech.statics.application.StaticsApplication;
import java.awt.Font;
import java.io.StringReader;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.CSS;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author Calvin Ashmore
 */
public class AppInterface {
    
    private static StyleSheet htmlStyle;
    public static StyleSheet getHtmlStyle() {return htmlStyle;}
    
    public Toolbar getToolbar() {return null;}
    
    static {
        htmlStyle = new StyleSheet() {
            public Font getFont (AttributeSet attrs) {
                // Java's style sheet parser annoyingly looks up whatever is
                // supplied for font-family and if it doesn't map to an
                // internal Java font; it discards it. Thanks! So we do this
                // hackery with the font-variant which it passes through
                // unmolested.
                String variant = (String)
                    attrs.getAttribute(CSS.Attribute.FONT_VARIANT);
                if (variant != null && variant.equalsIgnoreCase("Test")) {
                    int style = Font.PLAIN;
                    if (StyleConstants.isBold(attrs)) {
                        style |= Font.BOLD;
                    }
                    if (StyleConstants.isItalic(attrs)) {
                        style |= Font.ITALIC;
                    }
                    int size = StyleConstants.getFontSize(attrs);
                    if (StyleConstants.isSuperscript(attrs) ||
                        StyleConstants.isSubscript(attrs)) {
                        size -= 2;
                    }
                    return new Font("Serif", style, size);
                } else {
                    return super.getFont(attrs);
                }
            }
        };
        try {
            String styledef = ".test { " +
                "font-variant: \"Test\"; " +
                "font-size: 16; }";
            htmlStyle.loadRules(new StringReader(styledef), null);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
    
    public static int getScreenWidth() {return DisplaySystem.getDisplaySystem().getWidth();}
    public static int getScreenHeight() {return DisplaySystem.getDisplaySystem().getHeight();}
    //public static int getRightAnchor(BWindow window) {return getScreenWidth() - window.getPreferredSize(0,0).width;}
    //public static int getTopAnchor(BWindow window) {return getScreenHeight() - window.getPreferredSize(0,0).height;}
    //public static int getVCenterAnchor(BWindow window) {return (getScreenHeight() - window.getPreferredSize(0,0).height)/2;}
    //public static int getHCenterAnchor(BWindow window) {return (getScreenWidth() - window.getPreferredSize(0,0).width)/2;}
    
    // defines program interface
    
    private PolledRootNode buiNode;
    public PolledRootNode getBuiNode() {return buiNode;}
    
    protected StaticsApplication getApp() {return StaticsApplication.getApp();}
    protected RootInterface getRootInterface() {return getApp().getRootInterface();}
    
    /** Creates a new instance of AppInterface */
    public AppInterface() {
        //node = new Node();
        buiNode = new PolledRootNode(getApp().getTimer(), getApp().getInput());
    }
    
    public boolean hasMouse() {
        
        int mouseX = (int)getApp().getMouse().getLocalTranslation().x;
        int mouseY = (int)getApp().getMouse().getLocalTranslation().y;
        
        for(BWindow window : buiNode.getWindows()) {
            if(window.getHitComponent(mouseX, mouseY) != null)
                return true;
        }
        return false;
    }
    
    public void dispose() {
        buiNode.removeAllWindows();
    }
    
    public void activate() {
        if(getToolbar() != null)
            getToolbar().activate();
    }
    
    public void update() {}
    
    //public void hover(SimulationObject obj) {}
    //public void select(SimulationObject obj) {}

}
