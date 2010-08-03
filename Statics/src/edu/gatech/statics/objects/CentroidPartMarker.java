/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.representations.CentroidPartLabel;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartMarker extends SimulationObject implements ResolvableByName{

    private Point pointCenter;
    private CentroidPartObject cpo;
    /**
     * For persistence, do not call directly.
     * @deprecated
     */
    @Deprecated
    public CentroidPartMarker(String name){
        setName(name);
    }

    public CentroidPartObject getCpo() {
        return cpo;
    }

    public CentroidPartMarker(String name, Point pointCenter, CentroidPartObject cpo) {
        setName(name);
        this.pointCenter = pointCenter;
        this.cpo = cpo;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        CentroidPartLabel rep = new CentroidPartLabel(this);
//        PointRepresentation rep2 = new PointRepresentation(pointCenter);
        rep.setOffset(-15, -20);
        addRepresentation(rep);
//        addRepresentation(rep2);
    }

    @Override
    public Vector3f getTranslation() {
        // this is a special case that occurs during persistence
        if(pointCenter == null)
            return new Vector3f();
        return pointCenter.getTranslation();
    }
}
