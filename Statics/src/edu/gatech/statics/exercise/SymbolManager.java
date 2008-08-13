/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SymbolManager {

    private List<String> symbols = new ArrayList<String>();
    private List<AnchoredVector> symbolicLoads = new ArrayList<AnchoredVector>();

    public List<AnchoredVector> getLoads() {
        return Collections.unmodifiableList(symbolicLoads);
    }

    public List<String> getSymbols() {
        return Collections.unmodifiableList(symbols);
    }

    /**
     * Add a symbol to the symbol manager for the quantified object. 
     * Loads are recognized and kept track of for use later.
     * Loads are also *cloned* so that solving a AnchoredVector in an equation does not have
     * unexpected consequences.
     * @param quantified
     */
    public void addSymbol(Quantified quantified) {
        if (quantified.isSymbol()) {
            if (!symbols.contains(quantified.getSymbolName())) {
                symbols.add(quantified.getSymbolName());
                if (quantified instanceof AnchoredVector) {
                    //symbolicLoads.add(((AnchoredVector) quantified).clone());
                    symbolicLoads.add(new AnchoredVector((AnchoredVector) quantified));
                }
            }
        } else {
            throw new UnsupportedOperationException(
                    "Cannot add a symbol to the symbol manager for the non-symbolic quantified object \"" + quantified + "\"");
        }
    }

    /**
     * This attempts to find a stored symbolic AnchoredVector for the provided AnchoredVector.
     * The direction of the symbolic AnchoredVector should not be taken as meaningful, as its opposite may
     * be the one of interest depending on the circumstance.
     * This should return a single AnchoredVector if presented with either end of a two force member.
     * @param AnchoredVector
     * @return
     */
    public AnchoredVector getLoad(AnchoredVector load) {
        for (AnchoredVector toCheck : symbolicLoads) {
            if (load.getAnchor() == toCheck.getAnchor() &&
                    (load.getVectorValue().equals(toCheck.getVectorValue()) ||
                    (load.getVectorValue().equals(toCheck.getVectorValue().negate())))) {
                return toCheck;
            }
        }

        // okay, here we try to check for the 2fm case
        // make a list of connectors present at that point
        // the reaction was not found at this AnchoredVector, so we want to try to find the opposite one.
        //List<Connector> connectors = Exercise.getExercise().getSelectDiagram().getConnectorsAtPoint(AnchoredVector.getAnchor());
        //List<Connector> connectors = Exercise.getExercise().getSchematic().getConnectorsAtPoint(AnchoredVector.getAnchor());
        //for (Connector connector : connectors) {
        for (SimulationObject obj : Exercise.getExercise().getSchematic().allObjects()) {
            if (obj instanceof Connector2ForceMember2d) {
                Connector2ForceMember2d connector2fm = (Connector2ForceMember2d) obj;
                if (connector2fm.getDirection().equals(load.getVectorValue()) || connector2fm.getDirection().equals(load.getVectorValue().negate())) {

                    // this one lines up with the connector2fm
                    // perform the main check, but use the other anchor.
                    Connector2ForceMember2d opposite = connector2fm.getOpposite();

                    if (opposite == null) {
                        continue;
                    }

                    // perform the main check again
                    for (AnchoredVector toCheck : symbolicLoads) {
                        if (opposite.getAnchor() == toCheck.getAnchor() &&
                                (load.getVectorValue().equals(toCheck.getVectorValue()) ||
                                (load.getVectorValue().equals(toCheck.getVectorValue().negate())))) {
                            return toCheck;
                        }
                    }
                }
            }
        }

        return null;
    }

    /*public AnchoredVector getLoad2FM(TwoForceMember tfm, AnchoredVector AnchoredVector) {
    for (AnchoredVector toCheck : symbolicLoads) {
    if (tfm.containsPoints(toCheck.getAnchor(), AnchoredVector.getAnchor()) &&
    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue()) ||
    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue().negate())))) {
    
    return toCheck;
    }
    }
    return null;
    }*/
    public List<AnchoredVector> allLoads() {
        return Collections.unmodifiableList(symbolicLoads);
    }

    /**
     * Returns the symbolic quantity for the specified AnchoredVector. This method is used for verifying that
     * the AnchoredVector given is the same as one which has been stored in the symbol manager. 
     * If it does match, then the symbol name is returned, otherwise the method returns null.
     * @param AnchoredVector
     * @return
     */
    public Quantity getSymbol(AnchoredVector AnchoredVector) {
        for (AnchoredVector toCheck : symbolicLoads) {
            if (AnchoredVector.getAnchor() == toCheck.getAnchor() &&
                    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue()) ||
                    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue().negate())))) {

                return toCheck.getVector().getQuantity();
            }
        }
        return null;
    }
    /*public Quantity getSymbol2FM(TwoForceMember tfm, AnchoredVector AnchoredVector) {
    for (AnchoredVector toCheck : symbolicLoads) {
    if (tfm.containsPoints(toCheck.getAnchor(), AnchoredVector.getAnchor()) &&
    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue()) ||
    (AnchoredVector.getVectorValue().equals(toCheck.getVectorValue().negate())))) {
    
    return toCheck.getVector().getQuantity();
    }
    }
    return null;
    }*/
}
