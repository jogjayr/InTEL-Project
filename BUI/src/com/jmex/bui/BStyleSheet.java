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

package com.jmex.bui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

import com.jme.renderer.ColorRGBA;

import com.jmex.bui.background.BBackground;
import com.jmex.bui.background.BlankBackground;
import com.jmex.bui.background.ImageBackground;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.border.BBorder;
import com.jmex.bui.border.EmptyBorder;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.BlankIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.text.AWTTextFactory;
import com.jmex.bui.text.BKeyMap;
import com.jmex.bui.text.BTextFactory;
import com.jmex.bui.text.DefaultKeyMap;
import com.jmex.bui.util.Dimension;
import com.jmex.bui.util.Insets;

/**
 * Defines a stylesheet which is used to configure the style (font family, font size, foreground
 * and background color, etc.) of components in the BUI library. The BUI stylesheets syntax is a
 * subset of the Cascading Style Sheet sytnax and follows its semantic conventions as well where
 * possible.
 *
 * <p> A basic stylesheet enumerating most usable values is as follows:
 * <pre>
 * style_class {
 *   // foreground and background properties
 *   color: 0 0 0;
 *   background: solid #00000088; // note the 50% alpha
 *   background: image monkey.png XX; // XX = centerx|centery|centerxy|
 *                                    //      scalex|scaley|scalexy|
 *                                    //      tilex|tiley|tilexy|
 *                                    //      framex|framey|framexy
 *   background: image monkey.png framexy top right bottom left;
 *   cursor: name;
 *
 *   // text properties
 *   font: Helvetica XX 12; // XX = normal|bold|italic|bolditalic
 *   text-align: XX; // XX = left|center|right
 *   vertical-align: XX; // XX = top|center|bottom
 *   text-effect: XX; // XX = none|outline|shadow
 *
 *   // box properties
 *   padding: top; // right=top, bottom=top, left=top
 *   padding: top, right; // bottom=top, left=right
 *   padding: top, right, bottom, left;
 *   border: 1 solid #FFCC99;
 *   border: 1 blank;
 *   size: 250 100; // overrrides component preferred size
 *
 *   // explicit inheritance
 *   parent: other_class; // other_class must be defined *before* this one
 * }
 * </pre>
 *
 * Each component is identified by its default stylesheet class, which are derived from the
 * component's Java class name: <code>window, label, textfield, component, popupmenu, etc.</code>
 * The component's stylesheet class can be overridden with a call to {@link
 * BComponent#setStyleClass}.
 *
 * <p> A component's style is resolved in the following manner:
 * <ul>
 * <li> First by looking up the property using the component's stylesheet class.

 * <li> <em>For certain properties</em>, the interface hierarchy is then climbed and each parents'
 * stylesheet class is checked for the property in question. The properties for which that applies
 * are: <code>color, font, text-align, vertical-align</code>.
 *
 * <li> Lastly the <code>root</code> stylesheet class is checked (for all properties, not just
 * those for which we climb the interface hierarchy).
 * </ul>
 *
 * <p> This resolution process is followed at the time the component is added to the interface
 * hierarchy and the result is used to configure the component. We tradeoff the relative expense of
 * doing the lookup every time the component is rendered (every frame) with the memory expense of
 * storing the style of every component in memory.
 */
public class BStyleSheet
{
    /** An interface used by the stylesheet to obtain font and image resources. */
    public interface ResourceProvider
    {
        /**
         * Creates a factory that will render text using the specified font.
         */
        public BTextFactory createTextFactory (
            String family, String style, int size);

        /**
         * Loads the image with the specified path.
         */
        public BImage loadImage (String path) throws IOException;

        /**
         * Loads the cursor with the specified name.
         */
        public BCursor loadCursor (String name) throws IOException;
    }

    /** A default implementation of the stylesheet resource provider. */
    public static class DefaultResourceProvider implements ResourceProvider
    {
        public BTextFactory createTextFactory (
            String family, String style, int size) {
            int nstyle = Font.PLAIN;
            if (style.equals(BOLD)) {
                nstyle = Font.BOLD;
            } else if (style.equals(ITALIC)) {
                nstyle = Font.ITALIC;
            } else if (style.equals(BOLD_ITALIC)) {
                nstyle = Font.ITALIC|Font.BOLD;
            }
            return new AWTTextFactory(new Font(family, nstyle, size), true);
        }

