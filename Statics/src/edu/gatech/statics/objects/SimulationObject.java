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
 * SimulationObject.java
 *
 * Created on June 4, 2007, 2:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.*;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SimulationObject {
    // given objects appear in the ExerciseWorld, and are assumed to be common
    // in other words, they are not constructed by the student.
    // Exercise world automatically sets given to true when added, so if a student
    // constructs something in an FBD, given defaults to false.
    //private boolean given = false;

    private String name = "";
    private Vector3f translation = new Vector3f();
    private Matrix3f rotation = new Matrix3f();
    private Vector3f selectionHandle = new Vector3f();
    private float selectRadius;
    private List<Representation> representations = new ArrayList();
    private Map<RepresentationLayer, List<Representation>> representationMap = new HashMap<RepresentationLayer, List<Representation>>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sometimes we want to get the logical center of the object, rather than the origin of its coordinate system
     * epecially in the case of vectors. This is useful for finding the point at which to apply a label.
     */
    public Vector3f getDisplayCenter() {
        return getTranslation();
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setRotation(Matrix3f rotation) {
        this.rotation = rotation;
    }

    public Matrix3f getRotation() {
        return rotation;
    }

    public void setSelectionHandle(Vector3f selectionHandle) {
        this.selectionHandle = selectionHandle;
    }

    public Vector3f getSelectionHandle() {
        return selectionHandle;
    }

    public void setSelectRadius(float selectRadius) {
        this.selectRadius = selectRadius;
    }

    public float getSelectRadius() {
        return selectRadius;
    }

    public void addRepresentation(Representation r) {
        representations.add(r);
        List<Representation> reps = representationMap.get(r.getLayer());
        if (reps == null) {
            representationMap.put(r.getLayer(), reps = new ArrayList<Representation>());
        }
        reps.add(r);
    }

    public void removeRepresentation(Representation r) {
        representations.remove(r);
        List<Representation> reps = representationMap.get(r.getLayer());
        if (reps != null) // should not fail this...
        {
            reps.remove(r);
        }
    }

    public List<Representation> allRepresentations() {
        return Collections.unmodifiableList(representations);
    }

    public List<Representation> getRepresentation(RepresentationLayer layer) {
        List<Representation> reps = representationMap.get(layer);
        if (reps == null) {
            return Collections.emptyList();
        }
        return reps;
    }

    /** Creates a new instance of SimulationObject */
    public SimulationObject() {
    }

    public void update() {
        for (Representation r : allRepresentations()) {
            r.update();
            //r.updateGeometricState(0, true);
            //r.updateRenderState();
            //r.updateModelBound();
        }
    }

    abstract public void createDefaultSchematicRepresentation();

    public void initializePhysics() {
    }
    private boolean selectable = true;
    private boolean selected = false;
    private boolean hover = false;
    private boolean grayed = false;

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    /*public void setSelected(boolean selected) {
    
    if(selectable || !selected) {
    setDisplaySelected(selected);
    enableManipulators(selected);
    }
    this.selected = selected;
    }*/
    /*public void enableManipulators(boolean enabled) {
    for(Manipulator m : manipulators) {
    m.setEnabled(enabled);
    m.setEnabledGlobally(enabled);
    }
    }*/
    public boolean isDisplayGrayed() {
        return grayed;
    }



    public void setDisplayGrayed(boolean grayed) {

        if (this.grayed != grayed) {
            for (Representation rep : representations) {
                rep.setDisplayGrayed(grayed);
            }
        }
        this.grayed = grayed;
    }

    public void setDisplaySelected(boolean selected) {

        if (this.selected != selected) {
            for (Representation rep : representations) {
                rep.setDisplaySelected(selected);
            }
        }
        this.selected = selected;
    }

    public void setDisplayHighlight(boolean hover) {

        if (selectable || !hover) {
            if (this.hover != hover) {
                for (Representation rep : representations) {
                    rep.setDisplayHighlight(hover);
                }
            }
            this.hover = hover;
        }
    }

    public void destroy() {
        // destroys and detaches all parts of this object.
        representations.clear();
    }

    public String getLabelText() {
        return getName();
    }

    /**
     * toString is overridden to return the name of the simulation object.
     * Subclasses should override this if they want to provide more detailed information.
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }
}
