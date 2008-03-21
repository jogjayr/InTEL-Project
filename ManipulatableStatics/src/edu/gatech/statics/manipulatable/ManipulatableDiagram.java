/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.manipulatable;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.geometry.PhysicsCylinder;
import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.joints.Pin2d;
import edu.gatech.statics.objects.joints.Roller2d;
import java.util.ArrayList;
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

    //private Node dynamicsRootNode;
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
        //dynamicsRootNode = new Node("Dynamics Root");
        dynamics = PhysicsSpace.create();
        dynamics.setDirectionalGravity(new Vector3f(0, 0, 0));

        for (SimulationObject obj : allObjects()) {

            // THIS SHOULD BE REPLACED
            // Later, we will want each class to be able to create its own physics representation
            // here we just cheat.

            if (obj instanceof Body) {
                
                List<Load> loads = new ArrayList<Load>();
                for(SimulationObject attached : ((Body)obj).getAttachedObjects()) {
                    if(attached instanceof Load) {
                        loads.add((Load)attached);
                    }
                }
                
                if (obj instanceof Beam) {
                    DynamicPhysicsNode node = dynamics.createDynamicNode();
                    Beam beam = (Beam) obj;
                    PhysicsCylinder pCylinder = node.createCylinder(null);

                    pCylinder.setLocalRotation(beam.getRotation());
                    pCylinder.setLocalTranslation(beam.getTranslation());
                    pCylinder.setLocalScale(new Vector3f(.3f, .3f, beam.getHeight()));

                    dynamicsMap.put(beam, node);
                    
                    for(Load load : loads) {
                        Vector3f value = new Vector3f(load.getVectorValue());
                        value.multLocal(100f);
                        Vector3f at = load.getAnchor().getTranslation();
                        
                        if(load instanceof Force) {
                            node.addForce(value, at);
                        } else if(load instanceof Moment) {
                            //node.addTo
                        }
                    }
                }
            }
        }
        
        for (SimulationObject obj : allObjects()) {

            if (obj instanceof edu.gatech.statics.objects.Joint) {
                edu.gatech.statics.objects.Joint staticsJoint = (edu.gatech.statics.objects.Joint) obj;
                Joint dynamicsJoint = dynamics.createJoint();
                dynamicsJoint.setAnchor(staticsJoint.getTranslation());

                // Hack this for now

                if (staticsJoint instanceof Pin2d) {
                    RotationalJointAxis axis = dynamicsJoint.createRotationalAxis();
                    axis.setDirection(Vector3f.UNIT_Z);
                } else if (staticsJoint instanceof Roller2d) {
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

    /*private Representation getDefaultRepresentation(SimulationObject obj) {
    List<Representation> reps = obj.getRepresentation(RepresentationLayer.schematicBodies);
    if (reps.isEmpty()) {
    return null;
    } else {
    return reps.get(0);
    }
    }*/
    private void updateDynamics() {
        if (!dynamicsSetup) {
            return;
        }

        dynamics.update(.03f);

        for (Map.Entry<Body, DynamicPhysicsNode> entry : dynamicsMap.entrySet()) {
            Body body = entry.getKey();
            DynamicPhysicsNode node = entry.getValue();
            Spatial dynamicBody = node.getChild(0);

            body.setTranslation(dynamicBody.getWorldTranslation());
            body.setRotation(dynamicBody.getWorldRotation().toRotationMatrix());
        }

    }

    @Override
    public void render(Renderer r) {
        super.render(r);

        if (dynamicsSetup) {
            PhysicsDebugger.drawPhysics(dynamics, r);
        }
    }

    void stopDynamics() {
        if (!dynamicsSetup) {
            return;
        }

        dynamicsSetup = false;
        dynamics.delete();
        dynamics = null;
        //dynamicsRootNode = null;

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
