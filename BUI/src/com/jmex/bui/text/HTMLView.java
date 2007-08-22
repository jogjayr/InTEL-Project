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

import java.io.StringReader;
import java.util.logging.Level;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import com.jme.renderer.Renderer;

import com.jmex.bui.BComponent;
import com.jmex.bui.BImage;
import com.jmex.bui.Log;
import com.jmex.bui.util.Dimension;
import com.jmex.bui.util.Insets;

/**
 * Displays HTML using Java's HTML rendering support to layout and render the
 * HTML. This is not a part of the text factory system as we're not going to
 * write our own HTML renderer to avoid dependence on Sun's JDK. If you don't
 * want to depend on that, don't use this class.
 *
 * <p>Note: width and height hints do not work. The Java HTML code doesn't seem
 * to handle being sized to a particular width or height and then determining
 * its preferred span along the other axis. So we always get the "natural"
 * preferred size of the HTML without any forced wrapping. Of course if the
 * component is forcibly made smaller, the HTML will be wrapped, but it may not
 * fit in the vertical or horizontal space made available. Caveat user.
 */
public class HTMLView extends BComponent
{
    /**
     * Creates a blank HTML view. The HTML contents can be set later with a
     * call to {@link #setContents}.
     */
    public HTMLView ()
    {
    }

    /**
     * Creates an HTML view with the specified contents.
     */
    public HTMLView (String stylesheet, String contents)
    {
        setStyleSheet(stylesheet);
        setContents(contents);
    }

    /**
     * Configures whether or not our text is antialiased. Antialiasing is on by
     * default.
     */
    public void setAntialiased (boolean antialias)
    {
        if (_antialias != antialias) {
            _antialias = antialias;
            forceRelayout();
        }
    }

    /**
     * Configures the stylesheet used to render HTML in this view.
     */
    public void setStyleSheet (String stylesheet)
    {
        StyleSheet ss = new StyleSheet();
        try {
            // parse the stylesheet definition
            ss.loadRules(new StringReader(stylesheet), null);
            setStyleSheet(ss);
        } catch (Throwable t) {
            Log.log.log(Level.WARNING, "Failed to parse stylesheet " +
                        "[sheet=" + stylesheet + "].", t);
        }
    }

    /**
     * Configures the stylesheet used to render HTML in this view.
     */
    public void setStyleSheet (StyleSheet stylesheet)
    {
        _style = stylesheet;
        forceRelayout();
    }

    /**
     * Returns the stylesheet in effect for this view.
     */
    public StyleSheet getStyleSheet ()
    {
        return _style;
    }

    /**
     * Returns the HTML editor kit used by this view.
     */
    public HTMLEditorKit getEditorKit ()
    {
        return _kit;
    }

    /**
     * Configures the contents of this HTML view. This should be well-formed
     * HTML which will be laid out according to the previously configured style
     * sheet (which must be set before the contents).
     */
    public void setContents (String contents)
    {
        // lazily create a blank stylesheet
        if (_style == null) {
            _style = new StyleSheet();
        }

        // then parse the HTML document
        HTMLDocument document = new HTMLDocument(_style);
        try {
            _kit.read(new StringReader(contents), document, 0);
            setContents(document);
        } catch (Throwable t) {
            Log.log.log(Level.WARNING, "Failed to parse HTML " +
                        "[contents=" + contents + "].", t);
        }
    }

    /**
     * Configures the contents of this HTML view.
     */
    public void setContents (HTMLDocument document)
    {
        _view = new BridgeView(
            _kit.getViewFactory().create(document.getDefaultRootElement()));
        forceRelayout();
    }

    // documentation inherited
    protected void wasRemoved ()
    {
        super.wasRemoved();
        release();
    }

