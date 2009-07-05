/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import edu.gatech.statics.objects.bodies.TwoForceMember;
import edu.gatech.statics.tasks.Solve2FMTask;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Calvin Ashmore
 */
public class BridgeExerciseRandomizedGoals extends BridgeExercise {

    private static final int NUMBER_TO_SOLVE = 4;

    @Override
    public void initParameters() {

        // build a list of all the solvable 2fms
        List<String> all2FMs = create2FMNameList();

        // build a subset of this to solve.
        Random random = new Random();
        Set<String> toSolve = new HashSet<String>();
        while (toSolve.size() < NUMBER_TO_SOLVE) {
            String twofm = all2FMs.get(random.nextInt(all2FMs.size()));
            toSolve.add(twofm);
        }

        getState().setParameter("toSolve", toSolve);
    }

    @Override
    public void applyParameters() {
        getTasks().clear();
        Set<String> toSolve = (Set<String>) getState().getParameter("toSolve");

        for (String twoForceMemberName : toSolve) {

            TwoForceMember twoForceMember = (TwoForceMember) getSchematic().getAllObjectsByName().get(twoForceMemberName);
            String taskName = "Solve " + twoForceMember.getName();
            addTask(new Solve2FMTask(taskName, twoForceMember, twoForceMember.getConnector1()));
        }
    }

    private List<String> create2FMNameList() {

        List<String> allNames = new ArrayList<String>();

        // THIS IS COPIED WHOLECLOTH FROM BridgeExercise
        // IF THAT IS CHANGED AT ALL, THIS MUST BE CHANGED AS WELL!!!

        for (int i = 0; i < 14; i++) {
            // upper bars
            add2FMName(allNames, "U", i, "U", i + 1);
            if (i > 0) {
                // lower bars
                add2FMName(allNames, "L", i, "L", i + 1);
                // verticals
                add2FMName(allNames, "U", i, "L", i);
            }
            // cross bars
            String crossPrefix1 = i % 2 == 0 ? "U" : "L";
            String crossPrefix2 = i % 2 == 1 ? "U" : "L";
            add2FMName(allNames, crossPrefix1, i, crossPrefix2, i + 1);
        }

        // get that middle bar
        add2FMName(allNames, "U", 14, "L", 14);

        return allNames;
    }

    private void add2FMName(List<String> allNames, String prefix1, int index1, String prefix2, int index2) {
        boolean isZfm = false;
        if (index1 == index2 && index1 % 2 == 0 && index1 != 8) {
            isZfm = true;
        }
        if (index1 == 13 && index2 == 14 && prefix1.equals("L") && prefix2.equals("L")) {
            isZfm = true;
        }

        if (!isZfm) {
            String joint1Name = getJointName(prefix1, index1);
            String joint2Name = getJointName(prefix2, index2);
            String barName = "Bar " + joint1Name + "-" + joint2Name;
            allNames.add(barName);
        }
    }
}
