/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.components;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.background.ImageBackground;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.BIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Calvin Ashmore
 */
public class ChromaButton extends BButton {

    private final String imagePath;
    private ColorRGBA chroma;
    private float hueShift;
    private float saturationShift;
    private float brightnessShift;

    public ChromaButton(String imagePath, ColorRGBA chroma, BIcon icon, ActionListener listener, String action) {
        super(icon, listener, action);
        this.imagePath = imagePath;
        setChroma(chroma);
    }

    public ChromaButton(String imagePath, ColorRGBA chroma, BIcon icon, String action) {
        super(icon, action);
        this.imagePath = imagePath;
        setChroma(chroma);
    }

    public ChromaButton(String imagePath, ColorRGBA chroma, String text, ActionListener listener, String action) {
        super(text, listener, action);
        this.imagePath = imagePath;
        setChroma(chroma);
    }

    public ChromaButton(String imagePath, ColorRGBA chroma, String text, String action) {
        super(text, action);
        this.imagePath = imagePath;
        setChroma(chroma);
    }

    public ChromaButton(String imagePath, ColorRGBA chroma, String text) {
        super(text);
        this.imagePath = imagePath;
        setChroma(chroma);
    }

    public void setChroma(ColorRGBA chroma) {

        // only update if the color is non-null and actually different.
        if (chroma != null && !chroma.equals(this.chroma)) {

            this.chroma = chroma;
            calculateShift();
            if (isAdded()) {
                updateChroma();
            }
        }
    }

    private void updateChroma() {
        String imageSuffix[] = new String[]{
            "_up.png",
            "_hover.png",
            "_disabled.png",
            "_down.png"};

        try {
            // for each background type for the button assign the image.
            for (int i = 0; i < 4; i++) {

                String fullImageURL = imagePath + imageSuffix[i];

                // LOAD THE IMAGE
                URL resource = getClass().getClassLoader().getResource(fullImageURL);
                BufferedImage image = ImageIO.read(resource);

                // PERFORM OPERATION HERE
                performChromaShift(image, hueShift, brightnessShift, saturationShift);

                // ASSIGN TO THE BACKGROUND
                BImage bImg = new BImage(image);
                ImageBackground oldBackground = (ImageBackground) _backgrounds[i];
//                ImageBackground newBackground = new ImageBackground(ImageBackground.FRAME_XY, bImg, oldBackground.getFrame());
                ImageBackground newBackground;
                if (oldBackground == null) {
                    newBackground = new ImageBackground(ImageBackground.FRAME_XY, bImg);
                } else {
                    newBackground = new ImageBackground(ImageBackground.FRAME_XY, bImg, oldBackground.getFrame());
                }

                setBackground(i, newBackground);

                //styleSheet.get

            }
        } catch (IOException ex) {
            throw new RuntimeException("Can't seem to load the image: " + imagePath);
        }
    }

    @Override
    protected void wasAdded() {
        super.wasAdded();

        updateChroma();
    }

    private void performChromaShift(BufferedImage image, float hueShift, float brightnessShift, float saturationShift) {

        int w = image.getWidth();
        int h = image.getHeight();
        //int[] row = new int[w];
        //byte[] rowBytes = new byte[w * 4];
        int a, r, g, b, argb;
        float[] f = new float[3];
        for (int y = 0; y < h; y++) {
            //image.getRaster().getDataElements(0, y, w, 1, rowBytes);
            for (int x = 0; x < w; x++) {
//                argb = row[x];
                argb = image.getRGB(x, y);
                a = argb & 0xff000000;
                r = (argb >> 16) & 0xff;
                g = (argb >> 8) & 0xff;
                b = (argb >> 0) & 0xff;

                Color.RGBtoHSB(r, g, b, f);
                f[0] = (f[0] + hueShift) % 1;
                if (f[0] < 0) {
                    f[0] += 1;
                }
                f[1] = Math.min(Math.max(f[1] + saturationShift, 0), 1);
                f[2] = Math.min(Math.max(f[2] + brightnessShift, 0), 1);
                argb = Color.HSBtoRGB(f[0], f[1], f[2]);
                argb = (argb & 0xffffff) + a;

                image.setRGB(x, y, argb);
                //row[x] = argb;
//                rowBytes[4 * x + 3] = (byte) (argb & 0xff000000);
//                rowBytes[4 * x + 2] = (byte) ((argb >> 16) & 0xff);
//                rowBytes[4 * x + 1] = (byte) ((argb >> 8) & 0xff);
//                rowBytes[4 * x + 0] = (byte) ((argb >> 0) & 0xff);
            }
//            image.getRaster().setDataElements(0, y, w, 1, rowBytes);
        }

    }

    private void calculateShift() {

        //int argbBase = 0xff3b60a1;
        int rBase = 59;
        int gBase = 96;
        int bBase = 161;

        int rChroma = (int) (chroma.r * 255);
        int gChroma = (int) (chroma.g * 255);
        int bChroma = (int) (chroma.b * 255);

        float hsbBase[] = new float[3];
        Color.RGBtoHSB(rBase, gBase, bBase, hsbBase);

        float hsbChroma[] = new float[3];
        Color.RGBtoHSB(rChroma, gChroma, bChroma, hsbChroma);

        hueShift = hsbChroma[0] - hsbBase[0];
        saturationShift = hsbChroma[1] - hsbBase[1];
        brightnessShift = hsbChroma[2] - hsbBase[2];
    }
}
