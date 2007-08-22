/*
 * SimulationObject.java
 *
 * Created on June 4, 2007, 2:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.manipulators.Manipulator;
import edu.gatech.statics.util.SelectionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SimulationObject {
    
    //private World world;
    //public World getWorld() {return world;}
    
    // given objects appearr in the ExerciseWorld, and are assumed to be common
    // in other words, they are not constructed by the student.
    // Exercise world automatically sets given to true when added, so if a student
    // constructs something in an FBD, given defaults to false.
    private boolean given = false;
    public boolean isGiven() {return given;}
    public void setGiven(boolean given) {this.given = given;}
    
    private String name = "";
    private String notes = null;
    
    private Vector3f translation = new Vector3f();
    private Matrix3f rotation = new Matrix3f();
    
    private Vector3f selectionHandle = new Vector3f();
    private float selectRadius;
    
    private List<Representation> representations = new ArrayList();
    private List<Manipulator> manipulators = new ArrayList();
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    
    // sometimes we want to get the logical center of the object, rather than the origin of its coordinate system
    // epecially in the case of vectors. Override for these cases
    public Vector3f getDisplayCenter() {
        return getTranslation();
    }
    
    public void setTranslation(Vector3f translation) {this.translation = translation;}
    public Vector3f getTranslation() {return translation;}
    
    public void setRotation(Matrix3f rotation) {this.rotation = rotation;}
    public Matrix3f getRotation() {return rotation;}
    
    public void setSelectionHandle(Vector3f selectionHandle) {this.selectionHandle = selectionHandle;}
    public Vector3f getSelectionHandle() {return selectionHandle;}
    
    public void setSelectRadius(float selectRadius) {this.selectRadius = selectRadius;}
    public float getSelectRadius() {return selectRadius;}
    
    /*private List<SelectionListener> selectionListeners = new ArrayList();
    public void addSellectionListener(SelectionListener listener) {selectionListeners.add(listener);}
    public void removeSellectionListener(SelectionListener listener) {selectionListeners.remove(listener);}
    public void removeAllSellectionListeners() {selectionListeners.clear();}*/
    
    public void addRepresentation(Representation r) {representations.add(r);}
    public void removeRepresentation(Representation r) {representations.remove(r);}
    public List<Representation> allRepresentations() {return Collections.unmodifiableList(representations);}
    
    public List<Representation> getRepresentation(RepresentationLayer layer) {
        // quick and dirty way, would like to use a map, but may be impractical
        List<Representation> allType = new ArrayList();
        for(Representation r : representations)
            if(r.getLayer() == layer)
                allType.add(r);
        return allType;
    }
    
    public void addManipulator(Manipulator m) {manipulators.add(m);}
    public void removeManipulator(Manipulator m) {manipulators.remove(m);}
    public List<Manipulator> allManipulators() {return Collections.unmodifiableList(manipulators);}
    
    /** Creates a new instance of SimulationObject */
    public SimulationObject(/*World world*/) {
        //this.world = world;
    }
    
    public void update() {
        for(Representation r : allRepresentations()) {
            r.update();
            r.updateGeometricState(0, true);
            r.updateRenderState();
            r.updateModelBound();
            r.updateWorldBound();
        }
    }
    
    abstract public void createDefaultSchematicRepresentation();
    
    public void initializePhysics() {}
    
    private boolean selectable = true;
    private boolean selected = false;
    private boolean hover = false;
    private boolean grayed = false;
    
    public void setSelectable(boolean selectable) {this.selectable = selectable;}
    public boolean isSelectable() {return selectable;}
    
    /*public void setSelected(boolean selected) {
        
        if(selectable || !selected) {
            setDisplaySelected(selected);
            enableManipulators(selected);
        }
        this.selected = selected;
    }*/
    
    public void enableManipulators(boolean enabled) {
        for(Manipulator m : manipulators) {
            m.setEnabled(enabled);
            m.setEnabledGlobally(enabled);
        }
    }
    
    public boolean isDisplayGrayed() {return grayed;}
    
    public void setDisplayGrayed(boolean grayed) {
        
        if(this.grayed != grayed) {
            for(Representation rep : representations)
                rep.setDisplayGrayed(grayed);
        }
        this.grayed = grayed;
    }
    
    public void setDisplaySelected(boolean selected) {
        
        if(this.selected != selected) {
            for(Representation rep : representations)
                rep.setDisplaySelected(selected);
        }
        this.selected = selected;
    }
    public void setDisplayHighlight(boolean hover) {
        
        if(selectable || !hover) {
            if(this.hover != hover) {
                for(Representation rep : representations)
                    rep.setDisplayHighlight(hover);
            }
            this.hover = hover;
        }
    }
    
    public void destroy() {
        // destroys and detaches all parts of this object.
        enableManipulators(false);
        representations.clear();
        manipulators.clear();
    }

    public String getDescription() {
        return "SimulationObject";
    }

    public String getLabelText() {
        return getName();
    }
    
}