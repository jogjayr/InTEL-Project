/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.exercise.state.ExerciseState;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseStatePersistenceDelegate extends DefaultPersistenceDelegate {

    //private static final ExerciseStatePersistenceHelper helper = new ExerciseStatePersistenceHelper();
    //private static boolean writing = false;

    //@Override
    //protected Expression instantiate(Object oldInstance, Encoder out) {
    //}
    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {

        //writing = true;

        ExerciseState oldState = (ExerciseState) oldInstance;
        ExerciseState newState = (ExerciseState) newInstance;
        newState.setEncoding(true);

        // *** This doesn't work:
        // what we need is to put the helper object (or static method) in the exercise state class itself.
        //out.writeStatement(new Statement(ExerciseStatePersistenceHelper.class, "println", new Object[]{"oh noes!"}));

        // write out the SymbolManager
        //out.writeStatement(new Statement(helper, "setupSymbolManager", new Object[]{oldInstance, state.getSymbolManager().getLoads()}));
        out.writeStatement(new Statement(oldState, "initSymbolManager", new Object[]{new ArrayList(oldState.getSymbolManager().getLoads())}));

        // write out the diagrams
        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : oldState.allDiagrams().entrySet()) {
            DiagramKey diagramKey = entry.getKey();
            Map<DiagramType, Diagram> map = entry.getValue();
            for (Map.Entry<DiagramType, Diagram> entry1 : map.entrySet()) {
                DiagramType diagramType = entry1.getKey();
                Diagram diagram = entry1.getValue();
                DiagramState diagramState = diagram.getCurrentState();

                //out.writeStatement(new Statement(helper, "makeDiagram", new Object[]{oldInstance, diagramKey, diagramType, diagramState}));
                out.writeStatement(new Statement(oldState, "initDiagram", new Object[]{diagramKey, diagramType, diagramState}));
            }
        }

        super.initialize(type, oldInstance, newInstance, out);
        newState.setEncoding(false);

        //writing = false;
    }

    /**
     * This class and all its methods are intended to be helper initializer methods
     * to help in the persistence of the ExerciseState.
     */
    /*public static class ExerciseStatePersistenceHelper {

        //public static void println(String s) {
        //    System.out.println(s);
        //}

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
    }*/
}
