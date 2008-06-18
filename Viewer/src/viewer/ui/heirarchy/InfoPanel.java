/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer.ui.heirarchy;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Calvin Ashmore
 */
public class InfoPanel extends JPanel {

    private Spatial currentSelection;
    private JTextField translationX,  translationY,  translationZ;
    private JTextField rotationX,  rotationY,  rotationZ;
    private JTextField scaleX,  scaleY,  scaleZ;

    public InfoPanel() {

        JPanel translationPanel = new JPanel();
        JPanel rotationPanel = new JPanel();
        JPanel scalePanel = new JPanel();

        translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
        rotationPanel.setLayout(new BoxLayout(rotationPanel, BoxLayout.Y_AXIS));
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        add(translationPanel);
        add(rotationPanel);
        add(scalePanel);

        translationPanel.add(new JLabel("Translation"));
        translationPanel.add(translationX = new JTextField());
        translationPanel.add(translationY = new JTextField());
        translationPanel.add(translationZ = new JTextField());

        rotationPanel.add(new JLabel("Rotation"));
        rotationPanel.add(rotationX = new JTextField());
        rotationPanel.add(rotationY = new JTextField());
        rotationPanel.add(rotationZ = new JTextField());

        scalePanel.add(new JLabel("Scale"));
        scalePanel.add(scaleX = new JTextField());
        scalePanel.add(scaleY = new JTextField());
        scalePanel.add(scaleZ = new JTextField());

        int height = translationX.getPreferredSize().height;

        translationX.setPreferredSize(new Dimension(100, height));
        rotationX.setPreferredSize(new Dimension(100, height));
        scaleX.setPreferredSize(new Dimension(100, height));
    }

    public void updateSelection(Spatial spatial) {
        this.currentSelection = spatial;

        if (spatial == null) {
            translationX.setText("");
            translationY.setText("");
            translationZ.setText("");
            rotationX.setText("");
            rotationY.setText("");
            rotationZ.setText("");
            scaleX.setText("");
            scaleY.setText("");
            scaleZ.setText("");
        } else {
            Vector3f localTranslation = spatial.getLocalTranslation();
            Vector3f localScale = spatial.getLocalScale();
            float eulerAngles[] = new float[3];
            spatial.getLocalRotation().toAngles(eulerAngles);

            translationX.setText("" + localTranslation.x);
            translationY.setText("" + localTranslation.y);
            translationZ.setText("" + localTranslation.z);

            for (int i = 0; i < eulerAngles.length; i++) {
                eulerAngles[i] = 180*(eulerAngles[i]/(float)Math.PI);
            }
            
            rotationX.setText("" + eulerAngles[0]);
            rotationY.setText("" + eulerAngles[1]);
            rotationZ.setText("" + eulerAngles[2]);

            scaleX.setText("" + localScale.x);
            scaleY.setText("" + localScale.y);
            scaleZ.setText("" + localScale.z);
        }
    }
}
