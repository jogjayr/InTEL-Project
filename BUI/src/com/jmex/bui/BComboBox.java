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

import java.util.ArrayList;

import com.jme.renderer.Renderer;

import com.jmex.bui.background.BBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.BEvent;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.util.Dimension;
import com.jmex.bui.util.Insets;

/**
 * Displays a selected value and allows that value to be changed by selecting from a popup menu.
 */
public class BComboBox extends BLabel
{
    /** Used for displaying a label that is associated with a particular non-displayable value. */
    public static class Item
        implements Comparable<Item>
    {
        public Object value;

        public Item (Object value, String label) {
            this.value = value;
            _label = label;
        }

        public String toString () {
            return _label;
        }

        public boolean equals (Object other) {
            Item oitem = (Item)other;
            return (value == null) ? (oitem.value == null) : value.equals(oitem.value);
        }

        public int compareTo (Item other) {
            return _label.compareTo(other._label);
        }

        protected String _label;
    }

    /**
     * Creates an empty combo box.
     */
    public BComboBox ()
    {
        super("");
        setFit(Fit.TRUNCATE);
    }

    /**
     * Creates a combo box with the supplied set of items. The result of {@link Object#toString}
     * for each item will be displayed in the list.
     */
    public BComboBox (Object[] items)
    {
        super("");
        setItems(items);
    }

    /**
     * Creates a combo box with the supplied set of items. The result of {@link Object#toString}
     * for each item will be displayed in the list.
     */
    public BComboBox (Iterable<?> items)
    {
        super("");
        setItems(items);
    }

    /**
     * Appends an item to our list of items. The result of {@link Object#toString} for the item
     * will be displayed in the list.
     */
    public void addItem (Object item)
    {
        addItem(_items.size(), item);
    }

    /**
     * Inserts an item into our list of items at the specified position (zero being before all
     * other items and so forth).  The result of {@link Object#toString} for the item will be
     * displayed in the list.
     */
    public void addItem (int index, Object item)
    {
        _items.add(index, new ComboMenuItem(item));
        clearCache();
    }

    /**
     * Replaces any existing items in this combo box with the supplied items.
     */
    public void setItems (Iterable<?> items)
    {
        clearItems();
        for (Object item : items) {
            addItem(item);
        }
    }

    /**
     * Replaces any existing items in this combo box with the supplied items.
     */
    public void setItems (Object[] items)
    {
        clearItems();
        for (int ii = 0; ii < items.length; ii++) {
            addItem(items[ii]);
        }
    }

    /**
     * Returns the index of the selected item or -1 if no item is selected.
     */
    public int getSelectedIndex ()
    {
        return _selidx;
    }

    /**
     * Returns the selected item or null if no item is selected.
     */
    public Object getSelectedItem ()
    {
        return getItem(_selidx);
    }

    /**
     * Requires that the combo box be configured with {@link Item} items, returns the {@link
     * Item#value} of the currently selected item.
     */
    public Object getSelectedValue ()
    {
        return getValue(_selidx);
    }

    /**
     * Selects the item with the specified index.
     */
    public void selectItem (int index)
    {
        selectItem(index, 0L, 0);
    }

    /**
     * Selects the item with the specified index. <em>Note:</em> the supplied item is compared with
     * the item list using {@link Object#equals}.
     */
    public void selectItem (Object item)
    {
        int selidx = -1;
        for (int ii = 0, ll = _items.size(); ii < ll; ii++) {
            ComboMenuItem mitem = _items.get(ii);
            if (mitem.item.equals(item)) {
                selidx = ii;
                break;
            }
        }
        selectItem(selidx);
    }

    /**
     * Requires that the combo box be configured with {@link Item} items, selects the item with a
     * {@link Item#value} equal to the supplied value.
     */
    public void selectValue (Object value)
    {
        // Item.equals only compares the values
        selectItem(new Item(value, ""));
    }

    /**
     * Returns the number of items in this combo box.
     */
    public int getItemCount ()
    {
        return _items.size();
    }

    /**
     * Returns the item at the specified index.
     */
    public Object getItem (int index)
    {
        return (index < 0 || index >= _items.size()) ? null : _items.get(index).item;
    }

