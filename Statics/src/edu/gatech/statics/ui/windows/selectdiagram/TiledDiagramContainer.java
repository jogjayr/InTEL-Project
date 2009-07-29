/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.selectdiagram;

import com.jmex.bui.BButton;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.Diagram;

/**
 *
 * @author Calvin Ashmore
 */
public class TiledDiagramContainer extends DiagramContainer {

    public TiledDiagramContainer() {
        super(GroupLayout.makeVert(GroupLayout.CENTER));
    }
    
    @Override
    protected void placeItem(BButton item) {
        // do nothing
    }

    public void onDiagramChanged(Diagram newDiagram) {
        // do nothing
    }
    
}
