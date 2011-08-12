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
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.modes.centroid.ui.CentroidInterfaceConfiguration;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;

/**
 * The specific implementation of the OrdinaryExercise for centroids. It is
 * where the diagram gets set to the CentroidDiagram, the selectDiagram gets set
 * to the CentroidSelectDiagram, and the AbstractInterfaceConfiguration gets set
 * to the CentroidInterfaceConfiguration.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
abstract public class CentroidExercise extends OrdinaryExercise {

    public CentroidExercise() {
    }

    public CentroidExercise(Schematic schematic) {
        super(schematic);
    }

    /**
     * Creates a CentroidDiagram
     * @param key 
     * @param type
     * @return
     */
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == CentroidMode.instance.getDiagramType()) {
            return createCentroidDiagram((CentroidBody) key);
        }

        return super.createNewDiagramImpl(key, type);
    }

    /**
     * 
     * @param body
     * @return
     */
    protected CentroidDiagram createCentroidDiagram(CentroidBody body) {
        return new CentroidDiagram(body);
    }

    /**
     * Creates a CentroidSelectDiagram
     * @return
     */
    @Override
    protected SelectDiagram createSelectDiagram() {
        return new CentroidSelectDiagram();
    }

    /**
     * 
     * @return
     */
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new CentroidInterfaceConfiguration();
    }

}
