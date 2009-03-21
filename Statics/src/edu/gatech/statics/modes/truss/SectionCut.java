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

    public SectionCut(Vector2f sectionStart, Vector2f sectionEnd) {
        this.sectionStart = new Vector2f(sectionStart);
        this.sectionEnd = new Vector2f(sectionEnd);
    }

    public Vector2f getSectionEnd() {
        return sectionEnd;
    }

    public Vector2f getSectionStart() {
        return sectionStart;
    }

    public Vector3f getSectionEnd3d() {
        return StaticsApplication.getApp().getCamera().getWorldCoordinates(sectionEnd, 0.1f);
    }

    public Vector3f getSectionStart3d() {
        return StaticsApplication.getApp().getCamera().getWorldCoordinates(sectionStart, 0.1f);
    }
}
