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
package example01;

import edu.gatech.statics.application.AppletLauncher;
import edu.gatech.statics.application.StaticsApplet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Calvin Ashmore
 */
public class AppletTester {

    private static StaticsApplet createApplet() {

        final Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("userID", "0");
        parameters.put("assignmentID", "0");
        parameters.put("problemID", "0");
        parameters.put("problemName", "Arm and Purse");

        StaticsApplet myApplet = new AppletLauncher() {

            @Override
            public String getParameter(String name) {
                return parameters.get(name);
            }
        };

        myApplet.setExercise("example01.PurseExerciseGraded");
        int width = 1100;
        int height = 768;

        //myApplet.setResolution(width, height);
        myApplet.setPreferredSize(new Dimension(width, height));
        return myApplet;
    }
    static StaticsApplet applet;

    public static void main(String args[]) {
        final JFrame frame = new JFrame("Applet Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        frame.setLayout(new BorderLayout());

        applet =
                createApplet();
        applet.init();
        applet.start();
        frame.add(applet, BorderLayout.CENTER);


        JButton button = new JButton("Change");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                applet.stop();
                applet.destroy();
                frame.remove(applet);
                applet = null;

                System.gc();
                System.out.println("******************");
                System.out.println("LOADING NEW APPLET");
                System.out.println("******************");

                applet = createApplet();
                applet.init();
                applet.start();
                frame.add(applet, BorderLayout.CENTER);
                frame.pack();
            }
        });

        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
    }
}
