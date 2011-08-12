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

package edu.gatech.statics.ui.applicationbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel extends BContainer {

    private ApplicationTab tab;
    private BLabel titleLabel;
    
    public abstract String getPanelName();
    
    protected BLabel getTitleLabel() {
        return titleLabel;
    }
    
    public ApplicationModePanel() {
        super(new BorderLayout());
        tab = createTab();
        titleLabel = new BLabel("", "mode_title");
        add(titleLabel,BorderLayout.NORTH);
    }
    
    abstract public void activate();
    
    public ApplicationTab getTab() {return tab;}

    abstract protected ApplicationTab createTab();
}
