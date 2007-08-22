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

/**
 * Codes and constants shared by the BUI components.
 */
public interface BConstants
{
    /** An alignment constant. */
    public static final int LEFT = 0;

    /** An alignment constant. */
    public static final int RIGHT = 1;

    /** An alignment constant. */
    public static final int CENTER = 2;

    /** An alignment constant. */
    public static final int TOP = 0;

    /** An alignment constant. */
    public static final int BOTTOM = 1;

    /** An orientation constant. */
    public static final int HORIZONTAL = 0;

    /** An orientation constant. */
    public static final int VERTICAL = 1;

    /** A special orientation constant for labels. */
    public static final int OVERLAPPING = 2;

    /** A code for text with no effects.*/
    public static final int NORMAL = 0;

    /** A code for text with a single pixel outline.*/
    public static final int OUTLINE = 1;

    /** A code for text with a single pixel drop shadow.*/
    public static final int SHADOW = 2;

    /** A code for text with no effect and no styling.*/
    public static final int PLAIN = 3;

    /** The default text effect size. */
    public static final int DEFAULT_SIZE = 1;
}
