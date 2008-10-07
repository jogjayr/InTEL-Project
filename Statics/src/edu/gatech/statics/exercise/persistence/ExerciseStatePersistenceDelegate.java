/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.exercise.state.ExerciseState;
import edu.gatech.statics.math.AnchoredVector;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseStatePersistenceDelegate extends DefaultPersistenceDelegate {

    private static final ExerciseStatePersistenceHelper helper = new ExerciseStatePersistenceHelper();
    private static boolean writing = false;

    //@Override
    //protected Expression instantiate(Object oldInstance, Encoder out) {
    //}
    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {

        writing = true;

        ExerciseState state = (ExerciseState) oldInstance;

        out.writeStatement(new Statement(helper, "println", new Object[]{"oh noes!"}));

        // write out the SymbolManager
        out.writeStatement(new Statement(helper, "setupSymbolManager", new Object[]{oldInstance, state.getSymbolManager().getLoads()}));

        // write out the diagrams
        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : state.allDiagrams().entrySet()) {
            DiagramKey diagramKey = entry.getKey();
            Map<DiagramType, Diagram> map = entry.getValue();
            for (Map.Entry<DiagramType, Diagram> entry1 : map.entrySet()) {
                DiagramType diagramType = entry1.getKey();
                Diagram diagram = entry1.getValue();
                DiagramState diagramState = diagram.getCurrentState();

                out.writeStatement(new Statement(helper, "makeDiagram", new Object[]{oldInstance, diagramKey, diagramType, diagramState}));
            }
        }

        super.initialize(type, oldInstance, newInstance, out);

        writing = false;
    }

    /**
     * This class and all its methods are intended to be helper initializer methods
     * to help in the persistence of the ExerciseState.
     */
    public static class ExerciseStatePersistenceHelper {

        public void println(String s) {
            System.out.println(s);
        }

        public void setupSymbolManager(ExerciseState newState, List<AnchoredVector> symbolManagerLoads) {
            if (writing) {
                return;
            }
            for (AnchoredVector load : symbolManagerLoads) {
                newState.getSymbolManager().addSymbol(load);
            }
        }

        public void makeDiagram(ExerciseState newState, DiagramKey key, DiagramType type, DiagramState state) {
            if (writing) {
                return;
            }
            Diagram diagram = Exercise.getExercise().createNewDiagram(key, type);
            diagram.pushState(state);
            diagram.clearStateStack();
        }
    }
}
