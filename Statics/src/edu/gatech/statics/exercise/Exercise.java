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
import edu.gatech.statics.CoordinateSystem;
import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.state.ExerciseState;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import edu.gatech.statics.ui.InterfaceConfiguration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Exercise {

    private static Exercise currentExercise;
    private DisplayConstants displayConstants;
    //private SymbolManager symbolManager;
    private ExerciseState state;

    public static Exercise getExercise() {
        return currentExercise;
    }

    public DisplayConstants getDisplayConstants() {
        return displayConstants;
    }

    public SymbolManager getSymbolManager() {
        return state.getSymbolManager();
    }

    // informational collection of world and diagram objects
    // meant to control functional aspect of exercize, not graphical or engine related
    abstract public Mode loadStartingMode();

    //abstract public UnitUtils getUnitUtils();
    abstract public InterfaceConfiguration createInterfaceConfiguration();
    private List<Task> tasks = new ArrayList<Task>();
    private List<Task> satisfiedTasks = new ArrayList<Task>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
    private List<TaskStatusListener> taskListeners = new ArrayList<TaskStatusListener>();

    public void addTaskListener(TaskStatusListener listener) {
        taskListeners.add(listener);
    }

    /**
     * This is meant to be called only once by StaticsApplication, after the exercise is loaded.
     * This method locks the schematic.
     */
    public void lockSchematic() {
        schematic.lock();
    }

    /**
     * This method is called when the user tries to submit the exercise.
     * Do nothing here, it should be overridden.
     */
    public void onSubmit() {
    }

    public void removeTaskListener(TaskStatusListener listener) {
        taskListeners.remove(listener);
    }
    private String name = "Exercise";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String description;

    public String getFullDescription() {

        StringBuffer taskString = new StringBuffer();
        //taskString.append("<ol>");
        taskString.append("<br>");
        for (Task task : tasks) {
            //taskString.append("<li>");
            taskString.append("->");
            taskString.append("<b>").append(task.getDescription()).append("</b>");
            if (task.isSatisfied()) {
                taskString.append(": DONE!!");
            }
            taskString.append("<br/>");
        //taskString.append("</li>");
        }
        //taskString.append("</ol>");

        return "<html><body>" +
                "<center><font size=\"6\">" + getName() + "</font></center>" +
                description + "<br/>" +
                taskString +
                "</body></html>";
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private Schematic schematic;

    public Schematic getSchematic() {
        return schematic;
    }
    private CoordinateSystem coordinateSystem = new CoordinateSystem();

    public CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(CoordinateSystem sys) {
        this.coordinateSystem = sys;
    }

    public Exercise() {
        this(new Schematic());
    }

    /** Creates a new instance of Exercize */
    public Exercise(Schematic world) {
        this.schematic = world;
        currentExercise = this;
        displayConstants = new DisplayConstants();

        state = new ExerciseState();
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
            if (type.getPriority() > maxPriority && state.getDiagram(key, type) != null) {
                maxDiagram = state.getDiagram(key, type);
                maxPriority = type.getPriority();
            }
        }

        return maxDiagram;
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
            } else if (!satisfiedTasks.contains(task)) {
                satisfiedTasks.add(task);
                for (TaskStatusListener listener : taskListeners) {
                    listener.taskSatisfied(task);
                }
            }
        }
        if (satisfied && !isExerciseFinished()) {
            finishExercise();
        }
    }
    private boolean finished = false;

    /**
     * Returns true if the exercise has been completed.
     * @return
     */
    public boolean isExerciseFinished() {
        return finished;
    }

    protected void finishExercise() {
        finished = true;
    }

    /**
     * This is a utility method for overriding classes to use
     * when loading textures to use in representations.
     * @param textureUrl
     * @return
     */
    protected Texture loadTexture(String textureUrl) {
        return loadTexture(textureUrl, Texture.FM_LINEAR, Texture.FM_LINEAR);
    }

    /**
     * This is a utility method for overriding classes to use
     * when loading textures to use in representations.
     * @param textureUrl
     * @param minFilter
     * @param maxFilter
     * @return
     */
    protected Texture loadTexture(String textureUrl, int minFilter, int maxFilter) {
        Texture texture = TextureManager.loadTexture(getClass().getClassLoader().getResource(textureUrl), minFilter, maxFilter);
        return texture;
    }
}
