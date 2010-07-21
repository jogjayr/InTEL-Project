/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.renderer.ColorRGBA;

/**
 * Utility class for anything that makes sense to put in here. Currently there
 * is a non-implemented method for unshading the CentroidPartObjects that have
 * been solved. This is unimplemented because I have not gotten to the polishing
 * of the UI yet but would eventually unshade parts of the centroid body as
 * their individual centroids are solved.
 * @author Jimmy Truesdell
 */
class CentroidUtil {

    private static float hue = -0.15f;
    private float personalHue = 0.0f;
    private final float sat = 0.86f;
    private final float light = 0.70f;
    private final float alpha = 0.9f;

    private float temp2 = (light + sat) - (light * sat);
    private float temp1 = 2.0f * light - temp2;

    private float red, green, blue;

    public CentroidUtil() {
    }

    public ColorRGBA generatePastelColor() {

        hue += 0.15;
        if (hue > 1.0) {
            hue -= 1.0;
        }

        personalHue = hue;

        red = hue + 0.33f;
        green = hue;
        blue = hue - 0.33f;

        red = RGBConvert(temp1, temp2, red);
        green = RGBConvert(temp1, temp2, green);
        blue = RGBConvert(temp1, temp2, blue);

        return new ColorRGBA(red, green, blue, alpha);
    }

    public ColorRGBA highlight(ColorRGBA color) {
        float highlight = 0.85f;
        float temp0 = (highlight + sat) - (highlight * sat);
        float temp = 2.0f * highlight - temp0;
        float newRed, newGreen, newBlue;

        newRed = personalHue + 0.33f;
        newGreen = personalHue;
        newBlue = personalHue - 0.33f;

        newRed = RGBConvert(temp, temp0, newRed);
        newGreen = RGBConvert(temp, temp0, newGreen);
        newBlue = RGBConvert(temp, temp0, newBlue);

        return new ColorRGBA(newRed, newGreen, newBlue, alpha);
    }

    private float adjustColor(float color) {
        if (color < 0) {
            return color++;
        } else if (color > 1) {
            return color--;
        } else {
            return color;
        }
    }

    private float RGBConvert(float temp1, float temp2, float color) {
        float temp3 = adjustColor(color);
        if (6.0 * temp3 < 1) {
            return temp1 + (temp2 - temp1) * 6.0f * temp3;
        } else if (2.0 * temp3 < 1) {
            return temp2;
        } else if (3.0 * temp3 < 2) {
            return temp1 + (temp2 - temp1) * ((2.0f / 3.0f) - temp3) * 6.0f;
        } else {
            return temp1;
        }
    }
}
