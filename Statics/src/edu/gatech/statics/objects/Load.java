/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.math.Vector;
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

    public Load(Point anchor, Vector vector) {
        super(anchor, vector);
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
