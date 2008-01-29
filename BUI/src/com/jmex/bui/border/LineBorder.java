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

package com.jmex.bui.border;

import org.lwjgl.opengl.GL11;

import com.jme.renderer.ColorRGBA;
import com.jme.renderer.RenderContext;
import com.jme.renderer.Renderer;
import com.jme.scene.state.lwjgl.records.LineRecord;
import com.jme.system.DisplaySystem;

import com.jmex.bui.BComponent;
import com.jmex.bui.BImage;
import com.jmex.bui.util.Insets;

/**
 * Defines a border that displays a single line around the bordered component in a specified color.
 */
public class LineBorder extends BBorder
{
    public LineBorder (ColorRGBA color)
    {
        this(color, 1);
    }

    public LineBorder (ColorRGBA color, int width)
    {
        _color = color;
        _width = width;
    }

    @Override // from BBorder
    public Insets adjustInsets (Insets insets)
    {
        return new Insets(_width + insets.left, _width + insets.top,
                          _width + insets.right, _width + insets.bottom);
    }

    @Override // from BBorder
    public void render (Renderer renderer, int x, int y, int width, int height, float alpha)
    {
        super.render(renderer, x, y, width, height, alpha);

        BComponent.applyDefaultStates();
        BImage.blendState.apply();

        RenderContext ctx = DisplaySystem.getDisplaySystem().getCurrentContext();
        ((LineRecord)ctx.getLineRecord()).applyLineWidth(_width);
        float offset = _width / 2f;
        GL11.glColor4f(_color.r, _color.g, _color.b, _color.a * alpha);
        /*GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x + offset, y + offset);
        GL11.glVertex2f(x + width - offset, y + offset);
        GL11.glVertex2f(x + width - offset, y + height - offset);
        GL11.glVertex2f(x + offset, y + height - offset);
        GL11.glVertex2f(x + offset, y + offset);
        GL11.glEnd();*/
        
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x, y + offset);
        GL11.glVertex2f(x + width, y + offset);
        
        GL11.glVertex2f(x + width - offset, y);
        GL11.glVertex2f(x + width - offset, y + height);
        
        GL11.glVertex2f(x + width, y + height - offset);
        GL11.glVertex2f(x, y + height - offset);
        
        GL11.glVertex2f(x + offset, y + height);
        GL11.glVertex2f(x + offset, y);
        GL11.glEnd();
    }

    protected ColorRGBA _color;
    protected int _width;

    protected static final Insets ONE_PIXEL_INSETS = new Insets(1, 1, 1, 1);
}
