/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