        public BImage loadImage (String path) throws IOException {
            // normalize the image path
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            // first check the cache
            WeakReference<BImage> iref = _cache.get(path);
            BImage image;
            if (iref != null && (image = iref.get()) != null) {
                return image;
            }

            // create and cache a new BUI image with the appropriate data
            URL url = getClass().getResource(path);
            if (url == null) {
                throw new IOException("Can't locate image '" + path + "'.");
            }
            image = new BImage(url);
            _cache.put(path, new WeakReference<BImage>(image));
            return image;
        }

        public BCursor loadCursor (String path) throws IOException {
            // we'll just assume the name is an image path
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            // first check the cache
            WeakReference<BCursor> cref = _ccache.get(path);
            BCursor cursor;
            if (cref != null && (cursor = cref.get()) != null) {
                return cursor;
            }

            // create and cache a new cursor with the appropriate data
            URL url = getClass().getResource(path);
            if (url == null) {
                throw new IOException("Can't locate cursor image '" + path + "'.");
            }
            BufferedImage image = ImageIO.read(url);
            BufferedImage cimage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = cimage.createGraphics();
            try {
                g.drawImage(image, null, 0, 0);
                cursor = new BCursor(cimage, 0, 0);
            } finally {
                g.dispose();
            }
            _ccache.put(path, new WeakReference<BCursor>(cursor));
            return cursor;
        }

        /** A cache of {@link BImage} instances. */
        protected HashMap<String,WeakReference<BImage>> _cache =
            new HashMap<String,WeakReference<BImage>>();

        /** A cache of {@link BCursor} instances. */
        protected HashMap<String, WeakReference<BCursor>> _ccache =
            new HashMap<String, WeakReference<BCursor>>();
    }

    /** A font style constant. */
    public static final String PLAIN = "plain";

    /** A font style constant. */
    public static final String BOLD = "bold";

    /** A font style constant. */
    public static final String ITALIC = "italic";

    /** A font style constant. */
    public static final String BOLD_ITALIC = "bolditalic";

