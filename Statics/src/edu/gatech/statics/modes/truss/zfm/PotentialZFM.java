/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.representations.MimicRepresentation;
import java.util.List;

/**
 * This is an object that represents a two force member, and that appears in the
 * ZFM mode. The user must select those of these that are actually zero force members.
 * There must be one of these for each ZFM and 2FM in the diagram. When there is a
 * T shape in a truss, the vertical bar of the T will be a ZFM, and the left and right
 * members will actually make up a single member. It is up to the exercise design to actually
 * define both the potentialZFMs and the whole 2FMs (not the parts), so the left and right
 * of the T must be merged as a 2FM in the actual schematic.
 *
 * @author Calvin Ashmore
 */
public class PotentialZFM extends SimulationObject implements ResolvableByName {

    /**
     * "absorbs" the representations of the given object. This allows the potentialZFM to 
     * effectively stand in for other objects.
     * @param base
     */
    public void addRepresentations(SimulationObject base) {
        for (RepresentationLayer layer : RepresentationLayer.getLayers()) {
            List<Representation> representations = base.getRepresentation(layer);
            for (Representation representation : representations) {
                addRepresentation(new MimicRepresentation(this, representation));
            }
        }
    }
    private String baseName;
    private boolean zfm;

    public String getBaseName() {
        return baseName;
    }

    private boolean inZFMMode() {
        return StaticsApplication.getApp().getCurrentDiagram() instanceof ZFMDiagram;
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean isDisplayGrayed() {
        if (inZFMMode()) {
            return super.isDisplayGrayed();
        } else {
            super.setDisplayGrayed(true);
            return true;
        }
    }

    /**
     * Grays the display by flag grayed
     * @param grayed
     */
    @Override
    public void setDisplayGrayed(boolean grayed) {

        //System.out.println("*** setting grayed: " + this);
        if (inZFMMode()) {
            super.setDisplayGrayed(grayed);
        } else {
            //System.out.println("**** BLERG " + this);
            super.setDisplayGrayed(true);
        }
    }

    /**
     * For persistence, do not use
     * @deprecated
     */
    @Deprecated
    public PotentialZFM(String name) {
        if (name.startsWith("potential")) {
            setBaseName(name.substring("potential".length()));
        }
    }

    /**
     * 
     * @param zfm
     */
    public PotentialZFM(boolean zfm) {
        this.zfm = zfm;
    }

    /**
     * 
     * @return Is it a potential zero force member
     */
    public boolean isZfm() {
        return zfm;
    }

    public void setZfm(boolean zfm) {
        this.zfm = zfm;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getName() {
        return "potential" + baseName;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        // do nothing
    }
}
