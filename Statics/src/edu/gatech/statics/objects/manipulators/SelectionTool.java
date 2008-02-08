/*
 * SelectionTool.java
 *
 * Created on July 18, 2007, 1:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.util.SelectionFilter;
import edu.gatech.statics.util.SelectionListener;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectionTool extends Tool implements SelectionListener {
    
    private Class<? extends SimulationObject> selectionClass;
    private World world;
    protected World getWorld() {return world;}
    
    /** Creates a new instance of SelectionTool */
    public SelectionTool(World world, Class<? extends SimulationObject> selectionClass) {
        this.world = world;
        this.selectionClass = selectionClass;
    }
    
    protected void onActivate() {
        world.setSelectableFilter(new SelectionFilter() {
            public boolean canSelect(SimulationObject obj) {
                return selectionClass.isAssignableFrom( obj.getClass() );
            }
        });
        
        world.addSelectionListener(this);
    }
    
    protected void onCancel() {
        world.clearSelection();
    }

    protected void onFinish() {
        world.removeSelectionListener(this);
        
        world.setSelectableFilterDefault();
        world.enableManipulatorsOnSelectDefault();
        world.enableSelectMultipleDefault();
    }

    // do nothing for now, but overriding classes may find these useful
    public void onClick(SimulationObject obj) {}
    public void onHover(SimulationObject obj) {}
    
}
