/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.Mode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.ui.InterfaceConfiguration;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.modes.fbd.SimpleFreeBodyDiagram;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;
import edu.gatech.statics.modes.select.ui.SelectModePanel;

/**
 *
 * @author Calvin Ashmore
 */
public class SimpleFBDExercise extends Exercise {

    public SimpleFBDExercise(Schematic world) {
        super(world);
    }

    public SimpleFBDExercise() {
    }

    @Override
    public Mode loadStartingMode() {
        SelectMode.instance.load();
        return SelectMode.instance;
    }

    /**
     * Returns a SimpleFreeBodyDiagram instead of a regular FreeBodyDiagram.
     * @param bodySubset
     * @return
     */
    @Override
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodySubset) {
        return new SimpleFreeBodyDiagram(bodySubset);
    }

    /**
     * Returns an interface configuration without the EquationModePanel.
     * @return
     */
    @Override
    public InterfaceConfiguration createInterfaceConfiguration() {
        return new SimpleInterfaceConfiguration();
    }

    private class SimpleInterfaceConfiguration extends DefaultInterfaceConfiguration {

        public SimpleInterfaceConfiguration() {
        }

        @Override
        public void createModePanels() {
            List<ApplicationModePanel> r = getModePanels();
            r.add(new SelectModePanel());
            r.add(new FBDModePanel());
        }        
    }
}
