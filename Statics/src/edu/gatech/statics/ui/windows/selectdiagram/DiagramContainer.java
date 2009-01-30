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
import edu.gatech.statics.exercise.DiagramKey;
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
    private Map<String, DiagramKey> actionMap = new HashMap<String, DiagramKey>();
    private ActionListener listener = new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            DiagramKey key = actionMap.get(event.getAction());
            StaticsApplication.getApp().selectDiagramKey(key);
        }
    };

    public void addItem(DiagramKey diagramKey) {
        if (actionMap.containsValue(diagramKey)) {
            // we've already added this, return.
            return;
        }

        BButton button = null;

        if (diagramKey == null) {
            button = createNewButton();
        } else if (diagramKey instanceof BodySubset) {
            button = createBodySubsetButton((BodySubset) diagramKey);
        } else {
            button = createGenericButton(diagramKey);
        }

        if (button != null) {
            button.setPreferredSize(140, -1);
            actionMap.put(button.getAction(), diagramKey);
            placeItem(button);
        }
    }

    private BButton createGenericButton(DiagramKey diagramKey) {
        String action = diagramKey.toString();
        BButton item = new BButton(action, listener, action);
        return item;
    }

    private BButton createNewButton() {
        String action = "new";
        BButton item = new BButton(action, listener, action);
        return item;
    }

    public BButton createBodySubsetButton(BodySubset bodies) {
        // adds a new button corresponding to the new diagram
        // If the set of bodies exists, the label is formatted and the action
        // set to a simple hash code referencing the collection
        BButton item;
        String action = bodies.toString();
        if (bodies.getSpecialName() != null) {
            item = new BButton(bodies.getSpecialName(), listener, action);
        } else {
            String buttonString = bodies.toString().replaceAll(",", ",\n");
            item = new BButton(buttonString, listener, action);
        }
        return item;
    }

    abstract protected void placeItem(BButton item);

    public void onDiagramCreated(Diagram diagram) {
        addItem(diagram.getKey());
        getWindow().pack();
    }
}
