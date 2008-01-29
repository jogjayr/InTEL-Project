/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.applicationbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.layout.BLayoutManager;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel extends BContainer {

    private ApplicationTab tab;
    
    public ApplicationModePanel(BLayoutManager layout) {
        super(layout);
        tab = createTab();
    }
    
    public ApplicationTab getTab() {return tab;}

    abstract protected ApplicationTab createTab();
}
