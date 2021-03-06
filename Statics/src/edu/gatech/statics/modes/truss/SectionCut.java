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
package edu.gatech.statics.modes.truss;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class SectionCut {

    private final Vector2f sectionStart;
    private final Vector2f sectionEnd;

    /**
     * Constructor
     * @param sectionStart Start of the section cut
     * @param sectionEnd End of the section cut
     */
    public SectionCut(Vector2f sectionStart, Vector2f sectionEnd) {
        this.sectionStart = new Vector2f(sectionStart);
        this.sectionEnd = new Vector2f(sectionEnd);
    }

    /**
     * Getter
     * @return
     */
    public Vector2f getSectionEnd() {
        return sectionEnd;
    }

    /**
     * Getter
     * @return
     */
    public Vector2f getSectionStart() {
        return sectionStart;
    }

    /**
     * Get the section end as a 3 dimensional vector
     * @return
     */
    public Vector3f getSectionEnd3d() {
        return StaticsApplication.getApp().getCamera().getWorldCoordinates(sectionEnd, 0.1f);
    }

    /**
     * Get the section start as a 3 dimensional vector
     * @return
     */
    public Vector3f getSectionStart3d() {
        return StaticsApplication.getApp().getCamera().getWorldCoordinates(sectionStart, 0.1f);
    }

    /**
     * Calculates which side of the cut the given point is on. Returns values of -1, or 1 indicating
     * which side of the line the point is on. May also return zero if the point is the same.
     * @return
     */
    public int getCutSide(Vector2f point) {
        Vector2f direction = sectionEnd.subtract(sectionStart);
        Vector2f perp = new Vector2f(direction.y, -direction.x);
        float distance = perp.dot(point.subtract(sectionStart));
        return (int) Math.signum(distance);
    }
}
