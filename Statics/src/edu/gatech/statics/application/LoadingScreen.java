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
package edu.gatech.statics.application;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import edu.gatech.statics.objects.representations.PointRepresentation;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class LoadingScreen extends Node {

    /**
     * Constructor
     */
    public LoadingScreen() {

        String imagePath = "rsrc/logo.png";

        URL textureLoc = PointRepresentation.class.getClassLoader().getResource(imagePath);
        Texture pointTexture = TextureManager.loadTexture(textureLoc, Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);

        Quad pointQuad = new Quad("", .5f, .5f);
        TextureState textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        textureState.setTexture(pointTexture);
        pointQuad.setRenderState(textureState);
        pointQuad.setLightCombineMode(LightCombineMode.Off);
        pointQuad.setLocalTranslation(0, 0, 1);

        pointQuad.setLocalScale(new Vector3f(-3.0f, 3.0f, 1));
        attachChild(pointQuad);


        updateRenderState();
    }
}
