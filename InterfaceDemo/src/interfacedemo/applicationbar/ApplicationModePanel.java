/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.applicationbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class ApplicationModePanel extends BContainer {

    private ApplicationTab tab;
    private BLabel titleLabel;

    public BLabel getTitleLabel() {
        return titleLabel;
    }
    
    public ApplicationModePanel() {
        super(new BorderLayout());
        tab = createTab();
        titleLabel.
    }
    
    public ApplicationTab getTab() {return tab;}

    abstract protected ApplicationTab createTab();
}
