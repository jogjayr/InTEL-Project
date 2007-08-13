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
    
    /** Creates a new instance of DeletableManipulator */
    public DeletableManipulator(final World world, final SimulationObject target) {
        super(target);
        
        InputActionInterface action = new InputActionInterface() {
            public void performAction(InputActionEvent evt) {
                world.clearSelection();
                world.remove(target);
                world.updateNodes();
                
                target.destroy();
            }
        };
        
        addAction(action, "delete", KeyInput.KEY_DELETE, false);
    }
    
}
