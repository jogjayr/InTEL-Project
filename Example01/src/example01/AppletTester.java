/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example01;

import com.jme.scene.Node;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.World;
import edu.gatech.statics.application.AppletLauncher;
import edu.gatech.statics.application.StaticsApplet;
import edu.gatech.statics.application.StaticsApplication;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Calvin Ashmore
 */
public class AppletTester {

    private static StaticsApplet createApplet() {
        StaticsApplet applet = new AppletLauncher();
        applet.setExercise("example01.PurseExercise");
        applet.setPreferredSize(new Dimension(900, 675));
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
        
        JPanel buttonBar = new JPanel(new FlowLayout());
        
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
        buttonBar.add(button);
        
        /*button = new JButton("Thingy");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Texture texture = TextureManager.loadTexture(getClass().getClassLoader().
                        getResource( "example01/assets/lowerArm.png" ), Texture.FM_LINEAR, Texture.FM_LINEAR);
                com.jme.image.Image jmeImage = texture.getImage();
                BufferedImage img = new BufferedImage(jmeImage.getWidth(), jmeImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                //img.setData(new Raster())
                
                jmeImage.getData().rewind();
                
                for(int x=0;x<jmeImage.getWidth();x++)
                for(int y=0;y<jmeImage.getHeight();y++) {
                    int pixel = 0;
                    pixel |= jmeImage.getData().get() << 0;
                    pixel |= jmeImage.getData().get() << 8;
                    pixel |= jmeImage.getData().get() << 16;
                    pixel |= jmeImage.getData().get() << 24;
                    img.setRGB(x, y, pixel);
                }
                
                JFrame frame = new JFrame("Thingy");
                frame.add(new JLabel(new ImageIcon(img)));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                frame.pack();
            }
        });
        buttonBar.add(button);*/
        
        
        /*button = new JButton("Thingy");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Texture texture = TextureManager.loadTexture(getClass().getClassLoader().
                        getResource( "example01/assets/lowerArm.png" ), Texture.FM_LINEAR, Texture.FM_LINEAR);
                
                TextureManager.releaseTexture(texture);
            }
        });
        buttonBar.add(button);*/
        
        button = new JButton("Thingy");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                World world = StaticsApplication.getApp().getCurrentWorld();
                
                List<RepresentationLayer> layers = RepresentationLayer.getLayers();
                for(RepresentationLayer layer : layers) {
                    Node node = world.getNode(layer);
                    System.out.println(layer.getName() + ": "+node.getWorldBound());
                }
            }
        });
        buttonBar.add(button);
        
        frame.add(buttonBar, BorderLayout.SOUTH);
        frame.pack();
    }
}
