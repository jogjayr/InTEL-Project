/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.exercise.Schematic;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.frame.FrameSelectDiagram;
import edu.gatech.statics.modes.frame.FrameUtil;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.truss.ui.TrussInterfaceConfiguration;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new TrussInterfaceConfiguration();
    }

    public TrussExercise() {
        //  set the "Whole Frame" label in FrameUtil to "Whole Truss"
        FrameUtil.whatToCallTheWholeDiagram = "Whole Truss";
    }

    public TrussExercise(Schematic schematic) {
        super(schematic);
    }

    @Override
    public Mode loadStartingMode() {
        //return super.loadStartingMode();
        ZFMMode.instance.load();
        return ZFMMode.instance;
    }

    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (type == TrussSectionMode.instance.getDiagramType()) {
            return createSectionDiagram();
        }
        if (type == ZFMMode.instance.getDiagramType()) {
            return createZFMDiagram();
        }

        return super.createNewDiagramImpl(key, type);
    }

    protected TrussSectionDiagram createSectionDiagram() {
        return new TrussSectionDiagram();
    }

    protected ZFMDiagram createZFMDiagram() {
        return new ZFMDiagram();
    }

    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodies) {

        // set up the special name for the whole truss, in truss problems
        if (FrameUtil.isWholeDiagram(bodies)) {
            bodies.setSpecialName(FrameUtil.whatToCallTheWholeDiagram);
        }

        // return a special truss fbd, which handles a few things that are not covered elsewhere.
        return new TrussFreeBodyDiagram(bodies);
    }

    @Override
    protected SelectDiagram createSelectDiagram() {
        return new FrameSelectDiagram();
    }
}
