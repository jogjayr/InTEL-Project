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
package edu.gatech.statics.tasks;

import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 * A task for graded problems specifically for Centroid problems. This is to
 * determine if a particular CentroidPartObject is solved.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class SolveCentroidPartTask extends Task {

    CentroidPartObject cpo;

    @Deprecated
    public SolveCentroidPartTask(String name) {
        super(name);
    }

    public SolveCentroidPartTask(String name, CentroidPartObject cpo) {
        super(name);
        this.cpo = cpo;
    }

    public CentroidPartObject getCentroidPartObject() {
        return cpo;
    }

    @Override
    public boolean isSatisfied() {
        if(cpo.getState() == null || (cpo.getState() != null && !cpo.getState().isLocked())){
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Solve for " + cpo.getName() + "'s centroid";
    }
}
