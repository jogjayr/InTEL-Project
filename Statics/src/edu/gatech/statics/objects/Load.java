/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Vector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Load extends VectorObject {

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
    public void setVectorValue(Vector3f value) {

        Vector3f oldValue = getVectorValue();
        super.setVectorValue(value);

        for (VectorListener listener : listeners) {
            listener.valueChanged(oldValue);
        }
    }
}
