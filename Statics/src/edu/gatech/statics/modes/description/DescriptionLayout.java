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
package edu.gatech.statics.modes.description;

/**
 *
 * @author Calvin Ashmore
 */
public interface DescriptionLayout {

//    private DescriptionUI ui;
//
//    public DescriptionLayout(DescriptionUI ui) {
//        this.ui = ui;
//    }
//
//    public DescriptionUI getUI() {
//        return ui;
//    }
    /**
     * This methods adds and places the UI components. This will only happen ONCE.
     * An important detail to note with BUI is that BLabels are placed from a top left coordinate system,
     * while HTMLViews are placed from a bottom left coordinate system.
     * @param ui
     */
    abstract public void addComponents(DescriptionUI ui);

    abstract public void layout(DescriptionUI ui, Description description);
}
