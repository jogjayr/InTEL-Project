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

package com.jmex.bui;

import com.jmex.bui.layout.GroupLayout;

/**
 * A top-level window with a border, a background and a title bar. Note that a
 * decorated window uses a stretching {@link GroupLayout} and adds a label at
 * the top in the <code>window_title</code> style if a title was specified.
 */
public class BDecoratedWindow extends BWindow
{
    /**
     * Creates a decorated window using the supplied look and feel.
     *
     * @param title the title of the window or null if no title bar is
     * desired.
     */
    public BDecoratedWindow (BStyleSheet style, String title)
    {
        super(style, GroupLayout.makeVStretch());
        ((GroupLayout)getLayoutManager()).setOffAxisPolicy(
            GroupLayout.CONSTRAIN);

        if (title != null) {
            add(new BLabel(title, "window_title"), GroupLayout.FIXED);
        }
    }

    // documentation inherited
    protected String getDefaultStyleClass ()
    {
        return "decoratedwindow";
    }
}
