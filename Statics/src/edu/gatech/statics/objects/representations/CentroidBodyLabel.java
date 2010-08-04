/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.CentroidSelectDiagram;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.CentroidBodyMarker;
import edu.gatech.statics.objects.CentroidPartMarker;

/**
 * This class creates the floating white boxes that contain the part names
 * before the CentroidParts are solved for and then the name and derived data
 * after the part is solved for.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidBodyLabel extends LabelRepresentation {

    private boolean enabled;
    private CentroidBody body;

    public CentroidBodyLabel(CentroidBodyMarker target) {
        super(target, "label_hovering_centroid_part");

        this.body = target.getBody();

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

        if (body == null) {
            return "Not ready yet.";
        } else {
            return "Centroid position of " + body.getName();
        }
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

//        if (cpo != null && cpo.getState() != null) {
        setHidden(false);
//        } else {
//            setHidden(true);
//        }
        super.update();
    }
}
