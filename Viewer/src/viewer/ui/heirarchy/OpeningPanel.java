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
