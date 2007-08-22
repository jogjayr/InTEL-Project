//
// $Id$
//
// BUI - a user interface library for the JME 3D engine
// Copyright (C) 2006, Pär Winzell, All Rights Reserved
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

import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import com.jme.renderer.Renderer;
import com.jmex.bui.event.MouseWheelListener;
import com.jmex.bui.event.ChangeListener;
import com.jmex.bui.event.ChangeEvent;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.util.Dimension;
import com.jmex.bui.util.Insets;
import com.jmex.bui.util.Rectangle;
import com.jmex.bui.layout.GroupLayout;

/**
 * Provides a scrollable, lazily instantiated component view of values
 */
public abstract class BScrollingList<V, C extends BComponent> extends BContainer
{
    /**
     * Instantiates an empty {@link BScrollingList}.
     */
    public BScrollingList ()
    {
        this(null);
    }

    /**
     * Instantiates a {@link BScrollingList} with an initial value collection.
     */
    public BScrollingList (Collection<V> values)
    {
        super(new BorderLayout(0, 0));

        _values = new ArrayList<Entry<V, C>>();
        if (values != null) {
            for (V value : values) {
                _values.add(new Entry<V, C>(value));
            }
        }
        _lastBottom = 0;

        // we'll set up our model in layout()
        _model = new BoundedRangeModel(0, 0, 1, 1);

        // create our viewport and scrollbar
        add(_vport = new BViewport(), BorderLayout.CENTER);
        _model.addChangeListener(_vport);
        add(_vbar = new BScrollBar(BScrollBar.VERTICAL, _model),
            BorderLayout.EAST);
    }

    /**
     * Appends a value to our list, possibly scrolling our view to display it.
     */
    public void addValue (V value, boolean snapToBottom)
    {
        addValue(_values.size(), value, snapToBottom);
    }

    /**
     * Inserts a value into our list at the specified position.
     */
    public void addValue (int index, V value)
    {
        addValue(index, value, false);
    }

    /**
     * Clears all the current values and any related components.
     */
    public void removeValues ()
    {
        _values.clear();
        _model.setValue(0);
        _vport.removeAll();
        _vport.invalidate();
    }

    /**
     * Must be implemented by subclasses to instantiate the correct BComponent
     * subclass for a given list value.
     */
    protected abstract C createComponent (V value);

    /**
     * Adds a value to the list and snaps to the bottom of the list if desired.
     */
    protected void addValue (int index, V value, boolean snap)
    {
        _values.add(index, new Entry<V, C>(value));
        _vport.invalidateAndSnap();
    }

