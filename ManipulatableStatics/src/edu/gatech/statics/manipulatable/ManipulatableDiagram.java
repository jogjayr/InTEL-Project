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
import com.jmex.physics.impl.ode.DynamicPhysicsNodeImpl;
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

    private class MomentTracker {

        public Vector3f value;
        public DynamicPhysicsNode node;
    }

    private class ForceTracker {

        public Vector3f value,  at;
        public DynamicPhysicsNode node;
    }
    private List<ForceTracker> forceRoster = new ArrayList<ManipulatableDiagram.ForceTracker>();
    private List<MomentTracker> momentRoster = new ArrayList<ManipulatableDiagram.MomentTracker>();

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
        //dynamics.setDirectionalGravity(new Vector3f(0, 0, 0));

        for (SimulationObject obj : allObjects()) {

            // THIS SHOULD BE REPLACED
            // Later, we will want each class to be able to create its own physics representation
            // here we just cheat.

            if (obj instanceof Body) {

                List<Load> loads = new ArrayList<Load>();
                for (SimulationObject attached : ((Body) obj).getAttachedObjects()) {
                    if (attached instanceof Load) {
                        loads.add((Load) attached);
                    }
                }

                if (obj instanceof Beam) {
                    DynamicPhysicsNode node = dynamics.createDynamicNode();
                    Beam beam = (Beam) obj;
                    PhysicsCylinder pCylinder = node.createCylinder(null);

                    //pCylinder.setLocalRotation(beam.getRotation());
                    //pCylinder.setLocalTranslation(beam.getTranslation());
                    //pCylinder.setLocalScale(new Vector3f(.03f, .03f, beam.getHeight()));
                    node.setMass(beam.getWeight().doubleValue());
                    node.setCenterOfMass(beam.getTranslation());

                    node.setLocalRotation(new Matrix3f(beam.getRotation()));
                    node.setLocalTranslation(new Vector3f(beam.getTranslation()));
                    node.setLocalScale(new Vector3f(.03f, .03f, beam.getHeight()));

                    dynamicsMap.put(beam, node);

                    for (Load load : loads) {
                        Vector3f value = new Vector3f(load.getVectorValue().mult(load.doubleValue()));
                        //value.multLocal(100f);
                        Vector3f at = load.getAnchor().getTranslation();

                        if (load instanceof Force) {
                            //node.addForce(value, at);
                            ForceTracker ft = new ForceTracker();
                            ft.at = at;
                            ft.value = value;
                            ft.node = node;
                            forceRoster.add(ft);
                        } else if (load instanceof Moment) {
                            //node.addTo
                            MomentTracker mt = new MomentTracker();
                            mt.value = value;
                            mt.node = node;
                            momentRoster.add(mt);
                        }
                    }
                }
            }
        }

        for (SimulationObject obj : allObjects()) {

            if (obj instanceof edu.gatech.statics.objects.Joint) {
                edu.gatech.statics.objects.Joint staticsJoint = (edu.gatech.statics.objects.Joint) obj;

                // Hack this for now

                if (staticsJoint instanceof Pin2d) {
                    Joint dynamicsJoint = dynamics.createJoint();
                    RotationalJointAxis axis = dynamicsJoint.createRotationalAxis();
                    axis.setDirection(Vector3f.UNIT_Z);
                    attachRegularJoint(dynamicsJoint, staticsJoint);

                } else if (staticsJoint instanceof Roller2d) {
                    // a roller, in this case, is a combination of two different joints
                    // 1) a translational joint tha permits sliding, and
                    // 2) a rotational joint that permits rotation
                    // the rotating joint attaches the body to a middle node
                    // which is attached to the 

                    Joint dynamicsJoint1 = dynamics.createJoint();
                    Joint dynamicsJoint2 = dynamics.createJoint();
                    dynamicsJoint2.setAnchor(staticsJoint.getTranslation());

                    DynamicPhysicsNode intermediate = dynamics.createDynamicNode();
                    intermediate.setMass(.1f);

                    intermediate.setLocalTranslation(new Vector3f(staticsJoint.getTranslation()));

                    RotationalJointAxis rotationalAxis = dynamicsJoint1.createRotationalAxis();
                    rotationalAxis.setDirection(Vector3f.UNIT_Z);

                    TranslationalJointAxis translationalAxis = dynamicsJoint2.createTranslationalAxis();
                    //translationalAxis.setRelativeToSecondObject(true);
                    translationalAxis.setDirection(Vector3f.UNIT_X);

                    //attachRegularJoint(dynamicsJoint1, staticsJoint);
                    attachRollerJoint(dynamicsJoint1, dynamicsJoint2, intermediate, staticsJoint);

                } else {
                    // otherwise it should be a fix, in which case, we leave alone.
                    Joint dynamicsJoint = dynamics.createJoint();
                    attachRegularJoint(dynamicsJoint, staticsJoint);
                }

            }
        }
    }

    private void attachRegularJoint(Joint dynamicsJoint, edu.gatech.statics.objects.Joint staticsJoint) {

        Body body1 = staticsJoint.getBody1();
        Body body2 = staticsJoint.getBody2();
        if (body1 == null) {
            body1 = body2;
            body2 = null;
        }
        if (body2 == null) {
            dynamicsJoint.setAnchor(staticsJoint.getTranslation());
            DynamicPhysicsNode node1 = dynamicsMap.get(body1);
            dynamicsJoint.attach(node1);
        } else {
            
            
            dynamicsJoint.setAnchor(staticsJoint.getTranslation().subtract(body1.getTranslation()));
            DynamicPhysicsNode node1 = dynamicsMap.get(body1);
            DynamicPhysicsNode node2 = dynamicsMap.get(body2);
            dynamicsJoint.attach(node1, node2);
        }
    }

    private void attachRollerJoint(Joint joint1, Joint joint2, DynamicPhysicsNode intermediate, edu.gatech.statics.objects.Joint staticsJoint) {

        Body body1 = staticsJoint.getBody1();
        Body body2 = staticsJoint.getBody2();
        if (body1 == null) {
            body1 = body2;
            body2 = null;
        }
        if (body2 == null) {
            DynamicPhysicsNode node1 = dynamicsMap.get(body1);
            //joint1.attach(node1, intermediate);
            joint1.attach(intermediate, node1);
            joint2.attach(intermediate);
        } else {
            DynamicPhysicsNode node1 = dynamicsMap.get(body1);
            DynamicPhysicsNode node2 = dynamicsMap.get(body2);
            joint1.attach(node1, intermediate);
            joint2.attach(intermediate, node2);
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
    private static final int number_steps = 1;
    private static final float step_size = .008f;
    private static final float force_multiplier = 101.7f;

    private void updateDynamics() {
        if (!dynamicsSetup) {
            return;
        }

        //for (int i = 0; i < number_steps; i++) {
        float timestep = step_size / number_steps;
        for (ForceTracker ft : forceRoster) {
            //ft.node.addForce(ft.value.mult(step_size / number_steps), ft.at.subtract(ft.node.getLocalTranslation()));
            ((DynamicPhysicsNodeImpl) ft.node).getBody().addForceAtPos(
                    ft.value.x * force_multiplier * timestep, ft.value.y * force_multiplier * timestep, ft.value.z * force_multiplier * timestep,
                    ft.at.x, ft.at.y, ft.at.z);

            Vector3f v = new Vector3f();
            ft.node.getForce(v);
        }
        for (MomentTracker mt : momentRoster) {
            mt.node.addTorque(mt.value.mult(timestep));
        }

        dynamics.update(timestep);
        //}

        for (Map.Entry<Body, DynamicPhysicsNode> entry : dynamicsMap.entrySet()) {
            Body body = entry.getKey();
            DynamicPhysicsNode node = entry.getValue();
            Spatial dynamicBody = node;//.getChild(0);

            body.setTranslation(dynamicBody.getWorldTranslation().clone());
            body.setRotation(dynamicBody.getWorldRotation().toRotationMatrix());
        }

    }

    @Override
    public void render(Renderer r) {
        super.render(r);

        if (dynamicsSetup) {
            //PhysicsDebugger.drawPhysics(dynamics, r);
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

        forceRoster.clear();
        momentRoster.clear();

        dynamicsMap.clear();
        initialConditions.clear();
    }

    @Override
    public Mode getMode() {
        return ManipulatableMode.instance;
    }
}
