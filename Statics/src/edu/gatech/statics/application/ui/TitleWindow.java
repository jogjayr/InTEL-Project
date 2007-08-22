/*
 * TitleWindow.java
 *
 * Created on August 21, 2007, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class TitleWindow extends AppWindow {
    
    private BLabel titleLabel;
    private BLabel subTitle;
    
    /** Creates a new instance of TitleWindow */
    public TitleWindow() {
        super(new BorderLayout());
        setStyleClass("title_container");
        
        titleLabel = new BLabel(StaticsApplication.getApp().getExercise().getName());
        add(titleLabel, BorderLayout.CENTER);
        
        subTitle = new BLabel("");
        subTitle.setStyleClass("subTitle");
        add(subTitle, BorderLayout.SOUTH);
    }

    void setSubTitle(String subTitle) {
        this.subTitle.setText(subTitle);
    }
    
}
