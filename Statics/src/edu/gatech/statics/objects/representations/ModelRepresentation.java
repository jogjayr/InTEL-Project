/*
 * ModelRepresentation.java
 *
 * Created on June 9, 2007, 3:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.util.TextureKey;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.AseToJme;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.MilkToJme;
import edu.gatech.newcollada.ColladaImporter;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class ModelRepresentation extends Representation {
    
    private Node modelNode;
    
    public void setModelOffset(Vector3f offset) {
        modelNode.setLocalTranslation(offset);
    }
    
    /** Creates a new instance of ModelRepresentation */
    public ModelRepresentation(SimulationObject target, String textureDirectory, String filename) {
        super(target);
        setLayer(RepresentationLayer.modelBodies);
        
        setUseWorldScale(false);
        
        URL fileUrl = getClass().getClassLoader().getResource(filename);
        URL textureUrl = getClass().getClassLoader().getResource(textureDirectory);
        
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        
        try {
            if(extension.equals("dae")) {
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
                System.out.println("ModelRepresentation: Unrecognized file format: "+extension);
            }
        
        } catch(IOException e) {
            System.out.println("IO error while loading file \""+filename+"\"");
        }
        
        if(modelNode != null) {
            attachChild(modelNode);
            updateRenderState();
        }
    }
    
}
