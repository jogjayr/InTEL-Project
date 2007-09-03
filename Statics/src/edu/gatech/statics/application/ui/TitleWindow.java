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
        setStyleClass("main_title");
        
        titleLabel = new BLabel("@=#F8F400("+StaticsApplication.getApp().getExercise().getName()+":)   ");
        add(titleLabel, BorderLayout.CENTER);
        
        subTitle = new BLabel("");
        add(subTitle, BorderLayout.EAST);
    }

    void setSubTitle(String subTitle) {
        this.subTitle.setText(subTitle);
    }
    
}
