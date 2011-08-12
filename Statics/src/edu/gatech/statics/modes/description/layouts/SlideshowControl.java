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
package edu.gatech.statics.modes.description.layouts;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.AbsoluteLayout;
import com.jmex.bui.util.Point;
import edu.gatech.statics.ui.ButtonUtil;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SlideshowControl extends BContainer {

    private List<ImageIcon> icons = new ArrayList<ImageIcon>();
    private BLabel mainLabel;
    private int position = 0;
    private int width, height;
    private BButton previousButton, nextButton;


    /**
     * 
     * @param width of control
     * @param height of control
     */
    public SlideshowControl(int width, int height) {

        super(new AbsoluteLayout());

        mainLabel = new BLabel("");
        add(mainLabel, new Point(0, 0));

        this.width = width;
        this.height = height;
        setPreferredSize(width, height);

    }

    /**
     * 
     * @param images List of images displayed by control
     */
    public void setImages(List<BufferedImage> images) {

        if (icons.size() > 0) {
            //throw new IllegalStateException("Can't add set up the slideshow control twice!");
            return;
        }

        for (BufferedImage bufferedImage : images) {
            icons.add(new ImageIcon(new BImage(bufferedImage)));
        }

        if (icons.size() == 0) {
            return;
        }

        position = 0;
        setIcon(position);

        if (icons.size() > 1) {

            ActionListener listener = new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    if ("previous".equals(event.getAction())) {
                        onPrevious();
                    } else if ("next".equals(event.getAction())) {
                        onNext();
                    }
                }
            };

            previousButton = new BButton("", listener, "previous");
            nextButton = new BButton("", listener, "next");

            ButtonUtil.setImageBackground(nextButton, "rsrc/interfaceTextures/navigation/nav_right");
            ButtonUtil.setImageBackground(previousButton, "rsrc/interfaceTextures/navigation/nav_left");

            int offset = 10;

            add(previousButton, new Point(offset, offset));
            add(nextButton, new Point(width - 30 - offset, offset));
        }
    }

    /**
     * Handles previous button click
     */
    private void onPrevious() {
        if (icons.size() == 0) {
            return;
        }

        position--;
        if (position < 0) {
            position += icons.size();
        }

        setIcon(position);
    }

    /**
     * Handles next button click
     */
    private void onNext() {
        if (icons.size() == 0) {
            return;
        }

        position++;
        if (position >= icons.size()) {
            position -= icons.size();
        }

        setIcon(position);
    }

    /**
     *
     * @param position 
     */
    private void setIcon(int position) {
        ImageIcon icon = icons.get(position);
        mainLabel.setIcon(icon);
        remove(mainLabel);
        add(mainLabel, new Point((width - icon.getWidth()) / 2, mainLabel.getY()));

        // remove and add buttons so that they
        if (previousButton != null) {
            remove(previousButton);
            remove(nextButton);
            
            int offset = 10;
            add(previousButton, new Point(offset, offset));
            add(nextButton, new Point(width - 30 - offset, offset));
        }
    }
}
