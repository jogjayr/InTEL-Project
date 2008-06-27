/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.objects.Load;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SymbolManager {

    private List<String> symbols = new ArrayList<String>();
    private List<Load> symbolicLoads = new ArrayList<Load>();

    public List<String> getSymbols() {
        return Collections.unmodifiableList(symbols);
    }

    /**
     * Add a symbol to the symbol manager for the quantified object. 
     * Loads are recognized and kept track of for use later.
     * @param quantified
     */
    public void addSymbol(Quantified quantified) {
        if (quantified.isSymbol()) {
            symbols.add(quantified.getSymbolName());
            if (quantified instanceof Load) {
                symbolicLoads.add((Load) quantified);
            }
        } else {
            throw new UnsupportedOperationException(
                    "Cannot add a symbol to the symbol manager for the non-symbolic quantified object \"" + quantified + "\"");
        }
    }
    
    public Load getLoad(Load load) {
        for (Load toCheck : symbolicLoads) {
            if (load.getAnchor() == toCheck.getAnchor() &&
                    (load.getVectorValue().equals(toCheck.getVectorValue()) ||
                    (load.getVectorValue().equals(toCheck.getVectorValue().negate()))))  {

                return toCheck;
            }
        }
        return null;
    }

    public List<Load> allLoads() {
        return Collections.unmodifiableList(symbolicLoads);
    }
    
    /**
     * Returns the symbolic quantity for the specified load. This method is used for verifying that
     * the load given is the same as one which has been stored in the symbol manager. 
     * If it does match, then the symbol name is returned, otherwise the method returns null.
     * @param load
     * @return
     */
    public Quantity getSymbol(Load load) {
        for (Load toCheck : symbolicLoads) {
            if (load.getAnchor() == toCheck.getAnchor() &&
                    (load.getVectorValue().equals(toCheck.getVectorValue()) ||
                    (load.getVectorValue().equals(toCheck.getVectorValue().negate()))))  {

                return toCheck.getVector().getQuantity();
            }
        }
        return null;
    }
}