    /** Does all the heavy lifting for the {@link BScrollingList}. */
    protected class BViewport extends BContainer
        implements ChangeListener
    {
        public BViewport ()
        {
            super(GroupLayout.makeVert(
                      GroupLayout.NONE, GroupLayout.TOP,
                      GroupLayout.STRETCH));
        }

        /**
         * Returns a reference to the vertical scroll bar.
         */
        public BScrollBar getVerticalScrollBar ()
        {
            return _vbar;
        }

        /**
         * Recomputes our layout and snaps to the bottom if we were at the
         * bottom previously.
         */
        public void invalidateAndSnap ()
        {
            _snap = 
                _model.getValue() + _model.getExtent() >= _model.getMaximum();
            invalidate();
        }

        @Override // documentation inherited
        protected void wasAdded ()
        {
            super.wasAdded();
            addListener(_wheelListener = _model.createWheelListener());
        }

        @Override // from BComponent
        protected void wasRemoved ()
        {
            super.wasRemoved();
            if (_wheelListener != null) {
                removeListener(_wheelListener);
                _wheelListener = null;
            }
        }

        // from interface ChangeListener
        public void stateChanged (ChangeEvent event)
        {
            invalidate();
        }

        @Override // from BComponent
        public void invalidate ()
        {
            // if we're not attached, don't worry about it
            BWindow window;
            BRootNode root;
            if (!_valid || (window = getWindow()) == null ||
                (root = window.getRootNode()) == null) {
                return;
            }

            _valid = false;
            root.rootInvalidated(this);
        }

        @Override // from BComponent
        public void layout ()
        {
            Insets insets = getInsets();
            int twidth = getWidth() - insets.getHorizontal();
            int theight = getHeight() - insets.getVertical();
            int gap = ((GroupLayout)getLayoutManager()).getGap();

            // first make sure all of our entries have been measured and
            // compute our total height and extent
            int totheight = 0;
            for (Entry<V, C> entry : _values) {
                if (entry.height < 0) {
                    if (entry.component == null) {
                        entry.component = createComponent(entry.value);
                    }
                    boolean remove = false;
                    if (!entry.component.isAdded()) {
                        add(entry.component);
                        remove = true;
                    }
                    entry.height =
                        entry.component.getPreferredSize(twidth, 0).height;
                    if (remove) {
                        remove(entry.component);
                    }
                }
                totheight += entry.height;
            }
            if (_values.size() > 1) {
                totheight += (gap * _values.size()-1);
            }
            int extent = Math.min(theight, totheight);

            // if our most recent value was added with _snap then we scroll to
            // the bottom on this layout and clear our snappy flag
            int value = _snap ? (totheight-extent) : _model.getValue();
            _snap = false;

            // if our extent or total height have changed, update the model
            // (because we're currently invalid, the resulting call to
            // invalidate() will have no effect)
            if (extent != _model.getExtent() ||
                totheight != _model.getMaximum()) {
                _model.setRange(0, value, extent, totheight);
            }

            // now back up from the last component until we reach the first one
            // that's in view
            _offset = _model.getValue();
            int compIx = 0;
            for (int ii = 0; ii < _values.size(); ii++) {
                Entry<V, C> entry = _values.get(ii);
                if (_offset < entry.height) {
                    compIx = ii;
                    break;
                }
                _offset -= (entry.height + gap);

                // remove and clear out the components before our top component
                if (entry.component != null) {
                    if (entry.component.isAdded()) {
                        remove(entry.component);
                    }
                    entry.component = null;
                }
            }

            // compensate for the partially visible topmost component
            extent += _offset;

            // now add components until we use up our extent
            int topIx = compIx;
            while (compIx < _values.size() && extent > 0) {
                Entry<V, C> entry = _values.get(compIx);
                if (entry.component == null) {
                    entry.component = createComponent(entry.value);
                }
                if (!entry.component.isAdded()) {
                    add(compIx-topIx, entry.component);
                }
                extent -= (entry.height + gap);
                compIx++;
            }

            // lastly remove any components below the last visible component
            while (compIx < _values.size()) {
                Entry<V, C> entry = _values.get(compIx);
                if (entry.component != null) {
                    if (entry.component.isAdded()) {
                        remove(entry.component);
                    }
                    entry.component = null;
                }
                compIx++;
            }

            // now have the layout manager layout our added components
            super.layout();
        }

        @Override // from BComponent
        protected void renderComponent (Renderer renderer)
        {
            Insets insets = getInsets();
            GL11.glTranslatef(0, _offset, 0);
            boolean scissored = intersectScissorBox(_srect,
                getAbsoluteX() + insets.left,
                getAbsoluteY() + insets.bottom,
                _width - insets.getHorizontal(),
                _height - insets.getVertical());
            try {
                // render our children
                for (int ii = 0, ll = getComponentCount(); ii < ll; ii++) {
                    getComponent(ii).render(renderer);
                }
            } finally {
                restoreScissorState(scissored, _srect);
                GL11.glTranslatef(0, -_offset, 0);
            }
        }

        @Override // documentation inherited
        public BComponent getHitComponent (int mx, int my)
        {
            // if we're not within our bounds, we needn't check our target
            Insets insets = getInsets();
            if ((mx < _x + insets.left) || (my < _y + insets.bottom) ||
                (mx >= _x + _width - insets.right) ||
                (my >= _y + _height - insets.top)) {
                return null;
            }

            // translate the coordinate into our children's coordinates
            mx -= _x;
            my -= (_y + _offset);

            BComponent hit = null;
            for (int ii = 0, ll = getComponentCount(); ii < ll; ii++) {
                BComponent child = getComponent(ii);
                if ((hit = child.getHitComponent(mx, my)) != null) {
                    return hit;
                }
            }
            return this;
        }

        protected int _offset;
        protected boolean _snap;
        protected Rectangle _srect = new Rectangle();
    }

    /** Used to track the total height of our entries. */
    protected static class Entry<V, C extends BComponent>
    {
        public C component;
        public V value;
        public int height = -1;
        public Entry (V value) {
            this.value = value;
        }
    }

    protected MouseWheelListener _wheelListener;
    protected BoundedRangeModel _model;
    protected List<Entry<V, C>> _values;
    protected BViewport _vport;
    protected BScrollBar _vbar;
    protected int _lastBottom;

    protected static final int EXTENT = 2;
}
