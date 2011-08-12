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
package edu.gatech.statics.objects.representations;

import com.jme.light.Light;
import com.jme.light.LightNode;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.resource.ResourceLocatorTool;
import com.jme.util.resource.SimpleResourceLocator;
//import com.jmex.model.collada.ColladaImporter;
import edu.gatech.newcollada.ColladaImporter;
import com.jmex.model.converters.AseToJme;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.MilkToJme;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.SimulationObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Calvin Ashmore
 */
public class ModelNode {

    private Node rootNode;
    private List<Light> lights;

    private ModelNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Creates a model representation representing everything that was not extracted
     * as individual elements. This can be assigned to the background.
     * @return
     */
    public ModelRepresentation getRemainder(SimulationObject target) {
        return new ModelRepresentation(target, rootNode);
    }

    /**
     * extracts a node denoted by the elementPath, and removes that from the ModelNode.
     * The element path should have the form of names separated by slashes indicating a 
     * path into the node tree.
     * @param elementPath
     * @return
     * @throws NullPointerException if the path is invalid.
     */
    public ModelRepresentation extractElement(SimulationObject target, String elementPath) {
        String[] path = elementPath.split("/");

        Node nextNode = rootNode;
        for (int i = 0; i < path.length; i++) {
            if (nextNode.getChild(path[i]) == null) {
                StaticsApplication.logger.info("Cannot find element: \"" + path[i] + "\" within path " + elementPath);
                StaticsApplication.logger.info("There do exist siblings " + nextNode.getChildren() + " at that level, though.");
                throw new NullPointerException();
            }
            nextNode = (Node) nextNode.getChild(path[i]);
        }

        // remove the child from the parent
        Node parent = nextNode.getParent();
        parent.detachChild(nextNode);

        return new ModelRepresentation(target, nextNode);
    }

    /**
     * Extracts the lights present in the model and puts them in the light state 
     * for the model representation layer.
     * @return
     */
    public void extractLights() {
        applyLights(RepresentationLayer.modelBodies);
        applyLights(RepresentationLayer.schematicBodies);
    }

    private void applyLights(RepresentationLayer layer) {
        if (lights.isEmpty()) {
            return;
        }
        LightState lightState = layer.getLightState();
        lightState.detachAll();

        for (Light light : lights) {
            lightState.attach(light);
        }
    }

    public static ModelNode load(String textureDirectory, String filename) {
        return load(
                ModelRepresentation.class.getClassLoader().getResource(textureDirectory),
                ModelRepresentation.class.getClassLoader().getResource(filename));
    }
    private static boolean textureLocatorAdded = false;

    public static ModelNode load(URL textureUrl, URL fileUrl) {

        // attempt to assign a resource locator for textures.
        // This should get rid of some of the error messages in the bridge problem.
        if (!textureLocatorAdded) {
            textureLocatorAdded = true;

            try {
                SimpleResourceLocator locator = new SimpleResourceLocator(textureUrl.toURI());
                ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, locator);
            } catch (URISyntaxException ex) {
                StaticsApplication.logger.log(Level.SEVERE, null, ex);
            }
        }

        Node rootNode = null;
        String filename = fileUrl.toString();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        List<Light> lights = new ArrayList<Light>();

        try {
            if (extension.equals("dae")) {
                //ColladaImporter.load(fileUrl.openStream(), "model");
                ColladaImporter.load(fileUrl.openStream(), textureUrl, "model");
                rootNode = ColladaImporter.getModel();

                if (ColladaImporter.getLightNodeNames() != null) {
                    for (String name : ColladaImporter.getLightNodeNames()) {
                        LightNode ln = ColladaImporter.getLightNode(name);
                        Light light = ln.getLight();
                        lights.add(light);
                    }
                }

                ColladaImporter.cleanUp();

            } else if (extension.equals("ms3d")) {
                MilkToJme converter = new MilkToJme();
                ByteArrayOutputStream BO = new ByteArrayOutputStream();
                converter.convert(fileUrl.openStream(), BO);
                //TextureKey.setOverridingLocation(textureUrl);
                rootNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));

            } else if (extension.equals("max")) {
                MaxToJme converter = new MaxToJme();
                ByteArrayOutputStream BO = new ByteArrayOutputStream();
                converter.convert(new BufferedInputStream(fileUrl.openStream()), BO);
                //TextureKey.setOverridingLocation(textureUrl);
                rootNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));

            } else if (extension.equals("ase")) {
                AseToJme converter = new AseToJme();
                ByteArrayOutputStream BO = new ByteArrayOutputStream();
                converter.convert(new BufferedInputStream(fileUrl.openStream()), BO);
                //TextureKey.setOverridingLocation(textureUrl);
                rootNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));

            } else {
                // ???
                StaticsApplication.logger.warning("ModelRepresentation: Unrecognized file format: " + extension);
            }

        } catch (IOException e) {
            StaticsApplication.logger.log(Level.SEVERE, "IO error while loading file \"" + filename + "\"", e);
        }
        if (rootNode != null) {
            ModelNode modelNode = new ModelNode(rootNode);
            modelNode.lights = lights;
            return modelNode;
        }
        return null;
    }
}
