//
// $Id$
//
// BUI - a user interface library for the JME 3D engine
// Copyright (C) 2005-2006, Michael Bayne, All Rights Reserved
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

import java.io.InputStream;
import java.io.InputStreamReader;

import com.jme.app.SimpleGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.MouseInput;
import com.jme.renderer.ColorRGBA;

import com.jmex.bui.BRootNode;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.PolledRootNode;

/**
 * A base class for our various visual tests.
 */
public abstract class BaseTest extends SimpleGame
{
    protected void simpleInitGame ()
    {
        _root = new PolledRootNode(timer, input);
        rootNode.attachChild(_root);

        // we don't hide the cursor
        MouseInput.get().setCursorVisible(true);

        // load up the default BUI stylesheet
        BStyleSheet style = null;
        try {
            InputStream stin = getClass().getClassLoader().
                getResourceAsStream("rsrc/style.bss");
            style = new BStyleSheet(new InputStreamReader(stin),
                                    new BStyleSheet.DefaultResourceProvider());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        createWindows(_root, style);

        // these just get in the way
        KeyBindingManager.getKeyBindingManager().remove("toggle_pause");
        KeyBindingManager.getKeyBindingManager().remove("toggle_wire");
        KeyBindingManager.getKeyBindingManager().remove("toggle_lights");
        KeyBindingManager.getKeyBindingManager().remove("toggle_bounds");
        KeyBindingManager.getKeyBindingManager().remove("camera_out");

        lightState.setEnabled(false);

        display.getRenderer().setBackgroundColor(ColorRGBA.gray);
    }

    protected void simpleUpdate ()
    {
    }

    protected abstract void createWindows (BRootNode root, BStyleSheet style);

    protected PolledRootNode _root;
}
