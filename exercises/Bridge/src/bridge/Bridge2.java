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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This exercise selects TWO spans, and four forces total among them.
 * @author Calvin Ashmore
 */
public class Bridge2 extends BridgeBase {

    protected Random rand = new Random();

    @Override
    protected Set<String> selectToSolve() {

        Set<String> toSolve = new HashSet<String>();


        // choose a number between 1 and 12
        int span1 = 1 + rand.nextInt(11);
        int span2;
        do {
            span2 = 1 + rand.nextInt(11);
        } while (span1 == span2);

        // span1 and span2 should not equal each other.

        // select 1 to 3 forces in span1.
        int span1Count = 1 + rand.nextInt(2);

        selectFromSpan(span1Count, span1, toSolve);
        selectFromSpan(4 - span1Count, span2, toSolve);
        return toSolve;
    }

    protected void selectFromSpan(int number, int span, Set<String> toSolve) {

        List<String> spanForces = new ArrayList<String>();

        spanForces.add("U" + span + "-U" + (span + 1));
        spanForces.add("L" + span + "-L" + (span + 1));

        if (span % 2 == 0) {
            // in even spans the middle bar points down
            spanForces.add("U" + span + "-L" + (span + 1));
        } else {
            // in odd spans the middle bar points up
            spanForces.add("L" + span + "-U" + (span + 1));
        }

        // spanForces contains 3 Strings.
        while (number > 0) {
            String force = spanForces.remove(rand.nextInt(spanForces.size()));
            toSolve.add(force);
            number--;
        }
    }
}
