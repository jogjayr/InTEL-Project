/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.state;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.exercise.SymbolManager;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.tasks.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseState implements State {
    // these are used in keeping track of exercise used with the web based
    // applet deployment
    private int userID;
    private int assignmentID;
    private SymbolManager symbolManager;
    private Map<DiagramKey, Map<DiagramType, Diagram>> allDiagrams = new HashMap<DiagramKey, Map<DiagramType, Diagram>>();
    private List<Task> satisfiedTasks = new ArrayList<Task>();

    public void satisfyTask(Task satisfiedTask) {
        if (!satisfiedTasks.contains(satisfiedTask)) {
            satisfiedTasks.add(satisfiedTask);
        }
    }

    public List<Task> getSatisfiedTasks() {
        return Collections.unmodifiableList(satisfiedTasks);
    }

    public boolean isSatisfied(Task task) {
        return satisfiedTasks.contains(task);
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setAssignmentID(int exerciseID) {
        this.assignmentID = exerciseID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

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

    public Map<DiagramKey, Map<DiagramType, Diagram>> allDiagrams() {
        return Collections.unmodifiableMap(allDiagrams);
    }

    /**
     * This sets the encoding flag for persistence of the state. 
     * This should ONLY be called by ExerciseStatePersistenceDelegate.
     * @param encoding
     */
    public void setEncoding(boolean encoding) {
        this.encoding = encoding;
    }
    private boolean encoding;

    /**
     * This is for persistence and deserialization. This should never be called directly!
     * @param state
     * @deprecated
     */
    @Deprecated
    public void initTasks(List<Task> tasks) {
        if (encoding) {
            return;
        }
        for (Task task : tasks) {
            satisfyTask(task);
        }
    }

    /**
     * This is for persistence and deserialization. This should never be called directly!
     * @param loads
     */
    @Deprecated
    public void initSymbolManager(List<AnchoredVector> loads) {
        if (encoding) {
            return;
        }
        for (AnchoredVector load : loads) {
            Exercise.getExercise().getState().getSymbolManager().addSymbol(load);
        }
    }

    /**
     * This is for persistence and deserialization. This should never be called directly!
     * @param key
     * @param type
     * @param state
     */
    @Deprecated
    public void initDiagram(DiagramKey key, DiagramType type, DiagramState state) {
        if (encoding) {
            return;
        }
        Diagram diagram = Exercise.getExercise().createNewDiagram(key, type);
        diagram.pushState(state);
        diagram.clearStateStack();
    }

    @Override
    public String toString() {
        String s = "ExerciseState: {\n";
        s += "  userId=" + userID + ", assignmentId=" + assignmentID + ",\n";
        s += "  satisfiedTasks=" + satisfiedTasks + ",\n";
        s += "  symbolManager=" + symbolManager + ",\n";
        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : allDiagrams.entrySet()) {
            DiagramKey key = entry.getKey();
            for (Map.Entry<DiagramType, Diagram> entry1 : entry.getValue().entrySet()) {
                DiagramType type = entry1.getKey();
                DiagramState diagramState = entry1.getValue().getCurrentState();
                s += "  [" + key + ", " + type + "] => " + diagramState + ",\n";
            }
        }
        s += "}";

        return s;
    }
}
