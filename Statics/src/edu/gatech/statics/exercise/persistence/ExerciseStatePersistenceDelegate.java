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
import edu.gatech.statics.math.Vector;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseStatePersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {

        ExerciseState oldState = (ExerciseState) oldInstance;
        ExerciseState newState = (ExerciseState) newInstance;
        newState.setEncoding(true);

        // write out the SymbolManager
        out.writeStatement(new Statement(oldState, "initSymbolManager", new Object[]{new ArrayList(oldState.getSymbolManager().getLoads())}));

        // write out the exerciseParameters
        out.writeStatement(new Statement(oldState, "initParameters", new Object[]{new HashMap(oldState.getParameters())}));

        // write out the solved reactions
        Map<String, List<Vector>> solvedReactions = oldState.getSolvedReactions();
        Map<String, List<Vector>> solvedReactionsCopy = new HashMap<String, List<Vector>>();
        for (Map.Entry<String, List<Vector>> entry : solvedReactions.entrySet()) {
            solvedReactionsCopy.put(entry.getKey(), new ArrayList<Vector>(entry.getValue()));
        }
        out.writeStatement(new Statement(oldState, "initReactions", new Object[]{new HashMap(solvedReactionsCopy)}));

        // write out the diagrams
        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : oldState.allDiagrams().entrySet()) {
            DiagramKey diagramKey = entry.getKey();
            Map<DiagramType, Diagram> map = entry.getValue();
            for (Map.Entry<DiagramType, Diagram> entry1 : map.entrySet()) {
                DiagramType diagramType = entry1.getKey();
                Diagram diagram = entry1.getValue();
                DiagramState diagramState = diagram.getCurrentState();

                out.writeStatement(new Statement(oldState, "initDiagram", new Object[]{diagramKey, diagramType, diagramState}));
            }
        }

        super.initialize(type, oldInstance, newInstance, out);
        newState.setEncoding(false);

    }
}
