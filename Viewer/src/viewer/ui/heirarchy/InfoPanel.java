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

import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState.StateType;
import com.jme.scene.state.TextureState;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Calvin Ashmore
 */
public class InfoPanel extends JPanel {

    private Spatial currentSelection;
//    private JTextField translationX, translationY, translationZ;
//    private JTextField rotationX, rotationY, rotationZ;
//    private JTextField scaleX, scaleY, scaleZ;
//    private JRadioButton displayNormal;
//    private JRadioButton displayHighlight;
//    private JRadioButton displaySelected;
//    private JRadioButton displayGrayed;
    private JTextArea infoArea;

    public InfoPanel() {

        setLayout(new BorderLayout(0, 5));

        infoArea = new JTextArea();
        add(infoArea, BorderLayout.CENTER);
//        JPanel transformPanel = new JPanel();
//
//        JPanel translationPanel = new JPanel();
//        JPanel rotationPanel = new JPanel();
//        JPanel scalePanel = new JPanel();
//
//        translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
//        rotationPanel.setLayout(new BoxLayout(rotationPanel, BoxLayout.Y_AXIS));
//        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));
//
//        transformPanel.add(translationPanel);
//        transformPanel.add(rotationPanel);
//        transformPanel.add(scalePanel);
//
//        translationPanel.add(new JLabel("Translation"));
//        translationPanel.add(translationX = new JTextField());
//        translationPanel.add(translationY = new JTextField());
//        translationPanel.add(translationZ = new JTextField());
//
//        rotationPanel.add(new JLabel("Rotation"));
//        rotationPanel.add(rotationX = new JTextField());
//        rotationPanel.add(rotationY = new JTextField());
//        rotationPanel.add(rotationZ = new JTextField());
//
//        scalePanel.add(new JLabel("Scale"));
//        scalePanel.add(scaleX = new JTextField());
//        scalePanel.add(scaleY = new JTextField());
//        scalePanel.add(scaleZ = new JTextField());
//
//        int height = translationX.getPreferredSize().height;
//
//        translationX.setPreferredSize(new Dimension(100, height));
//        rotationX.setPreferredSize(new Dimension(100, height));
//        scaleX.setPreferredSize(new Dimension(100, height));
//
//        add(transformPanel, BorderLayout.CENTER);
//
//        JPanel radioPanel = new JPanel();
//        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
//
//        displayNormal = new JRadioButton("normal");
//        displayHighlight = new JRadioButton("highlight");
//        displaySelected = new JRadioButton("selected");
//        displayGrayed = new JRadioButton("grayed");
//
//        ButtonGroup radioGroup = new ButtonGroup();
//        radioGroup.add(displayNormal);
//        radioGroup.add(displayHighlight);
//        radioGroup.add(displaySelected);
//        radioGroup.add(displayGrayed);
//
//        ActionListener listener = new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                onRadioButton(e);
//            }
//        };
//
//        displayNormal.addActionListener(listener);
//        displayHighlight.addActionListener(listener);
//        displaySelected.addActionListener(listener);
//        displayGrayed.addActionListener(listener);
//
//        radioPanel.add(displayNormal);
//        radioPanel.add(displayHighlight);
//        radioPanel.add(displaySelected);
//        radioPanel.add(displayGrayed);
//
//        add(radioPanel, BorderLayout.SOUTH);
    }

//    private void onRadioButton(ActionEvent e) {
//
//        if(currentSelection == null || !(currentSelection instanceof Representation))
//            return;
//
//        Representation rep = (Representation) currentSelection;
//
//        if(e.getSource() == displayNormal) {
//            rep.setDisplayGrayed(false);
//            rep.setDisplayHighlight(false);
//            rep.setDisplaySelected(false);
//        } else if(e.getSource() == displayHighlight) {
//            rep.setDisplayGrayed(false);
//            rep.setDisplayHighlight(true);
//            rep.setDisplaySelected(false);
//        } else if(e.getSource() == displaySelected) {
//            rep.setDisplayGrayed(false);
//            rep.setDisplayHighlight(false);
//            rep.setDisplaySelected(true);
//        } else if(e.getSource() == displayGrayed) {
//            rep.setDisplayGrayed(true);
//            rep.setDisplayHighlight(false);
//            rep.setDisplaySelected(false);
//        }
//    }
    public void updateSelection(Spatial spatial) {
        this.currentSelection = spatial;

        String text = "";

        text += "name: " + spatial.getName() + "\n";
        text += "triangle count: " + spatial.getTriangleCount() + "\n";
        text += "vertext count: " + spatial.getVertexCount() + "\n";
        text += spatial.getClass() + "\n";

        MaterialState materialState = (MaterialState) spatial.getRenderState(StateType.Material);
        if (materialState != null) {
            text += "material: \n";
            text += "    ambient:  " + materialState.getAmbient() + "\n";
            text += "    diffuse:  " + materialState.getDiffuse() + "\n";
            text += "    specular: " + materialState.getSpecular() + "\n";
            text += "    emissive: " + materialState.getEmissive() + "\n";
        } else {
            text += "no material\n";
        }

        TextureState textureState = (TextureState) spatial.getRenderState(StateType.Texture);
        if (textureState != null) {
            text += "texture: \n";
            text += "    "+textureState.getTexture().getImageLocation();
        } else {
            text += "no texture\n";
        }

        if (spatial instanceof TriMesh) {
            TriMesh mesh = (TriMesh) spatial;

        }


        infoArea.setText(text);

//
//        if (spatial == null) {
//            translationX.setText("");
//            translationY.setText("");
//            translationZ.setText("");
//            rotationX.setText("");
//            rotationY.setText("");
//            rotationZ.setText("");
//            scaleX.setText("");
//            scaleY.setText("");
//            scaleZ.setText("");
//        } else {
//            Vector3f localTranslation = spatial.getLocalTranslation();
//            Vector3f localScale = spatial.getLocalScale();
//            float eulerAngles[] = new float[3];
//            spatial.getLocalRotation().toAngles(eulerAngles);
//
//            translationX.setText("" + localTranslation.x);
//            translationY.setText("" + localTranslation.y);
//            translationZ.setText("" + localTranslation.z);
//
//            for (int i = 0; i < eulerAngles.length; i++) {
//                eulerAngles[i] = 180 * (eulerAngles[i] / (float) Math.PI);
//            }
//
//            rotationX.setText("" + eulerAngles[0]);
//            rotationY.setText("" + eulerAngles[1]);
//            rotationZ.setText("" + eulerAngles[2]);
//
//            scaleX.setText("" + localScale.x);
//            scaleY.setText("" + localScale.y);
//            scaleZ.setText("" + localScale.z);
//        }
//
//        if (spatial != null && spatial instanceof Representation) {
//            displayNormal.setEnabled(true);
//            displayHighlight.setEnabled(true);
//            displaySelected.setEnabled(true);
//            displayGrayed.setEnabled(true);
//
//            Representation rep = (Representation) spatial;
//
//            if (rep.getDisplayGrayed()) {
//                displayGrayed.setSelected(true);
//            } else if (rep.isHover()) {
//                displayHighlight.setSelected(true);
//            } else if (rep.isSelected()) {
//                displaySelected.setSelected(true);
//            } else {
//                displayNormal.setSelected(true);
//            }
//
//        } else {
//            displayNormal.setEnabled(false);
//            displayHighlight.setEnabled(false);
//            displaySelected.setEnabled(false);
//            displayGrayed.setEnabled(false);
//        }

    }
}
