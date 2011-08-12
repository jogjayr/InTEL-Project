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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiagramType is a simple class representing the type of a diagram. Ideally it should
 * work like an enum, but it is meant to be created by new types of modes. Diagrams
 * should be uniquely referentiable by their type and their key. 
 * @author Calvin Ashmore
 */
final public class DiagramType {

    private static Map<String, DiagramType> allTypes = new HashMap();

    public static DiagramType getType(String name) {
        return allTypes.get(name);
    }

    /**
     * Returns a list of all of the diagram types currently known.
     * @return
     */
    public static List<DiagramType> allTypes() {
        return new ArrayList<DiagramType>(allTypes.values());
    }

    /**
     * DiagramTypes must ALL be created before the main loop of the application.
     * DiagramTypes should be created ONLY within the Mode. This way, when mode instances 
     * are loaded, (at exercise startup) the diagram types are all created.
     * The variable priority represents the relative progress of the diagram, with lower numbers
     * meaning early progress, and higher numbers being the most progress.
     * 
     * @param name
     * @param priority an int denoting the priority of the type. Should be positive. 
     * @return
     */
    public static DiagramType create(String name, int priority) {
        if (getType(name) != null) {
            return getType(name);
        }
        DiagramType newType = new DiagramType(name, priority);
        allTypes.put(name, newType);
        return newType;
    }
    private String name;
    private int priority;

    /**
     * The name of the diagram type is the way that it is accessed normally.
     * The name should be relatively simple. "fbd" for FreeBodyDiagram, "equation"
     * for EquationDiagram, etc.
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * This gives the priority of a diagram. The most straightforward explanation
     * of the priority is that it represents which diagram is the greatest ranking, or
     * has the furthest progress for a given key.
     * @return Priority of diagrm
     */
    public int getPriority() {
        return priority;
    }

    private DiagramType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}
