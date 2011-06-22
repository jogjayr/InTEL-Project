/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.Mode;
import edu.gatech.statics.modes.description.DescriptionDiagram;
import edu.gatech.statics.modes.description.DescriptionMode;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 * OrdinaryExercise is the default type of exercise. It uses the DefaultInterfaceConfiguration,
 * and starts with the select mode. Users are able to select subsets of bodies, build FBDs for those
 * bodies, and then allows users to solve for equilibrium. Generally, this most easily allows
 * solutions of problems that might be designated as "frame" problems, but does not include
 * the other interface elements to support them.
 * @author Calvin Ashmore
 */
abstract public class OrdinaryExercise extends Exercise {

    public OrdinaryExercise() {
    }

    public OrdinaryExercise(Schematic schematic) {
        super(schematic);
    }

    /**
     * 
     * @return
     */
    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        return new DefaultInterfaceConfiguration();
    }

    /**
     * 
     * @return
     */
    @Override
    public Mode loadStartingMode() {
        SelectMode.instance.load();
        return SelectMode.instance;
    }

    /**
     * 
     * @return
     */
    protected SelectDiagram createSelectDiagram() {
        return new SelectDiagram();
    }

    /**
     *
     * @param bodies
     * @return
     */
    protected FreeBodyDiagram createFreeBodyDiagram(BodySubset bodies) {
        return new FreeBodyDiagram(bodies);
    }

    /**
     * 
     * @param bodies
     * @return
     */
    protected EquationDiagram createEquationDiagram(BodySubset bodies) {
        return new EquationDiagram(bodies);
    }

    /**
     * 
     * @return
     */
    private Diagram createDescriptionDiagram() {
        return new DescriptionDiagram();
    }

    /**
     * Ordinary exercises support the Select, FBD, and Equation modes.
     * @param type
     * @return
     */
    @Override
    public boolean supportsType(DiagramType type) {
        if (type == DescriptionMode.instance.getDiagramType()) {
            return true;
        } else if (type == SelectMode.instance.getDiagramType()) {
            return true;
        } else if (type == FBDMode.instance.getDiagramType()) {
            return true;
        } else if (type == EquationMode.instance.getDiagramType()) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param key
     * @param type
     * @return
     */
    @Override
    protected Diagram createNewDiagramImpl(DiagramKey key, DiagramType type) {
        if (!supportsType(type)) {
            throw new UnsupportedOperationException("OrdinaryExercise does not support the diagram type: " + type);
        }

        if (type == DescriptionMode.instance.getDiagramType()) {
            return createDescriptionDiagram();
        } else if (type == SelectMode.instance.getDiagramType()) {
            return createSelectDiagram();
        } else if (type == FBDMode.instance.getDiagramType()) {
            return createFreeBodyDiagram((BodySubset) key);
        } else if (type == EquationMode.instance.getDiagramType()) {
            return createEquationDiagram((BodySubset) key);
        }

        throw new AssertionError("OrdinaryExercise does not support the diagram type: " + type + ". " +
                "This should be marked in the supportsType method!!!");
    }
}
