/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Exercize.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import com.jme.image.Texture;
import com.jme.util.TextureManager;
import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.state.ExerciseState;
import edu.gatech.statics.modes.description.Description;
import edu.gatech.statics.modes.description.DescriptionMode;
import edu.gatech.statics.modes.findpoints.FindPointsMode;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import edu.gatech.statics.ui.InterfaceConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class representing an exercise. 
 * @author Calvin Ashmore
 */
public abstract class Exercise {

    private static Exercise currentExercise;
    private DisplayConstants displayConstants;
    //private SymbolManager symbolManager;
    private ExerciseState state;

    /**
     * Create a description for this exercise. This method will only be called once.
     * @return
     */
    public abstract Description getDescription();

    /**
     * Returns exercise completion status
     * @return  1: not started
     *          2: started
     *          3: partially completed
     *          4: solved
     */
    public int getCompletionStatus() {
        // 1: not started
        // 2: started
        // 3: partially completed
        // 4: solved

        int totalTasks = getTasks().size();
        int completedTasks = 0;
        for (Task task : getTasks()) {
            if (getState().isSatisfied(task)) {
                completedTasks++;
            }
        }
        if (completedTasks == totalTasks) {
            return 4;
        } else if (completedTasks > 1) {
            return 3;
        } else {
            return 2;
        }
    }

    public ExerciseState getState() {
        return state;
    }
    
    final private long sessionID;

    /**
     * The exercise session is used for logging. The session is not part of the state,
     * and lives in this class. It should be generated when the exercise is constructed,
     * according to the system time and a random number.
     */
    public long getSessionID() {
        return sessionID;
    }

    /**
     * Getter
     * @return
     */
    public static Exercise getExercise() {
        return currentExercise;
    }

    /**
     * Getter
     * @return
     */
    public DisplayConstants getDisplayConstants() {
        return displayConstants;
    }

    /**
     * Getter
     * @return
     */
    public SymbolManager getSymbolManager() {
        return state.getSymbolManager();
    }

    // informational collection of world and diagram objects
    // meant to control functional aspect of exercize, not graphical or engine related
    abstract public Mode loadStartingMode();

    //abstract public UnitUtils getUnitUtils();
    abstract public InterfaceConfiguration createInterfaceConfiguration();
    private List<Task> tasks = new ArrayList<Task>();

    /**
     * Adds task to the exercise. Calls tasksChanged on taskStatusListener
     * @param task
     */
    public void addTask(Task task) {
        tasks.add(task);
        for (TaskStatusListener taskStatusListener : taskListeners) {
            taskStatusListener.tasksChanged();
        }
    }

    /**
     * Returns the live array with the tasks in it. Accessing this can allow modification
     * or existing tasks, adding tasks, or removing them.
     * @return
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds task with name name
     * @param name
     * @return 
     */
    public Task getTask(String name) {
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }
    private List<TaskStatusListener> taskListeners = new ArrayList<TaskStatusListener>();

    /**
     * Adds listener to taskListeners
     * @param listener
     */
    public void addTaskListener(TaskStatusListener listener) {
        taskListeners.add(listener);
    }

    /**
     * Remvoes listener from taskListeners
     * @param listener
     */
    public void removeTaskListener(TaskStatusListener listener) {
        taskListeners.remove(listener);
    }

    private String appletExerciseName = "";
    private int problemID;

    /**
     * This method sets a name that is recorded specifically for web recording.
     * @param problemName
     */
    public void setAppletExerciseName(String problemName) {
        this.appletExerciseName = problemName;
    }

    /**
     * Getter
     * @return
     */
    public String getAppletExerciseName() {
        return appletExerciseName;
    }

    /**
     * Getter
     * @return
     */
    public int getProblemID() {
        return problemID;
    }

    /**
     * Getter
     * @param problemID
     */
    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }

    private Schematic schematic;

    /**
     * Getter
     * @return
     */
    public Schematic getSchematic() {
        return schematic;
    }
