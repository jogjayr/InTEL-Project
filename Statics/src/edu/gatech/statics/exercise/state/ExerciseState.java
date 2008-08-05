/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.state;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.SymbolManager;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseState implements State {

    // satisfied tasks
    // solved loads (symbol manager)
    // solved connectors
    // diagrams?
    private SymbolManager symbolManager;
    private Map<DiagramKey, Map<DiagramType, Diagram>> allDiagrams = new HashMap<DiagramKey, Map<DiagramType, Diagram>>();

    public SymbolManager getSymbolManager() {
        return symbolManager;
    }

    public ExerciseState() {
        symbolManager = new SymbolManager();
    }

    /**
     * Attempts to find a diagram created by the user that matches the specified key, and is
     * of the expected type. This method will return null if no such diagram is found.
     * @param key
     * @param expectedType
     * @return
     */
    public Diagram getDiagram(DiagramKey key, DiagramType expectedType) {
        Map<DiagramType, Diagram> typeMap = allDiagrams.get(key);

        // nothing matching the key was found
        if (typeMap == null) {
            return null;
        }
        return typeMap.get(expectedType);
    }

    /**
     * Called when the user has created a diagram and it should be stored here.
     * @param diagram
     */
    public void storeDiagram(Diagram diagram) {
        DiagramKey key = diagram.getKey();
        Map<DiagramType, Diagram> typeMap = allDiagrams.get(key);
        if (typeMap == null) {
            typeMap = new HashMap<DiagramType, Diagram>();
            allDiagrams.put(key, typeMap);
        }

        // store if we have not stored it already.
        typeMap.put(diagram.getType(), diagram);
    }

    /**
     * Restores this exercise state as loaded.
     * When the state is loaded, the diagrams will not exist, but their types will?
     * Will have set of digram state and diagram?
     * Diagram thus needs some zero or 1-arg (for the key) constructor,
     * and can just be assigned state automatically.
     * Must ALSO be able to recognize and instantiate modified types of diagrams, 
     * so can't just make everything into a FBD, may need to be a DistributedFBD, etc.
     */
    void restore() {
    }
}
