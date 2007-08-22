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

package com.jmex.bui.layout;

import java.util.HashMap;

import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.util.Dimension;
import com.jmex.bui.util.Insets;
import com.jmex.bui.util.Point;
import com.jmex.bui.util.Rectangle;

/**
 * Lays out components at absolute coordinate and with (optional) absolute
 * sizes. <em>Note:</em> the components are laid out in a coordinate system
 * defined from the inside of the insets of the container rather than from the
 * very edge of the container.
 */
public class AbsoluteLayout extends BLayoutManager
{
    public AbsoluteLayout ()
    {
        this(false);
    }

    /**
     * @param flipped If true, will treat the y coordinates as 0 for the top
     * and height for the bottom.
     */
    public AbsoluteLayout (boolean flipped)
    {
        _flipped = flipped;
    }

    // documentation inherited
    public void addLayoutComponent (BComponent comp, Object constraints)
    {
        // various sanity checking
        if (constraints instanceof Point) {
            Point p = (Point)constraints;
            if (p.x < 0 || p.y < 0) {
                throw new IllegalArgumentException(
                    "Components must be laid out at positive coords: " + p);
            }

        } else if (constraints instanceof Rectangle) {
            Rectangle r = (Rectangle)constraints;
            if (r.x < 0 || r.y < 0) {
                throw new IllegalArgumentException(
                    "Components must be laid out at positive coords: " + r);
            }
            if (r.width < 0 || r.height < 0) {
                throw new IllegalArgumentException(
                    "Constraints must specify positive dimensions: " + r);
            }

        } else {
            throw new IllegalArgumentException(
                "Components must be added to an AbsoluteLayout with " +
                "Point or Rectangle constraints.");
        }

        _spots.put(comp, constraints);
    }

    // documentation inherited
    public void removeLayoutComponent (BComponent comp)
    {
        _spots.remove(comp);
    }

    // documentation inherited
    public Dimension computePreferredSize (
        BContainer target, int whint, int hhint)
    {
        // determine the largest rectangle that contains all of the components
        Rectangle rec = new Rectangle();
        for (int ii = 0, cc = target.getComponentCount(); ii < cc; ii++) {
            BComponent comp = target.getComponent(ii);
            if (!comp.isVisible()) {
                continue;
            }
            Object cons = _spots.get(comp);
            if (cons instanceof Point) {
                Point p = (Point)cons;
                Dimension d = comp.getPreferredSize(-1, -1);
                rec.add(p.x, p.y, d.width, d.height);
            } else if (cons instanceof Rectangle) {
                Rectangle r = (Rectangle)cons;
                rec.add(r.x, r.y, r.width, r.height);
            }
        }
        return new Dimension(rec.x + rec.width, rec.y + rec.height);
    }

    // documentation inherited
    public void layoutContainer (BContainer target)
    {
        Insets insets = target.getInsets();
        int height = target.getHeight();
        for (int ii = 0, cc = target.getComponentCount(); ii < cc; ii++) {
            BComponent comp = target.getComponent(ii);
            if (!comp.isVisible()) {
                continue;
            }
            Object cons = _spots.get(comp);
            if (cons instanceof Point) {
                Point p = (Point)cons;
                Dimension d = comp.getPreferredSize(-1, -1);
                comp.setBounds(insets.left + p.x, 
                        (_flipped ? height - d.height - insets.top - p.y : 
                                   insets.bottom + p.y), d.width, d.height);
            } else if (cons instanceof Rectangle) {
                Rectangle r = (Rectangle)cons;
                comp.setBounds(insets.left + r.x,
                        (_flipped ? height - r.height - insets.top - r.y : 
                                   insets.bottom + r.y), r.width, r.height);
            }
        }
    }

    protected boolean _flipped;
    protected HashMap<BComponent, Object> _spots =
        new HashMap<BComponent, Object>();
}