    public static void main (String[] args)
    {
        // load up the default BUI stylesheet
        BStyleSheet style = null;
        try {
            style = new BStyleSheet(new InputStreamReader(BStyleSheet.class.getClassLoader().
                                                          getResourceAsStream("rsrc/style.bss")),
                                    new DefaultResourceProvider());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    /**
     * Creates a stylesheet from the specified textual source.
     */
    public BStyleSheet (Reader reader, ResourceProvider rsrcprov)
        throws IOException
    {
        _rsrcprov = rsrcprov;
        StreamTokenizer tok = new StreamTokenizer(new BufferedReader(reader));
        tok.lowerCaseMode(true);
        tok.slashSlashComments(true);
        tok.slashStarComments(true);
        tok.eolIsSignificant(false);
        tok.wordChars('#', '#');
        tok.wordChars('_', '_');
        parse(tok);
    }

    public ColorRGBA getColor (BComponent component, String pseudoClass)
    {
        return (ColorRGBA)findProperty(component, pseudoClass, "color", true);
    }

    public BBackground getBackground (BComponent component, String pseudoClass)
    {
        return (BBackground)findProperty(component, pseudoClass, "background", false);
    }

    public BIcon getIcon (BComponent component, String pseudoClass)
    {
        return (BIcon)findProperty(component, pseudoClass, "icon", false);
    }

    public BCursor getCursor (BComponent component, String pseudoClass)
    {
        return (BCursor)findProperty(component, pseudoClass, "cursor", true);
    }

    public BTextFactory getTextFactory (
        BComponent component, String pseudoClass)
    {
        return (BTextFactory)findProperty(component, pseudoClass, "font", true);
    }

    public int getTextAlignment (BComponent component, String pseudoClass)
    {
        Integer value = (Integer)findProperty(component, pseudoClass, "text-align", true);
        return (value == null) ? BConstants.LEFT : value.intValue();
    }

    public int getVerticalAlignment (BComponent component, String pseudoClass)
    {
        Integer value = (Integer)findProperty(component, pseudoClass, "vertical-align", true);
        return (value == null) ? BConstants.CENTER : value.intValue();
    }

    public int getTextEffect (BComponent component, String pseudoClass)
    {
        Integer value = (Integer)findProperty(component, pseudoClass, "text-effect", true);
        return (value == null) ? BConstants.NORMAL : value.intValue();
    }

    public int getEffectSize (BComponent component, String pseudoClass)
    {
        Integer value = (Integer)findProperty(component, pseudoClass, "effect-size", true);
        return (value == null) ? BConstants.DEFAULT_SIZE : value.intValue();
    }

    public ColorRGBA getEffectColor (BComponent component, String pseudoClass)
    {
        return (ColorRGBA)findProperty(component, pseudoClass, "effect-color", true);
    }

    public Insets getInsets (BComponent component, String pseudoClass)
    {
        Insets insets = (Insets)findProperty(component, pseudoClass, "padding", false);
        return (insets == null) ? Insets.ZERO_INSETS : insets;
    }

    public BBorder getBorder (BComponent component, String pseudoClass)
    {
        return (BBorder)findProperty(component, pseudoClass, "border", false);
    }

    public Dimension getSize (BComponent component, String pseudoClass)
    {
        return (Dimension)findProperty(component, pseudoClass, "size", false);
    }

    public BKeyMap getKeyMap (BComponent component, String pseudoClass)
    {
        return new DefaultKeyMap();
    }

    protected Object findProperty (
        BComponent component, String pseudoClass, String property, boolean climb)
    {
        Object value;

        // first try this component's configured style class
        String styleClass = component.getStyleClass();
        String fqClass = makeFQClass(styleClass, pseudoClass);
        if ((value = getProperty(fqClass, property)) != null) {
            return value;
        }

        // next fall back to its un-qualified configured style
        if (pseudoClass != null) {
            if ((value = getProperty(styleClass, property)) != null) {
                return value;
            }
        }

        // if applicable climb up the hierarch to its parent and try there
        if (climb) {
            BComponent parent = component.getParent();
            if (parent != null) {
                return findProperty(parent, pseudoClass, property, climb);
            }
        }

        // finally check the "root" class
        fqClass = makeFQClass("root", pseudoClass);
        if ((value = getProperty(fqClass, property)) != null) {
            return value;
        }
        if (pseudoClass != null) {
            return getProperty("root", property);
        }

        return null;
    }

    protected Object getProperty (String fqClass, String property)
    {
        Rule rule = _rules.get(fqClass);
        if (rule == null) {
            return null;
        }

        // we need to lazily resolve certain properties at this time
        Object prop = rule.get(_rules, property);
        if (prop instanceof Property) {
            prop = ((Property)prop).resolve(_rsrcprov);
            rule.properties.put(property, prop);
        }
        return prop;
    }

    protected void parse (StreamTokenizer tok)
        throws IOException
    {
        while (tok.nextToken() != StreamTokenizer.TT_EOF) {
            Rule rule = startRule(tok);
            while (parseProperty(tok, rule)) {
            }
            _rules.put(makeFQClass(rule.styleClass, rule.pseudoClass), rule);
        }
    }

    protected Rule startRule (StreamTokenizer tok)
        throws IOException
    {
        if (tok.ttype != StreamTokenizer.TT_WORD) {
            fail(tok, "style-class");
        }

        Rule rule = new Rule();
        rule.styleClass = tok.sval;

        switch (tok.nextToken()) {
        case '{':
            return rule;

        case ':':
            if (tok.nextToken() != StreamTokenizer.TT_WORD) {
                fail(tok, "pseudo-class");
            }
            rule.pseudoClass = tok.sval;
            if (tok.nextToken() != '{') {
                fail(tok, "{");
            }
            return rule;

        default:
            fail(tok, "{ or :");
            return null; // not reachable
        }
    }

    protected boolean parseProperty (StreamTokenizer tok, Rule rule)
        throws IOException
    {
        if (tok.nextToken() == '}') {
            return false;
        } else if (tok.ttype != StreamTokenizer.TT_WORD) {
            fail(tok, "property-name");
        }

        int sline = tok.lineno();
        String name = tok.sval;

        if (tok.nextToken() != ':') {
            fail(tok, ":");
        }

        ArrayList<Comparable> args = new ArrayList<Comparable>();
        while (tok.nextToken() != ';' && tok.ttype != '}') {
            switch (tok.ttype) {
            case '\'':
            case '"':
            case StreamTokenizer.TT_WORD:
                args.add(tok.sval);
                break;
            case StreamTokenizer.TT_NUMBER:
                args.add(new Double(tok.nval));
                break;
            default:
                System.err.println(
                    "Unexpected token: '" + (char)tok.ttype + "'. Line " + tok.lineno() + ".");
                break;
            }
        }

        try {
            rule.properties.put(name, createProperty(name, args));
//             System.out.println("  " + name + " -> " + rule.get(name));
        } catch (Exception e) {
            System.err.println(
                "Failure parsing property '" + name + "' line " + sline + ": " + e.getMessage());
            if (!(e instanceof IllegalArgumentException)) {
                e.printStackTrace(System.err);
            }
        }
        return true;
    }

    protected Object createProperty (String name, ArrayList args)
    {
        if (name.equals("color") || name.equals("effect-color")) {
            return parseColor((String)args.get(0));

        } else if (name.equals("background")) {
            BackgroundProperty bprop = new BackgroundProperty();
            bprop.type = (String)args.get(0);
            if (bprop.type.equals("solid")) {
                bprop.color = parseColor((String)args.get(1));

            } else if (bprop.type.equals("image")) {
                bprop.ipath = (String)args.get(1);
                if (args.size() > 2) {
                    String scale = (String)args.get(2);
                    Integer scval = _ibconsts.get(scale);
                    if (scval == null) {
                        throw new IllegalArgumentException(
                            "Unknown background scaling type: '" + scale + "'");
                    }
                    bprop.scale = scval.intValue();
                    if (bprop.scale == ImageBackground.FRAME_XY && args.size() > 3) {
                        bprop.frame = new Insets();
                        bprop.frame.top = parseInt(args.get(3));
                        bprop.frame.right = (args.size() > 4) ?
                            parseInt(args.get(4)) : bprop.frame.top;
                        bprop.frame.bottom = (args.size() > 5) ?
                            parseInt(args.get(5)) : bprop.frame.top;
                        bprop.frame.left = (args.size() > 6) ?
                            parseInt(args.get(6)) : bprop.frame.right;
                    }
                }

            } else if (bprop.type.equals("blank")) {
                // nothing to do

            } else {
                throw new IllegalArgumentException(
                    "Unknown background type: '" + bprop.type + "'");
            }
            return bprop;

        } else if (name.equals("icon")) {
            IconProperty iprop = new IconProperty();
            iprop.type = (String)args.get(0);
            if (iprop.type.equals("image")) {
                iprop.ipath = (String)args.get(1);

            } else if (iprop.type.equals("blank")) {
                iprop.width = parseInt(args.get(1));
                iprop.height = parseInt(args.get(2));

            } else {
                throw new IllegalArgumentException("Unknown icon type: '" + iprop.type + "'");
            }
            return iprop;

        } else if (name.equals("cursor")) {
            CursorProperty cprop = new CursorProperty();
            cprop.name = (String)args.get(0);
            return cprop;

        } else if (name.equals("font")) {
            try {
                FontProperty fprop = new FontProperty();
                fprop.family = (String)args.get(0);
                fprop.style = (String)args.get(1);
                if (!fprop.style.equals(PLAIN) && !fprop.style.equals(BOLD) &&
                    !fprop.style.equals(ITALIC) && !fprop.style.equals(BOLD_ITALIC)) {
                    throw new IllegalArgumentException("Unknown font style: '" + fprop.style + "'");
                }
                fprop.size = parseInt(args.get(2));
                return fprop;

            } catch (Exception e) {
                e.printStackTrace(System.err);
                throw new IllegalArgumentException(
                    "Fonts must be specified as: " +
                    "\"Font name\" plain|bold|italic|bolditalic point-size");
            }

        } else if (name.equals("text-align")) {
            String type = (String)args.get(0);
            Object value = _taconsts.get(type);
            if (value == null) {
                throw new IllegalArgumentException("Unknown text-align type '" + type + "'");
            }
            return value;

        } else if (name.equals("vertical-align")) {
            String type = (String)args.get(0);
            Object value = _vaconsts.get(type);
            if (value == null) {
                throw new IllegalArgumentException("Unknown vertical-align type '" + type + "'");
            }
            return value;

        } else if (name.equals("text-effect")) {
            String type = (String)args.get(0);
            Object value = _teconsts.get(type);
            if (value == null) {
                throw new IllegalArgumentException("Unknown text-effect type '" + type + "'");
            }
            return value;

        } else if (name.equals("effect-size")) {
            Integer value = new Integer(parseInt(args.get(0)));
            return value;

        } else if (name.equals("padding")) {
            Insets insets = new Insets();
            insets.top = parseInt(args.get(0));
            insets.right = (args.size() > 1) ? parseInt(args.get(1)) : insets.top;
            insets.bottom = (args.size() > 2) ? parseInt(args.get(2)) : insets.top;
            insets.left = (args.size() > 3) ? parseInt(args.get(3)) : insets.right;
            return insets;

        } else if (name.equals("border")) {
            int thickness = parseInt(args.get(0));
            String type = (String)args.get(1);
            if (type.equals("blank")) {
                return new EmptyBorder(thickness, thickness, thickness, thickness);

            } else if (type.equals("solid")) {
                return new LineBorder(parseColor((String)args.get(2)), thickness);

            } else {
                throw new IllegalArgumentException("Unknown border type '" + type + "'");
            }

        } else if (name.equals("size")) {
            Dimension size = new Dimension();
            size.width = parseInt(args.get(0));
            size.height = parseInt(args.get(1));
            return size;

        } else if (name.equals("parent")) {
            Rule parent = _rules.get(args.get(0));
            if (parent == null) {
                throw new IllegalArgumentException("Unknown parent class '" + args.get(0) + "'");
            }
            return parent;

        } else {
            throw new IllegalArgumentException("Unknown property '" + name + "'");
        }
    }

    protected void fail (StreamTokenizer tok, String expected)
        throws IOException
    {
        String err = "Parse failure line: " + tok.lineno() +
            " expected: '" + expected + "' found: '";
        switch (tok.ttype) {
        case StreamTokenizer.TT_WORD: err += tok.sval; break;
        case StreamTokenizer.TT_NUMBER: err += tok.nval; break;
        case StreamTokenizer.TT_EOF: err += "EOF"; break;
        default: err += (char)tok.ttype;
        }
        throw new IOException(err + "'");
    }

    protected ColorRGBA parseColor (String hex)
    {
        if (!hex.startsWith("#") || (hex.length() != 7 && hex.length() != 9)) {
            String errmsg = "Color must be #RRGGBB or #RRGGBBAA: " + hex;
            throw new IllegalArgumentException(errmsg);
        }
        float r = Integer.parseInt(hex.substring(1, 3), 16) / 255f;
        float g = Integer.parseInt(hex.substring(3, 5), 16) / 255f;
        float b = Integer.parseInt(hex.substring(5, 7), 16) / 255f;
        float a = 1f;
        if (hex.length() == 9) {
            a = Integer.parseInt(hex.substring(7, 9), 16) / 255f;
        }
        return new ColorRGBA(r, g, b, a);
    }

    protected int parseInt (Object arg)
    {
        return (int)((Double)arg).doubleValue();
    }

    protected static String makeFQClass (String styleClass, String pseudoClass)
    {
        return (pseudoClass == null) ? styleClass : (styleClass + ":" + pseudoClass);
    }

    protected static class Rule
    {
        public String styleClass;

        public String pseudoClass;

        public HashMap<String, Object> properties = new HashMap<String, Object>();

        public Object get (HashMap rules, String key)
        {
            Object value = properties.get(key);
            if (value != null) {
                return value;
            }
            Rule prule = (Rule)properties.get("parent");
            return (prule != null) ? prule.get(rules, key) : null;
        }

        @Override // from Object
        public String toString () {
            return "[class=" + styleClass + ", pclass=" + pseudoClass + "]";
        }
    }

    protected static abstract class Property
    {
        public abstract Object resolve (ResourceProvider rsrcprov);
    }

    protected static class FontProperty extends Property
    {
        String family;
        String style;
        int size;

        @Override // from Property
        public Object resolve (ResourceProvider rsrcprov) {
//             System.out.println("Resolving text factory [family=" + family +
//                                ", style=" + style + ", size=" + size + "].");
            return rsrcprov.createTextFactory(family, style, size);
        }
    }

    protected static class BackgroundProperty extends Property
    {
        String type;
        ColorRGBA color;
        String ipath;
        int scale = ImageBackground.SCALE_XY;
        Insets frame;

        @Override // from Property
        public Object resolve (ResourceProvider rsrcprov) {
            if (type.equals("solid")) {
                return new TintedBackground(color);

            } else if (type.equals("image")) {
                BImage image;
                try {
                    image = rsrcprov.loadImage(ipath);
                } catch (IOException ioe) {
                    System.err.println("Failed to load background image '" + ipath + "': " + ioe);
                    return new BlankBackground();
                }
                return new ImageBackground(scale, image, frame);

            } else {
                return new BlankBackground();
            }
        }
    }

    protected static class IconProperty extends Property
    {
        public String type;
        public String ipath;
        public int width, height;

        @Override // from Property
        public Object resolve (ResourceProvider rsrcprov) {
            if (type.equals("image")) {
                BImage image;
                try {
                    image = rsrcprov.loadImage(ipath);
                } catch (IOException ioe) {
                    System.err.println("Failed to load icon image '" + ipath + "': " + ioe);
                    return new BlankIcon(10, 10);
                }
                return new ImageIcon(image);

            } else if (type.equals("blank")) {
                return new BlankIcon(width, height);

            } else {
                return new BlankIcon(10, 10);
            }
        }
    }

    protected static class CursorProperty extends Property
    {
        public String name;

        @Override // from Property
        public Object resolve (ResourceProvider rsrcprov) {
            try {
                return rsrcprov.loadCursor(name);
            } catch (IOException ioe) {
                System.err.println("Failed to load cursor '" + name + "': " + ioe);
                return null;
            }
        }
    }

    protected ResourceProvider _rsrcprov;
    protected HashMap<String, Rule> _rules = new HashMap<String, Rule>();

    protected static HashMap<String, Integer> _taconsts = new HashMap<String, Integer>();
    protected static HashMap<String, Integer> _vaconsts = new HashMap<String, Integer>();
    protected static HashMap<String, Integer> _teconsts = new HashMap<String, Integer>();
    protected static HashMap<String, Integer> _ibconsts = new HashMap<String, Integer>();

    static {
        // alignment constants
        _taconsts.put("left", new Integer(BConstants.LEFT));
        _taconsts.put("right", new Integer(BConstants.RIGHT));
        _taconsts.put("center", new Integer(BConstants.CENTER));

        _vaconsts.put("center", new Integer(BConstants.CENTER));
        _vaconsts.put("top", new Integer(BConstants.TOP));
        _vaconsts.put("bottom", new Integer(BConstants.BOTTOM));

        // effect constants
        _teconsts.put("none", new Integer(BConstants.NORMAL));
        _teconsts.put("shadow", new Integer(BConstants.SHADOW));
        _teconsts.put("outline", new Integer(BConstants.OUTLINE));
        _teconsts.put("plain", new Integer(BConstants.PLAIN));

        // background image constants
        _ibconsts.put("centerxy", new Integer(ImageBackground.CENTER_XY));
        _ibconsts.put("centerx", new Integer(ImageBackground.CENTER_X));
        _ibconsts.put("centery", new Integer(ImageBackground.CENTER_Y));
        _ibconsts.put("scalexy", new Integer(ImageBackground.SCALE_XY));
        _ibconsts.put("scalex", new Integer(ImageBackground.SCALE_X));
        _ibconsts.put("scaley", new Integer(ImageBackground.SCALE_Y));
        _ibconsts.put("tilexy", new Integer(ImageBackground.TILE_XY));
        _ibconsts.put("tilex", new Integer(ImageBackground.TILE_X));
        _ibconsts.put("tiley", new Integer(ImageBackground.TILE_Y));
        _ibconsts.put("framexy", new Integer(ImageBackground.FRAME_XY));
        _ibconsts.put("framex", new Integer(ImageBackground.FRAME_X));
        _ibconsts.put("framey", new Integer(ImageBackground.FRAME_Y));
    }
}
