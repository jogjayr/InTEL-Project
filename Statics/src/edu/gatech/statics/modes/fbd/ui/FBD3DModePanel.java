/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.ui;



import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.fbd.FBDMode;


/**
 *
 * @author Jayraj
 */
public class FBD3DModePanel extends FBDModePanel {

   
    /**
     * Constructor. Only a super() call
     */
    public FBD3DModePanel() {
        super(); 
        //tools = makeTools();//new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        
    }



    @Override
    public DiagramType getDiagramType() {
        return FBDMode.instance.getDiagramType();
    }

    @Override
    protected FBDTools makeTools() {
        return new FBDTools3D(this);
    }

    
}