//    private CoordinateSystem coordinateSystem = new CoordinateSystem();
//
//    public CoordinateSystem getCoordinateSystem() {
//        return coordinateSystem;
//    }
//
//    public void setCoordinateSystem(CoordinateSystem sys) {
//        this.coordinateSystem = sys;
//    }

    public Exercise() {
        this(new Schematic());
    }

    /**
     * Creates a new instance of Exercise
     * @param world 
     */
    public Exercise(Schematic world) {
        this.schematic = world;
        currentExercise = this;
        displayConstants = new DisplayConstants();

        state = new ExerciseState();

        long tempSessionID = System.currentTimeMillis();
        tempSessionID = tempSessionID ^ (new Random().nextInt() << 32);
        this.sessionID = tempSessionID;
    }

    /**
     * This is the method that subclasses of exercise should override to create new types of diagrams.
     * It is also the method that should be overridden to modify support for existing types of diagrams,
     * for subclasses to provide their own implementations of standard diagram types.
     * @param key
     * @param type
     */
    abstract protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type);

    /**
     * This is the publically accessible way to create new diagrams.
     * @param key
     * @param type
     * @return
     */
    public final Diagram createNewDiagram(DiagramKey key, DiagramType type) {
        Diagram oldDiagram = state.getDiagram(key, type);
        if (oldDiagram != null) {
            return oldDiagram;
        }
        Diagram diagram = createNewDiagramImpl(key, type);
        state.storeDiagram(diagram);
        return diagram;
    }

    /**
     * Returns true if the exercise can support a mode for the type of diagram provided.
     * This method is useful as a test case.
     * @param type
     * @return
     */
    abstract public boolean supportsType(DiagramType type);

    /**
     * Attempts to retrieve the specified diagram. If it does not exist, null is returned.
     * This method does not attempt to create a new diagram if one is missing.
     * @param key
     * @param type
     * @return
     */
    public final Diagram getDiagram(DiagramKey key, DiagramType type) {
        return state.getDiagram(key, type);
    }

    /**
     * Attempts to find a diagram with the specified key of the greatest priority.
     * If no diagrams have been created with this key, null is returned.
     * @param key
     * @return
     */
    public final Diagram getRecentDiagram(DiagramKey key) {

        Diagram maxDiagram = null;
        int maxPriority = 0;

        // go through all diagram types and pick out the best one.
        for (DiagramType type : DiagramType.allTypes()) {
            if (type.getPriority() > maxPriority &&
                    state.getDiagram(key, type) != null) {
                maxDiagram = state.getDiagram(key, type);
                maxPriority = type.getPriority();
            }
        }

        return maxDiagram;
    }

    /**
     * Initializes the parameters used by the exercise. Parameters are randomized
     * and other variables that change when the applet is run. This should call
     * getState().setParameter() to actually set its values. This method should
     * not actually affect the schematic itself. That should be done in applyParameters().
     */
    public void initParameters() {
    }

    /**
     * Applies the parameters from the given state. This must take the parameters
     * from the exercise state and apply them to the schematic of the problem.
     */
    public void applyParameters() {
    }

    /**
     * This method does initial loading before the interface is created.
     * You should put methods defining the precision and suffixes of units here,
     * as well as display scales, and any problem descriptions.
     */
    public void initExercise() {
    }

    /**
     * This method loads all of the objects and constructs the actual problem
     * itself. 
     * For file based exercises, this can involve deserialization of saved information.
     * For code based exercises, this method must be overridden to programmatically
     * create all the objects that belong within the exercise.
     */
    public void loadExercise() {
    }

    /**
     * Called after the exercise is loaded.
     * This is a good point for overriding classes to put special UI elements.
     */
    public void postLoadExercise() {
    }

    /**
     * This tests if each task is satisfied.
     * For tasks that are satisfied, the task listeners are triggered.
     * Also calls finishExercise() if the exercise is solved but not finished yet.
     * @return true if tasks are satisfied
     */
    public void testTasks() {
        boolean satisfied = true;
        for (Task task : tasks) {
            if (!task.isSatisfied()) {
                satisfied = false;
            } else if (!getState().isSatisfied(task)) {
                getState().satisfyTask(task);
                StaticsApplication.getApp().stateChanged();
                for (TaskStatusListener listener : taskListeners) {
                    listener.taskSatisfied(task);
                }
            }
        }
        if (satisfied && !finished) {
            finishExercise();
        }
    }
    private boolean finished = false;

    /**
     * Returns true if the exercise has been completed.
     * @return
     */
    public boolean isExerciseFinished() {
        testTasks();
        return finished;
    }

    protected void finishExercise() {
        finished = true;

        // don't show popup if the tasks are empty
        if (!tasks.isEmpty()) {
            ExerciseUtilities.showCompletionPopup();
        }
    }

    /**
     * This is a utility method for overriding classes to use
     * when loading textures to use in representations.
     * @param textureUrl
     * @return
     */
    protected Texture loadTexture(String textureUrl) {
        return loadTexture(textureUrl, Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);
    }

    /**
     * This is a utility method for overriding classes to use
     * when loading textures to use in representations.
     * @param textureUrl
     * @param minFilter
     * @param maxFilter
     * @return
     */
    protected Texture loadTexture(String textureUrl, Texture.MinificationFilter minFilter, Texture.MagnificationFilter maxFilter) {
        Texture texture = TextureManager.loadTexture(getClass().getClassLoader().getResource(textureUrl), minFilter, maxFilter);
        return texture;
    }

    /**
     * Loads exercise description mode
     */
    public void loadDescriptionMode() {
        DescriptionMode.instance.load();
    }

    /**
     * This method retrieves the diagram that is appropriate to display when the given
     * key and type are loaded. This does not actually load the diagram, just retrieves them,
     * and in the case that no appropriate diagram exists, null is returned.
     * This method exists because when the key is null, usually the select diagram should be returned,
     * but not always. If the key is not null, this method returns getRecentDiagram(key)
     * @param key
     * @param type
     * @return
     */
    public Diagram getAppropriateDiagram(DiagramKey key, DiagramType type) {
        if (key == null) {
            if (canSwitchToNullKeyDiagramType(type)) {
                return getDiagram(key, type);
            } else {
                return getDiagram(null, SelectMode.instance.getDiagramType());
            }
        } else {
            return getRecentDiagram(key);
        }
    }

    /**
     * This method only applies to diagram types who have null as a key.
     * This typically permits the description and select modes, but other types may also be allowed.
     * Subclasses of exercise must override this if they wish to include additional diagram types.
     * @return
     */
    public boolean canSwitchToNullKeyDiagramType(DiagramType type) {
        return type == DescriptionMode.instance.getDiagramType() ||
                type == SelectMode.instance.getDiagramType() ||
                type == ZFMMode.instance.getDiagramType() ||
                type == FindPointsMode.instance.getDiagramType();
    }
}
