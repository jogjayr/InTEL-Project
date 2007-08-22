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

import java.util.ArrayList;
import java.util.logging.Level;

import com.jmex.bui.Log;

/**
 * Defines the model that underlies the BUI text components.
 */
public class Document
{
    /** Used to listen for changes to this document. */
    public interface Listener
    {
        /**
         * Called when text is inserted into a document. The text will have
         * already been inserted into the document.
         *
         * @param document the document into which text was inserted.
         * @param offset the offset into the document of the inserted text.
         * @param length the length of the inserted text.
         */
        public void textInserted (Document document, int offset, int length);

        /**
         * Called when text is removed from a document. The text will have
         * already been removed from the document.
         *
         * @param document the document from which text was removed.
         * @param offset the offset into the document of the removed text.
         * @param length the length of the removed text.
         */
        public void textRemoved (Document document, int offset, int length);
    }

    /** Registers a document listener. */
    public void addListener (Listener listener)
    {
        if (_listeners == null) {
            _listeners = new ArrayList<Listener>();
        }
        _listeners.add(listener);
    }

    /** Clears a document listener registration. */
    public void removeListener (Listener listener)
    {
        if (_listeners != null) {
            _listeners.remove(listener);
        }
    }

    /** Returns the entire text of the document. */
    public String getText ()
    {
        return _text;
    }

    /** Returns a subset of the text of the document. */
    public String getText (int offset, int length)
    {
        return _text.substring(offset, length);
    }

    /**
     * Replaces the current contents of the document with the specified text.
     *
     * @return true if the text was changed, false if it was rejected by the
     * document validator.
     */
    public boolean setText (String text)
    {
        return replace(0, getLength(), text);
    }

    /** Returns the number of characters in the document. */
    public int getLength ()
    {
        return _text.length();
    }

    /**
     * Inserts the specified text at the specified offset.
     *
     * @return true if the text was inserted, false if it was rejected by the
     * document validator.
     */
    public boolean insert (int offset, String text)
    {
        return replace(offset, 0, text);
    }

    /**
     * Deletes specified run of text at the specified offset.
     *
     * @return true if the text was removed, false if it was rejected by the
     * document validator.
     */
    public boolean remove (int offset, int length)
    {
        return replace(offset, length, "");
    }

    /**
     * Replaces the specified run of text with the supplied new text.
     *
     * @return true if the text was replaced, false if it was rejected by the
     * document validator.
     */
    public boolean replace (int offset, int length, String text)
    {
        StringBuffer buf = new StringBuffer();
        if (offset > 0) {
            buf.append(_text.substring(0, offset));
        }
        buf.append(text);
        if (_text.length() > 0) {
            buf.append(_text.substring(offset+length, _text.length()));
        }

        String ntext = buf.toString();
        if (!validateEdit(_text, ntext)) {
            return false;
        }

        _text = ntext;
        if (length > 0) {
            notify(false, offset, length);
        }
        if (text.length() > 0) {
            notify(true, offset, text.length());
        }
        return true;
    }

    /**
     * Provides an opportunity for edits to be rejected before being applied to
     * the document.
     *
     * @return true if the document should be configured with the specified new
     * text, false if the old text should remain.
     */
    protected boolean validateEdit (String oldText, String newText)
    {
        return true;
    }

    /**
     * Notifies document listeners.
     */
    protected void notify (boolean inserted, int offset, int length)
    {
        if (_listeners != null) {
            String action = inserted ? "insertion" : "removal";
            for (int ii = _listeners.size()-1; ii >= 0; ii--) {
                Listener list = _listeners.get(ii);
                try {
                    if (inserted) {
                        list.textInserted(this, offset, length);
                    } else {
                        list.textRemoved(this, offset, length);
                    }
                } catch (Throwable t) {
                    Log.log.log(Level.WARNING, "Document listener choked on " +
                                action + " [doc=" + this +
                                ", offset=" + offset + ", length=" + length +
                                ", listener=" + list + "].", t);
                }
            }
        }
    }

    protected String _text = "";
    protected ArrayList<Listener> _listeners;
}
