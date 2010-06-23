/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidSelectDiagram;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.CentroidPartMarker;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartLabel extends LabelRepresentation {

    private boolean enabled;
    private CentroidPartObject cpo;

    public CentroidPartLabel(CentroidPartMarker target) {
        super(target, "label_hovering_centroid_part");

        this.cpo = target.getCpo();

        setOffset(queueDistance, queueDistance);

        getLabel().addListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                onClick();
            }
        });
    }

    protected void onClick() {
        if (isHidden()) {
            setHidden(false);
        } else {
            setHidden(true);
        }
    }

    @Override
    protected String getLabelText() {
        if (cpo != null) {
            return cpo.getCentroidPart().getPartName()
                    + "\nSurface Area: " + cpo.getCentroidPart().getSurfaceArea()
                    + "\nX Position: " + cpo.getCentroidPart().getxPosition()
                    + "\nY Position: " + cpo.getCentroidPart().getyPosition();
        }
        return "something went wrong";
    }

    @Override
    public void update() {

        // return if the the application is not loaded.
        if (StaticsApplication.getApp() == null) {
            return;
        }

        // we should enable the representation if the diagram is a select diagram
        boolean shouldEnable = StaticsApplication.getApp().getCurrentDiagram() instanceof CentroidSelectDiagram;

        enabled = shouldEnable;

        if (cpo != null && cpo.getState() != null && cpo.getState().isLocked()) {
            setHidden(false);
        } else {
            setHidden(true);
        }
        super.update();
    }
}
