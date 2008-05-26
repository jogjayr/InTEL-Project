/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.objects.Load;
import java.math.BigDecimal;

/**
 * 
 * @author Calvin Ashmore
 */
public class LoadLabelListener implements LabelListener {

    private Load myLoad;
    
    public LoadLabelListener(Load load) {
        this.myLoad = load;
    }
    
    public boolean onLabel(String label) {
        if (label.length() == 0) {
            return false;
        }
        if (Character.isDigit(label.charAt(0)) ||
                label.charAt(0) == '.' ||
                label.charAt(0) == '-') {
            // numerical, try to lop off tail end
            String[] split = label.split(" ");
            try {
                BigDecimal value = new BigDecimal(split[0]);
                
                if(value.compareTo(new BigDecimal(0)) == 0)
                    return false;
                
                myLoad.setDiagramValue(value);
                myLoad.setSymbol(null);
                myLoad.setKnown(true);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            //obj.setName(text);
            myLoad.setSymbol(label);
            myLoad.setKnown(false);
        }
        return true;
    }
}
