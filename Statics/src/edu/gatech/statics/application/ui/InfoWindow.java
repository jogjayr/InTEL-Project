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
public class InfoWindow extends AppWindow {
    
    private HTMLView view;
    
    public void select(SimulationObject obj) {
        
        if(obj == null)
            setContents(
                    "<html><body>" +
                    "Nothing Selected"+
                    "</body></html>");
        else {
            String contents =
                    "<html><body>" +
                    "<b>Name:</b> "+obj.getName()+"<br>";
            
            if(obj.getNotes() != null)
                contents +=
                        "<b>Notes:</b> "+obj.getNotes()+"<br>";
            
            contents +=
                    "<b>Type:</b> "+obj.getClass()+"<br>"+
                    "<b>Position:</b> "+obj.getTranslation()+"<br>"+
                    "</body></html>";
                    
            setContents(contents);
        }
    }
    
    public void setContents(String contents) {
        //this.contents = contents;
        view.setContents(contents);
    }
    
    /** Creates a new instance of InfoWindow */
    public InfoWindow() {
        super(new BorderLayout());
        view = new HTMLView();
        
        view.setStyleSheet(AppInterface.getHtmlStyle());
        view.setContents("");
        add(view, BorderLayout.CENTER);
    }
    
}
