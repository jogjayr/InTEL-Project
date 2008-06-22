/*
 * ModelRepresentation.java
 *
 * Created on June 9, 2007, 3:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.AseToJme;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.MilkToJme;
import edu.gatech.newcollada.ColladaImporter;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class ModelRepresentation extends Representation {

    private Node modelNode;

    public void setModelOffset(Vector3f offset) {
        modelNode.setLocalTranslation(offset);
    }

    public void setModelRotation(Matrix3f rotation) {
        modelNode.setLocalRotation(rotation);
    }

    ModelRepresentation(SimulationObject target, Node modelNode) {
        super(target);
        setLayer(RepresentationLayer.modelBodies);
        this.modelNode = modelNode;

        getRelativeNode().attachChild(modelNode);
        updateRenderState();
    }

    /*
    public ModelRepresentation(SimulationObject target, String textureDirectory, String filename) {
    this(target,
    ModelRepresentation.class.getClassLoader().getResource(textureDirectory),
    ModelRepresentation.class.getClassLoader().getResource(filename));
    }
    
    public ModelRepresentation(SimulationObject target, URL textureUrl, URL fileUrl) {
    super(target);
    setLayer(RepresentationLayer.modelBodies);
    
    //setUseWorldScale(false);
    
    String filename = fileUrl.toString();
    String extension = filename.substring(filename.lastIndexOf(".") + 1);
    
    try {
    if (extension.equals("dae")) {
    ColladaImporter.load(fileUrl.openStream(), textureUrl, "model");
    modelNode = ColladaImporter.getModel();
    ColladaImporter.cleanUp();
    
    } else if (extension.equals("ms3d")) {
    MilkToJme converter = new MilkToJme();
    ByteArrayOutputStream BO = new ByteArrayOutputStream();
    converter.convert(fileUrl.openStream(), BO);
    //TextureKey.setOverridingLocation(textureUrl);
    modelNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
    
    } else if (extension.equals("max")) {
    MaxToJme converter = new MaxToJme();
    ByteArrayOutputStream BO = new ByteArrayOutputStream();
    converter.convert(new BufferedInputStream(fileUrl.openStream()), BO);
    //TextureKey.setOverridingLocation(textureUrl);
    modelNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
    
    } else if (extension.equals("ase")) {
    AseToJme converter = new AseToJme();
    ByteArrayOutputStream BO = new ByteArrayOutputStream();
    converter.convert(new BufferedInputStream(fileUrl.openStream()), BO);
    //TextureKey.setOverridingLocation(textureUrl);
    modelNode = (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
    
    } else {
    // ???
    Logger.getLogger("Statics").warning("ModelRepresentation: Unrecognized file format: " + extension);
    }
    
    } catch (IOException e) {
    Logger.getLogger("Statics").log(Level.SEVERE, "IO error while loading file \"" + filename + "\"", e);
    }
    
    if (modelNode != null) {
    getRelativeNode().attachChild(modelNode);
    updateRenderState();
    }
    }*/
}
