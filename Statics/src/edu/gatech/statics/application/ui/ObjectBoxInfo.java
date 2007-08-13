/*
 * ObjectInfo.java
 *
 * Created on July 2, 2007, 3:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jme.math.Vector2f;
import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.background.BlankBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class ObjectBoxInfo extends BContainer {
    
    private SimulationObject obj;
    
    private HTMLView view;
    
    private boolean hover;
    private boolean selected;

    //private BBackground background;
    
    /** Creates a new instance of ObjectInfo */
    public ObjectBoxInfo(final SimulationObject obj) {
        super(new BorderLayout());
        this.obj = obj;
        setStyleClass("info_window");
        
        
        view = new HTMLView();
        
        view.setStyleSheet(AppInterface.getHtmlStyle());
        view.setContents(obj.getDescription());
        add(view, BorderLayout.CENTER);
        
        setPreferredSize(115, getPreferredSize(0,0).height);
        
        view.addListener(new MouseListener() {
            public void mousePressed(MouseEvent event) {}
            public void mouseReleased(MouseEvent event) {
                StaticsApplication.getApp().select(obj);
            }

            public void mouseEntered(MouseEvent event) {
                //System.out.println("enter");
                StaticsApplication.getApp().hover(obj);
            }

            public void mouseExited(MouseEvent event) {
                //System.out.println("exit");
                StaticsApplication.getApp().hover(null);
            }
        });
    }
    
    // ???
    void expand() {}
    void collapse() {}

    void setSelected(boolean selected) {
        this.selected = selected;
        updateDisplay();
    }
    void setHover(boolean hover) {
        this.hover = hover;
        updateDisplay();
    }

    private void updateDisplay() {
        if(selected) {
            setBackground(0, new TintedBackground(ColorRGBA.green));
            setBackground(1, new TintedBackground(ColorRGBA.green));
        } else if(hover) {
            setBackground(0, new TintedBackground(new ColorRGBA(.9f,.9f,.9f,1)));
            setBackground(1, new TintedBackground(new ColorRGBA(.9f,.9f,.9f,1)));
        } else {
            setBackground(0, new TintedBackground(ColorRGBA.lightGray));
            setBackground(1, new TintedBackground(ColorRGBA.lightGray));
        }
    }
    
}
