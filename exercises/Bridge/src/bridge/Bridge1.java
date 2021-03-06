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
package bridge;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This exercise selects ONE span, and the three forces within that span.
 * @author Calvin Ashmore
 */
public class Bridge1 extends BridgeBase {

    @Override
    protected Set<String> selectToSolve() {

        Set<String> toSolve = new HashSet<String>();

        Random rand = new Random();

        // choose a number between 1 and 12
        int span = 1 + rand.nextInt(11);

        toSolve.add("U" + span + "-U" + (span + 1));
        toSolve.add("L" + span + "-L" + (span + 1));

        if (span % 2 == 0) {
            // in even spans the middle bar points down
            toSolve.add("U" + span + "-L" + (span + 1));
        } else {
            // in odd spans the middle bar points up
            toSolve.add("L" + span + "-U" + (span + 1));
        }
        return toSolve;
    }
}
