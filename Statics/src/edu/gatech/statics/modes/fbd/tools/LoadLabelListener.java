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
                //double value = Double.parseDouble(split[0]);
                // we do not want null values.
                //if (value == 0) {
                //    return false;
                //}
                BigDecimal value = new BigDecimal(split[0]);
                
                if(value.compareTo(new BigDecimal(0)) == 0)
                    return false;
                
                myLoad.setValue(value);
                myLoad.setSymbol(null);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            //obj.setName(text);
            myLoad.setSymbol(label);
        }
        return true;
    }
}
