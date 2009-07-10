/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import edu.gatech.statics.objects.Force;
import java.math.BigDecimal;
import java.util.Random;

/**
 *
 * @author Calvin Ashmore
 */
public class BridgeExerciseRandomizedLoading extends BridgeExercise {

    private static final int MAX_VALUE = 600 + 300;
    private static final int MIN_VALUE = 600 - 300;
    private Random random = new Random();

    @Override
    public void initParameters() {

        String parameterName = "loading";
        int value = MIN_VALUE + random.nextInt(MAX_VALUE - MIN_VALUE);
        value = 10 * (value / 10);
        getState().setParameter(parameterName, new BigDecimal(value));

    /*for (int i = 0; i <= 14; i++) {
    String parameterName = "load-U" + i;
    int value = MIN_VALUE + random.nextInt(MAX_VALUE - MIN_VALUE);

    getState().setParameter(parameterName, new BigDecimal(value));
    }*/
    }

    @Override
    public void applyParameters() {
        //super.applyParameters();

        String parameterName = "loading";
        BigDecimal value = (BigDecimal) getState().getParameter(parameterName);

        for (int i = 0; i <= 14; i++) {
            String forceName = "load-U" + i;
            Force force = (Force) getSchematic().getAllObjectsByName().get(forceName);
            force.getAnchoredVector().setDiagramValue(value);
        }
    }
}
