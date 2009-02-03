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
import java.util.Collections;
import java.util.Comparator;
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

        // build a list of all of the diagrams.
        List<Diagram> allDiagrams = new ArrayList<Diagram>();
        
        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : oldState.allDiagrams().entrySet()) {
            Map<DiagramType, Diagram> map = entry.getValue();
            for (Map.Entry<DiagramType, Diagram> entry1 : map.entrySet()) {
                Diagram diagram = entry1.getValue();
                
                allDiagrams.add(diagram);
            }
        }

        // sort the list
        Collections.sort(allDiagrams, new Comparator<Diagram>() {
            public int compare(Diagram o1, Diagram o2) {
                return o1.getType().getPriority() - o2.getType().getPriority();
            }
        });
        
        // write out the diagrams
        for (Diagram diagram : allDiagrams) {
            DiagramKey diagramKey = diagram.getKey();
            DiagramType diagramType = diagram.getType();
            DiagramState diagramState = diagram.getCurrentState();
            out.writeStatement(new Statement(oldState, "initDiagram", new Object[]{diagramKey, diagramType, diagramState}));
        }

        super.initialize(type, oldInstance, newInstance, out);
        newState.setEncoding(false);

    }
}
