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
package edu.gatech.statics.exercise.persistence;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.state.ExerciseState;
import edu.gatech.statics.math.Vector;
import edu.gatech.newbeans.DefaultPersistenceDelegate;
import edu.gatech.newbeans.Encoder;
import edu.gatech.newbeans.Statement;
import edu.gatech.statics.exercise.state.DiagramState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseStatePersistenceDelegate extends DefaultPersistenceDelegate {

    /**
     * Writes exercise state to out. This includes symbolic loads, symbolic constants,
     * solved reactions, diagram list and diagram states of oldInstance.
     * @param type
     * @param oldInstance
     * @param newInstance
     * @param out
     */
    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {

        ExerciseState oldState = (ExerciseState) oldInstance;
        ExerciseState newState = (ExerciseState) newInstance;
        newState.setEncoding(true);

        // write out the SymbolManager
        //out.writeStatement(new Statement(oldState, "initSymbolManager", new Object[]{new ArrayList(oldState.getSymbolManager().getLoads())}));
        out.writeStatement(new Statement(oldState, "initSymbolManager", new Object[]{
                    new ArrayList(oldState.getSymbolManager().getSymbolicLoads()),
                    new ArrayList(oldState.getSymbolManager().getSymbolicConstants())}));

        // write out the exerciseParameters
        out.writeStatement(new Statement(oldState, "initParameters", new Object[]{new HashMap(oldState.getParameters())}));

        // write out the solved reactions
        Map<String, List<Vector>> solvedReactions = oldState.getSolvedReactions();
        Map<String, List<Vector>> solvedReactionsCopy = new HashMap<String, List<Vector>>();
        for (Map.Entry<String, List<Vector>> entry : solvedReactions.entrySet()) {
            solvedReactionsCopy.put(entry.getKey(), new ArrayList<Vector>(entry.getValue()));
        }
        out.writeStatement(new Statement(oldState, "initReactions", new Object[]{new HashMap(solvedReactionsCopy)}));

        // build a list of all of the diagrams.
        List<Diagram> allDiagrams = new ArrayList<Diagram>();

        for (Map.Entry<DiagramKey, Map<DiagramType, Diagram>> entry : oldState.allDiagrams().entrySet()) {
            Map<DiagramType, Diagram> map = entry.getValue();
            for (Map.Entry<DiagramType, Diagram> entry1 : map.entrySet()) {
                Diagram diagram = entry1.getValue();

                allDiagrams.add(diagram);
            }
        }

        // sort the list
        Collections.sort(allDiagrams, new Comparator<Diagram>() {

            public int compare(Diagram o1, Diagram o2) {
                return o1.getType().getPriority() - o2.getType().getPriority();
            }
        });

        // write out the diagrams
        for (Diagram diagram : allDiagrams) {
            DiagramKey diagramKey = diagram.getKey();
            DiagramType diagramType = diagram.getType();
            DiagramState diagramState = diagram.getCurrentState();
            out.writeStatement(new Statement(oldState, "initDiagram", new Object[]{diagramKey, diagramType, diagramState}));
        }

        super.initialize(type, oldInstance, newInstance, out);
        newState.setEncoding(false);

    }
}
