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
package edu.gatech.statics.objects;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class Load extends VectorObject {

    public Load(Load load) {
        super(load);
    }

    public Load(AnchoredVector vector) {
        super(vector);
    }
    private List<VectorListener> listeners = new ArrayList<VectorListener>();

    public void addListener(VectorListener listener) {
        listeners.add(listener);
    }

    public void removeListener(VectorListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setVectorValue(Vector3bd value) {

        Vector3bd oldValue = getVectorValue();
        super.setVectorValue(value);

        for (VectorListener listener : listeners) {
            listener.valueChanged(oldValue);
        }
    }

    @Override
    abstract public Load clone();
}
