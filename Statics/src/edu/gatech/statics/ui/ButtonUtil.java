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
