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
 *
 * @author Calvin Ashmore
 */
public class ViewConstraints {

    private float xposMax, yposMax;
    private float xposMin, yposMin;
    private float yawMax, pitchMax;
    private float yawMin, pitchMin;
    private float zoomMax = 1;
    private float zoomMin = 1;
    
    public void setPositionConstraints(
            float xposMin, float xposMax,
            float yposMin, float yposMax) {
        this.xposMax = xposMax;
        this.xposMin = xposMin;
        this.yposMax = yposMax;
        this.yposMin = yposMin;
    }
    
    public void setRotationConstraints(
            float yawMin, float yawMax) {
        this.yawMax = yawMax;
        this.yawMin = yawMin;
        this.pitchMax = 0;
        this.pitchMin = 0;
    }
    
    public void setRotationConstraints(
            float yawMin, float yawMax,
            float pitchMin, float pitchMax) {
        this.yawMax = yawMax;
        this.yawMin = yawMin;
        this.pitchMax = pitchMax;
        this.pitchMin = pitchMin;
    }
    
    public void setZoomConstraints(
            float zoomMin, float zoomMax) {
        this.zoomMax = zoomMax;
        this.zoomMin = zoomMin;
    }

    public void constrain(ViewUserState state) {
        if(state.getPitch() < pitchMin)
            state.setPitch(pitchMin);
        if(state.getPitch() > pitchMax)
            state.setPitch(pitchMax);
        
        if(state.getYaw() < yawMin)
            state.setYaw(yawMin);
        if(state.getYaw() > yawMax)
            state.setYaw(yawMax);
        
        if(state.getZoom() < zoomMin)
            state.setZoom(zoomMin);
        if(state.getZoom() > zoomMax)
            state.setZoom(zoomMax);
        
        if(state.getXPos() < xposMin)
            state.setXPos(xposMin);
        if(state.getXPos() > xposMax)
            state.setXPos(xposMax);
        
        if(state.getYPos() < yposMin)
            state.setYPos(yposMin);
        if(state.getYPos() > yposMax)
            state.setYPos(yposMax);
        
    }
}
