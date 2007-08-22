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

package com.jmex.bui.text;

import com.jme.renderer.ColorRGBA;

import com.jmex.bui.BConstants;

/**
 * Creates instances of {@link BText} using a particular technology and a
 * particular font configuration.
 */
public abstract class BTextFactory
    implements BConstants
{
    /**
     * Returns the height of our text.
     */
    public abstract int getHeight ();

    /**
     * Creates a text instance using our the font configuration associated
     * with this text factory and the foreground color specified.
     */
    public BText createText (String text, ColorRGBA color)
    {
        return createText(text, color, NORMAL, DEFAULT_SIZE, null, false);
    }

    /**
     * Creates a text instance using our the font configuration associated with
     * this text factory and the foreground color, text effect and text effect
     * color specified.
     *
     * @param useAdvance if true, the advance to the next insertion point will
     * be included in the bounds of the created text (this is needed by
     * editable text displays).
     */
    public abstract BText createText (
            String text, ColorRGBA color, int effect, int effectSize,
            ColorRGBA effectColor, boolean useAdvance);

    /**
     * Wraps a string into a set of text objects that do not exceed the
     * specified width.
     */
    public BText[] wrapText (String text, ColorRGBA color, int maxWidth)
    {
        return wrapText(text, color, NORMAL, DEFAULT_SIZE, null, maxWidth);
    }

    /**
     * Wraps a string into a set of text objects that do not exceed the
     * specified width.
     */
    public abstract BText[] wrapText (
        String text, ColorRGBA color, int effect, int effectSize, 
        ColorRGBA effectColor, int maxWidth);
}
