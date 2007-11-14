/*
 * DeletableManipulator.java
 *
 * Created on July 18, 2007, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.KeyInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;

/**
 *
 * @author Calvin Ashmore
 */
public class DeletableManipulator extends Manipulator {
    
    private World world;
    
    /** Creates a new instance of DeletableManipulator */
    public DeletableManipulator(final World world, final SimulationObject target) {
        super(target);
        this.world = world;
        
        InputActionInterface action = new InputActionInterface() {
            public void performAction(InputActionEvent evt) {
                performDelete();
            }
        };
        
        addAction(action, "delete", KeyInput.KEY_DELETE, false);
        addAction(action, "backspace", KeyInput.KEY_BACK, false);
    }
    
    public void performDelete() {
        world.clearSelection();
        world.remove(getTarget());
        //world.updateNodes();

        getTarget().destroy();
    }
}
