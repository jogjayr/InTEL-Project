/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.selectdiagram;

import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.icon.ImageIcon;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectFBDItem extends BContainer {
    
    BImage image;
    // FBDWorld fbd; ???
    
    public SelectFBDItem(int index) {
        this.index = index;
        
        add(setupLabel());
    }
    
    protected BLabel setupLabel() {
        
        image = getImage();
        return new BLabel(new ImageIcon(image));
    }
    
    private int index = 0;
    protected BImage getImage() {
        try {
            return new BImage(getClass().getClassLoader().getResource("fbd" + index + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(SelectFBDItem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
