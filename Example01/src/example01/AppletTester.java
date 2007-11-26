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
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Calvin Ashmore
 */
public class AppletTester {

    private static StaticsApplet createApplet() {
        StaticsApplet applet = new AppletLauncher();
        applet.setExercise("example01.PurseExercise");
        applet.setPreferredSize(new Dimension(1024, 768));
        return applet;
    }
    
    static StaticsApplet applet;
    public static void main(String args[]) {
        final JFrame frame = new JFrame("Applet Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
        
        frame.setLayout(new BorderLayout());
        
        applet = createApplet();
        applet.init();
        applet.start();
        frame.add(applet,BorderLayout.CENTER);
        
        
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
                frame.add(applet,BorderLayout.CENTER);
                frame.pack();
            }
        });
        
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
    }
}
