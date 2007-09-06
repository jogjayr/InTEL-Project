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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

import com.jmex.bui.BConstants;
import com.jmex.bui.BImage;
import com.jmex.bui.Log;
import com.jmex.bui.util.Dimension;

/**
 * Formats text by using the AWT to render runs of text into a bitmap and then texturing a quad
 * with the result.  This text factory handles a simple styled text syntax:
 *
 * <pre>
 * &#064;=b(this text would be bold)
 * &#064;=i(this text would be italic)
 * &#064;=s(this text would be striked-through)
 * &#064;=u(this text would be underlined)
 * &#064;=bi(this text would be bold and italic)
 * &#064;=bi#FFCC99(this text would be bold, italic and pink)
 * </pre>
 */
public class AWTTextFactory extends BTextFactory
{
    /**
     * Creates an AWT text factory with the supplied font.
     */
    public AWTTextFactory (Font font, boolean antialias)
    {
        _antialias = antialias;
        _attrs.put(TextAttribute.FONT, font);

        // we need a graphics context to figure out how big our text is going to be, but we need an
        // image to get the graphics context, but we don't want to create our image until we know
        // how big our text needs to be. dooh!
        _stub = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);

        // compute the height of our font by creating a sample text and storing its height
        _height = createText("J", ColorRGBA.black).getSize().height;
    }

    // documentation inherited
    public int getHeight ()
    {
        return _height;
    }

    // documentation inherited
    public BText createText (String text, ColorRGBA color, int effect, int effectSize,
                             ColorRGBA effectColor, boolean useAdvance)
    {
        if (text.equals("")) {
            text = " ";
        }

        Graphics2D gfx = _stub.createGraphics();
        TextLayout layout;
        try {
            if (_antialias) {
                gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                     RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
            layout = new TextLayout(
                    parseStyledText(text, _attrs, null, effect != BConstants.PLAIN).getIterator(),
                    gfx.getFontRenderContext());
        } finally {
            gfx.dispose();
        }

        return createText(layout, color, effect, effectSize, effectColor,
                          text.length(), useAdvance);
    }

    // documentation inherited
    public BText[] wrapText (String text, ColorRGBA color, int effect, int effectSize,
                             ColorRGBA effectColor, int maxWidth)
    {
        // the empty string will break things; so use a single space instead
        if (text.length() == 0) {
            text = " ";
        }

        ArrayList<BText> texts = new ArrayList<BText>();
        Graphics2D gfx = _stub.createGraphics();
        TextLayout layout;
        try {
            if (_antialias) {
                gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                     RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }

            String[] bare = new String[1];
            AttributedString atext =
                parseStyledText(text, _attrs, bare, effect != BConstants.PLAIN);
            LineBreakMeasurer measurer = new LineBreakMeasurer(
                atext.getIterator(), gfx.getFontRenderContext());
            text = bare[0];

            int pos = 0;
            while (pos < text.length()) {
                // stop at the next newline or the end of the line if there are no newlines in the
                // text
                int nextret = text.indexOf('\n', pos);
                if (nextret == -1) {
                    nextret = text.length();
                }

                // measure out as much text as we can render in one line
                layout = measurer.nextLayout(maxWidth, nextret, false);
                int length = measurer.getPosition() - pos;

                // skip past any newline that we used to terminate our wrap
                pos = measurer.getPosition();
                if (pos < text.length() && text.charAt(pos) == '\n') {
                    pos++;
                }

                texts.add(createText(layout, color, effect, effectSize, effectColor, length, true));
            }

        } finally {
            gfx.dispose();
        }

        return texts.toArray(new BText[texts.size()]);
    }

    /** Helper function. */
    protected BText createText (final TextLayout layout, ColorRGBA color, final int effect,
                                final int effectSize, ColorRGBA effectColor, final int length,
                                boolean useAdvance)
    {
        // determine the size of our rendered text
        final Dimension size = new Dimension();
        Rectangle2D bounds = layout.getBounds();

        // MacOS font rendering is buggy, so we must compute the outline and use that for bounds
        // computation and rendering
        if (effect == OUTLINE || _isMacOS) {
            bounds = layout.getOutline(null).getBounds();
        }
        if (useAdvance) {
            size.width = (int)Math.round(Math.max(bounds.getX(), 0) + layout.getAdvance());
        } else {
            size.width = (int)Math.round(Math.max(bounds.getX(), 0) + bounds.getWidth());
        }
        size.height = (int)(layout.getLeading() + layout.getAscent() + layout.getDescent());

        // blank text results in a zero sized bounds, bump it up to 1x1 to avoid freakout by the
        // BufferedImage
        size.width = Math.max(size.width, 1);
        size.height = Math.max(size.height, 1);

        switch (effect) {
        case SHADOW:
        case OUTLINE:
            size.width += effectSize;
            size.height += effectSize;
            break;
        }

        // render the text into the image
        BufferedImage image = new BufferedImage(size.width, size.height,
                                                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D gfx = image.createGraphics();
        try {
            if (effect == OUTLINE) {
                if (_antialias) {
                    gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                         RenderingHints.VALUE_ANTIALIAS_ON);
                }
                gfx.translate(0, layout.getAscent());
                if (effectSize > 1) {
                    gfx.setColor(new Color(effectColor.r, effectColor.g, effectColor.b,
                                           effectColor.a));
                    Stroke oldstroke = gfx.getStroke();
                    gfx.setStroke(new BasicStroke((float)effectSize, BasicStroke.CAP_ROUND,
                                                  BasicStroke.JOIN_ROUND));
                    gfx.draw(layout.getOutline(null));
                    gfx.setStroke(oldstroke);
                }
                gfx.setColor(new Color(color.r, color.g, color.b, color.a));
                gfx.fill(layout.getOutline(null));
                if (effectSize == 1) {
                    gfx.setColor(new Color(effectColor.r, effectColor.g,
                                           effectColor.b, effectColor.a));
                    gfx.draw(layout.getOutline(null));
                }

            } else {
                // if we're antialiasing, we need to set a custom compositing rule to avoid
                // incorrectly blending with the blank background
                Composite ocomp = gfx.getComposite();
                if (_antialias) {
                    gfx.setComposite(AlphaComposite.SrcOut);
                    // on the MacOS we're not using the TextLayout to render, so we have to
                    // explicitly activate anti-aliasing
                    if (_isMacOS) {
                        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                             RenderingHints.VALUE_ANTIALIAS_ON);
                    }
                }

                int dx = 0;
                if (effect == SHADOW) {
                    gfx.setColor(new Color(effectColor.r, effectColor.g,
                                           effectColor.b, effectColor.a));
                    float tx = effectSize - 1;
                    float ty = layout.getAscent() + effectSize;
                    if (_isMacOS) {
                        gfx.translate(tx, ty);
                        gfx.fill(layout.getOutline(null));
                        gfx.translate(-tx, -ty);
                    } else {
                        layout.draw(gfx, tx, ty);
                    }
                    dx = 1;
                    gfx.setComposite(ocomp);
                }

                gfx.setColor(new Color(color.r, color.g, color.b, color.a));
                if (_isMacOS) {
                    gfx.translate(dx, layout.getAscent());
                    gfx.fill(layout.getOutline(null));
                } else {
                    layout.draw(gfx, dx, layout.getAscent());
                }
            }

        } finally {
            gfx.dispose();
        }

        // TODO: render into a properly sized image in the first place and create a JME Image
        // directly
        final BImage bimage = new BImage(image);

//         final ByteBuffer idata =
//             ByteBuffer.allocateDirect(4 * image.getWidth() * image.getHeight());
//         idata.order(ByteOrder.nativeOrder());
//         byte[] data = (byte[])image.getRaster().getDataElements(
//             0, 0, image.getWidth(), image.getHeight(), null);
//         idata.clear();
//         idata.put(data);
//         idata.flip();

        // wrap it all up in the right object
        return new BText() {
            public int getLength () {
                return length;
            }
            public Dimension getSize () {
                return size;
            }
            public int getHitPos (int x, int y) {
                TextHitInfo info = layout.hitTestChar(x, y);
                return info.getInsertionIndex();
            }
            public int getCursorPos (int index) {
                Shape[] carets = layout.getCaretShapes(index);
                Rectangle2D bounds = carets[0].getBounds2D();
                return (int)Math.round(bounds.getX() + bounds.getWidth()/2);
            }
            public void wasAdded () {
                bimage.reference();
            }
            public void wasRemoved () {
                bimage.release();
            }
            public void render (Renderer renderer, int x, int y, float alpha) {
                //bimage.render(renderer, x, y, alpha);
                bimage.render(renderer,
                        0, 0, bimage.getWidth(), bimage.getHeight(),
                        x, y, alpha);
            }
            public void render (Renderer renderer, int x, int y, int w, int h, float alpha) {
                bimage.render(renderer, x, y, w, h, alpha);
            }
            public void release () {
                bimage.release();
            }
        };
    }

    /**
     * Parses our simple styled text formatting codes and creates an attributed string to render
     * them.
     */
    protected AttributedString parseStyledText (
        String text, HashMap<TextAttribute, Font> attrs, String[] bare, boolean style)
    {
        // if there are no style commands in the text, skip the complexity
        if (!style || text.indexOf("@=") == -1) {
            if (bare != null) {
                bare[0] = text;
            }
            return new AttributedString(text, attrs);
        }

        // parse the style commands into an array of runs and extract the raw text along the way
        ArrayList<StyleRun> stack = new ArrayList<StyleRun>();
        ArrayList<StyleRun> runs = new ArrayList<StyleRun>();
        StringBuffer raw = new StringBuffer();
        int rawpos = 0;
        for (int ii = 0, ll = text.length(); ii < ll; ii++) {
            char c = text.charAt(ii);

            if (c == ')') { // end of run
                if (stack.size() == 0) {
                    // not a problem, this is just a bare parenthesis
                    raw.append(c);
                    rawpos++;
                } else {
                    StyleRun run = stack.remove(0);
                    run.end = rawpos;
                    runs.add(run);
                }
                continue;

            } else if (c == '@') { // start of run
                // if we don't have enough characters left in the string for a complete run, skip
                // it; we need at least 5: @=X()
                if (ii >= ll-5) {
                    raw.append(c);
                    rawpos++;
                    continue;
                }

                // anything other than @= is a non-start-sequence
                if ((c = text.charAt(++ii)) != '=') {
                    // @ ( and ) are escaped as @@ @( and @) so we skip the @
                    if (c != '@' && c != '(' && c != ')') {
                        raw.append('@');
                        rawpos++;
                    }
                    raw.append(c);
                    rawpos++;
                    continue;
                }

                // otherwise fall through and parse the run

            } else { // plain old character
                raw.append(c);
                rawpos++;
                continue;
            }

            // otherwise this is the start of a style run
            StyleRun run = new StyleRun();
            run.start = rawpos;
            stack.add(0, run);

            int parenidx = text.indexOf('(', ii);
            if (parenidx == -1) {
                Log.log.info("Invalid style specification, missing paren " +
                                "[text=" + text + ", pos=" + ii + "].");
                continue;
            }

            String styles = text.substring(ii+1, parenidx);
            ii = parenidx;

            run.styles = new char[styles.length()];
            for (int ss = 0, ssl = styles.length(); ss < ssl; ss++) {
                run.styles[ss] = Character.toLowerCase(styles.charAt(ss));
                if (run.styles[ss] == '#') {
                    if (ss > ssl-7) {
                        Log.log.warning("Invalid color definition [text=" + text +
                                        ", color=" + styles.substring(ss) + "].");
                        ss = ssl;
                    } else {
                        String hex = styles.substring(ss+1, ss+7);
                        ss += 6;
                        try {
                            run.color = new Color(Integer.parseInt(hex, 16));
                        } catch (Exception e) {
                            Log.log.warning("Invalid color definition [text=" + text +
                                            ", color=#" + hex +"].");
                        }
                    }
                }
            }
        }

        String rawtext = raw.toString();
        if (bare != null) {
            bare[0] = rawtext;
        }

        // now create an attributed string and add our styles
        AttributedString string = new AttributedString(rawtext, attrs);
        for (int ii = 0; ii < runs.size(); ii++) {
            StyleRun run = runs.get(ii);
            if (run.styles == null) {
                continue; // ignore runs we failed to parse
            }
            for (int ss = 0; ss < run.styles.length; ss++) {
                switch (run.styles[ss]) {
                case '#':
                    if (run.color != null) {
                        string.addAttribute(TextAttribute.FOREGROUND, run.color,
                                            run.start, run.end);
                    }
                    break;

                case 'i':
                    string.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE,
                                        run.start, run.end);
                    break;

                case 'b':
                    // setting TextAttribute.WEIGHT doesn't seem to work
                    string.addAttribute(
                        TextAttribute.FONT, attrs.get(TextAttribute.FONT).deriveFont(Font.BOLD),
                        run.start, run.end);
                    break;

                case 's':
                    string.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON,
                                        run.start, run.end);
                    break;

                case 'u':
                    string.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,
                                        run.start, run.end);
                    break;

                case 0: // ignore blank spots
                    break;

                default:
                    Log.log.info("Invalid style command [text=" + text +
                                    ", command=" + run.styles[ss] + ", run=" + run + "].");
                    break;
                }
            }
        }

        return string;
    }

    protected static class StyleRun
    {
        public char[] styles;
        public Color color;
        public int start;
        public int end;

        public String toString () {
            StringBuffer buf = new StringBuffer();
            for (int ii = 0; ii < styles.length; ii++) {
                if (styles[ii] > 0) {
                    buf.append(styles[ii]);
                }
            }
            if (color != null) {
                buf.append(":").append(Integer.toHexString(color.getRGB()));
            }
            buf.append(":").append(start).append("-").append(end);
            return buf.toString();
        }
    }

    protected boolean _antialias;
    protected HashMap<TextAttribute, Font> _attrs = new HashMap<TextAttribute, Font>();
    protected int _height;
    protected BufferedImage _stub;

    protected static boolean _isMacOS;
    static {
        try {
            String osname = System.getProperty("os.name");
            osname = (osname == null) ? "" : osname;
            _isMacOS = (osname.indexOf("Mac OS") != -1 || osname.indexOf("MacOS") != -1);
        } catch (Exception e) {
            // oh well
        }
    }

    protected static final char NONE = '!';
    protected static final char BOLD = 'b';
    protected static final char ITALIC = 'i';
    protected static final char UNDERLINE = 'u';
    protected static final char STRIKE = 's';
    protected static final char COLOR = '#';
}
