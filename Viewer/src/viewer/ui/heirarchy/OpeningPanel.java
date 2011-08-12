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

import edu.gatech.statics.exercise.Exercise;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import viewer.ViewerExercise;

/**
 *
 * @author Calvin Ashmore
 */
public class OpeningPanel extends JPanel {

    private JTextField textField;

    public OpeningPanel() {
        //super("Select a file");

        setLayout(new BorderLayout());
        add(new JLabel("Please select a file"), BorderLayout.NORTH);

        JPanel selectPanel = new JPanel(new BorderLayout());
        add(selectPanel, BorderLayout.CENTER);

        textField = new JTextField("");
        selectPanel.add(textField, BorderLayout.CENTER);

        JButton button = new JButton("Open...");
        selectPanel.add(button, BorderLayout.EAST);

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showOpenDialog();
            }
        });

        textField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        setBorder(new EmptyBorder(4, 4, 4, 4));
    }
    private File lastPath = null;

    private void showOpenDialog() {
        JFileChooser chooser;
        if (lastPath == null) {
            lastPath = new File(System.getProperty("user.dir"));
            lastPath = lastPath.getParentFile();
            lastPath = new File(lastPath, "exercises");
        }
        chooser = new JFileChooser(lastPath);

        chooser.setFileFilter(new FileNameExtensionFilter("COLLADA files (*.dae)", "dae"));
        chooser.showOpenDialog(this);

        File file = chooser.getSelectedFile();


        if (file == null || !file.exists()) {
            return;
        }
        lastPath = new File(file.getParent());

        try {
            String filePath = file.getCanonicalPath();
            textField.setText(filePath);
            loadFile();
        } catch (IOException ex) {
        }
    }

    private void loadFile() {
        String filePath = textField.getText();

        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        String filename = file.getName();
        String parent = file.getParent();

        ViewerExercise exercise = (ViewerExercise) Exercise.getExercise();
        exercise.loadModel(filename, parent);
    }
}
