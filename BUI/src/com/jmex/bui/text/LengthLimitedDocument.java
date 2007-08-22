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

package com.jmex.bui.text;

import com.jmex.bui.BTextField;

/**
 * A document for use with a {@link BTextField} that limits the input to a
 * maximum length.
 */
public class LengthLimitedDocument extends Document
{
    /**
     * Creates a document that will limit its maximum length to the specified
     * value.
     */
    public LengthLimitedDocument (int maxLength)
    {
        _maxLength = maxLength;
    }

    // documentation inherited
    protected boolean validateEdit (String oldText, String newText)
    {
        return newText.length() <= _maxLength;
    }

    protected int _maxLength;
}
