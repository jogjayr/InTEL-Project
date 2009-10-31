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
public class Bridge3 extends Bridge2 {

    @Override
    protected Set<String> selectToSolve() {

        Set<String> toSolve = new HashSet<String>();

        List<Integer> spans = new ArrayList<Integer>();
        int totalSpans = 4;

        for (int i = 0; i < totalSpans; i++) {
            int span;
            do {
                span = 1 + rand.nextInt(11);
            } while (spans.contains(span));
            spans.add(span);
        }

        for (Integer span : spans) {
            selectFromSpan(1, span, toSolve);
        }
        return toSolve;
    }
}