    // documentation inherited
    protected void layout ()
    {
        super.layout();

        // if we have no view yet, stop now
        if (_view == null) {
            return;
        }

        // avoid rerendering our HTML unless something changed
        int vwidth = getWidth() - getInsets().getHorizontal();
        int vheight = getHeight() - getInsets().getVertical();
        if (_rendered != null && _rsize != null &&
            _rsize.width == vwidth && _rsize.height == vheight) {
            return;
        }

        // release our old texture image
        release();

        BufferedImage image = new BufferedImage(
            vwidth, vheight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D gfx = image.createGraphics();
        _rsize = new Rectangle(0, 0, vwidth, vheight);
        try {
            gfx.setClip(_rsize);
            if (_antialias) {
                gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
            }
            _view.paint(gfx, _rsize);
        } finally {
            gfx.dispose();
        }

        // TODO: render into a properly sized image in the first place and
        // create a JME Image directly
        _rendered = new BImage(image);
        _rendered.reference();
    }

    // documentation inherited
    protected void renderComponent (Renderer renderer)
    {
        super.renderComponent(renderer);

        if (_rendered != null) {
            Insets insets = getInsets();
            _rendered.render(renderer, insets.left, insets.bottom, _alpha);
        }
    }

    // documentation inherited
    protected Dimension computePreferredSize (int whint, int hhint)
    {
        // this might in theory work except that for whatever reason our
        // BoxView claims a size of zero even after we lay it out with
        // information on the size of one axis; grumble grumble
//         int px = 0, py = 0;
//         if (whint > 0) {
//             _view.setSize(whint, 0);
//             px = (int)Math.ceil(_view.getPreferredSpan(View.X_AXIS));
//             if (_view.getTarget() instanceof BoxView) {
//                 // px = ((BoxView)_view.getTarget()).getWidth();
//                 py = ((BoxView)_view.getTarget()).getHeight();
//             } else {
//                 py = (int)Math.ceil(_view.getPreferredSpan(View.Y_AXIS));
//             }
//         } else if (hhint > 0) {
//             _view.setSize(0, hhint);
//             py = (int)Math.ceil(_view.getPreferredSpan(View.Y_AXIS));
//             if (_view.getTarget() instanceof BoxView) {
//                 // py = ((BoxView)_view.getTarget()).getHeight();
//                 px = ((BoxView)_view.getTarget()).getWidth();
//             } else {
//                 px = (int)Math.ceil(_view.getPreferredSpan(View.X_AXIS));
//             }
//         } else {
//             _view.setSize(0, 0);
//             if (_view.getTarget() instanceof BoxView) {
//                 px = ((BoxView)_view.getTarget()).getWidth();
//                 py = ((BoxView)_view.getTarget()).getHeight();
//             } else {
//                 px = (int)Math.ceil(_view.getPreferredSpan(View.X_AXIS));
//                 py = (int)Math.ceil(_view.getPreferredSpan(View.Y_AXIS));
//             }
//         }

        if (_view != null) {
            _view.setSize((whint > 0) ? whint : 0, (hhint > 0) ? hhint : 0);
            int px = (int)Math.ceil(_view.getPreferredSpan(View.X_AXIS));
            int py = (int)Math.ceil(_view.getPreferredSpan(View.Y_AXIS));
            return new Dimension(Math.max(1, px), Math.max(1, py));
        } else {
            return new Dimension(Math.max(1, whint), Math.max(1, hhint));
        }
    }

    protected void forceRelayout ()
    {
        _rsize = null;
        invalidate();
    }

    protected void release ()
    {
        if (_rendered != null) {
            _rendered.release();
            _rendered = null;
        }
    }

    protected class BridgeView extends View
    {
        public BridgeView (View target) {
            super(null);
            _target = target;
            _target.setParent(this);
        }

        public View getTarget () {
            return _target;
        }

        public AttributeSet getAttributes () {
            return null;
        }

        public float getPreferredSpan (int axis) {
            return _target.getPreferredSpan(axis);
        }

        public float getMinimumSpan (int axis) {
            return _target.getMinimumSpan(axis);
        }

        public float getMaximumSpan (int axis) {
            return Integer.MAX_VALUE;
        }

        public void preferenceChanged (View child, boolean width, boolean height) {
            forceRelayout();
        }

        public float getAlignment (int axis) {
            return _target.getAlignment(axis);
        }

        public void paint (Graphics g, Shape allocation) {
            Rectangle alloc = allocation.getBounds();
            _target.setSize(alloc.width, alloc.height);
            _target.paint(g, allocation);
        }

        public void setParent (View parent) {
            throw new Error("Whatchu talkin' 'bout Willis?");
        }

        public int getViewCount () {
            return 1;
        }

        public View getView (int n) {
            return _target;
        }

        public Shape modelToView (int pos, Shape a, Position.Bias b)
            throws BadLocationException {
            return _target.modelToView(pos, a, b);
        }

        public Shape modelToView (int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a)
            throws BadLocationException {
	        return _target.modelToView(p0, b0, p1, b1, a);
        }

        public int viewToModel (float x, float y, Shape a, Position.Bias[] bias) {
	        return _target.viewToModel(x, y, a, bias);
        }

        public Document getDocument () {
            return _target.getDocument();
        }

        public int getStartOffset () {
            return _target.getStartOffset();
        }

        public int getEndOffset () {
            return _target.getEndOffset();
        }

        public Element getElement () {
            return _target.getElement();
        }

        public void setSize (float width, float height) {
            _target.setSize(width, height);
        }

        public Container getContainer () {
            return null;
        }

        public ViewFactory getViewFactory () {
            return _kit.getViewFactory();
        }

        protected View _target;
    }

    protected StyleSheet _style;
    protected Rectangle _rsize;
    protected BridgeView _view;
    protected BImage _rendered;
    protected boolean _antialias = true;
    protected HTMLEditorKit _kit = new HTMLEditorKit();
}
