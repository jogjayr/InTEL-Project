/*
 * Copyright (c) 2005-2007 jME Physics 2
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jME Physics 2' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jmetest.physics;

import java.util.concurrent.*;

import com.jme.bounding.*;
import com.jme.input.*;
import com.jme.light.*;
import com.jme.math.*;
import com.jme.renderer.*;
import com.jme.scene.*;
import com.jme.scene.shape.*;
import com.jme.scene.state.*;
import com.jme.system.*;
import com.jme.util.*;
import com.jmex.game.*;
import com.jmex.game.state.*;
import com.jmex.physics.*;
import com.jmex.physics.callback.*;
import com.jmex.physics.util.states.*;

public class SetStateMarbleDemo implements MouseInputListener{
    public static int FORCE_MAX = 100;
    public static int MOUSE_MULT = 15;

    StandardPhysicsGame game = null;
    DynamicPhysicsNode sphereNode = null;

    public static void main(String[] args) {
        new SetStateMarbleDemo();
    }

    public SetStateMarbleDemo() {
        game = new StandardPhysicsGame("SetStateDemo");
        game.start();
        init();
    }

    private void init() {
        game.lock();
        BasicGameState floorState = new BasicGameState("floor");
        PhysicsNode floor = game.getPhysicsSpace().createStaticNode();
        float boxSize = 15f;
        Box tile = new Box("floor",
                new Vector3f(0,-3,0), boxSize, 0.2f, boxSize);
        tile.setModelBound(new BoundingBox());
        tile.updateModelBound();
        floor.attachChild(tile);
        floor.generatePhysicsGeometry();
        floorState.getRootNode().attachChild(floor);
        normaliseNode(floorState.getRootNode());
        floorState.setActive(true);
        GameStateManager.create().attachChild(floorState);

        createSphere();
        MouseInput.get().addListener(this);

        BasicGameState bumpState = new BasicGameState("BumpState");
        DynamicPhysicsNode bumpNode = createBox("BumpMeOnMyLeftSide");
        bumpNode.setLocalTranslation(new Vector3f(2,2,10));
        bumpState.getRootNode().attachChild(bumpNode);
        normaliseNode(bumpState.getRootNode());
        bumpState.setActive(true);
        GameStateManager.getInstance().attachChild(bumpState);

        BasicGameState dropState = new BasicGameState("DropState");
        final DynamicPhysicsNode dropNode = createBox("NewPhysicsSpace");
        dropNode.setLocalTranslation(new Vector3f(-2,2,10));

        dropState.getRootNode().attachChild(dropNode);
        normaliseNode(dropState.getRootNode());
        dropState.setActive(true);
        dropNode.setActive(false);

        GameStateManager.getInstance().attachChild(dropState);

        game.unlock();


        Thread PhysicsSwitch = new Thread() {
            public void run() {
                Thread t = Thread.currentThread();
                synchronized (t) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    }
                    catch (InterruptedException e) { } // who cares - its a demo
                }
                GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).enqueue(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
//                        dropNode.clearForce(); //WHY should I STILL need to clear the force (etc)... it was DISABLED
//                        dropNode.clearDynamics();
                        dropNode.setActive(true);
                        return true;
                    }
                });
            }
        };
        PhysicsSwitch.start();
    }

    private void normaliseNode(Node n) {
        ZBufferState zBuf = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
        zBuf.setEnabled(true);
        zBuf.setFunction(ZBufferState.CF_LEQUAL);
        n.setRenderState(zBuf);
        n.updateRenderState();

        DirectionalLight light = new DirectionalLight();
        light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        light.setAmbient(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        light.setDirection(new Vector3f(-0.3f, -0.8f, -0.3f));
        light.setEnabled(true);

/** Attach the light to a lightState and the lightState to rootNode. */
        LightState lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
        lightState.setEnabled(true);
        lightState.attach(light);

        n.setRenderState(lightState);
        n.updateRenderState();
    }

    private DynamicPhysicsNode createBox(String name) {
        float size = 0.45f;
        Geometry box = new Box("name", new Vector3f(), size, size, size);
        box.setModelBound(new BoundingBox());
        box.updateModelBound();
        DynamicPhysicsNode boxNode = game.physicsSpace.createDynamicNode();
        boxNode.attachChild(box);
        boxNode.setMass(1); //arbitrary
        boxNode.generatePhysicsGeometry();

        MaterialState boxMaterial = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();

        boxMaterial.setAmbient(new ColorRGBA(0f, 0f, 0f, 1f));
        boxMaterial.setEmissive(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));
        boxMaterial.setDiffuse(new ColorRGBA(0.6f, 0.3f, 0.5f, 1f));
        boxMaterial.setSpecular(new ColorRGBA(0.5f, 0.7f, 0.5f, 1f));
        boxMaterial.setShininess(70);

        boxNode.setRenderState(boxMaterial);
        boxNode.updateRenderState();
        return boxNode;
    }

    private void createSphere() {
        PhysicsGameState sphereState = new PhysicsGameState("SphereState");
        int detail = 10;
        Sphere s = new Sphere("Sphere", detail, detail, 0.20f); //RADIUS
// s.setDefaultColor(ColorRGBA.cyan);
        s.setModelBound(new BoundingSphere());
        s.updateModelBound();
        sphereNode = game.getPhysicsSpace().createDynamicNode();
        sphereNode.clearForce();
        sphereNode.clearTorque();
        sphereNode.setMass(1.5f); // kg?
        sphereNode.setLocalTranslation(new Vector3f(0,1,10));
        sphereNode.attachChild(s);
        sphereNode.generatePhysicsGeometry();
        sphereState.getRootNode().attachChild(sphereNode);
        normaliseNode(sphereState.getRootNode());

        MaterialState ms = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();

        ms.setAmbient(new ColorRGBA(0f, 0f, 0f, 1f));
        ms.setEmissive(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));
        ms.setDiffuse(new ColorRGBA(0.2f, 0.9f, 0.9f, 1f));
        ms.setSpecular(new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
        ms.setShininess(70);

        sphereNode.setRenderState(ms);
        sphereNode.updateRenderState();

        FrictionCallback fc = new FrictionCallback();
        game.physicsSpace.addToUpdateCallbacks(fc);
        fc.add(sphereNode, 0, 5);

        sphereState.setActive(true);
        GameStateManager.getInstance().attachChild(sphereState);
    }

    public void onButton(int button, boolean pressed, int x, int y) {
        Vector3f force = new Vector3f(0, 30 * MOUSE_MULT, 0);
        sphereNode.addForce(force);
    }

    public void onWheel(int wheelDelta, int x, int y) { }

    public void onMove(int xDelta, int yDelta, int newX, int newY) {
        System.out.println("onMove:" + xDelta + ":" + yDelta);
        Vector3f force = sphereNode.getForce(null);

        int xForce = MOUSE_MULT * xDelta;
        int zForce = MOUSE_MULT * -yDelta;
        xForce += (int)force.x;
        zForce += (int)force.z;

        if (xForce > FORCE_MAX) xForce = FORCE_MAX;
        else if (xForce < -FORCE_MAX) xForce = -FORCE_MAX;

        if (zForce > FORCE_MAX) zForce = FORCE_MAX;
        else if (zForce < -FORCE_MAX) zForce = -FORCE_MAX;

        xForce -= (int)force.x;
        zForce -= (int)force.y;

        force.x = xForce;
        force.z = zForce;
        sphereNode.clearForce();
        sphereNode.addForce(force);
    }


    class StandardPhysicsGame extends StandardGame {
        private PhysicsSpace physicsSpace = null;

        public StandardPhysicsGame(String name) {
            super(name);
            physicsSpace = PhysicsSpace.create();
        }

        public PhysicsSpace getPhysicsSpace() {
            return physicsSpace;
        }

        @Override
        protected void update(float interpolation) {
            super.update(interpolation);
            physicsSpace.update(interpolation);
        }

    }
}

/*
 * $Log$
 */

