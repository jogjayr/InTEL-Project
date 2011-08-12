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
package viewer;

import edu.gatech.statics.exercise.state.DiagramState;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerState implements DiagramState<ViewerDiagram> {

    public ViewerState() {
    }

    public boolean isLocked() {
        return false;
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public class Builder implements edu.gatech.statics.util.Builder<ViewerState> {

        public ViewerState build() {
            return new ViewerState();
        }
    }
}
