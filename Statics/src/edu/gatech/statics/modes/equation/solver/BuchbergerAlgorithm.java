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
package edu.gatech.statics.modes.equation.solver;

import edu.gatech.statics.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is Buchberger's algorithm for finding a Groebner basis for a system of polynomial equations.
 * Implementation is roughly derived from here: http://www.risc.uni-linz.ac.at/education/courses/ws2009/ca/Kap02-1.pdf
 * @author Calvin Ashmore
 */
public class BuchbergerAlgorithm {

    public List<Polynomial> findBasis(List<Polynomial> system) {

        // STEPS:
        // 1) create an empty list for the basis, and add all equations in the system into it.
        List<Polynomial> basis = new ArrayList<Polynomial>();

        for (Polynomial polynomial : system) {
            polynomial = polynomial.getNormalizedPoly();

            basis.add(polynomial);
        }

        // 2) Create pairs of polynomials to check.
        Set<Pair<Polynomial, Polynomial>> toCheck = new HashSet<Pair<Polynomial, Polynomial>>();
        for (int i = 0; i < basis.size(); i++) {
            for (int j = 0; j < i; j++) {
                toCheck.add(new Pair<Polynomial, Polynomial>(basis.get(i), basis.get(j)));
            }
        }

        // 3) foreach pair, calculate its S-polynomial, reduce it, and add it to the basis (if it is unique and non-zero).
        // subsequently inject all pairs between the S-polynomial and the other elements of the basis.
        while (!toCheck.isEmpty()) {
            
            // pull out the pair of polynomials we are going to look at, and remove the pair from the big list.
            Pair<Polynomial, Polynomial> pair = toCheck.iterator().next();
            toCheck.remove(pair);

            Polynomial poly1 = pair.getLeft();
            Polynomial poly2 = pair.getRight();

            // calculate the s-polynomial, which is the result of cancelling the leading terms.
            // this is the equivalent to performing a substitution in solving an algebraic system of equations.
            Polynomial sPoly = poly1.cancelLeadingTerms(poly2);

            // now we want to reduce it. The reduction works by making sure that the s-polynomial has no
            // leading terms in common with the other elements of the basis.
            // because these terms may occur more than once, we keep reducing until we are sure there are no more common terms.
            boolean hasReduced;
            do {
                hasReduced = false;
                for (Polynomial polynomial : basis) {
                    if (sPoly.isReducible(polynomial)) {
                        sPoly = sPoly.reduce(polynomial);
                        if(!sPoly.isZero())
                            sPoly = sPoly.getNormalizedPoly();
                        hasReduced = true;
                    }
                }
            } while (hasReduced);

            // if the resulting term is identically zero, skip it and move on.
            if (sPoly.isZero()) {
                continue;
            }

            // normalize the polynomial, so its leading term has a coefficient of 1
            sPoly = sPoly.getNormalizedPoly();

            // if the polynomial is not contained in the basis,
            // add it, and all pairs between it and the basis members.
            if (!basis.contains(sPoly)) {
                for (Polynomial polynomial : basis) {
                    toCheck.add(new Pair<Polynomial, Polynomial>(sPoly, polynomial));
                }

                if(sPoly.getLeadingTerm().getSymbols().isEmpty()) {
                    // if we have a unit appear in the basis, then something is dreadfully wrong.
                    // this means that there is an expression of the form 1 = 0 in the basis.
                    return null;
                }

                basis.add(sPoly);
                //System.out.println("adding... " + sPoly);
            }
        }

        // 4) Cleanup! (part 1)
        // eliminate redundant polys in the basis
        List<Polynomial> toRemove = new ArrayList<Polynomial>();
        for (Polynomial polynomial : basis) {
            for (Polynomial polynomial1 : basis) {
                if (polynomial1 == polynomial || toRemove.contains(polynomial1)) {
                    continue;
                }

                if (polynomial.getLeadingTerm().isDivisible(polynomial1.getLeadingTerm())) {
                    toRemove.add(polynomial);
                }
            }
        }
        basis.removeAll(toRemove);

        // 5) Cleanup! (part 2)
        // make sure that the polynomials in the basis are maximally reduced.
        // that is- reduce the polynomials in the basis with respect to all the other polynomials in the basis.
        List<Polynomial> furtherBasis = new ArrayList<Polynomial>();
        for(int i=0;i<basis.size();i++) {
            Polynomial polynomial1 = basis.get(i);
            for(int j=0;j<basis.size();j++) {
                Polynomial polynomial2 = basis.get(j);
//        for (Polynomial polynomial1 : basis) {
//            for (Polynomial polynomial2 : basis) {
                //if (polynomial1.equals(polynomial2)) {
                if(i == j) {
                    continue;
                }

                if (polynomial1.isReducible(polynomial2)) {
                    polynomial1 = polynomial1.reduce(polynomial2);
                }
            }

            furtherBasis.add(polynomial1);
        }

        return furtherBasis;
    }
}
