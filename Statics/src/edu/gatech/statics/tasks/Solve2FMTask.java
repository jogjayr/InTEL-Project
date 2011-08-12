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

import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.bodies.TwoForceMember;

/**
 * A task for graded problems specifically for 2FMss
 * @author Jimmy Truesdell
 */
public class Solve2FMTask extends SolveConnectorTask {

    private TwoForceMember tfm;

    /**
     * For persistence, do not use.
     * @param name
     * @deprecated
     */
    @Deprecated
    public Solve2FMTask(String name) {
        super(name);
    }

    public Solve2FMTask(String name, TwoForceMember tfm, Connector connector) {
        super(name, connector);
        this.tfm = tfm;
    }

    public TwoForceMember getMember() {
        return tfm;
    }

    @Override
    public String getDescription() {
        return "Solve the two force member " +
        //return "Solve the 2FM " +
                tfm.getConnector1().getAnchor().getLabelText() +
                tfm.getConnector2().getAnchor().getLabelText();
    }
}
