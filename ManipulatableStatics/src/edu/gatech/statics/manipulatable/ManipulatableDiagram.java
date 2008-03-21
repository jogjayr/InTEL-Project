/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.manipulatable;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.geometry.PhysicsCylinder;
import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableDiagram extends Diagram {

    private Map<Body, BodyInitialCondition> initialConditions = new HashMap<Body, ManipulatableDiagram.BodyInitialCondition>();
    private Map<Body, DynamicPhysicsNode> dynamicsMap = new HashMap<Body, DynamicPhysicsNode>();
    private boolean dynamicsSetup;
    private PhysicsSpace dynamics;

    public ManipulatableDiagram() {
        addAll(getSchematic().allObjects());

    }

    private class BodyInitialCondition {

        private Matrix3f rotation;
        private Vector3f translation;

        public BodyInitialCondition(Body body) {
            rotation = body.getRotation();
            translation = body.getTranslation();
        }

        public void apply(Body body) {
            body.setRotation(rotation);
            body.setTranslation(translation);
        }
    }

    void startDynamics() {
        if (dynamicsSetup) {
            return;
        }
        
        // add initial conditions
        for (Body body : getSchematic().allBodies()) {
            initialConditions.put(body, new BodyInitialCondition(body));
        }

        dynamicsSetup = true;
        dynamics = PhysicsSpace.create();
        dynamics.setDirectionalGravity(new Vector3f(0,-1,0));

        for (SimulationObject obj : allObjects()) {

            // THIS SHOULD BE REPLACED
            // Later, we will want each class to be able to create its own physics representation
            // here we just cheat.

            if (obj instanceof Body) {
                DynamicPhysicsNode node = dynamics.createDynamicNode();
                if (obj instanceof Beam) {
                    //Beam beam = (Beam) obj;
                    PhysicsCylinder pCylinder = node.createCylinder(null);
                    Representation beamRep = getDefaultRepresentation(obj);
                    pCylinder.setLocalScale(beamRep.getLocalScale());
                    pCylinder.setLocalRotation(obj.getRotation());
                    pCylinder.setLocalTranslation(obj.getTranslation());

                    dynamicsMap.put((Body) obj, node);
                }
            }

            if (obj instanceof edu.gatech.statics.objects.Joint) {
                edu.gatech.statics.objects.Joint staticsJoint = (edu.gatech.statics.objects.Joint) obj;
                Joint dynamicsJoint = dynamics.createJoint();
                dynamicsJoint.createRotationalAxis();
                
                // Hack this for now
                
                if(staticsJoint instanceof Pin2d) {
                    RotationalJointAxis axis = dynamicsJoint.createRotationalAxis();
                    axis.setDirection(Vector3f.UNIT_Z);
                } else if(staticsJoint instanceof Roller2d) {
                    TranslationalJointAxis axis = dynamicsJoint.createTranslationalAxis();
                    axis.setDirection(Vector3f.UNIT_X);
                }
                // otherwise it should be a fix, in which case, we leave alone.
                
                Body body1 = staticsJoint.getBody1();
                Body body2 = staticsJoint.getBody2();
                if (body1 == null) {
                    body1 = body2;
                    body2 = null;
                }
                if (body2 == null) {
                    DynamicPhysicsNode node1 = dynamicsMap.get(body1);
                    dynamicsJoint.attach(node1);
                } else {
                    DynamicPhysicsNode node1 = dynamicsMap.get(body1);
                    DynamicPhysicsNode node2 = dynamicsMap.get(body2);
                    dynamicsJoint.attach(node1, node2);
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        updateDynamics();
    }
    
    private Representation getDefaultRepresentation(SimulationObject obj) {
        List<Representation> reps = obj.getRepresentation(RepresentationLayer.schematicBodies);
        if (reps.isEmpty()) {
            return null;
        } else {
            return reps.get(0);
        }
    }

    private void updateDynamics() {
        if (!dynamicsSetup) {
            return;
        }
        
        dynamics.update(.03f);
        
        for (Map.Entry<Body, DynamicPhysicsNode> entry : dynamicsMap.entrySet()) {
            Body body = entry.getKey();
            DynamicPhysicsNode dynamicBody = entry.getValue();
            body.setTranslation(dynamicBody.getLocalTranslation());
            body.setRotation(dynamicBody.getLocalRotation().toRotationMatrix());
        }

    }

    @Override
    public void render(Renderer r) {
        super.render(r);
        
        if(dynamicsSetup) {
            PhysicsDebugger.drawPhysics( dynamics, r );
        }
    }
    
    void stopDynamics() {
        if (!dynamicsSetup) {
            return;
        }

        dynamicsSetup = false;
        dynamics.delete();
        dynamics = null;

        // apply the initial conditions
        for (Body body : allBodies()) {
            initialConditions.get(body).apply(body);
        }
        
        dynamicsMap.clear();
        initialConditions.clear();
    }

    @Override
    public Mode getMode() {
        return ManipulatableMode.instance;
    }
}
