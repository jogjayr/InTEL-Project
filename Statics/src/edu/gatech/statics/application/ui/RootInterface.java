/*
 * RootInterface.java
 *
 * Created on June 22, 2007, 11:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BWindow;
import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class RootInterface extends AppInterface {
    
    private AdviceWindow adviceWindow;
    private ModeControlWindow modeControl;
    private DisplayControlWindow displayControl;
    private CoordinateSystemWindow coordinateSystem;
    private TitleWindow titleWindow;
    
    private BWindow border1;
    private BWindow border2;
    private BWindow border3;
    private BWindow border4;
    
    public static final int windowSpacing = 3;
    public static final int borderSize = 3;
    
    /** Creates a new instance of RootInterface */
    public RootInterface() {
                
        int distance = 0;
        Dimension dim;
        
        modeControl = new ModeControlWindow();
        getBuiNode().addWindow(modeControl);
        modeControl.pack();
        dim = modeControl.getPreferredSize(0,0);
        modeControl.setBounds(0,0,150, dim.height);
        modeControl.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - windowSpacing - borderSize);
        
        distance += dim.height + windowSpacing + borderSize;
        
        displayControl = new DisplayControlWindow();
        getBuiNode().addWindow(displayControl);
        displayControl.pack();
        dim = displayControl.getPreferredSize(0,0);
        displayControl.setBounds(0,0,150, dim.height);
        displayControl.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        
        distance += dim.height + windowSpacing;
        
        coordinateSystem = new CoordinateSystemWindow();
        getBuiNode().addWindow(coordinateSystem);
        coordinateSystem.pack();
        dim = coordinateSystem.getPreferredSize(0,0);
        coordinateSystem.setBounds(0,0,150, dim.height);
        coordinateSystem.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        distance += dim.height + windowSpacing;
        
        
        titleWindow = new TitleWindow();
        getBuiNode().addWindow(titleWindow);
        titleWindow.pack();
        titleWindow.center();
        titleWindow.setLocation(titleWindow.getX(), getScreenHeight() - titleWindow.getHeight() - windowSpacing - borderSize);
        
        border1 = new BWindow(StaticsApplication.getApp().getBuiStyle(), null);
        border2 = new BWindow(StaticsApplication.getApp().getBuiStyle(), null);
        border3 = new BWindow(StaticsApplication.getApp().getBuiStyle(), null);
        border4 = new BWindow(StaticsApplication.getApp().getBuiStyle(), null);
        
        border1.setBounds(0,0, getScreenWidth(), borderSize);
        border2.setBounds(0,getScreenHeight()-borderSize, getScreenWidth(), borderSize);
        border3.setBounds(0,0, borderSize, getScreenHeight());
        border4.setBounds(getScreenWidth()-borderSize,0, borderSize, getScreenHeight());
        
        border1.setStyleClass("border");
        border2.setStyleClass("border");
        border3.setStyleClass("border");
        border4.setStyleClass("border");
        
        getBuiNode().addWindow(border1);
        getBuiNode().addWindow(border2);
        getBuiNode().addWindow(border3);
        getBuiNode().addWindow(border4);
        
        adviceWindow = new AdviceWindow();
        getBuiNode().addWindow(adviceWindow);
        adviceWindow.setBounds(windowSpacing+borderSize,100+windowSpacing+borderSize,200,100);
    }
    
    public void setAdvice(String advice) {
        adviceWindow.setAdvice(advice);
    }
    
    public void setBorderColor(ColorRGBA color) {
        //border1.getBackground()
        //border1.getBackground()
    }
    
    public void setSubTitle(String subTitle) {
        titleWindow.setSubTitle(subTitle);
    }
    
    public void update() {
        //objectBox.update();
        int foo = 1;
    }
    
}
