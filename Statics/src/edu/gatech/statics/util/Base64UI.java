/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

/**
 *
 * @author Calvin Ashmore
 */
public class Base64UI extends JFrame {

    public static void main(String args[]) {
        Base64UI ui = new Base64UI();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.pack();
        ui.setVisible(true);
    }
    private JTextArea sourceArea;
    private JTextArea targetArea;

    public Base64UI() {

        sourceArea = new JTextArea(10, 40);
        targetArea = new JTextArea(10, 40);

        //sourceArea.setPreferredSize(new Dimension(400, 300));
        //targetArea.setPreferredSize(new Dimension(400, 300));

        JScrollPane sourceScrollPane = new JScrollPane(sourceArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane targetScrollPane = new JScrollPane(targetArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sourceScrollPane, targetScrollPane);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(splitPane, BorderLayout.CENTER);

        JButton button = new JButton("Decode");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                decode();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(contentPanel);
    }

    private void decode() {

        String text = sourceArea.getText();
        byte[] compressedData = Base64.decode(text);


        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);
        ByteArrayOutputStream zout = new ByteArrayOutputStream();
        try {
            int dataDecompressed;
            byte[] partialData = new byte[1024];
            while ((dataDecompressed = inflater.inflate(partialData)) != 0) {
                zout.write(partialData, 0, dataDecompressed);
            }
        } catch (DataFormatException ex) {
            throw new IllegalArgumentException("State data did not include a valid state ");
        }
        byte[] xmlData = zout.toByteArray();


        targetArea.setText(new String(xmlData));
    }
}
