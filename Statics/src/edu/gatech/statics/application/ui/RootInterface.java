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
import com.jmex.bui.background.TintedBackground;
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
    private DescriptionIcon descriptionIcon;
    
    private BWindow border1;
    private BWindow border2;
    private BWindow border3;
    private BWindow border4;
    
    public static final int windowSpacing = 3;
    public static final int borderSize = 3;
    public static final int sidebarSize = 150;
    
    /** Creates a new instance of RootInterface */
    public RootInterface() {
                
        int distance = 0;
        Dimension dim;
        
        titleWindow = new TitleWindow();
        getBuiNode().addWindow(titleWindow);
        titleWindow.pack();
        dim = titleWindow.getPreferredSize(0,0);
        //titleWindow.center();
        titleWindow.setBounds(0,0,dim.width, dim.height);
        titleWindow.setLocation(windowSpacing + borderSize, getScreenHeight() - dim.height - windowSpacing - borderSize);
        
        distance += dim.height + windowSpacing + borderSize;
        
        /*modeControl = new ModeControlWindow();
        getBuiNode().addWindow(modeControl);
        modeControl.pack();
        dim = modeControl.getPreferredSize(0,0);
        modeControl.setBounds(0,0,sidebarSize, dim.height);
        modeControl.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        
        distance += dim.height + windowSpacing;*/
        
        descriptionIcon = new DescriptionIcon();
        getBuiNode().addWindow(descriptionIcon);
        descriptionIcon.pack();
        dim = descriptionIcon.getPreferredSize(0,0);
        descriptionIcon.setBounds(0,0,sidebarSize, dim.height);
        descriptionIcon.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        
        distance += dim.height + windowSpacing;
        
        displayControl = new DisplayControlWindow();
        getBuiNode().addWindow(displayControl);
        displayControl.pack();
        dim = displayControl.getPreferredSize(0,0);
        displayControl.setBounds(0,0,sidebarSize, dim.height);
        displayControl.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        
        distance += dim.height + windowSpacing;
        
        coordinateSystem = new CoordinateSystemWindow();
        getBuiNode().addWindow(coordinateSystem);
        coordinateSystem.pack();
        dim = coordinateSystem.getPreferredSize(0,0);
        coordinateSystem.setBounds(0,0,sidebarSize, dim.height);
        coordinateSystem.setLocation(windowSpacing + borderSize, getScreenHeight()-dim.height - distance - windowSpacing);
        distance += dim.height + windowSpacing;
        
        
        
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
        adviceWindow.setBounds(windowSpacing+borderSize,100+windowSpacing+borderSize,sidebarSize,150);
    }
    
    public void setAdvice(String advice) {
        adviceWindow.setAdvice(advice);
    }
    
    public void setBorderColor(ColorRGBA color) {
        //border1.getBackground()
        ((TintedBackground)border1.getBackground()).setColor(color);
        ((TintedBackground)border2.getBackground()).setColor(color);
        ((TintedBackground)border3.getBackground()).setColor(color);
        ((TintedBackground)border4.getBackground()).setColor(color);
    }
    
    public void setSubTitle(String subTitle) {
        titleWindow.setSubTitle(subTitle);
        titleWindow.pack();
        //titleWindow.center();
        //titleWindow.setLocation(titleWindow.getX(), getScreenHeight() - titleWindow.getHeight() - windowSpacing - borderSize);
    }

    public void showDescription() {
        // when the root interface is first created, launch the description popup
        descriptionIcon.doPopup();
    }
    
    public void update() {
        //objectBox.update();
    }
    
}
