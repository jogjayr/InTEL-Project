/*
 * FBDIcon.java
 *
 * Created on June 22, 2007, 3:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.application.ui;

import com.jmex.bui.icon.BIcon;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.icon.ImageIcon;
import edu.gatech.statics.application.ScreenshotListener;
import edu.gatech.statics.application.StaticsApplication;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDIcon {//extends BContainer {

    //private BLabel label;
    
    public BIcon getIcon() {return myIcon;}
    private BIcon myIcon;
    
    public BLabel makeLabel() {
        if(getIcon() != null)
            return new BLabel(myIcon);
        else {
            BLabel label = new BLabel((BIcon)null);
            //label.setPreferredSize((int)(90*1.33333), 90);
            labelListeners.add(new LabelListener(label));
            return label;
        }
    }
    
    /** Creates a new instance of FBDIcon */
    public FBDIcon() {//(Texture tex) {
        //setStyleClass("info_window");
        
        //setPreferredSize(90, 90);

        // this is how we get our icon screenshot
        StaticsApplication.getApp().addScreenshotListener(new IconScreenshotListener());
    }
    
    private List<LabelListener> labelListeners = new ArrayList();
    
    private class LabelListener {
        private BLabel label;
        public LabelListener(BLabel label) {
            this.label = label;
        }
        
        public void update() {
            label.setIcon(myIcon);
        }
    }

    private class IconScreenshotListener implements ScreenshotListener {
        public void onScreenshot(BufferedImage image) {
            
            //int windowWidth = image.getWidth();
            int windowHeight = image.getHeight();

            int imageHeight = 90;
            //int imageWidth = windowWidth * imageHeight / windowHeight;

            float scale = (float) imageHeight / windowHeight;

            AffineTransform tx = new AffineTransform();
            tx.scale(scale, scale);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
            image = op.filter(image, null);

            BImage bImage = new BImage(image);
            BIcon icon = new ImageIcon(bImage);

            //label = new BLabel(icon);

            //add(label, BorderLayout.CENTER);
            //label.setBounds(0, 0, imageWidth, imageHeight);
            //setPreferredSize(imageWidth, imageHeight);
            
            myIcon = icon;

            // update
            for (FBDIcon.LabelListener labelListener : labelListeners) {
                labelListener.update();
            }

        }
    }

    /*@Override
    public void setParent(BContainer parent) {
        // override this as to clean things up and supress error messages
        if (getParent() != null) {
            getParent().remove(this);
        }

        super.setParent(parent);
    }*/
    
}
