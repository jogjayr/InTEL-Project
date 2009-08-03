/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.background.ImageBackground;
import java.io.IOException;
import java.net.URL;

/**
 * Static class for handy methods for working with buttons.
 * @author Calvin Ashmore
 */
public class ButtonUtil {

    private ButtonUtil() {
    }

    /**
     * This is a handy little helper method to set the image states for a button.
     * @param button
     * @param prefix
     */
    public static void setImageBackground(BButton button, String prefix) {

        // states are:
        // 0) up
        // 1) hover
        // 2) disabled
        // 3) down

        try {
            BImage image;
            URL resource;

            image = new BImage(BButton.class.getClassLoader().getResource(prefix + "_up.png"));
            button.setBackground(0, new ImageBackground(0, image));

            image = new BImage(BButton.class.getClassLoader().getResource(prefix + "_hover.png"));
            button.setBackground(1, new ImageBackground(0, image));

            image = new BImage(BButton.class.getClassLoader().getResource(prefix + "_down.png"));
            button.setBackground(3, new ImageBackground(0, image));

            resource = BButton.class.getClassLoader().getResource(prefix + "_disabled.png");
            if (resource != null) {
                image = new BImage(resource);
                button.setBackground(2, new ImageBackground(0, image));
            }
        } catch (IOException e) {
        }

        button.invalidate();
    }
}
