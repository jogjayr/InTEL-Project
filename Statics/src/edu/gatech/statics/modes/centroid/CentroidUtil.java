/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import com.jme.renderer.ColorRGBA;
import java.util.Random;

/**
 * Utility class for anything that makes sense to put in here. Currently there
 * is a non-implemented method for unshading the CentroidPartObjects that have
 * been solved. This is unimplemented because I have not gotten to the polishing
 * of the UI yet but would eventually unshade parts of the centroid body as
 * their individual centroids are solved.
 * @author Jimmy Truesdell
 */
public class CentroidUtil {

    Random rand = new Random();

    public CentroidUtil() {
    }

    public ColorRGBA generatePastelColor() {
        float hue = rand.nextFloat();
        float sat = 0.86f;
        float light = 0.70f;
        float temp1, temp2;
        float red, green, blue;
        temp2 = (light + sat) - (light * sat);
        temp1 = 2.0f * light - temp2;

        red = hue + 0.33f;
        green = hue;
        blue = hue - 0.33f;

        red = RGBConvert(temp1, temp2, red);
        green = RGBConvert(temp1, temp2, green);
        blue = RGBConvert(temp1, temp2, blue);

        return new ColorRGBA(red, green, blue, 1.0f);
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
