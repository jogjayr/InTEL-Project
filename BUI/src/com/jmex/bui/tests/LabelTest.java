//
// $Id$
//
// BUI - a user interface library for the JME 3D engine
// Copyright (C) 2005, Michael Bayne, All Rights Reserved
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.jmex.bui.tests;

import java.util.logging.Level;

import com.jme.util.LoggingSystem;

import com.jmex.bui.*;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.icon.ImageIcon;

/**
 * Does something extraordinary.
 */
public class LabelTest extends BaseTest
    implements BConstants
{
    protected void createWindows (BRootNode root, BStyleSheet style)
    {
        BWindow window = new BDecoratedWindow(style, null);
        window.setLayoutManager(GroupLayout.makeVStretch());

        BImage image = null;
        try {
            image = new BImage(getClass().getClassLoader().
                              getResource("rsrc/textures/scroll_right.png"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        ImageIcon icon = new ImageIcon(image);
        String[] aligns = { "left", "center", "right" };
        int[] orients = { HORIZONTAL, VERTICAL, OVERLAPPING };

        for (int yy = 0; yy < 3; yy++) {
            BContainer cont = new BContainer(GroupLayout.makeHStretch());
            window.add(cont);
            for (int xx = 0; xx < 3; xx++) {
                BLabel label = new BLabel("This is a lovely label " +
                                          aligns[xx] + "/" + orients[yy] + ".",
                                          aligns[xx]);
                label.setIcon(icon);
                label.setOrientation(orients[yy]);
                cont.add(label);
            }
        }

        root.addWindow(window);
        window.setSize(400, 400);
        window.setLocation(25, 25);
    }

    public static void main (String[] args)
    {
        LoggingSystem.getLogger().setLevel(Level.OFF);
        LabelTest test = new LabelTest();
        test.start();
    }
}
