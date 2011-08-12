/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.ui;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.objects.representations.ModelRepresentation;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;
import viewer.ViewerMode;
import viewer.ui.heirarchy.HeirarchyFrame;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewerModePanel extends ApplicationModePanel {

    //private BContainer itemContainer;
    private HeirarchyFrame heirarchyFrame;

    public void setModel(ModelRepresentation rep) {
        heirarchyFrame.setRoot(rep);
    }

    @Override
    public boolean isUndoVisible() {
        return false;
    }

    public ViewerModePanel() {

        //getTitleLabel().setText("Viewer");

        BContainer mainContainer = new BContainer(new BorderLayout());

        BButton showNormalsButton = new BButton("Show Normals", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                DisplayConstants.getInstance().setShowNormals(!DisplayConstants.getInstance().getShowNormals());
            }
        }, "button");

        BButton showBoundsButton = new BButton("Show Bounding", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                DisplayConstants.getInstance().setShowBoundingVolumes(!DisplayConstants.getInstance().getShowBoundingVolumes());
            }
        }, "button");

        mainContainer.add(showNormalsButton, BorderLayout.NORTH);
        mainContainer.add(showBoundsButton, BorderLayout.CENTER);

        //GroupLayout itemLayout = GroupLayout.makeVert(GroupLayout.CENTER);
        //itemLayout.setOffAxisJustification(GroupLayout.LEFT);
        //itemContainer = new BContainer(itemLayout);
        //BScrollPane scrollPane = new BScrollPane(itemContainer);
        //mainContainer.add(scrollPane, BorderLayout.WEST);

        add(mainContainer, BorderLayout.CENTER);
    }

    @Override
    public void activate() {
        super.activate();

        //ViewerDiagram diagram = (ViewerDiagram) getDiagram();
        //Node modelNode = (Node) diagram.getModel().getChild(0);

        //listNode(modelNode, 0);

        heirarchyFrame = new HeirarchyFrame();
        //frame.setRoot(modelNode);
        heirarchyFrame.pack();
        heirarchyFrame.setVisible(true);

        /*JFrame frame = new JFrame();
        frame.add(new JLabel("EEK!"));
        frame.pack();
        frame.setVisible(true);*/
    }

    private String makeSpacerString(int spaces) {
        char[] str = new char[spaces];
        for (int i = 0; i < str.length; i++) {
            str[i] = ' ';
        }
        return new String(str);
    }

//    private void listNode(Node node, int depth) {
//        int i = 0;
//        for (Spatial child : node.getChildren()) {
//            itemContainer.add(new BLabel(makeSpacerString(depth) + " child (" + i + "): " + child.toString()));
//            if (child instanceof Node) {
//                listNode((Node) child, depth + 1);
//            }
//            i++;
//        }
//    }
//    @Override
//    protected ApplicationTab createTab() {
//        return new ApplicationTab("Viewer");
//    }
    @Override
    public DiagramType getDiagramType() {
        //return DiagramType.getType("viewer");
        return ViewerMode.instance.getDiagramType();
    }
}
