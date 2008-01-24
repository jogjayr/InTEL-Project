/*
 * Exercize.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import com.jme.image.Texture;
import com.jme.util.TextureManager;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.modes.fbd.FBDWorld;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Exercise {

    // informational collection of world and diagram objects
    // meant to control functional aspect of exercize, not graphical or engine related
    
    //private UnitUtils units = new UnitUtils();
    //public void setUnits(UnitUtils units) {this.units = units;}
    //public UnitUtils getUnits() {return units;}
    
    abstract public Mode getFirstMode();
    
    private List<Task> tasks = new ArrayList<Task>();
    private List<Task> satisfiedTasks = new ArrayList<Task>();
    public void addTask(Task task) {
        tasks.add(task);
    }
    public List<Task> getTasks() {return tasks;}
    
    private List<TaskStatusListener> taskListeners = new ArrayList<TaskStatusListener>();
    public void addTaskListener(TaskStatusListener listener) {taskListeners.add(listener);}
    public void removeTaskListener(TaskStatusListener listener) {taskListeners.remove(listener);}
    
    private String name = "Exercise";
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    private String description;
    public String getFullDescription() {
        
        StringBuffer taskString = new StringBuffer();
        //taskString.append("<ol>");
        taskString.append("<br>");
        for(Task task : tasks) {
            //taskString.append("<li>");
            taskString.append("->");
            taskString.append("<b>").append(task.getDescription()).append("</b>");
            if(task.isSatisfied())
                taskString.append(": DONE!!");
            taskString.append("<br/>");
            //taskString.append("</li>");
        }
        //taskString.append("</ol>");
        
        return  "<html><body>" +
                "<center><font size=\"6\">"+getName()+"</font></center>"+
                description + "<br/>" +
                taskString +
                "</body></html>";
    }
    public void setDescription(String description) {this.description = description;}
    
    private ExerciseWorld world;
    private List<FBDWorld> diagrams = new ArrayList<FBDWorld>();
    
    public ExerciseWorld getWorld() {return world;}
    public List<FBDWorld> getDiagrams() {return Collections.unmodifiableList(diagrams);}
    
    /** Creates a new instance of Exercize */
    public Exercise(ExerciseWorld world) {
        this.world = world;
        world.setExercise(this);
    }
    
    public FBDWorld constructFBD(List<Body> bodies) {
        
        for(FBDWorld fbd : diagrams)
            if(fbd.getObservedBodies().containsAll(bodies) && bodies.containsAll(fbd.getObservedBodies()))
                return fbd;
        
        FBDWorld fbd = world.constructFBD(bodies);
        diagrams.add(fbd);
        return fbd;
    }
    
    /**
     * this handles things before the interface has been constructed:
     * should be material such as doing display and problem manipulation.
     */
    public void initExercise() {}
    
    /**
     * for file based, can involve deserialization
     * for code based can just create objects as is.
     */
    public void loadExercise() {}

    public void postLoadExercise() {}
    
    /**
     * This tests if each task is satisfied.
     * For tasks that are satisfied, the task listeners are triggered
     * @return true if tasks are satisfied
     */
    public boolean testExerciseSolved() {
        boolean satisfied = true;
        for(Task task : tasks) {
            if(!task.isSatisfied()) {
                satisfied = false;
            } else if(!satisfiedTasks.contains(task)) {
                satisfiedTasks.add(task);
                for(TaskStatusListener listener : taskListeners)
                    listener.taskSatisfied(task);
            }
        }
        return satisfied;
    }
    
    private boolean finished = false;
    public boolean isExerciseFinished() {return finished;}
    public void finishExercise() {finished = true;}
    
    // some utility functions
    
    /*private List<Texture> exerciseTextures;
    
    void shutdownExercise() {
        for(Texture texture : exerciseTextures)
            TextureManager.clearCache();
    }*/
    
    protected Texture loadTexture(String textureUrl) {
        return loadTexture(textureUrl, Texture.FM_LINEAR, Texture.FM_LINEAR);
    }
    
    protected Texture loadTexture(String textureUrl, int minFilter, int maxFilter) {
        Texture texture = TextureManager.loadTexture(getClass().getClassLoader().getResource(textureUrl), minFilter, maxFilter);
        //System.out.println(texture+" "+texture.getTextureId()+" "+texture.getTextureKey());
        return texture;
    }
}
