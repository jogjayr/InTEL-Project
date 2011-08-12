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
package viewer.ui.heirarchy;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Calvin Ashmore
 */
public class HeirarchyFrame extends JFrame {

    private JTree tree;
    //private JEditorPane textPane;
    private InfoPanel infoPanel;
    //private DefaultMutableTreeNode root;

    public HeirarchyFrame() {

        getContentPane().setLayout(new BorderLayout());

        tree = new JTree();//root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(treeListener);
        JScrollPane treeView = new JScrollPane(tree);

        OpeningPanel openingPanel = new OpeningPanel();

        infoPanel = new InfoPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(openingPanel, BorderLayout.NORTH);
        mainPanel.add(treeView, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        Dimension minimumSize = new Dimension(100, 50);
        //textView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        //splitPane.setDividerLocation(100);
        //splitPane.setPreferredSize(new Dimension(500, 300));
        //mainPanel.setPreferredSize(new Dimension(500, 300));

        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public void setRoot(Node rootNode) {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        root.removeAllChildren();
        root.setUserObject(rootNode);

        for (Spatial child : rootNode.getChildren()) {
            root.add(createChild(child));
        }

        tree.setModel(new DefaultTreeModel(root));
    }

    private MutableTreeNode createChild(Spatial spatial) {

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(spatial);

        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            if (node.getChildren() != null) {
                for (Spatial child : node.getChildren()) {
                    treeNode.add(createChild(child));
                }
            }
        }
        return treeNode;
    }
    private TreeSelectionListener treeListener = new TreeSelectionListener() {

        public void valueChanged(TreeSelectionEvent e) {
            Object lastSelection = tree.getLastSelectedPathComponent();
            //System.out.println(lastSelection);

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastSelection;
            if (treeNode == null) {
                return;
            }

            Object object = treeNode.getUserObject();

            if (object instanceof Spatial) {
                infoPanel.updateSelection((Spatial) object);
            }
        }
    };
}
