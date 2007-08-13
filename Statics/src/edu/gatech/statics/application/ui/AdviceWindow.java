/*
 * InfoWindow.java
 *
 * Created on June 21, 2007, 9:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;

/**
 *
 * @author Calvin Ashmore
 */
public class AdviceWindow extends AppWindow {
    
    private HTMLView view;
    
    
    public void setAdvice(String contents) {
        view.setContents("<html><body>"+contents+"</body></html>");
    }
    
    /** Creates a new instance of InfoWindow */
    public AdviceWindow() {
        super(new BorderLayout());
        view = new HTMLView();
        
        view.setStyleSheet(AppInterface.getHtmlStyle());
        view.setContents("");
        add(view, BorderLayout.CENTER);
    }
    
}
