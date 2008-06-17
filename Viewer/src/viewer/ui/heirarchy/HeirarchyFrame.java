/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.ui.heirarchy;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Calvin Ashmore
 */
public class HeirarchyFrame extends JFrame {

    private JTree tree;
    private JEditorPane textPane;
    private DefaultMutableTreeNode root;

    public HeirarchyFrame(Node rootNode) {

        getContentPane().setLayout(new BorderLayout());

        root = new DefaultMutableTreeNode();
        setRoot(rootNode);
        tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(treeListener);
        JScrollPane treeView = new JScrollPane(tree);

        textPane = new JEditorPane();
        textPane.setEditable(false);
        JScrollPane textView = new JScrollPane(textPane);

        //getContentPane().add(treeView);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(textView);

        Dimension minimumSize = new Dimension(100, 50);
        textView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));

        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private void setRoot(Node rootNode) {
        root.removeAllChildren();
        root.setUserObject(rootNode);

        for (Spatial child : rootNode.getChildren()) {
            if (child instanceof Node) {
                root.add(createChild(child));
            }
        }
    }

    private MutableTreeNode createChild(Spatial spatial) {

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(spatial);
        
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (Spatial child : node.getChildren()) {
                if (child instanceof Node) {
                    treeNode.add(createChild(child));
                }
            }
        }
        return treeNode;
    }
    private TreeSelectionListener treeListener = new TreeSelectionListener() {

        public void valueChanged(TreeSelectionEvent e) {
        }
    };
}
