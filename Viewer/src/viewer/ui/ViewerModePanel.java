/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.ui;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;
import viewer.ViewerDiagram;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerModePanel extends ApplicationModePanel {

    @Override
    public String getPanelName() {
        return "viewer";
    }
    private BContainer itemContainer;

    public ViewerModePanel() {

        getTitleLabel().setText("Viewer");

        BContainer mainContainer = new BContainer(new BorderLayout());

        GroupLayout itemLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        itemLayout.setOffAxisJustification(GroupLayout.LEFT);
        itemContainer = new BContainer(itemLayout);
        BScrollPane scrollPane = new BScrollPane(itemContainer);
        mainContainer.add(scrollPane, BorderLayout.WEST);

        add(mainContainer, BorderLayout.CENTER);
    }

    @Override
    public void activate() {

        ViewerDiagram diagram = (ViewerDiagram) getDiagram();
        Node modelNode = (Node) diagram.getModel().getChild(0);

        listNode(modelNode, 0);
    }
    
    private String makeSpacerString(int spaces) {
        char[] str = new char[spaces];
        for (int i = 0; i < str.length; i++) {
            str[i] = ' ';
        }
        return new String(str);
    }

    private void listNode(Node node, int depth) {
        int i = 0;
        for (Spatial child : node.getChildren()) {
            itemContainer.add(new BLabel(makeSpacerString(depth) + " child (" + i + "): " + child.toString()));
            if (child instanceof Node) {
                listNode((Node) child, depth + 1);
            }
            i++;
        }
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Viewer");
    }
}
