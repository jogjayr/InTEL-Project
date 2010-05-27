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
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.tasks.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Map<String, List<Vector>> solvedReactions = new HashMap<String, List<Vector>>();
    private Map<DiagramKey, Map<DiagramType, Diagram>> allDiagrams = new HashMap<DiagramKey, Map<DiagramType, Diagram>>();
    private List<Task> satisfiedTasks = new ArrayList<Task>();
    private Map<String, Object> exerciseParameters = new HashMap<String, Object>();

    public Object getParameter(String name) {
        return exerciseParameters.get(name);
    }

    public void setParameter(String name, Object value) {
        exerciseParameters.put(name, value);
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(exerciseParameters);
    }

    public Map<String, List<Vector>> getSolvedReactions() {
        return Collections.unmodifiableMap(solvedReactions);
    }

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
     * Adds the reactions of the given connector to the exercise statate.
     * @param connector
     */
    public void addReactions(Connector connector) {
        if (!connector.isSolved()) {
            return;
        }
        solvedReactions.put(connector.getName(), connector.getReactions(connector.getBody1()));
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
     * @param reactions
     * @deprecated
     */
    @Deprecated
    public void initReactions(Map<String, List<Vector>> reactions) {
        if (encoding) {
            return;
        }
        this.solvedReactions.putAll(reactions);
        for (Map.Entry<String, List<Vector>> entry : reactions.entrySet()) {
            String connectorName = entry.getKey();
            List<Vector> connectorReactions = entry.getValue();

            if (connectorReactions.isEmpty()) {
                Exception ex = new IllegalArgumentException("attempting to call initReactions with an empty list!");
                Logger.getLogger("Statics").log(Level.SEVERE, "Reactions for connector " + connectorName + " are empty!", ex);
            // allow program to continue, but do not attempt to add the reaction.
            } else {
                Connector connector = (Connector) Exercise.getExercise().getSchematic().getByName(connectorName);
                connector.solveReaction(connector.getBody1(), connectorReactions);
            }
        }
    }

    /**
     * This is for persistence and deserialization. This should never be called directly!
     * @param state
     * @deprecated
     */
    @Deprecated
    public void initParameters(Map<String, Object> parameters) {
        if (encoding) {
            return;
        }

        Logger.getLogger("Statics").info("initParameters: setting parameters: " + parameters);
        Exercise.getExercise().getState().exerciseParameters.putAll(parameters);
        Exercise.getExercise().applyParameters();
    }

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
        Logger.getLogger("Statics").info("initTasks: setting tasks: " + tasks);
        for (Task task : tasks) {
            satisfyTask(task);
        }
        Exercise.getExercise().testTasks();
    }

    /**
     * This is for persistence and deserialization. This should never be called directly!
     * @param loads
     */
//    @Deprecated
//    public void initSymbolManager(List<AnchoredVector> loads) {
//        if (encoding) {
//            return;
//        }
//        Logger.getLogger("Statics").info("initSymbolManager: adding loads to symbol manager: " + loads);
//        for (AnchoredVector load : loads) {
//            Exercise.getExercise().getState().getSymbolManager().addSymbol(load);
//        }
//    }

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
        Logger.getLogger("Statics").info("initDiagram: key: " + key + " type: " + type + " state: " + state);
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
