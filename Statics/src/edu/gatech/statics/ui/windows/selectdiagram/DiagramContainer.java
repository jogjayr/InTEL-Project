/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.selectdiagram;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BLayoutManager;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.SubDiagram;
import edu.gatech.statics.util.DiagramListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class DiagramContainer extends BContainer implements DiagramListener {

    public DiagramContainer(BLayoutManager layout) {
        super(layout);

        // add the selection item
        addItem(null);
        StaticsApplication.getApp().addDiagramListener(this);
    }

    private Map<String, BodySubset> actionMap = new HashMap<String, BodySubset>();
    private ActionListener listener = new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            BodySubset bodies = actionMap.get(event.getAction());
            //onSelect(bodies);
            StaticsApplication.getApp().selectBodies(bodies);
        }
    };

    public void addItem(BodySubset bodies) {

        // check already added items, and ignore if we've added this before.
        for (BodySubset existingSubset : actionMap.values()) {
            if (bodies == null && existingSubset == null) {
                return;
            }
            if (bodies != null && bodies.equals(existingSubset)) {
                return;
            }
        }

        BButton item;
        String action = bodies == null ? "new" : Integer.toHexString(bodies.hashCode());
        if (bodies == null) {
            item = new BButton("new", listener, action);
        } else {
            String buttonString = bodies.toString().replaceAll(",", ",\n");
            item = new BButton(buttonString, listener, action);
        }
        item.setPreferredSize(140, -1);

        actionMap.put(action, bodies);
        placeItem(item);
    }

    abstract protected void placeItem(BButton item);

    public void onDiagramCreated(Diagram diagram) {
        if(diagram instanceof SubDiagram)
            addItem( ((SubDiagram)diagram).getBodySubset() );
        getWindow().pack();
    }
    
}
