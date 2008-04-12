/*
 * Exercize.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.*;
import com.jme.image.Texture;
import com.jme.util.TextureManager;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Exercise {

    private static Exercise currentExercise;

    public static Exercise getExercise() {
        return currentExercise;
    }

    public float getDrawScale() {
        return 1;
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
    private SelectDiagram selectDiagram;
    private Map<BodySubset, FreeBodyDiagram> freeBodyDiagrams = new HashMap();
    private Map<BodySubset, EquationDiagram> equationDiagrams = new HashMap();

    public Schematic getSchematic() {
        return schematic;
    }

    public List<FreeBodyDiagram> getFreeBodyDiagrams() {
        return new ArrayList<FreeBodyDiagram>(freeBodyDiagrams.values());
    }

    public List<EquationDiagram> getEquationDiagrams() {
        return new ArrayList<EquationDiagram>(equationDiagrams.values());
    }
    private CoordinateSystem coordinateSystem = new CoordinateSystem();

    public CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(CoordinateSystem sys) {
        this.coordinateSystem = sys;
    }

    public SelectDiagram getSelectDiagram() {
        if (selectDiagram == null) {
            selectDiagram = new SelectDiagram();
        }
        return selectDiagram;
    }

    public Exercise() {
        this(new Schematic());
    }

    /** Creates a new instance of Exercize */
    public Exercise(Schematic world) {
        this.schematic = world;
        currentExercise = this;
    }

    public FreeBodyDiagram getFreeBodyDiagram(BodySubset bodySubset) {

        //BodySubset bodySubset = new BodySubset(bodies);
        FreeBodyDiagram fbd = freeBodyDiagrams.get(bodySubset);

        if (fbd == null) {
            fbd = new FreeBodyDiagram(bodySubset);
            freeBodyDiagrams.put(bodySubset, fbd);
        }
        return fbd;
    }

    public EquationDiagram getEquationDiagram(BodySubset bodySubset) {
        FreeBodyDiagram fbd = getFreeBodyDiagram(bodySubset);
        if (!fbd.isSolved()) {
            throw new IllegalStateException("Free Body Diagram " + fbd + " is not solved!");
        }
        //BodySubset bodySubset = fbd.getBodySubset();
        EquationDiagram eq = equationDiagrams.get(bodySubset);

        if (eq == null) {
            eq = new EquationDiagram(bodySubset);
            equationDiagrams.put(bodySubset, eq);
        }
        return eq;
    }

    public Diagram getRecentDiagram(BodySubset bodies) {
        if (bodies == null) {
            return getSelectDiagram();
        }
        if (equationDiagrams.get(bodies) != null) {
            return equationDiagrams.get(bodies);
        }
        if (freeBodyDiagrams.get(bodies) != null) {
            return freeBodyDiagrams.get(bodies);
        }
        throw new IllegalStateException("Cannot select recent diagram for: " + bodies);
    }

    /**
     * I am not sure if this is the best place to put this method.
     * It goes through the active diagrams and activates panels accordingly.
     * This place is also the most open to extension if new modes are added.
     * @param bodies
     */
    public void enableTabs(BodySubset bodies) {
        ApplicationBar applicationBar = InterfaceRoot.getInstance().getApplicationBar();
        applicationBar.disableAllTabs();
        applicationBar.enableTab(SelectMode.instance, true);
        if (equationDiagrams.get(bodies) != null) {
            applicationBar.enableTab(EquationMode.instance, true);
        }
        if (freeBodyDiagrams.get(bodies) != null) {
            applicationBar.enableTab(FBDMode.instance, true);
        }
    }

    /**
     * this handles things before the interface has been constructed:
     * should be material such as doing display and problem manipulation.
     */
    public void initExercise() {
    }

    /**
     * for file based, can involve deserialization
     * for code based can just create objects as is.
     */
    public void loadExercise() {
    }

    /**
     * Called after the exercise is loaded.
     */
    public void postLoadExercise() {
    }

    /**
     * This tests if each task is satisfied.
     * For tasks that are satisfied, the task listeners are triggered.
     * Also calls finishExercise() if the exercise is solved but not finished yet.
     * @return true if tasks are satisfied
     */
    public boolean testExerciseSolved() {
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

        return satisfied;
    }
    private boolean finished = false;

    public boolean isExerciseFinished() {
        return finished;
    }

    protected void finishExercise() {
        finished = true;
    }

    protected Texture loadTexture(String textureUrl) {
        return loadTexture(textureUrl, Texture.FM_LINEAR, Texture.FM_LINEAR);
    }

    protected Texture loadTexture(String textureUrl, int minFilter, int maxFilter) {
        Texture texture = TextureManager.loadTexture(getClass().getClassLoader().getResource(textureUrl), minFilter, maxFilter);
        //System.out.println(texture+" "+texture.getTextureId()+" "+texture.getTextureKey());
        return texture;
    }
}
