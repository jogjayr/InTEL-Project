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

package com.jmex.bui.event;

import java.util.EventObject;

/**
 * The base event class for all BUI events.
 */
public class BEvent extends EventObject
{
    /**
     * Returns the time at which this event was generated or -1 if this
     * event was not a result of a user action with an associated
     * timestamp.
     */
    public long getWhen ()
    {
        return _when;
    }

    /**
     * Generates a string representation of this instance.
     */
    public String toString ()
    {
        StringBuffer buf = new StringBuffer("[ev:");
        toString(buf);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Instructs this event to notify the supplied listener if they
     * implement an interface appropriate to this event.
     */
    public void dispatch (ComponentListener listener)
    {
        if (listener instanceof EventListener) {
            ((EventListener)listener).eventDispatched(this);
        }
    }

    /**
     * Returns true if this event should be propagated up the interface
     * hierarchy (input events) or false if it should be considered processed
     * once it is dispatched on its originating component (derivative events
     * like action or text events).
     */
    public boolean propagateUpHierarchy ()
    {
        return true;
    }

    protected BEvent (Object source, long when)
    {
        super(source);
        _when = when;
    }

    protected void toString (StringBuffer buf)
    {
        String name = getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        buf.append("type=").append(name);
        buf.append(", source=").append(source);
        buf.append(", when=").append(_when);
    }

    protected long _when;
}
