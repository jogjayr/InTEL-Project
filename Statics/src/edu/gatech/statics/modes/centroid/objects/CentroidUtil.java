/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.renderer.ColorRGBA;

/**
 * Utility class for anything that makes sense to put in here. Currently it is
 * used for automatically generating the color for each CentroidPartRepresentation.
 * For an explanation for my HSL to RGB conversion please look here:
 * http://130.113.54.154/~monger/hsl-rgb.html
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
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

    /**
     * Generates a new color by adding 0.15 to hue, and setting red = hue+0.33f, green=hue, blue=hue-0.33f
     * @return Generated color
     */
    public ColorRGBA generatePastelColor() {

        //adds a value of 0.15 to the hue, maxing out at 1.0.
        //This allows for 21 unique colors.
        hue += 0.15;
        if (hue > 1.0) {
            hue -= 1.0;
        }

        //since we have made hue static this is needed to keep each region unique
        personalHue = hue;

        red = hue + 0.33f;
        green = hue;
        blue = hue - 0.33f;

        red = RGBConvert(temp1, temp2, red);
        green = RGBConvert(temp1, temp2, green);
        blue = RGBConvert(temp1, temp2, blue);

        return new ColorRGBA(red, green, blue, alpha);
    }

    /**
     * Recreates a new color based on the same hue and saturation while shifting
     * the light up slightly. This is for onClick highlighting.
     * @param color
     * @return
     */
    public ColorRGBA highlight() {
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

    /**
     * This is to keep the color in the range of 0.0 and 1.0
     */
    private float adjustColor(float color) {
        if (color < 0) {
            return color++;
        } else if (color > 1) {
            return color--;
        } else {
            return color;
        }
    }

    /**
     * Converts our derived HSL value into either the red, green, or blue component
     * based on the color passed into the method.
     * @param temp1
     * @param temp2
     * @param color
     * @return
     */
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

    /**
     * Color with which to highlight centroid part
     * @return
     */
    ColorRGBA hoverHighlight() {
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
}
