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
package edu.gatech.statics.ui.windows.navigation;

/**
 * This defines the state of a view that a user may control
 * It represents the panning and zooming that the user may freely control, within
 * the view constraints.
 * @author Calvin Ashmore
 */
public class ViewUserState {

    private float xPos,  yPos, zPos;
    private float yaw,  pitch; // yaw corresponds to horizontal rotation, pitch to vertical
    private float zoom = 1;

    @Override
    public String toString() {
        //return super.toString();
        return "ViewUserState: {xPos=" + xPos + ", yPos=" + yPos + ", zPos=" + zPos + ", yaw=" + yaw + ", pitch=" + pitch + ", zoom=" + zoom + "}";
    }

    public ViewUserState() {
    }

    public ViewUserState(ViewUserState other) {
        this.xPos = other.xPos;
        this.yPos = other.yPos;
        this.zPos = other.zPos;
        this.yaw = other.yaw;
        this.pitch = other.pitch;
        this.zoom = other.zoom;
    }

    /**
     * Interpolates this state between state 0 and 1 with t ranging between [0,1]
     * @param state0
     * @param state1
     * @param t
     */
    public void interpolate(ViewUserState state0, ViewUserState state1, float t) {
        this.xPos = state1.xPos * t + state0.xPos * (1 - t);
        this.yPos = state1.yPos * t + state0.yPos * (1 - t);
        this.zPos = state1.zPos * t + state0.zPos * (1 - t);
        this.yaw = state1.yaw * t + state0.yaw * (1 - t);
        this.pitch = state1.pitch * t + state0.pitch * (1 - t);
        this.zoom = state1.zoom * t + state0.zoom * (1 - t);
    }

    public void incrementPitch(float d) {
        pitch += d;
    }

    public void incrementYaw(float d) {
        yaw += d;
    }

    public void incrementZoom(float d) {
        zoom += d;
    }

    public void incrementXPos(float d) {
        xPos += d;
    }

    public void incrementYPos(float d) {
        yPos += d;
    }

     public void incrementZPos(float d) {
        zPos += d;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

     public float getZPos() {
        return zPos;
    }

    public void setZPos(float yPos) {
        this.zPos = yPos;
    }
    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
