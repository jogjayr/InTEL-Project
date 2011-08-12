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

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;

/**
 * The background is a special type of body which represents the "world" in an exercise.
 * The background should show up in Select mode, but not in most others.
 * @author Calvin Ashmore
 */
public class Background extends Body{

    public Background() {
        super("background");
    }
    
    /**
     * This method should not be called, the background should not have a default representation.
     * @deprecated
     */
    @Override
    @Deprecated
    public void createDefaultSchematicRepresentation() {
        throw new UnsupportedOperationException("The background should not have a default representation.");
    }

}