    /**
     * Returns the value at the specified index, the item must be an instance of {@link Item}.
     */
    public Object getValue (int index)
    {
        return (index < 0 || index >= _items.size()) ? null : ((Item)_items.get(index).item).value;
    }

    /**
     * Removes all items from this combo box.
     */
    public void clearItems ()
    {
        clearCache();
        _items.clear();
        _selidx = -1;
    }

    /**
     * Sets the preferred number of columns in the popup menu.
     */
    public void setPreferredColumns (int columns)
    {
        _columns = columns;
        if (_menu != null) {
            _menu.setPreferredColumns(columns);
        }
    }

    @Override // from BComponent
    public boolean dispatchEvent (BEvent event)
    {
        if (event instanceof MouseEvent && isEnabled()) {
            MouseEvent mev = (MouseEvent)event;
            switch (mev.getType()) {
            case MouseEvent.MOUSE_PRESSED:
                if (_menu == null) {
                    _menu = new ComboPopupMenu(_columns);
                }
                _menu.popup(getAbsoluteX(), getAbsoluteY(), false);
                break;

            case MouseEvent.MOUSE_RELEASED:
                break;

            default:
                return super.dispatchEvent(event);
            }

            return true;
        }

        return super.dispatchEvent(event);
    }

    @Override // from BComponent
    protected String getDefaultStyleClass ()
    {
        return "combobox";
    }

    @Override // from BComponent
    protected Dimension computePreferredSize (int whint, int hhint)
    {
        // our preferred size is based on the widest of our items; computing this is rather
        // expensive, so we cache it like we do the menu
        if (_psize == null) {
            _psize = new Dimension();
            Label label = new Label(this);
            for (ComboMenuItem mitem : _items) {
                if (mitem.item instanceof BIcon) {
                    label.setIcon((BIcon)mitem.item);
                } else {
                    label.setText(mitem.item == null ? "" : mitem.item.toString());
                }
                Dimension lsize = label.computePreferredSize(-1, -1);
                _psize.width = Math.max(_psize.width, lsize.width);
                _psize.height = Math.max(_psize.height, lsize.height);
            }
        }
        return new Dimension(_psize);
    }

    protected void selectItem (int index, long when, int modifiers)
    {
        if (_selidx == index) {
            return;
        }

        _selidx = index;
        Object item = getSelectedItem();
        if (item instanceof BIcon) {
            setIcon((BIcon)item);
        } else {
            setText(item == null ? "" : item.toString());
        }
        emitEvent(new ActionEvent(this, when, modifiers, "selectionChanged"));
    }

    protected void clearCache ()
    {
        if (_menu != null) {
            _menu.removeAll();
            _menu = null;
        }
        _psize = null;
    }

    protected class ComboPopupMenu extends BPopupMenu
    {
        public ComboPopupMenu (int columns) {
            super(BComboBox.this.getWindow(), columns);
            for (int ii = 0; ii < _items.size(); ii++) {
                addMenuItem(_items.get(ii));
            }
        }

        protected void itemSelected (BMenuItem item, long when, int modifiers) {
            selectItem(_items.indexOf(item), when, modifiers);
            dismiss();
        }

        protected Dimension computePreferredSize (int whint, int hhint) {
            // prefer a size that is at least as wide as the combobox from which we will popup
            Dimension d = super.computePreferredSize(whint, hhint);
            d.width = Math.max(d.width, BComboBox.this.getWidth() - getInsets().getHorizontal());
            return d;
        }
    };

    protected class ComboMenuItem extends BMenuItem
    {
        public Object item;

        public ComboMenuItem (Object item)
        {
            super(null, null, "select");
            if (item instanceof BIcon) {
                setIcon((BIcon)item);
            } else {
                setText(item.toString());
            }
            this.item = item;
        }
    }

    /** The index of the currently selected item. */
    protected int _selidx = -1;

    /** The list of items in this combo box. */
    protected ArrayList<ComboMenuItem> _items = new ArrayList<ComboMenuItem>();

    /** A cached popup menu containing our items. */
    protected ComboPopupMenu _menu;

    /** Our cached preferred size. */
    protected Dimension _psize;

    /** Our preferred number of columns for the popup menu. */
    protected int _columns;
}
